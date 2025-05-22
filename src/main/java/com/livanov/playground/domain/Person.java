package com.livanov.playground.domain;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Setter
    private String name;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Set<String> tags;

    public Person(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public void addTags(String... tags) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }
        this.tags.addAll(List.of(tags));
    }
}
