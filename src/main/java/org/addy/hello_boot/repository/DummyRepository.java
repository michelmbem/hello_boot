package org.addy.hello_boot.repository;

import org.addy.hello_boot.model.Dummy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DummyRepository extends JpaRepository<Dummy, Long> {
    Optional<Dummy> findByName(String name);
}
