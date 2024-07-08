package co.com.template.Repositories;


import co.com.template.Repositories.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {



}

