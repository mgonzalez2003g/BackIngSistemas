package com.example.app_gestion_estudiantil.repository;

import com.example.app_gestion_estudiantil.entity.Archivo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivoRepository extends CrudRepository<Archivo,Long>{}






