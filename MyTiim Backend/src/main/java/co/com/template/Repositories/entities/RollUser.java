package co.com.template.Repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="t_roll_user")
public class RollUser implements Serializable {

    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roll_user_id")
    private Long rollUserId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "roll_roll_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "roll_roll_id", nullable = false)
    private Roll roll;

}
