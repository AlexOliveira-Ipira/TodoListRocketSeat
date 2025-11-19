package dev.oliveiratec.todolistrocketseat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String title;
    private Boolean completed = false;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Boolean delete = false;
    private LocalDateTime dateDelete;
//    private String description;

//    private String priority;
//
//    private UUID idUser;

//    @CreationTimestamp
//    private LocalDateTime createdAt;



}
