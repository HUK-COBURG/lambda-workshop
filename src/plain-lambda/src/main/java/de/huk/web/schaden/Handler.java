/*
 * 11.02.2025 ap4768
 * Copyright (c) 2025 HUK-COBURG. All Rights Reserved.
 */
package de.huk.web.schaden;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// tag::handler[]
public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    // end::handler[]
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private DynamoDBPetClient petClient;

    // tag::initialization[]
    public Handler() {
        var dynamodbTableId = System.getenv("DYNAMODB_TABLE_ID");
        if (dynamodbTableId == null) {
            System.err.println("could not determine dynamodb table id, defaulting");
            dynamodbTableId = "my-dynamodb-table";
        }
        this.petClient = new DynamoDBPetClient(dynamodbTableId);
    }
    // end::initialization[]

    // tag::handle_request[]
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        context.getLogger().log("event: " + event);
        return switch (event.getHttpMethod()) {
            case "GET" -> getPet(event);
            case "POST" -> createPet(event);
            default -> new APIGatewayProxyResponseEvent().withStatusCode(405);
        };
    }
    // end::handle_request[]

    private APIGatewayProxyResponseEvent createPet(APIGatewayProxyRequestEvent request) {
        try {
            Pet pet = OBJECT_MAPPER.readValue(request.getBody(), Pet.class);

            this.petClient.save(pet);

            return new APIGatewayProxyResponseEvent().withStatusCode(201);
        } catch (JsonProcessingException e) {
            return new APIGatewayProxyResponseEvent().withStatusCode(500);
        }
    }

    // tag::get_pet[]
    private APIGatewayProxyResponseEvent getPet(APIGatewayProxyRequestEvent request) {
        var petId = request.getPathParameters().get("petId");
        Pet pet = this.petClient.getById(Long.parseLong(petId));

        if (pet == null) {
            return new APIGatewayProxyResponseEvent().withStatusCode(404);
        } else {
            try {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(200)
                        .withIsBase64Encoded(false)
                        .withBody(OBJECT_MAPPER.writeValueAsString(pet));
            } catch (JsonProcessingException e) {
                return new APIGatewayProxyResponseEvent().withStatusCode(500);
            }
        }
    }
    // end::get_pet[]
}
