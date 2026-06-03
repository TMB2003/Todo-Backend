package com.learning.TodoList.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@NoArgsConstructor
@Data
public class User {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    @Indexed(unique = true)
    private String email;
    @NonNull
    private String password;

    @DBRef
    private List<Task> taskList = new ArrayList<>();
}
