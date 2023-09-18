package com.example.app_gestion_estudiantil.entity;

import com.example.app_gestion_estudiantil.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.hibernate.annotations.Formula;

import java.util.List;

public class Reaction {

    private List<User> likes;


    @Formula("(select count(user_id) from Likes l where l.post_id = post_id)")
    private int Like;




}