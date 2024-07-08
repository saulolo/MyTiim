package co.com.template.services;

import co.com.template.Repositories.*;
import co.com.template.Repositories.dto.CommentDTO;
import co.com.template.Repositories.dto.CreateCommentDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.*;
import co.com.template.utils.EmailServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentFeedbackRepository commentFeedbackRepository;
    @Mock
    private CommentTypeRepository commentTypeRepository;
    @Mock
    private ObjectiveRepository objectiveRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private CommentUserRepository commentUserRepository;
    @Mock
    private EmailServiceImpl emailService;
    @Mock
    private ConfigurationSystemService configurationService;

    @Mock
    CommentUser commentUser;


    @Test
    @DisplayName("Prueba del método getComment")
    public void getComment(){

        Long objectiveId = 1L;
        List<Comment> comments = Arrays.asList(
                new Comment(1L, "Prueba test", new User(), new Status(), LocalDateTime.now(), true, new CommentType(), new Objective(), false )
        );

        when(commentRepository.findTop20ByObjectiveObjectiveIdOrderByCommentDateDesc(objectiveId)).thenReturn(comments);
        when(commentFeedbackRepository.findByCommentCommentId(anyLong())).thenReturn(new ArrayList<>());

        ResponseDTO response = commentService.getCommentsByObjectiveId(objectiveId);

        List<CommentDTO> commentDTOS = (List<CommentDTO>) response.getData();
        assertEquals(comments.size(), commentDTOS.size());


    }
    @Test
    @DisplayName("Prueba del método setComment")
    public void setComment(){

        CreateCommentDTO commentDTO = new CreateCommentDTO();
        commentDTO.setObjectiveId(1L);
        commentDTO.setCommentType(true);
        commentDTO.setUserId(1L);
        commentDTO.setStatusId(1L);
        commentDTO.setCommentDescribe("un comentario");

        CommentType commentType =  new CommentType(2L, "Reconocimiento", 2);
        when(commentTypeRepository.findByCommentTypeId(2L)).thenReturn(commentType);

        Objective objective = new Objective();
        when(objectiveRepository.findByObjectiveId(1L)).thenReturn(objective);

        User user = new User();
        when(userRepository.findByUserId(2L)).thenReturn(user);

        Status status = new Status();
        when(statusRepository.findByStatusId(8L)).thenReturn(status);

        Comment comment = new Comment(1L, "un comentario", new User(), new Status(), LocalDateTime.now(), true, new CommentType(), new Objective(), true );
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentUser commentUser = new CommentUser();
        when(commentUserRepository.save(any(CommentUser.class))).thenReturn(commentUser);

        when(configurationService.getConfigValue(any())).thenReturn("config-value");

        ResponseDTO response = commentService.setComment(commentDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(null, response.getData());


    }

}
