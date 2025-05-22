package com.livanov.playground;

import com.livanov.playground.domain.PeopleRepository;
import com.livanov.playground.domain.Person;
import com.livanov.playground.domain.Subject;
import com.livanov.playground.domain.SubjectsRepository;
import lombok.val;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PlaygroundApplicationLocal {

    public static void main(String[] args) {

        SpringApplication.from(PlaygroundApplication::main)
                .with(DatabaseConfiguration.class)
                .with(DataInjector.class)
                .run(args);
    }

    public static class DataInjector {
        @Bean
        ApplicationRunner runner(PeopleRepository people, SubjectsRepository subjects) {

            return args -> {

                val person1 = new Person("Lyubo");
                person1.addTags("student", "musician");

                val person2 = new Person("George");
                person2.addTags("student", "painter");

                val subject1 = subjects.save(new Subject(
                        "M1",
                        person1,
                        new Subject.Name("en", "Mathematics"),
                        new Subject.Name("bg", "Математика")
                ));

                val subject2 = subjects.save(new Subject(
                        "P1",
                        person2,
                        new Subject.Name("en", "Physics"),
                        new Subject.Name("bg", "Физика")
                ));

                people.saveAll(List.of(person1, person2));
            };
        }
    }

    public static class DatabaseConfiguration {

        @Bean
        @RestartScope
        @ServiceConnection
        public PostgreSQLContainer<?> postgreSqlContainer() {
            val imageName = DockerImageName.parse("postgres").withTag("14.6-alpine");

            return new PostgreSQLContainer<>(imageName)
                    .withUsername("postgres")
                    .withPassword("postgres");
        }
    }
}
