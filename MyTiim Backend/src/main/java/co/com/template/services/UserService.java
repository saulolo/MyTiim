package co.com.template.services;


import co.com.template.Repositories.GroupRepository;
import co.com.template.Repositories.RollRepository;
import co.com.template.Repositories.StatusRepository;
import co.com.template.Repositories.dto.*;
import co.com.template.Repositories.UserRepository;
import co.com.template.Repositories.entities.Group;
import co.com.template.Repositories.entities.Roll;
import co.com.template.Repositories.entities.Status;
import co.com.template.Repositories.entities.User;
import co.com.template.exception.CustomException;
import co.com.template.utils.Constants;
import co.com.template.utils.EmailServiceImpl;
import co.com.template.utils.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.com.template.security.UtilsToken;
import org.springframework.http.HttpHeaders;


import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    private final StatusRepository statusRepository;

    private final RollRepository rollRepository;

    private final ConfigurationSystemService configurationService;

    private final EmailServiceImpl emailService;

    private final UtilsToken utilsToken;



    public ResponseDTO getUserForGroup(Long groupId) {
        List<User> list = userRepository.findByGroupGroupId(groupId);
        List<ObjectiveUserDTO> dtoList = new ArrayList<>();
        for (User user : list) {
            ObjectiveUserDTO objectiveUserDTO = new ObjectiveUserDTO();
            objectiveUserDTO.setUserId(user.getUserId());
            objectiveUserDTO.setUserName(user.getUserName());
            objectiveUserDTO.setUserLastName(user.getUserLastName());
            dtoList.add(objectiveUserDTO);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, dtoList);
    }


    public ResponseDTO getUser() {
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, userRepository.findAll());
    }

    public ResponseDTO getUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException(Constants.USER_NOT_EXISTS_ERROR, HttpStatus.OK);
        }
        return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, userRepository.findByUserId(userId));
    }


    public ResponseDTO getUsersList(int page, int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());
            Page<User> userPage = userRepository.findAll(pageable);

            List<UserListDTO> userListDTOS = userPage.getContent().stream()
                    .map(UserListDTO::new)
                    .collect(Collectors.toList());

            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, userListDTOS);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_RETRIEVING_USER_LIST, null);
        }
    }

    public ResponseDTO getListActiveUser() {
        List<User> user = userRepository.findByStatusStatusId(StatusEnum.ACTIVE_USER.getId());
        List<ViewListUserDTO> viewListUserDTO = new ArrayList<>();

        for (User u: user){
            ViewListUserDTO users = new ViewListUserDTO();
            users.setUserId(u.getUserId());
            users.setUserName(u.getUserName());
            users.setUserLastName(u.getUserLastName());
            users.setStatusId(u.getStatus().getStatusId());
            viewListUserDTO.add(users);


        }
            return new ResponseDTO(HttpStatus.OK,Constants.EMPTY_MESSAGE, viewListUserDTO);

    }

    public ResponseDTO createUser(CreateUserDTO createUserDTO) {
        try {
            if (userRepository.existsByUserEmail(createUserDTO.getUserEmail())){
                return new ResponseDTO(HttpStatus.BAD_REQUEST, Constants.USER_ALREADY_EXISTS_WITH_THAT_EMAIL_ERROR, null);
            }

            if (userRepository.existsByUser(createUserDTO.getUser())) {
                return new ResponseDTO(HttpStatus.BAD_REQUEST, Constants.USER_ALREADY_EXISTS_WITH_THAT_USERNAME_ERROR, null);
            }

            Status status = statusRepository.findByStatusId(createUserDTO.getStatusId());
            if (status == null) {
                return new ResponseDTO(HttpStatus.BAD_REQUEST, Constants.INVALID_STATUS, null);
            }

            User newUser = new User();

            newUser.setUserName(createUserDTO.getUserName());
            newUser.setUserLastName(createUserDTO.getUserLastName());
            newUser.setUserEmail(createUserDTO.getUserEmail());
            newUser.setUserPhone(createUserDTO.getUserPhone());
            newUser.setStatus(status);
            newUser.setLeaderId(createUserDTO.getLeaderId());
            newUser.setCreateDate(createUserDTO.createDate);

            String userEmail = createUserDTO.getUserEmail();
            String userPrefix = userEmail.split(Constants.AT_VALUE)[Constants.ZERO_INDEX];
            newUser.setUser(userPrefix);
            newUser.setUserPassword(userPrefix);
            newUser.setUserProfileId(createUserDTO.getProfileId());


            Set<Roll> selectedRoles = new HashSet<>();
            for (Long roleId : createUserDTO.getRoles()) {
                Roll roll = rollRepository.findByRollId(roleId);
                if (roll != null) {
                    selectedRoles.add(roll);
                }
            }
            newUser.setRolls(selectedRoles);


            Set<Group> selectedGroups = new HashSet<>();
            for (Long groupId : createUserDTO.getGroups()) {
                Group group = groupRepository.findByGroupId(groupId);
                if (group != null) {
                    selectedGroups.add(group);
                }
            }
            newUser.setGroups(selectedGroups);

            if (newUser.getGroups().isEmpty()) {
                return new ResponseDTO(HttpStatus.BAD_REQUEST, Constants.GROUP_REQUIRED, null);
            }

            userRepository.save(newUser);

            Map<String, Object> data = new HashMap<>();
            data.put(Constants.EMAIL_NAME, newUser.getUser());
            data.put(Constants.EMAIL_PASSWORD, newUser.getUserPassword());
            data.put(Constants.EMAIL_IMAGE_URL, configurationService.getConfigValue(Constants.IMAGE_URL_SYSTEM));
            data.put(Constants.EMAIL_URL, configurationService.getConfigValue(Constants.URL_SYSTEM)+Constants.EMAIL_USER_ACTIVE_URL);
            emailService.sendMail(data, createUserDTO.getUserEmail(), Constants.SUBJECT_MESSAGE_USER , Constants.INDEX_TEMPLATE_USER,null);

            return new ResponseDTO(HttpStatus.OK, Constants.USER_CREATED_SUCCESSFULLY, true);
        } catch (Exception e) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST,Constants.ERROR_CREATING_USER + e.getMessage(), null);
        }
    }

    public ResponseDTO getActiveUsersExceptCurrentUser(HttpHeaders headers) {
        try{
            Long userId = utilsToken.getUserIdFromToken(headers);
            if( Objects.isNull(userId))
                return new ResponseDTO(HttpStatus.BAD_REQUEST,Constants.USER_NOT_EXISTS_ERROR, null);

            List<User> users = userRepository.findByStatusStatusId(StatusEnum.ACTIVE_USER.getId());
            users.removeIf(user->userId.equals(user.getUserId()));

            List<ViewListUserDTO> viewListUserDTO = new ArrayList<>();

            for (User user : users) {
                ViewListUserDTO userDTO = new ViewListUserDTO();
                userDTO.setUserId(user.getUserId());
                userDTO.setUserName(user.getUserName());
                userDTO.setUserLastName(user.getUserLastName());
                userDTO.setStatusId(user.getStatus().getStatusId());
                viewListUserDTO.add(userDTO);
            }
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, viewListUserDTO);
        }catch(Exception err){
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }

}
