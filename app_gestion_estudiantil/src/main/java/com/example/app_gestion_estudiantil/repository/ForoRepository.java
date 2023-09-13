package com.example.app_gestion_estudiantil.repository;

import com.example.app_gestion_estudiantil.entity.Foro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ForoRepository {

    @Autowired
    private ForoCrudRepository foroCrudRepository;

    public List<Foro> findAll(){
        return (List<Foro>) foroCrudRepository.findAll();
    }

    public Foro save(Foro e){
        return foroCrudRepository.save(e);
    }

}
