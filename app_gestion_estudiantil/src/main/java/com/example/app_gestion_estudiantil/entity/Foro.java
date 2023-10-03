    package com.example.app_gestion_estudiantil.entity;


    import com.example.app_gestion_estudiantil.user.*;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor

    public class Foro {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)

        private Long id;
        private String contenido;
        private LocalDateTime fecha;
        //private Integer reacciones;

        @OneToMany(mappedBy = "foro" )
        @JsonIgnoreProperties("foro")
        private List<Archivo> files;

        @ManyToMany(mappedBy = "foros")
        @JsonIgnoreProperties("foros")
        private List<User> users;

        //private String comentarios;

        public void addUser(User user) {
            if (users == null) {
                users = new ArrayList<>();
            }
            users.add(user);
            user.addForo(this);
        }

        public void addFile(Archivo file) {
            if (files == null) {
                files = new ArrayList<>();
            }
            files.add(file);
            file.setForo(this);
        }


        @Override
        public String toString() {
            StringBuilder archivosString = new StringBuilder();
            if (files != null) {
                for (Archivo archivo : files) {
                    archivosString.append("Archivo{id_archivo=").append(archivo.getId_archivo())
                            .append(", nombre_archivo=").append(archivo.getNombre_archivo())
                            .append(", url=").append(archivo.getUrl())
                            .append("}, ");
                }
            }

            StringBuilder usuariosString = new StringBuilder();
            if (users != null) {
                for (User user : users) {
                    usuariosString.append("User{id=").append(user.getId()).append(", nombre=").append(user.getFirstname()).append("}, ");
                }
            }

            return "Foro{" +
                    "id=" + id +
                    ", contenido='" + contenido + '\'' +
                    ", fecha=" + fecha +
                    ", archivos=[" + archivosString.toString() +
                    "], users=[" + usuariosString.toString() +
                    "]}";
        }

    }
