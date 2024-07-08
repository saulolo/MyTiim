package co.com.template.Controllers;

import co.com.template.Repositories.dto.CreateUserDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getUser() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUser());
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }


    @GetMapping("/user/{groupId}")
    public ResponseEntity<Object> getUserForGroup(@PathVariable Long groupId) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserForGroup(groupId));
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }

     @GetMapping("/actives")
    public ResponseEntity<Object> getListUser() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getListActiveUser());
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }


    @GetMapping("/userList")
    public ResponseEntity<Object> getUsersListDTO(@RequestParam int page, @RequestParam int size) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersList(page, size));
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
        }
    }



    @PostMapping
    public ResponseEntity<Object> setUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(createUserDTO));
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getActiveFilterUsers(@RequestHeader HttpHeaders headers) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getActiveUsersExceptCurrentUser(headers));
        } catch(Exception err){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(HttpStatus.BAD_REQUEST,err.getMessage(),null));
        }
    }
}



