package org.addy.hello_boot.service.impl;

import lombok.RequiredArgsConstructor;
import org.addy.hello_boot.dto.DummyDto;
import org.addy.hello_boot.mapper.DummyMapper;
import org.addy.hello_boot.model.Dummy;
import org.addy.hello_boot.repository.DummyRepository;
import org.addy.hello_boot.service.DummyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DummyServiceImpl implements DummyService {
    private final DummyRepository dummyRepository;
    private final DummyMapper dummyMapper;

    @Override
    public List<DummyDto> findAll() {
        return dummyRepository.findAll().stream().map(dummyMapper::toDto).toList();
    }

    @Override
    public List<DummyDto> findByNameContainingIgnoreCase(String namePart) {
        return dummyRepository.findByNameContainingIgnoreCase(namePart).stream().map(dummyMapper::toDto).toList();
    }

    @Override
    public Optional<DummyDto> findById(Long id) {
        return dummyRepository.findById(id).map(dummyMapper::toDto);
    }

    @Override
    public Optional<DummyDto> findByName(String name) {
        return dummyRepository.findByName(name).map(dummyMapper::toDto);
    }

    @Override
    public DummyDto create(DummyDto dummyDto) {
        Dummy dummy = dummyMapper.fromDto(dummyDto);
        dummy = dummyRepository.save(dummy);

        return dummyMapper.toDto(dummy);
    }

    @Override
    public void update(Long id, DummyDto dummyDto) {
        dummyRepository.findById(id).ifPresentOrElse(
                dummy -> {
                    dummyMapper.updateFromDto(dummyDto, dummy);
                    dummyRepository.save(dummy);
                },
                () -> {
                    throw new NoSuchElementException("Could not find Dummy with id " + id);
                });
    }

    @Override
    public void delete(Long id) {
        dummyRepository.findById(id).ifPresentOrElse(
                dummyRepository::delete,
                () -> {
                    throw new NoSuchElementException("Could not find Dummy with id " + id);
                });
    }
}
