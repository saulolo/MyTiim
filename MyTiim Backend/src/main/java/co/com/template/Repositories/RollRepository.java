package co.com.template.Repositories;

import co.com.template.Repositories.entities.Roll;
import co.com.template.Repositories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RollRepository extends JpaRepository<Roll, Long> {

    Roll findByRollId(Long rollId);



}