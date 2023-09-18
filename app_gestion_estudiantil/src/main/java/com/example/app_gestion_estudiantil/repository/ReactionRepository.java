package com.example.app_gestion_estudiantil.repository;


import com.example.app_gestion_estudiantil.entity.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReactionRepository {

    @Autowired

    private ForoCrudRepository forocrudrepository;


    public List<Reaction> getUser(Long id) }
}
