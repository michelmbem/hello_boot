package org.addy.hello_boot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private static long nextId = 0;

    private Long id = nextId--;

    @NotNull
    @Size(min=1, max=50)
    private String name;

    @NotNull
    @Min(0)
    private BigDecimal price;
}
