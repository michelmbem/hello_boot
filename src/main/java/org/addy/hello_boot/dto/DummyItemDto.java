package org.addy.hello_boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class DummyItemDto {
    @Schema(description="Identifier of the dummy item", example="1")
    private Long id;

    @NotNull
    @Size(min=1, max=50)
    @Schema(description="Name of the dummy item", example="Red item #1")
    private String name;

    @NotNull
    @Positive
    @Schema(description="Price of the dummy item", example="100", $comment="Should always be positive")
    private BigDecimal price;
}
