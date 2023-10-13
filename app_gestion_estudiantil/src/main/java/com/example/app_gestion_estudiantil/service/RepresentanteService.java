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
                    .votos(0)
                    .build();

            representanterepository.save(representante);
        }
    }
    public void votar(Long id, Long repre) throws IOException, ParseException {
        List<Representante> lista = getall();
        int fea = 0; // Cambié Integer a int para simplificar la lógica.

        for (int i = 0; i < lista.size(); i++) {
            List<Long> votantes = lista.get(i).getVotantes();
            System.out.println(votantes);

            if (!votantes.isEmpty()) {
                for (int j = 0; j < votantes.size(); j++) {
                    if (id.equals(votantes.get(j))) {
                        fea = 1;
                        break; // Sal del bucle interno, ya que se encontró que el usuario votó.
                    }
                    if (fea == 1) {
                        break; // Sal del bucle externo, ya que el usuario ya votó por otro representante.
                    }
                }
            }
        }

        if (fea == 0) {
            Optional<Representante> r = getRepresentante(repre);
            if (r.isPresent()) {
                Representante r2 = r.get();
                System.out.println(r2.getVotos());
                r2.setVotos(r2.getVotos() + 1);
                r2.addVotante(id);
                System.out.println(r2.getVotos());
                representanterepository.save(r2);
            } else {
                throw new IllegalArgumentException("Representante no encontrado");
            }
        } else {
            throw new IllegalArgumentException("Ya votó");
        }
    }


    public Optional<Representante> getRepresentante(Long id) {
        return representanterepository.findById(id);

    }


    public List<Representante> getall(){
        return (List<Representante>) representanterepository.findAll();
    }

}
