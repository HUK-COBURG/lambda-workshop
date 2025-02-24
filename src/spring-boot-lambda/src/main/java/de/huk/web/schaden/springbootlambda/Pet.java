/*
 * 12.02.2025 ap4768
 * Copyright (c) 2025 HUK-COBURG. All Rights Reserved.
 */
package de.huk.web.schaden.springbootlambda;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Pet {
    private Long id;
    private String name;

    @DynamoDbPartitionKey
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
