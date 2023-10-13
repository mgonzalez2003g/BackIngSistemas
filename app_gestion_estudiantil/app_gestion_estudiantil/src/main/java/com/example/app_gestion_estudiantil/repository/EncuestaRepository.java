package com.example.app_gestion_estudiantil.repository;


import com.example.app_gestion_estudiantil.entity.Encuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EncuestaRepository {

    @Autowired
    private EncuestaCrudRepository encuestaCrudRepository;

    public List<Encuesta> findAll() {
        return (List<Encuesta>) encuestaCrudRepository.findAll();
    }

    public Optional<Encuesta> findById(Long id) {
        return encuestaCrudRepository.findById(id);
    }

    public Encuesta save(Encuesta encuesta) {
        return encuestaCrudRepository.save(encuesta);
    }

    public void deleteById( Long id){
         encuestaCrudRepository.deleteById(id);
    }

}

