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

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Encuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_encuesta;
    private String titulo;
    private String pregunta1;
    private String pregunta2;
    private String pregunta3;
    private String pregunta4;
    private boolean esOpcionUnica1;
    private boolean esOpcionUnica2;
    private boolean esOpcionUnica3;
    private boolean esOpcionUnica4;
    private String opciones1;
    private String opciones2;
    private String opciones3;
    private String opciones4;
    private LocalDateTime fecha;

    @ManyToMany(mappedBy = "encuestas")
    @JsonIgnoreProperties("encuestas")
    private List<User> users;

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        user.addEncuesta(this);
    }
}
