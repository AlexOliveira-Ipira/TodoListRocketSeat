package dev.oliveiratec.todolistrocketseat.controller;

import dev.oliveiratec.todolistrocketseat.model.TaskModel;
import dev.oliveiratec.todolistrocketseat.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/created")
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel){
        taskModel.setStartAt(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskRepository.save(taskModel));
    }

    @PutMapping("/completed/{uuid}")
    public void update(@PathVariable UUID uuid){
        TaskModel taskUpdate = taskRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        taskUpdate.setCompleted(true);
        taskUpdate.setEndAt(LocalDateTime.now());
        taskRepository.save(taskUpdate);
    }

    @GetMapping()
    public List<TaskModel> list(){
        return taskRepository.findAll();
    }

    @DeleteMapping("/delete/{uuid}") void delete(@PathVariable UUID uuid){
        TaskModel taskDelete = taskRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        if(taskDelete.getCompleted().equals(true))
            throw  new RuntimeException("Tarefa concluida, exclusão não permitida.");

        taskDelete.setDelete(true);
        taskDelete.setDateDelete(LocalDateTime.now());
        taskRepository.save(taskDelete);
    }
}
