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
@Table(name ="t_module_roll")
public class ModuleRoll implements Serializable {

    private static final long serialVersionUID = -3791785012018326096L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_roll_id")
    private Long moduleRollId;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="module_roll_module_id")
    private Module module;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="module_roll_roll_id")
    private Roll roll;

    @Column(name = "module_roll_edit")
    private Boolean moduleRollEdit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="module_roll_option_module_id")
    private OptionModule optionModule;




}
