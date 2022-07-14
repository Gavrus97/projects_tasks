package de.telran.project_management_system.controller;

import de.telran.project_management_system.dto.request.AddTaskRequestDTO;
import de.telran.project_management_system.dto.request.PushTaskRequestDTO;
import de.telran.project_management_system.dto.response.TaskResponseDTO;
import de.telran.project_management_system.entity.types.ProgressType;
import de.telran.project_management_system.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/tasks")
    public void addTaskToProject(@RequestBody @Valid AddTaskRequestDTO request){
        service.addTaskToProject(request);
    }

    @PutMapping("/tasks")
    void pushTask(@RequestBody @Valid PushTaskRequestDTO request){
        service.pushTask(request);
    }

    @GetMapping("/tasks")
    public List<TaskResponseDTO> getAll(){
        return service.getAll();
    }

    @GetMapping("/tasks/{name}")
    List<TaskResponseDTO> getByName(@PathVariable("name") String name){
        return service.getByName(name);
    }

    @GetMapping("/tasks/type")
    TaskResponseDTO getByStatus(@RequestBody ProgressType type){
        //TODO
        return null;
    }

    @GetMapping("/tasks/abandoned")
    List<TaskResponseDTO> findAbandoned(){
        return service.findAbandoned();
    }



}
