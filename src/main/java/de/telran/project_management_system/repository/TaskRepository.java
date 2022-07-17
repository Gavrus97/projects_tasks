package de.telran.project_management_system.repository;

import de.telran.project_management_system.entity.Project;
import de.telran.project_management_system.entity.Task;
import de.telran.project_management_system.entity.types.ProgressType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByProject(Project project);

    List<Task> findAllByTaskName(String name);

    List<Task> findAllByProgressTypeAndUpdatedOnIsBefore(ProgressType type, LocalDateTime date);

    List<Task> findAllByProgressType(ProgressType type);
}
