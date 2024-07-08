package co.com.template.Repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO implements Serializable {

    private String userName;
    private String userLastName;
    private String userPassword;
    private String userEmail;
    private Long userPhone;
    private Long leaderId;
    private Long statusId;
    private Integer profileId;
    public String user;
    public LocalDate createDate;
    public List<Long> roles;
    private List<Long> groups;

}

