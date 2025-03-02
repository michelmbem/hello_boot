package org.addy.hello_boot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.addy.hello_boot.dto.DummyDto;
import org.addy.hello_boot.service.DummyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/dummy")
@RequiredArgsConstructor
public class DummyController {

    private final DummyService dummyService;

    @GetMapping
    public List<DummyDto> getAllDummies() {
        return dummyService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<DummyDto> getDummyById(@PathVariable Long id) {
        return dummyService.findById(id).map(ResponseEntity::ok).orElseThrow();
    }

    @GetMapping("of-name/{name}")
    public ResponseEntity<DummyDto> getDummyByName(@PathVariable String name) {
        return dummyService.findByName(name).map(ResponseEntity::ok).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<DummyDto> createDummy(@RequestBody @Valid DummyDto dummyDto) {
        dummyDto = dummyService.create(dummyDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dummyDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dummyDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<DummyDto> updateDummy(@PathVariable Long id, @RequestBody @Valid DummyDto dummyDto) {
        dummyService.update(id, dummyDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DummyDto> deleteDummy(@PathVariable Long id) {
        dummyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
