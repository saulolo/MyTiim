package co.com.template.Repositories;


import co.com.template.Repositories.entities.RollUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RollUserRepository extends JpaRepository<RollUser, Long> {

    List<RollUser> findByUserUserId(Long userId);






}