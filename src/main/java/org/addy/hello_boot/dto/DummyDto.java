package org.addy.hello_boot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class DummyDto {
    private static long nextId = 0;

    private Long id = nextId--;

    @NotNull
    @Size(min=1, max=50)
    private String name;

    @Size(max=200)
    private String description;

    @Valid
    private Set<DummyItemDto> items = new HashSet<>();
}
