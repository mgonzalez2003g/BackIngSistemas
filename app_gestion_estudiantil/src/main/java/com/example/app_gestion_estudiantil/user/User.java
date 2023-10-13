package com.example.app_gestion_estudiantil.user;

import com.example.app_gestion_estudiantil.entity.Foro;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(unique = true)
    private String apodo;
    private String firstname;
    private String lastname;
    //@Column(unique = true)
    private String email;
    private String password;
    private String carrera;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "reel_foro_user",
            joinColumns = @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "foro_id", nullable = false, referencedColumnName = "id")
    )
    @JsonIgnoreProperties("users")
    private List<Foro> foros;


    public void addForo(Foro foro) {
        if (foros == null) {
            foros = new ArrayList<>();
        }
        foros.add(foro);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder forosString = new StringBuilder();
        if (foros != null) {
            for (Foro foro : foros) {
                forosString.append("Foro{id=").append(foro.getId()).append(", contenido=").append(foro.getContenido()).append("}, ");
            }
        }


        return "User{" +
                "id=" + id +
                ", apodo='" + apodo + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", foros=[" + forosString.toString() +
                '}';
    }

}
