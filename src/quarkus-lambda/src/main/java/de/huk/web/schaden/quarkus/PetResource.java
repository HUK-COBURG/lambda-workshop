/*
 * 14.02.2025 ap4768
 * Copyright (c) 2025 HUK-COBURG. All Rights Reserved.
 */
package de.huk.web.schaden.quarkus;

import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.extensions.Extension;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/pet")
public class PetResource {
    private final PetRepository petRepository;

    @Inject
    public PetResource(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    // tag::openapi[]
    @POST
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Pet created successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON)),
                    @APIResponse(
                            responseCode = "500",
                            description = "InternalServerError")})
    @Operation(
            summary = "Create a Pet",
            description = "Create a Pet with the given arguments")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Extension(name = OpenApiConstants.X_AMAZON_APIGATEWAY_INTEGRATION, value = OpenApiConstants.AMAZON_GATEWAY_INTEGRATION_CONTENTS, parseValue = true)
    // end::openapi[]
    public Response createPet(@NotNull Pet pet) {
        this.petRepository.createPet(pet);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{petId}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Pet retrieved successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Pet.class))),
                    @APIResponse(
                            responseCode = "404",
                            description = "Pet not found",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Pet.class))),
                    @APIResponse(
                            responseCode = "500",
                            description = "InternalServerError")})
    @Operation(
            summary = "Create a Pet",
            description = "Create a Pet with the given arguments")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Extension(name = OpenApiConstants.X_AMAZON_APIGATEWAY_INTEGRATION, value = OpenApiConstants.AMAZON_GATEWAY_INTEGRATION_CONTENTS, parseValue = true)
    public Response getPet(@NotNull @PathParam("petId") Long petId) {
        Pet pet = this.petRepository.getPetById(petId);

        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pet).build();
    }
}
