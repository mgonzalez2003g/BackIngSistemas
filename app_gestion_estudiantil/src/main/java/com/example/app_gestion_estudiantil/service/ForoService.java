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
                .reacciones(0)
                .build();

        // Guardar el foro en la base de datos
        Foro foroGuardado = fororepository.save(foro); // El ID del foro se generará automáticamente

        // Guardar el archivo si no está vacío y asociarlo con el foro
        if (archivo != null && !archivo.isEmpty()) {
            Archivo archivoGuardado = archivoService.leerArchivo(archivo, foroGuardado);
            if (archivoGuardado != null) {
                String url = archivoGuardado.getNombre_archivo();
                // Agregar la ruta del archivo a la lista de archivos en el foro
                foroGuardado.addFile(url);
            }
        }

        // Asociar el usuario al foro
        foroGuardado.addUser(user);

        // Guardar el foro actualizado en la base de datos
        fororepository.save(foroGuardado);
    }


    public Foro getForo(Long id) {
        Optional<Foro> e = fororepository.findById(id);
        return e.orElse(null);
    }

    /*
    public Foro update(String contenido, MultipartFile archivo, Long id) {
        System.out.println("servicio");
        Foro foro = getForo(id);
        archivoService.actualizarArchivo(archivo,foro);
        if (foro.getId() != null) {
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
*/
    public List<Foro> getall(){
        return (List<Foro>) fororepository.findAll();
    }
}