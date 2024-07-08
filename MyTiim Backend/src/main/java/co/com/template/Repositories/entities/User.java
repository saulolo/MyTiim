package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_user"})})
@Component
public class User implements Serializable {

    private static final long serialVersionUID = 5022341805021141326L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name="user_password")
    private String userPassword;

    @Column(name="user_name", length = 100)
    private String userName;

    @Column(name="user_last_name", length = 100)
    private String userLastName;

    @Column(name="user_phone", length = 20)
    private Long userPhone;

    @Column(name="user_profile_id")
    private Integer userProfileId;

    @Column(name="user_email", length = 50)
    private String userEmail;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_status_id", nullable = false)
    private Status status;

    @Column(name="user_user")
    public String user;

    @Column(name="user_created_date")
    private LocalDate createDate;

    @Column(name = "user_leader_id")
    private Long leaderId;

    @Column(name = "user_last_login")
    private LocalDateTime lastLogin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "t_roll_user",
            joinColumns = @JoinColumn(name = "roll_roll_user_id"),
            inverseJoinColumns = @JoinColumn(name = "roll_roll_id"))
    private Set<Roll> rolls = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "t_group_user",
            joinColumns = @JoinColumn(name = "user_user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_group_id")
    )
    private Set<Group> groups = new HashSet<>();

    @Column(name = "user_activated_date")
    private LocalDate activatedDate;

}
