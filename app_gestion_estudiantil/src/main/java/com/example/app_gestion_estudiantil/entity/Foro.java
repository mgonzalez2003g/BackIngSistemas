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

public class Foro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id_foro;
    private String contenido;
    private LocalDateTime fecha;
    private Integer reacciones;

    @OneToMany(mappedBy = "foro",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Archivo> files = new ArrayList<>();

    @ManyToMany(mappedBy = "foros")
    @JsonIgnoreProperties("foro")
    private List<User> users;

    //private String comentarios;







}
