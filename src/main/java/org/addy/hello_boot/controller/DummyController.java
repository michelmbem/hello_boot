package org.addy.hello_boot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.addy.hello_boot.controller.advice.GlobalErrorHandler;
import org.addy.hello_boot.dto.DummyDto;
import org.addy.hello_boot.service.DummyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/dummy")
@Tag(name="Dummy API", description="Dummy entity operations")
@RequiredArgsConstructor
public class DummyController {

    private final DummyService dummyService;

    @GetMapping
    @Operation(summary="Get the list of all dummies")
    @ApiResponse(responseCode="200", description="Dummies successfully fetched",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=DummyDto[].class)))
    @ApiResponse(responseCode="500", description="Something went wrong",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    public List<DummyDto> getAllDummies() {
        return dummyService.findAll();
    }

    @GetMapping("of-name-part/{namePart}")
    @Operation(summary="Get the list of all dummies that have the given part in their name")
    @ApiResponse(responseCode="200", description="Dummies successfully fetched",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=DummyDto[].class)))
    @ApiResponse(responseCode="500", description="Something went wrong",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    public List<DummyDto> getDummiesByNamePart(@PathVariable String namePart) {
        return dummyService.findByNameContainingIgnoreCase(namePart);
    }

    @GetMapping("{id}")
    @Operation(summary="Get the dummy that has the given id")
    @ApiResponse(responseCode="200", description="Dummy successfully fetched",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=DummyDto.class)))
    @ApiResponse(responseCode="404", description="Dummy not found",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    @ApiResponse(responseCode="500", description="Something went wrong",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    public ResponseEntity<DummyDto> getDummyById(@PathVariable Long id) {
        return dummyService.findById(id).map(ResponseEntity::ok).orElseThrow();
    }

    @GetMapping("of-name/{name}")
    @Operation(summary="Get the dummy that has the given name")
    @ApiResponse(responseCode="200", description="Dummy successfully fetched",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=DummyDto.class)))
    @ApiResponse(responseCode="404", description="Dummy not found",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    @ApiResponse(responseCode="500", description="Something went wrong",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    public ResponseEntity<DummyDto> getDummyByName(@PathVariable String name) {
        return dummyService.findByName(name).map(ResponseEntity::ok).orElseThrow();
    }

    @PostMapping
    @Operation(summary="Register a new dummy")
    @ApiResponse(responseCode="201", description="Dummy successfully created",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=DummyDto.class)))
    @ApiResponse(responseCode="400", description="Data validation failed",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    @ApiResponse(responseCode="500", description="Something went wrong",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    public ResponseEntity<DummyDto> createDummy(@Valid @RequestBody DummyDto dummyDto) {
        dummyDto = dummyService.create(dummyDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dummyDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dummyDto);
    }

    @PutMapping("{id}")
    @Operation(summary="Update the dummy that has the given id")
    @ApiResponse(responseCode="204", description="Dummy successfully updated", content=@Content(mediaType="application/json"))
    @ApiResponse(responseCode="400", description="Data validation failed",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    @ApiResponse(responseCode="404", description="Dummy not found",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    @ApiResponse(responseCode="500", description="Something went wrong",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    public ResponseEntity<DummyDto> updateDummy(@PathVariable Long id, @Valid @RequestBody DummyDto dummyDto) {
        dummyService.update(id, dummyDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    @Operation(summary="Delete the dummy that has the given id")
    @ApiResponse(responseCode="204", description="Dummy successfully deleted", content=@Content(mediaType="application/json"))
    @ApiResponse(responseCode="404", description="Dummy not found",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    @ApiResponse(responseCode="500", description="Something went wrong",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=GlobalErrorHandler.ApiErrorResponse.class)))
    public ResponseEntity<DummyDto> deleteDummy(@PathVariable Long id) {
        dummyService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
