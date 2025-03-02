package org.addy.hello_boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Dummy {
    @Id
    @Generated(event=EventType.INSERT)
    private Long id;

    @NotNull
    @Size(min=1, max=50)
    private String name;

    @Size(max=200)
    private String description;

    @OneToMany(mappedBy="parent",
            fetch=FetchType.EAGER,
            cascade=CascadeType.ALL,
            orphanRemoval=true)
    private Set<DummyItem> items = new HashSet<>();
}
