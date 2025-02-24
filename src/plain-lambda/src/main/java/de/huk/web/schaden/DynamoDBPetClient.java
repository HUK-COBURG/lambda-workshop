/*
 * 12.02.2025 ap4768
 * Copyright (c) 2025 HUK-COBURG. All Rights Reserved.
 */
package de.huk.web.schaden;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class DynamoDBPetClient {
    private final DynamoDbTable<Pet> table;

    public DynamoDBPetClient(String tableName) {
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
