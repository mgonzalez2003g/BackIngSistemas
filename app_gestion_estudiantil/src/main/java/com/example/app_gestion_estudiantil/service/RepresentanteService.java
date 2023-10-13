package com.example.app_gestion_estudiantil.service;

import aj.org.objectweb.asm.Opcodes;
import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.entity.Representante;
import com.example.app_gestion_estudiantil.repository.RepresentanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class RepresentanteService {

    @Autowired
    private RepresentanteRepository representanterepository;


    public void guardarRepresentante(Long id, String firstname, String description, String url) throws IOException, ParseException {

        Optional<Representante> existingRepresentante = getRepresentante(id);

        if (existingRepresentante.isPresent()) {
            throw new IllegalArgumentException("El representante con ID " + id + " ya existe");
        } else {
            Representante representante = Representante.builder()
                    .id(id)
                    .firstname(firstname)
                    .description(description)
                    .url(url)
                    .build();

            representanterepository.save(representante);
        }
    }

    public Optional<Representante> getRepresentante(Long id) {
        return representanterepository.findById(id);

    }


    public List<Representante> getall(){
        return (List<Representante>) representanterepository.findAll();
    }

}
