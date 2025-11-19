package dev.oliveiratec.todolistrocketseat.repository;

import dev.oliveiratec.todolistrocketseat.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

}
