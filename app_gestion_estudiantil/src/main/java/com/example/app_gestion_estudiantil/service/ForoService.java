package com.example.app_gestion_estudiantil.service;

import com.example.app_gestion_estudiantil.entity.Archivo;
import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.repository.ForoRepository;
import com.example.app_gestion_estudiantil.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ForoService {

    @Autowired
    private UserService userService;

    @Autowired
    private ForoRepository fororepository;

    @Autowired
    private ArchivoService archivoService;

    public void guardarForo(String contenido, MultipartFile archivo, Long idUsuario) throws IOException, ParseException {
        // Obtener el usuario por su ID
        User user = userService.getUser2(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Crear el foro
        Foro foro = Foro.builder()
                .contenido(contenido)
                .fecha(LocalDateTime.now())
                .build();
        // Guardar el foro en la base de datos
        fororepository.save(foro);
        foro.addUser(user);

        // Guardar el archivo si no está vacío
        Archivo archivoGuardado = null;
        if (archivo != null && !archivo.isEmpty()) {
            archivoGuardado = archivoService.leerArchivo(archivo, foro);
        }

        // Asociar el archivo al estado
        if (archivoGuardado != null) {
            foro.addFile(archivoGuardado);
            foro.addUser(user);
        }

    }

    public Foro getForo(Long id) {
        Optional<Foro> e = fororepository.findById(id);
        return e.orElse(null);
    }
    public Foro update(Foro foro) {
        if (foro.getId_foro() != null) {
            Optional<Foro> existingForo = fororepository.getForo(foro.getId_foro());
            if (existingForo.isPresent()) {
                Foro foroToUpdate = existingForo.get();

            if (foro.getContenido() != null) {
                foroToUpdate.setContenido(foro.getContenido());
            }
            if (foro.getFecha() != null) {
                foroToUpdate.setFecha(LocalDateTime.now());
            }
            if (foro.getFiles() != null) {
                foroToUpdate.setFiles(foro.getFiles());
            }
       //     if (foro.getReacciones() != null) {
       //         foroToUpdate.setReacciones(foro.getReacciones());
       //     }

            fororepository.save(foroToUpdate);
            return foroToUpdate;
        }
    }

    return foro;
    }

    public List<Foro> getall(){
        System.out.println("servicio");
        return (List<Foro>) fororepository.findAll();
    }
}
