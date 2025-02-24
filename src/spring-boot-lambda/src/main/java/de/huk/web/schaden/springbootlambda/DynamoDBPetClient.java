/*
 * 12.02.2025 ap4768
 * Copyright (c) 2025 HUK-COBURG. All Rights Reserved.
 */
package de.huk.web.schaden.springbootlambda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Component
public class DynamoDBPetClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDBPetClient.class);
    private final DynamoDbTable<Pet> table;

    public DynamoDBPetClient(@Value("${dynamodb.table.id}") String tableName) {
        LOGGER.info("connecting to table: {}", tableName);
        var enhancedClient = DynamoDbEnhancedClient.create();
        this.table = enhancedClient.table(tableName, TableSchema.fromBean(Pet.class));
    }

    public void save(Pet pet) {
        this.table.putItem(pet);
    }

    public Pet getById(Long id) {
        var pet = new Pet();
        pet.setId(id);
        return this.table.getItem(pet);
    }
}
