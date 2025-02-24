/*
 * 13.02.2025 ap4768
 * Copyright (c) 2025 HUK-COBURG. All Rights Reserved.
 */
package de.huk.web.schaden.springbootlambda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("pet")
@RestController
public class PetController {
    private final DynamoDBPetClient petClient;

    @Autowired
    public PetController(DynamoDBPetClient petClient) {
        this.petClient = petClient;
    }

    @PostMapping
    public ResponseEntity<String> createPet(@RequestBody Pet pet) {
        this.petClient.save(pet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{petId}")
    public ResponseEntity<Pet> getPet(@PathVariable("petId") Long petId) {
        var pet = this.petClient.getById(petId);
        if (pet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(pet, HttpStatus.OK);
    }
}
