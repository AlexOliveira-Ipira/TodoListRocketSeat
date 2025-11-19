package dev.oliveiratec.todolistrocketseat.controller;

import dev.oliveiratec.todolistrocketseat.model.TaskModel;
import dev.oliveiratec.todolistrocketseat.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/created")
    public ResponseEntity create(@RequestBody TaskModel taskModel){
        var task = taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping()
    public List<TaskModel> list(){
        return taskRepository.findAll();
    }
}
