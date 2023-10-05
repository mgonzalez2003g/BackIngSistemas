package com.example.app_gestion_estudiantil.repository;

import com.example.app_gestion_estudiantil.entity.Archivo;
import com.example.app_gestion_estudiantil.entity.Foro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ArchivoRepository {

    @Autowired
    private ArchivoCrudRepository archivoCrudRepository;

    public Archivo save(Archivo a) {
        return archivoCrudRepository.save(a);
    }
    public Archivo update(Archivo a, Long id_foro) {
        if (id_foro != null) {
            Optional<Foro> existingForo = fororepository.findById(foro.getId());
            if (existingForo.isPresent()) {
                Foro foroToUpdate = existingForo.get();
                System.out.println(existingForo);
                System.out.println(foroToUpdate);

                if (foro.getContenido() != null) {
                    foroToUpdate.setContenido(contenido);
                }
                if (foro.getFecha() != null) {
                    foroToUpdate.setFecha(LocalDateTime.now());
                }
                if (foro.getFiles() != null) {
                    foroToUpdate.setFiles(foro.getFiles());
                }

                fororepository.save(foroToUpdate);
                return foroToUpdate;
            }
        }
        return null; // Devuelve null si no se pudo actualizar el foro
    }

}






