package org.addy.hello_boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.math.BigDecimal;

@Entity
@Table(name="DUMMY_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of= "id")
public class DummyItem {
    @Id
    @Generated(event=EventType.INSERT)
    private Long id;

    @NotNull
    @Size(min=1, max=50)
    private String name;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotNull
    @ManyToOne
    @JoinColumn(name="PARENT_ID")
    private Dummy parent;
}
