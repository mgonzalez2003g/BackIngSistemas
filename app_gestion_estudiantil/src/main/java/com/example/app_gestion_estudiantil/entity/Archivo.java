package com.example.app_gestion_estudiantil.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_archivo;
    private String nombre_archivo;
    private String url;

    @ManyToOne
    @JoinColumn(name = "foro_id")
    private Foro foro;



    @Override
    public String toString() {
        return "Archivo{" +
                "id_archivo=" + id_archivo +
                ", nombre_archivo='" + nombre_archivo + '\'' +
                ", url='" + url + '\'' +
                ", foroId=" + (foro != null ? foro.getId() : null) +
                '}';
    }


}
