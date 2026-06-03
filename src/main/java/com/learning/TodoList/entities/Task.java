package com.learning.TodoList.entities;


import com.learning.TodoList.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "tasks")
@Data
@NoArgsConstructor
public class Task {
    @Id
    private String id;
    @NonNull
    private String name;
    private String description;
    @NonNull
    private String userId;
    private Status status;
    private LocalDate DeadLine;
    private LocalDateTime EntryTime;
}
