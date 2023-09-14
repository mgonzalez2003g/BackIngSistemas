package com.example.app_gestion_estudiantil.repository;

import com.example.app_gestion_estudiantil.entity.Comentarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ComentariosRepository {


    @Autowired
    private ComentariosCrudRepository comentariosCrudRepository;

    //public List<Comentarios> findAll() {
       // return (List<Comentarios>) comentariosCrudRepository.findAll();
    //}

    public Optional<Comentarios> findById(Long id) {
        return comentariosCrudRepository.findById(id);
    }

    public Comentarios save(Comentarios e) {
        return comentariosCrudRepository.save(e);

    }
}
