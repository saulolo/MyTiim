package co.com.template.Repositories;


import co.com.template.Repositories.entities.ModuleRoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ModuleRollRepository extends JpaRepository<ModuleRoll, Long> {

     List<ModuleRoll> findByModuleModuleId(Long moduleId);

    List<ModuleRoll> findByOptionModuleOptionModuleId(Long optionModuleId);

    List<ModuleRoll> findByRollRollIdIn(List<Long> ids);

}

