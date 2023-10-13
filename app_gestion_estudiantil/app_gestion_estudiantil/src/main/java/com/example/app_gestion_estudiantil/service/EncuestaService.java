package com.example.app_gestion_estudiantil.service;

import com.example.app_gestion_estudiantil.entity.Encuesta;
import com.example.app_gestion_estudiantil.repository.EncuestaCrudRepository;
import com.example.app_gestion_estudiantil.repository.EncuestaRepository;
import com.example.app_gestion_estudiantil.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EncuestaService {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @Autowired
    private UserService userService;

    public List<Encuesta> obtenerEncuestas() {
        return encuestaRepository.findAll();
    }

    public Optional<Encuesta> obtenerEncuestaPorId(Long id) {
        return encuestaRepository.findById(id);
    }

    public void crearEncuesta(String titulo, String pregunta1, String pregunta2, String pregunta3, String pregunta4,
                              boolean esOpcionUnica1, boolean esOpcionUnica2, boolean esOpcionUnica3, boolean esOpcionUnica4,
                              String opciones1, String opciones2, String opciones3, String opciones4, Long idUsuario) {
        // Obtener el usuario por su ID
        User user = userService.getUser2(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Dividir las opciones separadas por comas en listas
        List<String> opcionesPregunta1 = Arrays.asList(opciones1.split(","));
        List<String> opcionesPregunta2 = Arrays.asList(opciones2.split(","));
        List<String> opcionesPregunta3 = Arrays.asList(opciones3.split(","));
        List<String> opcionesPregunta4 = Arrays.asList(opciones4.split(","));

        // Crear la encuesta
        Encuesta encuesta = Encuesta.builder()
                .titulo(titulo)
                .pregunta1(pregunta1)
                .pregunta2(pregunta2)
                .pregunta3(pregunta3)
                .pregunta4(pregunta4)
                .esOpcionUnica1(esOpcionUnica1)
                .esOpcionUnica2(esOpcionUnica2)
                .esOpcionUnica3(esOpcionUnica3)
                .esOpcionUnica4(esOpcionUnica4)
                .opciones1(opcionesPregunta1.toString())
                .opciones2(opcionesPregunta2.toString())
                .opciones3(opcionesPregunta3.toString())
                .opciones4(opcionesPregunta4.toString())
                .fecha(LocalDateTime.now())
                .build();

        // Guardar la encuesta en la base de datos
        Encuesta encuestaGuardada = encuestaRepository.save(encuesta);

        // Asociar el usuario a la encuesta
        encuestaGuardada.addUser(user);
    }

    public void eliminarEncuesta(Long id) {
        encuestaRepository.deleteById(id);
    }


    }


