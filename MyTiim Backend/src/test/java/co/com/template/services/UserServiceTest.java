package co.com.template.services;

import co.com.template.Repositories.UserRepository;
import co.com.template.Repositories.dto.CreateUserDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.dto.UserListDTO;
import co.com.template.Repositories.entities.Group;
import co.com.template.Repositories.entities.Status;
import co.com.template.Repositories.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;


    @Mock
    private UserRepository userRepository;


    @Test
    @DisplayName("Prueba del método createUser")
    public void testCreateUser() {

        CreateUserDTO request = new CreateUserDTO();
        request.setUser("Usuario de Prueba 1: Saulolo");
        request.setUserName("Nombre de Prueba 1: Saul");

        ResponseDTO response = userService.createUser(request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(null, response.getData());

    }

    @Test
    @DisplayName("Prueba del método getUsersList")
    public void testGetUsersList() {

        int page = 0;
        int size = 10;

        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUserName("Usuario de prueba 1: Armando");
        user1.setUserLastName("Bulla");
        user1.setUserEmail("correo@gmail.com");
        user1.setUserPhone(32248853L);
        user1.setGroup(new Group());
        user1.setLeaderId(1L);
        user1.setRolls(new HashSet<>());
        user1.setStatus(new Status());
        userList.add(user1);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(userList));

        ResponseDTO response = userService.getUsersList(page, size);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.getData());
        assertTrue(response.getData() instanceof List);

        List<UserListDTO> userListDTOs = (List<UserListDTO>) response.getData();
        assertEquals(1, userListDTOs.size());

        UserListDTO userListDTO = userListDTOs.get(0);
        assertEquals(1L, userListDTO.getUserId());
        assertEquals("Usuario de prueba 1: Armando", userListDTO.getUserName());
        assertEquals("Bulla", userListDTO.getUserLastName());
        assertEquals("correo@gmail.com", userListDTO.getUserEmail());
        assertEquals(32248853L, userListDTO.getUserPhone());
        assertNotNull(userListDTO.getGroup());
        assertEquals(1L, userListDTO.getLeaderId());
        assertNotNull(userListDTO.getRoles());
        assertNotNull(userListDTO.getStatus());
    }

}