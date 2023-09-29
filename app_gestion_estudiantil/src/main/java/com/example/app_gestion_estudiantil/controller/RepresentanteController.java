package com.example.app_gestion_estudiantil.controller;

import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.entity.Representante;
import com.example.app_gestion_estudiantil.service.RepresentanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/representante")
public class RepresentanteController {
    @Autowired
    private RepresentanteService representanteService;


    @GetMapping("/getall")
    public List<Representante> getall(){
        return  representanteService.getall();
    }



}
