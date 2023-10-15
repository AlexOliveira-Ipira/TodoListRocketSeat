package dev.oliveiratec.todolistrocketseat.controller;

import dev.oliveiratec.todolistrocketseat.model.TaskModel;
import dev.oliveiratec.todolistrocketseat.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/created")
    public TaskModel create(@RequestBody TaskModel taskModel){
        var task = taskRepository.save(taskModel);
        return task;
    }
}
