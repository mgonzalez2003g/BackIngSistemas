package com.example.app_gestion_estudiantil.service;

import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.entity.Representante;
import com.example.app_gestion_estudiantil.repository.RepresentanteRepository;
import com.example.app_gestion_estudiantil.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentanteService {
    @Autowired
    private RepresentanteRepository representanteRepository;

    public Representante save(Representante r) {
        return representanteRepository.save(r);
    }

    public List<Representante> getall(){
        return (List<Representante>) representanteRepository.getAll();
    }
}
