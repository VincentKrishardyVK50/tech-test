package com.parkee.techtest.repository;

import com.parkee.techtest.model.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
    Optional<People> findByEmail(String email);

    Optional<People> findByNik(String nik);
}
