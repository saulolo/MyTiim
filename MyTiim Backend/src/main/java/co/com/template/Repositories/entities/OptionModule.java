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
@Table(name ="t_option_module")
public class OptionModule implements Serializable {

    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_module_id")
    private Long optionModuleId;

    @Column(name="option_module_describe")
    private String optionModuleDescribe;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "option_module_status_id", nullable = false)
    private Status status;


}
