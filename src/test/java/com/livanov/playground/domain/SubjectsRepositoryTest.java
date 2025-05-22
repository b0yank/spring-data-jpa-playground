package com.livanov.playground.domain;

import com.livanov.playground.BaseDatabaseIntegrationTest;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class SubjectsRepositoryTest extends BaseDatabaseIntegrationTest {

    @Autowired
    private PeopleRepository people;

    @Autowired
    private SubjectsRepository repository;

    @Test
    void test() {

        // GIVEN
        val persistedPerson = people.save(new Person("Boyan"));
        val persistedPerson2 = people.save(new Person("Stoyan"));

        val subject1 = repository.save(new Subject("XX", persistedPerson));
        val subject2 = repository.save(new Subject("XY", persistedPerson));
        val subject3 = repository.save(new Subject("XZ", persistedPerson2));

        // WHEN
        val result = repository.getPersonSubjects();

        val personSubjectsMap = result.stream()
            .collect(
                groupingBy(
                    PersonSubject::person,
                    mapping(PersonSubject::subject, toSet())
                )
            );

        assertThat(personSubjectsMap.get(persistedPerson)).hasSameElementsAs(Set.of(subject1, subject2));
        assertThat(personSubjectsMap.get(persistedPerson2)).hasSameElementsAs(Set.of(subject3));
    }
}
