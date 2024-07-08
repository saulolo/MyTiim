package co.com.template.services;

import co.com.template.Repositories.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ObjectiveServiceTest {

    @InjectMocks
    private ObjectiveService objectiveService;

    @Mock
    private ObjectiveRepository objectiveRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PeriodRepository periodRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private ObjectiveTypeRepository objectiveTypeRepository;



    @Test
    @DisplayName("Prueba del método getObjective")
    public void testGetObjective() {
        List<Objective> objectives = Arrays.asList(
                new Objective(1L, "Objetivo test 1: Aprender Spring boot"),
                new Objective(2L, "Objective test 2: Aprender React")
        );
        when(objectiveRepository.findAll()).thenReturn(objectives);

        ResponseDTO response = objectiveService.getObjective();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(objectives, response.getData());

        verify(objectiveRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Prueba del método setObjective")
    public void testSetObjective() {

        CreateObjectiveDTO request = new CreateObjectiveDTO();
        request.setObjectiveDescribe("Objetivo de prueba 1: Aprender bachata");
        request.setObjectiveTypeId(1L);
        request.setUserId(1L);

        List<CommitmentDTO> commitmentDTOS = new ArrayList<>();
        commitmentDTOS.add(new CommitmentDTO());
        request.setCommitments(commitmentDTOS);

        request.setPeriodId(1l);
        request.setAlignGroupId(1L);
        request.setAlignUserId(1l);
        request.setAlignObjectiveId(1l);

        ObjectiveType objectiveType = new ObjectiveType(1l, "Tipo de objetivo", 1);
        when(objectiveTypeRepository.findByObjectiveTypeId(1l)).thenReturn(objectiveType);

        Status status = new Status(1L, "Activo", "Tipo de Status");
        when(statusRepository.findByStatusId(1L)).thenReturn(status);


        Period period = new Period(1L, "Q3-2023", LocalDate.parse("2023-05-01"), LocalDate.parse("2023-08-31"), LocalDate.parse("2023-09-01"), LocalDate.parse("2023-12-31"), new Status(1L, "Estado de ejemplo", "Ejemplo de tipo de estado"));
        when(periodRepository.findByPeriodId(1L)).thenReturn(period);

        Objective saveObjective = new Objective(1L,  "Objetivo de prueba 1: Aprender bachata");
        when(objectiveRepository.save(any(Objective.class))).thenReturn(saveObjective);

        ResponseDTO response = objectiveService.setObjective(request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());

        verify(objectiveTypeRepository, times(1)).findByObjectiveTypeId(1L);
        verify(statusRepository, times(1)).findByStatusId(1L);

    }

    @Test
    @DisplayName("Prueba del método updateObjective")
    public void testUpdateObjective() {

        Long objectiveId = 1L;

        ObjectiveEditionDTO request = new ObjectiveEditionDTO();
        request.setObjectiveDescribe("Objetivo de prueba actualizado: 1. Aprender a bailar salsa");
        request.setObjectiveTypeId(2L);
        request.setPeriodId(2L);
        request.setAlignGroupId(1L);
        request.setAlignUserId(1L);
        request.setAlignObjectiveId(1L);

        ObjectiveType objectiveType = new ObjectiveType(2L, "Tipo de objetivo", 2);
        when(objectiveTypeRepository.findByObjectiveTypeId(2L)).thenReturn(objectiveType);

        Period period = new Period(2L, "Q3-2023", LocalDate.parse("2023-05-01"), LocalDate.parse("2023-08-31"), LocalDate.parse("2023-09-01"), LocalDate.parse("2023-12-31"), new Status(1L, "Estado de ejemplo", "Ejemplo de tipo de estado"));
        when(periodRepository.findByPeriodId(2L)).thenReturn(period);

        Group group = new Group(1L, "Arquitectura", "Arquitectura");
        when(groupRepository.findByGroupId(1L)).thenReturn(group);

        Objective updateObjective = new Objective(objectiveId, "Objetivo de prueba 1: Actualizar aprender");
        when(objectiveRepository.findByObjectiveId(objectiveId)).thenReturn(updateObjective);

        ResponseDTO response = objectiveService.updateObjective(objectiveId, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(true, response.getData());

        verify(objectiveRepository, times(1)).save(any(Objective.class));

        verify(objectiveRepository, times(1)).findByObjectiveId(objectiveId);

        verify(periodRepository, times(1)).findByPeriodId(2L);

        verify(groupRepository, times(1)).findByGroupId(1L);

        assertEquals(request.getObjectiveDescribe(), updateObjective.getObjectiveDescribe());
        assertEquals(objectiveType, updateObjective.getObjectiveType());
        assertEquals(period, updateObjective.getPeriod());
    }


    @Test
    @DisplayName("Prueba del método deleteObjective")
    public void testDeleteObjective() {

        Long id = 1l;

        ResponseDTO responseDTO = objectiveService.deleteObjective(id);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
        assertEquals(true, responseDTO.getData());

        verify(objectiveRepository, times(1)).deleteById(1L);

    }

    @Test
    @DisplayName("Prueba del método getObjectiveForGroupAndForUser")
    public void testGetObjectiveForGroupAndForUser(){

        Long groupGroupId = 1l;
        Long userUserId = 1l;

        List<Objective> objectives = Arrays.asList(
                new Objective(1L, "Objective test 1"),
                new Objective(2L, "Objective test 2")
        );

        when(objectiveRepository.findByGroupGroupIdAndUserUserId(1l, 1l)).thenReturn(objectives);


        ResponseDTO responseDTO = objectiveService.getObjectiveForGroupAndForUser(1L, 1L);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());

        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);

        List<ObjectiveAlignedDTO> objectiveAlignedDTOS = (List<ObjectiveAlignedDTO>) responseDTO.getData();
        assertEquals(objectives.size(), objectiveAlignedDTOS.size());

        for (int i = 0; i < objectives.size(); i++) {
            ObjectiveAlignedDTO expectedObjective = new ObjectiveAlignedDTO();
            expectedObjective.setObjectiveId(objectives.get(i).getObjectiveId());
            expectedObjective.setObjectiveDescribe(objectives.get(i).getObjectiveDescribe());

            assertEquals(expectedObjective.getObjectiveId(), objectiveAlignedDTOS.get(i).getObjectiveId());
            assertEquals(expectedObjective.getObjectiveDescribe(), objectiveAlignedDTOS.get(i).getObjectiveDescribe());
        }

        verify(objectiveRepository, times(1)).findByGroupGroupIdAndUserUserId(1l, 1l);

    }


    @Test
    @DisplayName("Prueba del método getObjectiveById")
    public void testGetObjectiveById() {

        Long objectiveId = 1L;

        Objective objective = new Objective();
        objective.setObjectiveId(1L);
        objective.setObjectiveDescribe("Objetivo Prueba 1: aprender Pruebas unitarias");

        Status status = new Status();
        status.setStatusId(1L);
        status.setStatusDescribe("Activo");
        status.setStatusType("Tipo de Status");

        User user = new User();
        user.setUserId(2L);
        objective.setUser(user);


        objective.setStatus(status);

        ObjectiveType objectiveType = new ObjectiveType();
        objectiveType.setObjectiveTypeId(1L);
        objectiveType.setObjectiveTypeDescribe("Tipo de descripción");
        objectiveType.setObjectiveTypeStatusId(1);

        objective.setObjectiveType(objectiveType);

        when(objectiveRepository.findByObjectiveId(objectiveId)).thenReturn(objective);

        ResponseDTO responseDTO = objectiveService.getObjectiveById(objectiveId);

        ObjectiveDTO expectedObjectiveDTO = new ObjectiveDTO(objective);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());

        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof ObjectiveDTO);
        assertEquals(expectedObjectiveDTO.getObjectiveId(), ((ObjectiveDTO) responseDTO.getData()).getObjectiveId());
        assertEquals(expectedObjectiveDTO.getObjectiveDescribe(), ((ObjectiveDTO) responseDTO.getData()).getObjectiveDescribe());

        verify(objectiveRepository, times(1)).findByObjectiveId(1L);

    }


    @Test
    @DisplayName("Prueba del método getObjectiveForUser")
    public void testGetObjectiveForUser() {
        Long userUserId = 1L;

        User user1 = new User();

        List<Objective> objectives = Arrays.asList(
                new Objective(1L, "Objective test 1", user1)
        );

        when(objectiveRepository.findByUserUserId(1L)).thenReturn(objectives);

        ResponseDTO responseDTO = objectiveService.getObjectiveForUser(1L);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());

        assertNotNull(responseDTO.getData());
        assertTrue(responseDTO.getData() instanceof List);

        List<ObjectiveUserDTO> objectiveUserDTOS = (List<ObjectiveUserDTO>) responseDTO.getData();
        assertEquals(objectives.size(), objectiveUserDTOS.size());

        for (int i = 0; i < objectives.size(); i++) {
            ObjectiveUserDTO expectedObjective = new ObjectiveUserDTO();
            expectedObjective.setUserId(objectives.get(i).getUser().getUserId());
            expectedObjective.setUserName(objectives.get(i).getUser().getUserName());
            expectedObjective.setUserLastName(objectives.get(i).getUser().getUserLastName());

            assertEquals(expectedObjective.getUserId(), objectiveUserDTOS.get(i).getUserId());
            assertEquals(expectedObjective.getUserName(), objectiveUserDTOS.get(i).getUserName());
            assertEquals(expectedObjective.getUserLastName(), objectiveUserDTOS.get(i).getUserLastName());
        }

        verify(objectiveRepository, times(1)).findByUserUserId(1L);
    }


    @Test
    @DisplayName("Prueba del método getObjectiveForGroup")
    public void testGetObjectiveForGroup() {

        Long groupId = 1L;

        Group group = new Group(1L, "Prueba descricpión grupo", "100");

        List<Objective> objectives = Arrays.asList(
                new Objective(1L, "Objective test 1", group)
        );

        when(objectiveRepository.findByGroupGroupId(1L)).thenReturn(objectives);

        ResponseDTO responseDTO = objectiveService.getObjectiveForGroup(1L);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());

        assertTrue(responseDTO.getData() instanceof List);

        List<Objective> resultObjectives = (List<Objective>) responseDTO.getData();
        assertEquals(objectives.size(), resultObjectives.size());

        for (int i = 0; i < objectives.size(); i++) {
            assertEquals(objectives.get(i).getObjectiveId(), resultObjectives.get(i).getObjectiveId());
            assertEquals(objectives.get(i).getObjectiveDescribe(), resultObjectives.get(i).getObjectiveDescribe());
        }

        verify(objectiveRepository, times(1)).findByGroupGroupId(1L);

    }


    @Test
    @DisplayName("Prueba del método closeObjective")
    public void testCloseObjective() {

        Long objectiveId = 1L;

        CloseObjectiveDTO request = new CloseObjectiveDTO();
        request.setStatusId(1L);
        request.setObjectiveObservations("Objetivo de observación de pruena 1");
        request.setObjectiveQualify(0L);

        Objective objective = new Objective(1L, "Objetivo Prueba 1: aprender Pruebas unitarias");
        when(objectiveRepository.findByObjectiveId(1L)).thenReturn(objective);

        Status status = new Status(1L, "Descrpición de Estado", "Activo");
        when((statusRepository.findByStatusId(1L))).thenReturn(status);

        ResponseDTO responseDTO = objectiveService.closeObjective(1L, request);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
        assertEquals(true, responseDTO.getData());

        assertEquals(request.getObjectiveQualify(),objective.getObjectiveQualify());
        assertEquals(request.getObjectiveObservations(), objective.getObjectiveObservations());
        assertEquals(request.getStatusId(), objective.getStatus().getStatusId());

        verify(objectiveRepository, times(1)).findByObjectiveId(1L);
        verify(statusRepository, times(1)).findByStatusId(1L);

    }


    @Test
    @DisplayName("Prueba del método getObjectivesByUserId")
    public void testGetObjectivesByUserId() {

        Long userId = 1L;

        User user = new User();

        List<Objective> objectives = Arrays.asList(
                new Objective(1L, "Objective test 1", user)
        );

        when(objectiveRepository.findByUserUserId(1L)).thenReturn(objectives);

        ResponseDTO responseDTO = objectiveService.getObjectivesByUserId(userId);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.OK, responseDTO.getStatus());
        assertNotNull(responseDTO.getData());

        List<ObjectiveDTO> objectiveDTOList = (List<ObjectiveDTO>) responseDTO.getData();
        assertEquals(objectives.size(), objectiveDTOList.size());

        verify(objectiveRepository, times(1)).findByUserUserId(userId);

    }


    @Test
    @DisplayName("Prueba del método testFindObjectiveFilter")
    public void testFindObjectiveFilter() {

        ObjectiveFilterDTO objectiveFilterDTO = new ObjectiveFilterDTO();
        objectiveFilterDTO.setObjectiveTypeId(1L);

        List<Objective> objectives = Arrays.asList(
                new Objective(1L, "Objective test 1"),
                new Objective(2L, "Objective test 2")
        );

        when(objectiveRepository.findAll()).thenReturn(objectives);

        ResponseDTO responseDTO = objectiveService.findObjectiveFilter(objectiveFilterDTO);

        assertNotNull(responseDTO);
        assertEquals(HttpStatus.BAD_REQUEST, responseDTO.getStatus());


        verify(objectiveRepository, times(1)).findAll();

    }


}
