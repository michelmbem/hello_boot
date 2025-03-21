package org.addy.hello_boot.service;

import org.addy.hello_boot.dto.DummyDto;

import java.util.List;
import java.util.Optional;

public interface DummyService {
    List<DummyDto> findAll();
    List<DummyDto> findByNameContainingIgnoreCase(String namePart);
    Optional<DummyDto> findById(Long id);
    Optional<DummyDto> findByName(String name);
    DummyDto create(DummyDto dummyDto);
    void update(Long id, DummyDto dummyDto);
    void delete(Long id);
}
