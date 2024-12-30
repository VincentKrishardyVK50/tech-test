package com.parkee.techtest.repository;

import com.parkee.techtest.model.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
    Optional<People> findByEmailAndDeletedFalse(String email);

    Optional<People> findByNikAndDeletedFalse(String nik);

    Optional<People> findByEmailAndIdNotAndDeletedFalse(String email, long id);

    Optional<People> findByNikAndIdNotAndDeletedFalse(String nik, long id);

    @Query(nativeQuery = true, value =
            "SELECT * FROM people p " +
                    "WHERE (:keyword = '' OR p.nik LIKE CONCAT('%', :keyword, '%') " +
                    "   OR p.name LIKE CONCAT('%', :keyword, '%') " +
                    "   OR p.email LIKE CONCAT('%', :keyword, '%')) " +
                    "   AND p.deleted = false ")
    Page<People> findBooksByKeyword(Pageable pageable, String keyword);
}
