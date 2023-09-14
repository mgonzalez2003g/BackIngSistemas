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

    public Optional <Foro> getForo(Long id) {
        return fororepository.findById(id);
    }

    public List<Foro> getallforo(){
        System.out.println("servicio");
        return fororepository.findAll();
    }
}
