package com.example.app_gestion_estudiantil.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.app_gestion_estudiantil.user.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Representante {

    @Id
    private Long id;
    private String firstname;
    private String description;
    private String url;
    private Integer votos;
    @ElementCollection
    private List<Long> votantes;


    public void addvote(){
        votos++;
    }

    public void addVotante(Long user) {
        if (votantes == null) {
            votantes = new ArrayList<>();
        }
        votantes.add(user);
    }
}
