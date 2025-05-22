create table people
(
    id text primary key,
    name text not null,
    tags jsonb null
);

create table subjects
(
    id text primary key,
    person_id text not null references people(id),
    code text not null
);

create table subject_names
(
    subject_id text not null references subjects(id),
    language_iso_alpha2 text not null,
    value text not null
);

--create table people_subjects
--(
--    person_id text not null references people(id),
--    subject_id text not null references subjects(id),
--
--    unique (person_id, subject_id)
--);
