package com.livanov.playground.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Getter
@Entity
@Table(name = "subjects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subject {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Column(name = "code")
    private String code;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @ElementCollection
    @CollectionTable(
            name = "subject_names",
            joinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Name> names;

    public Subject(String code, Person person, Name... names) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.person = person;
        this.names = Arrays.stream(names).collect(toSet());
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    public static class Name {

        @Column(name = "language_iso_alpha2")
        private String language;

        private String value;

        public Name(String language, String value) {
            this.language = language;
            this.value = value;
        }
    }
}
