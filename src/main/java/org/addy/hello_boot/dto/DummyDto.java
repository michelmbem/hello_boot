package org.addy.hello_boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description="Identifier of the dummy entity", example="1")
    private Long id;

    @NotNull
    @Size(min=1, max=50)
    @Schema(description="Name of the dummy entity", example="Red")
    private String name;

    @Size(max=200)
    @Schema(description="Description of the dummy entity", example="Red dummy entity")
    private String description;

    @Valid
    @Schema(description="The items attached to this dummy entity")
    private Set<DummyItemDto> items = new HashSet<>();
}
