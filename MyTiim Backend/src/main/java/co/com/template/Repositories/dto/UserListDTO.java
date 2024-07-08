package co.com.template.Repositories.dto;

import co.com.template.Repositories.entities.Group;
import co.com.template.Repositories.entities.Roll;
import co.com.template.Repositories.entities.Status;
import co.com.template.Repositories.entities.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserListDTO implements Serializable {


    private Long userId;
    private String userName;
    private String userLastName;
    private String userEmail;
    private Long userPhone;
    private Group group;
    private Long leaderId;
    private List<Roll> roles;
    private Status status;

    public UserListDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userLastName = user.getUserLastName();
        this.userEmail = user.getUserEmail();
        this.userPhone = user.getUserPhone();
        this.group = user.getGroup();
        this.leaderId = user.getLeaderId();
        this.roles = user.getRolls().stream().collect(Collectors.toList());
        this.status = user.getStatus();
    }
}
