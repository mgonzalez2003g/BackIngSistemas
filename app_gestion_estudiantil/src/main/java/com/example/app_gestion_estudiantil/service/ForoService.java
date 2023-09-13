package com.example.app_gestion_estudiantil.service;

import com.example.app_gestion_estudiantil.entity.Archivo;
import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.repository.ArchivoRepository;
import com.example.app_gestion_estudiantil.repository.ForoRepository;
import com.example.app_gestion_estudiantil.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForoService {


    @Autowired
    private UserService userService;

    @Autowired
    private ForoRepository repository;

    public void guardarForo(String contenido, MultipartFile archivo, Long idUsuario) throws IOException, ParseException {
        // Obtener el usuario por su ID
        User user = userService.getUser2(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Crear el estado y asociar el usuario
        Foro foro = Foro.builder()
                .contenido(contenido)
                .users((List<User>) user)
                .fecha(LocalDateTime.now())
                .build();
        // Guardar el estado en la base de datos
        ForoRepository.save(foro);

        // Guardar el archivo si no está vacío
        Archivo archivoGuardado = null;
        if (archivo != null && !archivo.isEmpty()) {
            archivoGuardado = ArchivoService.leerArchivo(archivo, foro);
        }

        // Asociar el archivo al estado
        if (archivoGuardado != null) {
            foro.addFile(archivoGuardado);
        }

    }


}
