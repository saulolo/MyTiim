package co.com.template.services;

import co.com.template.Repositories.CommitmentRepository;
import co.com.template.Repositories.MeasureRepository;
import co.com.template.Repositories.ObjectiveRepository;
import co.com.template.Repositories.dto.*;
import co.com.template.Repositories.entities.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommitmentServiceTest {

    @InjectMocks
    private CommitmentService commitmentService;
    @Mock
    private CommitmentRepository commitmentRepository;
    @Mock
    private MeasureRepository measureRepository;
    @Mock
    private ObjectiveRepository objectiveRepository;


    @Test
    @DisplayName("Prueba del método getCommitmentByObjectiveId")
    public void testGetCommitmentByObjectiveId(){

    Long objectiveId = 1L;

    List<Commitment> commitments = Arrays.asList(
        new Commitment(1L, new Objective(), new Measure(1L, "Porcentaje"), "Prueba test", LocalDate.now(), 50.0, 100.0, LocalDate.now())
    );

        when(commitmentRepository.findByObjectiveObjectiveId(objectiveId)).thenReturn(commitments);

        ResponseDTO response = commitmentService.getCommitmentByObjectiveId(objectiveId);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());

        List<CommitmentDTO> commitmentDTOS = (List<CommitmentDTO>) response.getData();
        assertEquals(commitments.size(), commitmentDTOS.size());

        verify(commitmentRepository, times(1));

    }

    @Test
    @DisplayName("Prueba del método setCommitment")
    public void testSetCommitment(){

        CreateCommitmentDTO request = new CreateCommitmentDTO();
        request.setObjectiveId(1L);
        request.setCommitmentDescribe("Aprender Java");
        request.setCommitmentDate(LocalDate.now());
        request.setMeasureId(1L);
        request.setCommitmentAdvance(50.0);
        request.setCommitmentGoal(100.0);

        List<CommitmentDTO> commitmentDTOS = new ArrayList<>();
        commitmentDTOS.add(new CommitmentDTO());
        request.setCommitmentDTO(commitmentDTOS);

        Measure measure = new Measure(1L, "Porcentaje");
        when(measureRepository.findByMeasureId(1L)).thenReturn(measure);

        Objective objective = new Objective();
        when(objectiveRepository.findByObjectiveId(1L)).thenReturn(objective);

        Commitment saveCommitment = new Commitment(1L, new Objective(), new Measure(), "Aprender Java", LocalDate.now(), 50.0, 100.0, LocalDate.now());
        when(commitmentRepository.save(any(Commitment.class))).thenReturn(saveCommitment);

        ResponseDTO response = commitmentService.SetCommitment(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(true, response.getData());

        verify(measureRepository, times(1)).findByMeasureId(1L);
        verify(objectiveRepository, times(1)).findByObjectiveId(1L);


    }


    @Test
    @DisplayName("Prueba del método updateCommitment")
    public void testUpdateCommitment(){

        CommitmentEditionDTO request = new CommitmentEditionDTO();
        request.setCommitmentId(1L);
        request.setCommitmentDescribe("Aprender Java");
        request.setCommitmentDate(LocalDate.now());
        request.setMeasureId(1L);
        request.setCommitmentAdvance(50.0);
        request.setCommitmentGoal(100.0);

        Measure measure = new Measure(1L, "Porcentaje");
        when(measureRepository.findByMeasureId(1L)).thenReturn(measure);

        Commitment updateCommitment = new Commitment(1L, new Objective(), new Measure(), "Aprender Angular", LocalDate.now(), 50.0, 100.0, LocalDate.now());
        when(commitmentRepository.findByCommitmentId(request.getCommitmentId())).thenReturn(updateCommitment);

        ResponseDTO response = commitmentService.updateCommitment(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(true, response.getData());

        verify(commitmentRepository, times(1)).save(any(Commitment.class));

        verify(commitmentRepository, times(1)).findByCommitmentId(1L);
        verify(measureRepository, times(1)).findByMeasureId(1L);

        assertEquals(request.getCommitmentDescribe(), updateCommitment.getCommitmentDescribe());
        assertEquals(measure, updateCommitment.getMeasure());

    }

    @Test
    @DisplayName("Prueba del método advanceCommitment")
    public void testAdvanceCommitment(){

        AdvanceCommitmentDTO request = new AdvanceCommitmentDTO();

        request.setCommitmentId(1L);
        request.setCommitmentAdvance(50.0);

        Commitment advanceCommitment = new Commitment(1L, new Objective(), new Measure(), "Aprender Angular", LocalDate.now(), 50.0, 100.0, LocalDate.now());
        when(commitmentRepository.findByCommitmentId(request.getCommitmentId())).thenReturn(advanceCommitment);

        ResponseDTO response = commitmentService.advanceCommitment(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(true, response.getData());

        verify(commitmentRepository, times(1)).save(any(Commitment.class));

        verify(commitmentRepository, times(1)).findByCommitmentId(1L);

        assertEquals(request.getCommitmentAdvance(), advanceCommitment.getCommitmentAdvance());
        assertEquals(request.getCommitmentId(), advanceCommitment.getCommitmentId());

    }


}
