package com.example.app_gestion_estudiantil.repository;

import com.example.app_gestion_estudiantil.entity.Foro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForoCrudRepository extends CrudRepository <Foro,Long>{



}
