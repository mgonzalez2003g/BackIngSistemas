package com.example.app_gestion_estudiantil.entity;

import com.example.app_gestion_estudiantil.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Foro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id_foro;
    private String contenido;
    private LocalDateTime fecha;
    private Integer Like;

    @OneToMany(mappedBy = "foro",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Archivo> files;

    @ManyToMany(mappedBy = "foros")
    @JsonIgnoreProperties("foro")
    private List<User> users;

    //private String comentarios;

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        user.addForo(this);
    }

    public void addFile(Archivo file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
        file.setForo(this);
    }
}
