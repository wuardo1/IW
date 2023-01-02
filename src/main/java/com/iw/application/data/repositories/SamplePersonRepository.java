package com.iw.application.data.repositories;

import com.iw.application.data.entity.SamplePerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, UUID> {

}