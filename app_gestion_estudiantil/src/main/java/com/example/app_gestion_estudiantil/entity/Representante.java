package com.example.app_gestion_estudiantil.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer votos=0;


    public void addvote(){
        votos++;
    }
}
