package com.th.repository;

import com.th.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

    Optional<Courses> findById(Integer id);

    Optional<Courses> findByCoursesName(String coursesName);

//    @Query("FROM Courses ")
//     List<Courses> getListCourses();

    @Query("FROM Courses WHERE id = :id")
    Courses getCoursesById(@Param("id") Integer id);

    @Query("FROM Courses c " +
            "WHERE (:coursesName IS NULL OR c.coursesName LIKE %:coursesName%) " +
            "AND (:description IS NULL OR c.description LIKE %:description%) " +
            "AND (:instructor IS NULL OR c.instructor LIKE %:instructor%) " +
            "AND (:duration IS NULL OR c.duration = :duration) " +
            "AND (:numberOfLessons IS NULL OR CAST(c.numberOfLessons AS string) = :numberOfLessons)")
    List<Courses> search(@Param("coursesName") String coursesName,
                                @Param("description") String description,
                                @Param("instructor") String instructor,
                                @Param("duration") Integer duration,
                                @Param("numberOfLessons") Integer numberOfLessons);



}
