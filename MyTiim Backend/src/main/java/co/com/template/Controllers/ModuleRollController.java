package co.com.template.Controllers;

import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.ModuleRollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/module")
public class ModuleRollController {

    @Autowired
    private ModuleRollService moduleRollService;

    @GetMapping("/roll")
    public ResponseEntity<Object> getUserAccess(@RequestHeader HttpHeaders headers){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(moduleRollService.getUserAccess(headers));
        }catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
}