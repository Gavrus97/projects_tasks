package de.telran.project_management_system.controller;

import de.telran.project_management_system.dto.request.AddTaskRequestDTO;
import de.telran.project_management_system.dto.response.TaskResponseDTO;
import de.telran.project_management_system.entity.types.ProgressType;
import de.telran.project_management_system.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/tasks")
    public void addTaskToProject(@RequestBody @Valid AddTaskRequestDTO request) {
        service.addTaskToProject(request);
    }

    @PutMapping("/tasks/forward/{taskId}")
    public void pushTaskForward(@PathVariable("taskId")
                                @Positive(message = "id must be positive number") Long id) {

        service.pushTaskForward(id);
    }

    @PutMapping("/tasks/backward/{taskId}")
    public void pushTaskBackward(@PathVariable("taskId")
                                 @Positive(message = "id must be positive number") Long id) {
      service.pushTaskBackward(id);
    }

    @GetMapping("/tasks")
    public List<TaskResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/tasks/{name}")
    List<TaskResponseDTO> getByName(@PathVariable("name") String name) {
        return service.getByName(name);
    }

    @GetMapping("/tasks/type")
    List<TaskResponseDTO> getByStatus(@RequestParam("status") ProgressType type) {
        return service.getByStatus(type);
    }

    @GetMapping("/tasks/abandoned")
    List<TaskResponseDTO> findAbandoned() {
        return service.findAbandoned();
    }


}
