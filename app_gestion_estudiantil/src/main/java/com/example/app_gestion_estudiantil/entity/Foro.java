package com.example.app_gestion_estudiantil.entity;


import com.example.app_gestion_estudiantil.user.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Foro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String contenido;
    private LocalDateTime fecha;
    private Integer reacciones;
    @ElementCollection
    private List<String> files;

    @ManyToMany(mappedBy = "foros")
    @JsonIgnoreProperties("foros")
    private List<User> users;

    //private String comentarios;

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        user.addForo(this);
    }

    public void addFile(String file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
    }

    @Override
    public String toString() {
        return "Foro{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                ", fecha=" + fecha +
                ", reacciones=" + reacciones +
                ", files=" + files +
                ", users=" + users +
                '}';
    }
}