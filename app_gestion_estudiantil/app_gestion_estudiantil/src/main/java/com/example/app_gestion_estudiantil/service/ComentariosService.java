package com.example.app_gestion_estudiantil.service;

import com.example.app_gestion_estudiantil.entity.Comentarios;
import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.repository.ComentariosRepository;
import com.example.app_gestion_estudiantil.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class ComentariosService {



    @Autowired
    private UserService userService;

    @Autowired
    private ComentariosRepository comentariosrepository;

    @Autowired
    private ForoService foroService;


    public void guardarComentarios(String contenido, Long idUsuario, Long idforo) throws IOException, ParseException {
        // Obtener el usuario por su ID
        User user = userService.getUser2(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Foro foro =foroService.getForo(idforo)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Crear el comentario
        Comentarios comentarios = Comentarios.builder()
                .contenido(contenido)
                .fecha(LocalDateTime.now())
                .user(user)
                .foro(foro)
                .build();
        // Guardar el comentario en la base de datos
        comentariosrepository.save(comentarios);
    }

    public Comentarios getComentarios(Long id) {
        Optional<Comentarios> e = comentariosrepository.findById(id);
        return e.orElse(null);
    }
}
