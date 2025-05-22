package com.th.repository;

import com.th.DTO.LessonsDTO;
import com.th.entity.Courses;
import com.th.entity.Lessons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonsRepository extends JpaRepository<Lessons, Integer> {

    Optional<Lessons> findById(Integer id);

    Optional<Lessons> findByLessonsName(String lessonsName);

    @Query("SELECT new com.th.DTO.LessonsDTO(l.id, l.lessonsName, l.description, l.duration) FROM Lessons l")
    Page<LessonsDTO> findAllLessonsDTO(Pageable pageable);

//    @Query("FROM Lessons ")
//     List<Lessons> getListLessons();

    @Query("FROM Lessons WHERE id = :id")
    Lessons getLessonsById(@Param("id") Integer id);

    @Query("FROM Lessons l " +
            "WHERE (:lessonsName IS NULL OR l.lessonsName LIKE %:lessonsName%) " +
            "AND (:description IS NULL OR l.description LIKE %:description%) " +
            "AND (:duration IS NULL OR l.duration = :duration) ")
    List<Courses> search(@Param("lessonsName") String lessonsName,
                                @Param("description") String description,
                                @Param("duration") Integer duration);
}
