package com.example.app_gestion_estudiantil.entity;

import com.example.app_gestion_estudiantil.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Representante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String propuesta;

    @OneToMany(mappedBy = "representante",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("representante")
    private List<User> listar;

    public void addrepresentante(User user) {
        if (listar == null) {
            listar = new ArrayList<>();
        }
        listar.add(user);
        user.setRepresentante(this);
    }

}
