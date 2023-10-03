package com.example.app_gestion_estudiantil.repository;

import com.example.app_gestion_estudiantil.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotacionesCRUDRepository extends JpaRepository<Voto,Long> {

}
