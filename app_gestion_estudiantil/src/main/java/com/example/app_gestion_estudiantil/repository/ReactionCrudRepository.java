package com.example.app_gestion_estudiantil.repository;

import com.example.app_gestion_estudiantil.entity.Reaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionCrudRepository extends CrudRepository <Reaction, Long> {
}
