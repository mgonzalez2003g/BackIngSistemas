package com.example.app_gestion_estudiantil.repository;

import com.example.app_gestion_estudiantil.entity.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncuestaCrudRepository extends CrudRepository<Encuesta, Long> {

    Optional<Encuesta> findById(Long id);
     void deleteById(Long id);
}

