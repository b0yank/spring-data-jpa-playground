package com.livanov.playground.web;

import com.livanov.playground.BaseDatabaseIntegrationTest;
import com.livanov.playground.domain.PeopleRepository;
import com.livanov.playground.domain.Person;
import com.livanov.playground.domain.Subject;
import com.livanov.playground.domain.SubjectsRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class PeopleControllerTest extends BaseDatabaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PeopleRepository people;

    @Autowired
    private SubjectsRepository subjects;

    @Test
    void can_fetch_people() throws Exception {

        // GIVEN
        val person = new Person("Lyubo");
        val subject = subjects.save(new Subject("M1", person));

        people.save(person);

        // WHEN
        mockMvc.perform(get("/api/people"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").isString())
                .andExpect(jsonPath("$.[0].name").isString())
                .andExpect(jsonPath("$.[0].subjects.[0].id").isString());
    }
}
