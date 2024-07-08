package co.com.template.services;




import co.com.template.Repositories.ModuleRollRepository;
import co.com.template.Repositories.RollUserRepository;
import co.com.template.Repositories.dto.ModuleRollResponseDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.ModuleRoll;
import co.com.template.Repositories.entities.OptionModule;
import co.com.template.Repositories.entities.RollUser;
import co.com.template.security.UtilsToken;
import co.com.template.utils.Constants;
import co.com.template.utils.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RequiredArgsConstructor
@Service
@Transactional
@Log4j2
public class ModuleRollService {


        private final ModuleRollRepository moduleRollRepository;

        private final RollUserRepository rollUserRepository;

        private final UtilsToken utilsToken;



    public ResponseDTO getUserAccess(HttpHeaders headers) {

        ModuleRollResponseDTO response = new ModuleRollResponseDTO();

        try {
            Long userId = utilsToken.getUserIdFromToken(headers);
            if (Objects.isNull(userId))
                return new ResponseDTO(HttpStatus.BAD_REQUEST, Constants.USER_NOT_EXISTS_ERROR, null);

            List<RollUser> rolls = rollUserRepository.findByUserUserId(userId);

            List<Long> ids = rolls.stream().map(rollUser -> rollUser.getRoll().getRollId()).toList();

            List<ModuleRoll> moduleRolls = moduleRollRepository.findByRollRollIdIn(ids);

            Map<String, Object> modulePermissionsMap = new HashMap<>();


            moduleRolls.forEach(moduleRoll-> {

                Map<String, Object> optionPermissionsMap = new HashMap<>();

                List<ModuleRoll> listOptionModule = moduleRollRepository.findByModuleModuleId(moduleRoll.getModule().getModuleId());
                List<OptionModule> optionModules = listOptionModule.stream().map(ModuleRoll::getOptionModule)
                        .filter(o->o.getStatus().getStatusId().equals(Constants.STATUS_ACTIVE))
                        .distinct().toList();

                optionModules.forEach(optionModuleRoll -> {

                    Map<String, Boolean> rollPermissionsMap = new HashMap<>();

                    List<ModuleRoll> listPermissions = moduleRollRepository.findByOptionModuleOptionModuleId(optionModuleRoll.getOptionModuleId());

                    listPermissions.forEach(p -> {
                        rollPermissionsMap.put(p.getRoll().getRollDescribe(), p.getModuleRollEdit());
                    });

                    Map<String, Object> roll = new HashMap<>();
                    roll.put(Constants.NAME_ROLL, rollPermissionsMap);

                    optionPermissionsMap.put(optionModuleRoll.getOptionModuleDescribe(), roll);

                });

                modulePermissionsMap.put(moduleRoll.getModule().getModuleDescribe(), optionPermissionsMap);

            });



            response.setModulos(modulePermissionsMap);


            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, response);

        } catch (Exception err) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }

}
