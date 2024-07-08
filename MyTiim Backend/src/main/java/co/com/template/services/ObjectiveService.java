package co.com.template.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import co.com.template.Repositories.*;
import co.com.template.Repositories.dto.*;
import co.com.template.Repositories.entities.*;
import co.com.template.utils.Constants;
import co.com.template.utils.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.com.template.exception.CustomException;
import org.springframework.http.HttpStatus;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ObjectiveService {


    private final ObjectiveRepository objectiveRepository;

    private final ObjectiveTypeRepository objectiveTypeRepository;

    private final StatusRepository statusRepository;

    private final UserRepository userRepository;

    private final CommitmentRepository commitmentRepository;

    private final MeasureRepository measureRepository;

    private final GroupRepository groupRepository;

    private final PeriodRepository periodRepository;
    public ResponseDTO getObjective() {
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, objectiveRepository.findAll());
    }

    public ResponseDTO setObjective(CreateObjectiveDTO request) {
        try {
            ObjectiveType type = objectiveTypeRepository.findByObjectiveTypeId(request.getObjectiveTypeId());
            Status status = statusRepository.findByStatusId(StatusEnum.ACTIVE_OBJECTIVE.getId());
            User user = userRepository.findByUserId(request.getUserId());
            Period period = periodRepository.findByPeriodId(request.getPeriodId());
            Objective entity = new Objective();
            entity.setObjectiveDescribe(request.getObjectiveDescribe());
            entity.setObjectiveQualify(Constants.MIN_VALUE_QUALIFY);
            entity.setObjectiveType(type);
            entity.setStatus(status);
            entity.setUser(user);
            entity.setGroup(user.getGroup());
            entity.setPeriod(period);
            Long alignObjective = Objects.isNull(request.getAlignObjectiveId()) ? null : request.getAlignObjectiveId();
            entity.setAlignObjectiveId(alignObjective);
            User userAlign = Objects.isNull(request.getAlignUserId()) ? null : userRepository.findByUserId(request.getAlignUserId());
            entity.setAlignUser(userAlign);
            Group alignGroup = Objects.isNull(request.getAlignGroupId()) ? null : groupRepository.findByGroupId(request.getAlignGroupId());
            entity.setAlignGroup(alignGroup);

            entity = objectiveRepository.save(entity);
            List<Commitment> commitmentsEntity = new ArrayList<>();
            List<CommitmentDTO> commitmentDTOS = request.getCommitments();

            for (CommitmentDTO c : commitmentDTOS) {
                Commitment commitment = new Commitment();
                commitment.setObjective(entity);
                commitment.setCommitmentDescribe(c.getCommitmentDescribe());

                String commitmentDate = c.getCommitmentDate();
                if (commitmentDate != null && commitmentDate.length() >= Constants.TEN_INDEX) {
                    commitment.setCommitmentDate(LocalDate.parse(commitmentDate.substring(Constants.ZERO_INDEX, Constants.TEN_INDEX)));
                } else {
                    commitment.setCommitmentDate(null);
                }

                commitment.setCommitmentGoal(c.getCommitmentGoal());
                Long measureId = c.getMeasureId();
                Measure measure = measureRepository.findByMeasureId(measureId);
                commitment.setMeasure(measure);
                commitment.setCommitmentAdvance(c.getCommitmentAdvance());
                commitmentsEntity.add(commitment);
            }

            commitmentRepository.saveAll(commitmentsEntity);
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);
        } catch (Exception err) {
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }


    public ResponseDTO updateObjective(Long objectiveId, ObjectiveEditionDTO objective) {

        Objective entity = objectiveRepository.findByObjectiveId(objectiveId);

        if (Objects.isNull(entity)) {
            return new ResponseDTO(HttpStatus.OK, Constants.OBJECT_NOT_EXISTS_ERROR, null);
        }

        ObjectiveType type = objectiveTypeRepository.findByObjectiveTypeId(objective.getObjectiveTypeId());
        Period period = periodRepository.findByPeriodId(objective.getPeriodId());
        entity.setObjectiveDescribe(objective.getObjectiveDescribe());
        entity.setObjectiveType(type);
        entity.setPeriod(period);
        Long alignObjective = Objects.isNull(objective.getAlignObjectiveId()) ? null : objective.getAlignObjectiveId();
        entity.setAlignObjectiveId(alignObjective);
        User userAlign = Objects.isNull(objective.getAlignUserId()) ? null : userRepository.findByUserId(objective.getAlignUserId());
        entity.setAlignUser(userAlign);
        Group alignGroup = Objects.isNull(objective.getAlignGroupId()) ? null : groupRepository.findByGroupId(objective.getAlignGroupId());
        entity.setAlignGroup(alignGroup);
        objectiveRepository.save(entity);
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);
    }


    public ResponseDTO deleteObjective(Long id) {
        objectiveRepository.deleteById(id);
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);
    }

    public ResponseDTO getObjectiveById(Long objectiveId) {
        Objective objective = objectiveRepository.findByObjectiveId(objectiveId);
        if (Objects.isNull(objectiveId)) {
            throw new CustomException(Constants.OBJECT_NOT_EXISTS_ERROR, HttpStatus.OK);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, new ObjectiveDTO(objective));
    }

    public ResponseDTO getObjectiveForGroupAndForUser(Long GroupGroupId, Long UserUserId) {
        List<Objective> list = objectiveRepository.findByGroupGroupIdAndUserUserId(GroupGroupId, UserUserId);
        List<ObjectiveAlignedDTO> dtoList = new ArrayList<>();
        for (Objective obj : list) {
            ObjectiveAlignedDTO objAligDTO = new ObjectiveAlignedDTO();
            objAligDTO.setObjectiveId(obj.getObjectiveId());
            objAligDTO.setObjectiveDescribe(obj.getObjectiveDescribe());
            dtoList.add(objAligDTO);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, dtoList);
    }


    public ResponseDTO getObjectiveForUser(Long UserUserId) {
        List<Objective> list = objectiveRepository.findByUserUserId(UserUserId);
        List<ObjectiveUserDTO> dtoList = new ArrayList<>();
        for (Objective obj : list) {
            ObjectiveUserDTO objectiveUserDTO = new ObjectiveUserDTO();
            objectiveUserDTO.setUserId(obj.getUser().getUserId());
            objectiveUserDTO.setUserName(obj.getUser().getUserName());
            objectiveUserDTO.setUserLastName(obj.getUser().getUserLastName());
            dtoList.add(objectiveUserDTO);
        }
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, dtoList);
    }


    public ResponseDTO getObjectiveForGroup(Long GroupGroupId) {
        List<Objective> list = objectiveRepository.findByGroupGroupId(GroupGroupId);
        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, list);
    }


    public ResponseDTO closeObjective(Long objectiveId, CloseObjectiveDTO objective) {
        try {
            Objective entity = objectiveRepository.findByObjectiveId(objectiveId);
            if (Objects.isNull(entity)) {
                return new ResponseDTO(HttpStatus.OK, Constants.OBJECT_NOT_EXISTS_ERROR, null);
            }
            if (objective.getStatusId().equals(StatusEnum.CLOSED_OBJECTIVE.getId()) &&
                    (objective.getObjectiveQualify() <= Constants.MIN_VALUE_QUALIFY || objective.getObjectiveQualify() > Constants.MAX_VALUE_QUALIFY)) {
                return new ResponseDTO(HttpStatus.OK, Constants.INVALID_RATING, null);
            }
            if (!objective.getStatusId().equals(StatusEnum.CLOSED_OBJECTIVE.getId())) {
                entity.setObjectiveQualify(Constants.MIN_VALUE_QUALIFY);
            } else
                entity.setObjectiveQualify(objective.getObjectiveQualify());

            entity.setObjectiveObservations(objective.getObjectiveObservations());
            Status status = statusRepository.findByStatusId(objective.getStatusId());
            entity.setStatus(status);
            objectiveRepository.save(entity);
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, Boolean.TRUE);

        } catch (Exception err) {
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }

    }

    public ResponseDTO getObjectivesByUserId(Long userId) {
        try {
            List<Objective> objectives = objectiveRepository.findByUserUserId(userId);
            List<ObjectiveDTO> result = objectives.stream().map(obj -> new ObjectiveDTO(obj)).collect(Collectors.toList());
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, result);
        } catch (Exception err) {
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }

    }

    public ResponseDTO findObjectiveFilter(ObjectiveFilterDTO objectiveFilterDTO) {

        try {
            List<Objective> objectives = objectiveRepository.findAll();


            if (objectiveFilterDTO.getObjectiveTypeId() != null) {
                objectives = objectives.stream()
                        .filter(obj -> obj.getObjectiveType().getObjectiveTypeId().equals(objectiveFilterDTO.getObjectiveTypeId()))
                        .collect(Collectors.toList());

            }
            if (objectiveFilterDTO.getGroupId() != null) {
                objectives = objectives.stream()
                        .filter(obj -> obj.getGroup().getGroupId().equals(objectiveFilterDTO.getGroupId()))
                        .collect(Collectors.toList());

            }
            if (objectiveFilterDTO.getUserId() != null) {
                objectives = objectives.stream()
                        .filter(obj -> obj.getUser().getUserId().equals(objectiveFilterDTO.getUserId()))
                        .collect(Collectors.toList());

            }

            if (objectiveFilterDTO.getStatusId() != null) {
                objectives = objectives.stream()
                        .filter(obj -> obj.getStatus().getStatusId().equals(objectiveFilterDTO.getStatusId()))
                        .collect(Collectors.toList());


            }
            List<ObjFilterResponseDTO> listObj = new ArrayList<>();
            for(Objective objective:objectives) {
                ObjFilterResponseDTO obj = new ObjFilterResponseDTO();
                obj.setObjectiveId(objective.getObjectiveId());
                obj.setObjectiveDescribe(objective.getObjectiveDescribe());
                obj.setObjectiveType(objective.getObjectiveType());
                obj.setStatus(objective.getStatus());
                obj.setGroupId(objective.getGroup().getGroupId());
                obj.setUserId(objective.getUser().getUserId());

                listObj.add(obj);
            }
            listObj = listObj.stream().skip((objectiveFilterDTO.getSet()-Constants.ONE_VALUE)*Constants.ONE_HUNDRED_LIMIT).limit(Constants.ONE_HUNDRED_LIMIT).toList();
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, listObj);

        } catch (Exception err) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);


        }

    }


}

