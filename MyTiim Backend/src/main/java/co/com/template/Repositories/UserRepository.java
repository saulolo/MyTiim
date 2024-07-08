package co.com.template.Repositories;

import co.com.template.Repositories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByGroupGroupId(Long GroupGroupId);

    User findByUserId(Long userId);

    User findByUser(String user);

    List<User> findByStatusStatusId(Long StatusStatusId);

    List<User> findByStatusStatusIdAndActivatedDateLessThan(Long StatusStatusId, LocalDate date);

    List<User> findByStatusStatusId(Integer ACTIVE_USER);

    List<User> findByUserIdIn(List<Long> ids);

    List<User> findByActivatedDateLessThan(LocalDate endDate);


    boolean existsByUserEmail(String userEmail);

    boolean existsByUser(String user);




}
