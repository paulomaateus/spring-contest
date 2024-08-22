package com.PauloMoreira.contest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PauloMoreira.contest.dto.ReuDTO;
import com.PauloMoreira.contest.service.ReuService;

@RestController
@RequestMapping("/reu")
public class ReuController {

    @Autowired
    private ReuService reuService;

    @PostMapping
    public ResponseEntity<ReuDTO> createReu(@RequestBody ReuDTO reu) {
        ReuDTO saved = reuService.createReu(reu);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
