package de.telran.project_management_system.service.impl;

import de.telran.project_management_system.converters.ProjectConverter;
import de.telran.project_management_system.dto.request.ProjectRequestDTO;
import de.telran.project_management_system.dto.request.TaskRequestDTO;
import de.telran.project_management_system.dto.response.ProjectResponseDTO;
import de.telran.project_management_system.entity.Project;
import de.telran.project_management_system.entity.Task;
import de.telran.project_management_system.entity.types.ProgressType;
import de.telran.project_management_system.repository.ProjectRepository;
import de.telran.project_management_system.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl service;

    @Nested
    @DisplayName("Create method tests")
    class MethodCreateTest {

        @Test
        @DisplayName("Should throw 409 exception when such project already exist")
        public void shouldThrow409WhenProjectAlreadyExists() {
            ProjectRequestDTO request = ProjectRequestDTO
                    .builder()
                    .projectName("Abc")
                    .tasks(null)
                    .build();

            HttpStatus expectedStatus = HttpStatus.CONFLICT;
            String expectedMessage = String.format("Project with name [%s] already exists", request.getProjectName());

            Mockito
                    .when(projectRepository.existsByProjectName(request.getProjectName()))
                    .thenReturn(true);

            var exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.create(request)
            );

            Assertions.assertEquals(expectedStatus, exception.getStatus());
            Assertions.assertEquals(expectedMessage, exception.getReason());
        }

        @Test
        @DisplayName("Should save the project")
        public void shouldSaveProjectAndTasks() {
            var tasksDTO = List.of(
                    TaskRequestDTO.builder().taskName("1").daysToComplete(1).build(),
                    TaskRequestDTO.builder().taskName("2").daysToComplete(1).build(),
                    TaskRequestDTO.builder().taskName("3").daysToComplete(1).build(),
                    TaskRequestDTO.builder().taskName("4").daysToComplete(1).build()
            );

            ProjectRequestDTO request = ProjectRequestDTO
                    .builder()
                    .projectName("Abc")
                    .tasks(tasksDTO)
                    .build();

            Project project = Project.builder().projectName(request.getProjectName()).build();

            Mockito
                    .when(projectRepository.existsByProjectName(request.getProjectName()))
                    .thenReturn(false);

            Mockito
                    .when(projectRepository.save(
                            ArgumentMatchers.argThat(
                                    savedProject -> savedProject.getProjectName().equals(request.getProjectName())
                            )))
                    .thenReturn(project);

            service.create(request);
        }
    }

    @Nested
    @DisplayName("GetById method tests")
    class GetByIdMethodTest {

        @Test
        @DisplayName("Should throw NOT_FOUND if no such a project")
        public void shouldThrowExceptionWhenThereAreNoSuchProjects() {
            Long requestId = 1L;

            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = String.format("No projects with id [%s] found", requestId);

            Mockito
                    .when(projectRepository.findById(requestId))
                    .thenReturn(Optional.empty());

            Mockito
                    .verify(taskRepository,
                            Mockito.times(0))
                    .findAllByProject(ArgumentMatchers.any());

            var exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.getById(requestId)
            );

            Assertions.assertEquals(expectedStatus, exception.getStatus());
            Assertions.assertEquals(expectedMessage, exception.getReason());
        }

        @Test
        @DisplayName("should return dto with empty list of tasks")
        public void shouldReturnDTO() {
            Long requestId = 1L;

            Project project = Project
                    .builder()
                    .projectName("Abc")
                    .id(requestId)
                    .build();

            var expectedTaskSize = 0;
            var expectedProjectName = project.getProjectName();

            Mockito
                    .when(projectRepository.findById(requestId))
                    .thenReturn(Optional.of(project));


            Mockito
                    .when(taskRepository.findAllByProject(project))
                    .thenReturn(List.of());

            ProjectResponseDTO response = service.getById(requestId);

            Assertions.assertEquals(expectedTaskSize, response.getTasks().size());
            Assertions.assertEquals(expectedProjectName, response.getProjectName());
        }

    }

    @Nested
    @DisplayName("getAll method tests")
    class GetAllMethodTest {

        @Test
        @DisplayName("should return empty list if no projects")
        public void shouldReturnEmptyList() {

            var expectedSize = 0;
            Mockito
                    .when(projectRepository.findAll())
                    .thenReturn(List.of());
            Mockito
                    .verify(taskRepository,
                            Mockito.times(0))
                    .findAllByProject(ArgumentMatchers.any());


            var response = service.getAll();
            Assertions.assertEquals(expectedSize, response.size());
        }
    }
}
