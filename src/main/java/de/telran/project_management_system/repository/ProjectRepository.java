package de.telran.project_management_system.repository;

import de.telran.project_management_system.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByProjectName(String projectName);
}
