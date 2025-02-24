/*
 * 29.02.2024, ap4212
 * Copyright (c) 2024 HUK-COBURG. All Rights Reserved.
 */
package de.huk.web.schaden.quarkus;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@ApplicationScoped
public class PetRepository {
    private DynamoDbTable<Pet> petTable;
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @ConfigProperty(name = "dynamodb.table.id", defaultValue = "")
    String tableName;

    @Inject
    public PetRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
    }

    @PostConstruct
    void init() {
        this.petTable = dynamoDbEnhancedClient.table(this.tableName, TableSchema.fromClass(Pet.class));
    }

    public void createPet(Pet pet) {
        // store it
        petTable.putItem(pet);
    }

    public Pet getPetById(Long id) {
        Pet key = new Pet();
        key.setId(id);
        return petTable.getItem(key);
    }
}
