package de.telran.project_management_system.controller;

import de.telran.project_management_system.dto.request.ProjectRequestDTO;
import de.telran.project_management_system.dto.response.ProjectResponseDTO;
import de.telran.project_management_system.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService service;

    @PostMapping("/projects")
    public void create (@RequestBody @Valid ProjectRequestDTO request){
        service.create(request);
    }

    @GetMapping("/projects/{projectId}")
    public ProjectResponseDTO getById(@PathVariable("projectId") Long id){
        return service.getById(id);
    }

    @GetMapping("/projects")
    public List<ProjectResponseDTO> getAll(){
        return service.getAll();
    }
}
