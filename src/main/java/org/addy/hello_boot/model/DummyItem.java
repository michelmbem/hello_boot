package org.addy.hello_boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="DUMMY_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DummyItem {
    @Id
    @Generated(event=EventType.INSERT)
    private Long id;

    @NotEmpty
    @Size(max=50)
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @ManyToOne
    @JoinColumn(name="PARENT_ID")
    private Dummy parent;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (obj instanceof DummyItem other) {
            return Objects.nonNull(id)
                    ? Objects.equals(id, other.id)
                    : Objects.equals(parent, other.parent) && Objects.equals(name, other.name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.nonNull(id) ? Objects.hash(id) : Objects.hash(parent, name);
    }
}
