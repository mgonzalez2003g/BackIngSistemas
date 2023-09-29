package com.example.app_gestion_estudiantil.repository;

import com.example.app_gestion_estudiantil.entity.Representante;
import com.example.app_gestion_estudiantil.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepresentanteRepository {
    @Autowired
    private RepresentanteCrudRepository representanteCrudRepository;

    public Representante save(Representante u){  return representanteCrudRepository.save(u);  }
    public List<Representante> getAll(){
        return (List<Representante>) representanteCrudRepository.findAll();
    }
}
