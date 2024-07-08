package co.com.template.Repositories.dto;


import lombok.Data;
import java.io.Serializable;
import java.util.Map;



@Data
public class ModuleRollResponseDTO implements Serializable {

   private Map<String, Object> modulos;


}
