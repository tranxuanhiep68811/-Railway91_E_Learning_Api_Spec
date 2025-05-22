package com.th.controller;

import com.th.DTO.CoursesDTO;
import com.th.entity.Courses;
import com.th.entity.Lessons;
import com.th.repository.CoursesRepository;
import com.th.req.CoursesCreateReq;
import com.th.req.CoursesUpdateReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@RequestMapping("courses")
@RestController
public class CoursesController {

    private final CoursesRepository coursesRepo;

    public CoursesController(CoursesRepository coursesRepo) {
        this.coursesRepo = coursesRepo;
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses(Pageable pageable) {
        System.out.println(pageable);
        Page<CoursesDTO> courses = coursesRepo.findAllCoursesDTO(pageable);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<Courses> courses = coursesRepo.findById(id);
        if (courses.isEmpty()) {
            return new ResponseEntity<>("Course ID not found: " + id, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courses.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CoursesCreateReq coursesCreateReq) {
        String coursesName= coursesCreateReq.getCoursesName();
        Optional<Courses> opCourses = coursesRepo.findByCoursesName(coursesName);
        if (opCourses.isPresent()) {
            return new ResponseEntity<>("Courses Name already exists: " + coursesName, HttpStatus.CONFLICT);
        }
        Courses courses = new Courses();
        courses.setCoursesName(coursesCreateReq.getCoursesName());
        courses.setNumberOfLessons(coursesCreateReq.getNumberOfLessons());
        courses.setDescription(coursesCreateReq.getDescription());
        courses.setDuration(coursesCreateReq.getDuration());
        courses.setInstructor(coursesCreateReq.getInstructor());
        coursesRepo.save(courses);
        System.out.println("***********************");
        System.out.println("Created Successfully!");
        System.out.println(coursesCreateReq);
        return new ResponseEntity<>("Created successFully!", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CoursesUpdateReq coursesUpdateReq) {
        Optional<Courses> opCourses = coursesRepo.findById(id);
        if (opCourses.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy sản phẩm với ID: " + id, HttpStatus.NOT_FOUND);
        } else
        if (coursesUpdateReq.getCoursesName() == null  || coursesUpdateReq.getDescription().trim().isEmpty()) {
            return new ResponseEntity<>("Tên khóa học không được để trống!", HttpStatus.BAD_REQUEST);
        } else
        if (coursesUpdateReq.getNumberOfLessons() <= 0 || coursesUpdateReq.getNumberOfLessons() == null) {
            return new ResponseEntity<>("Số khóa học không được để trống!", HttpStatus.BAD_REQUEST);
        } else
        if (coursesUpdateReq.getDescription() == null || coursesUpdateReq.getDescription().trim().isEmpty()) {
            return new ResponseEntity<>("Mô tả khóa học không được để trống!", HttpStatus.BAD_REQUEST);
        } else
        if (coursesUpdateReq.getDuration() <= 0 || coursesUpdateReq.getDuration() == null) {
            return new ResponseEntity<>("Số giờ khóa học không được để trống!", HttpStatus.BAD_REQUEST);
        } else
        if (coursesUpdateReq.getInstructor() == null || coursesUpdateReq.getInstructor().trim().isEmpty()) {
            return new ResponseEntity<>("Tên người hướng dẫn không được để trống!", HttpStatus.BAD_REQUEST);
        }
        Courses courses = opCourses.get();
        courses.setCoursesName(coursesUpdateReq.getCoursesName());
        courses.setNumberOfLessons(coursesUpdateReq.getNumberOfLessons());
        courses.setDescription(coursesUpdateReq.getDescription());
        courses.setDuration(coursesUpdateReq.getDuration());
        courses.setInstructor(coursesUpdateReq.getInstructor());
        coursesRepo.save(courses);
        System.out.println("***********************");
        System.out.println("Update Successfully!");
        System.out.println(coursesUpdateReq);
        return new ResponseEntity<>("Update Successfully!", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
//       ***** Kiểm tra ID có tồn tại không
        if (!coursesRepo.existsById(id)) {
            return new ResponseEntity<>("Course ID not found: " + id,HttpStatus.NOT_FOUND);
        }
        coursesRepo.deleteById(id);
        System.out.println("***********************");
        System.out.println("Deleted Successfully!");
        return new ResponseEntity<>("Deleted Successfully!", HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> getAll(@RequestParam(required = false) String coursesName,
                                    @RequestParam(required = false) String description,
                                    @RequestParam(required = false) String instructor,
                                    @RequestParam(required = false) Integer duration,
                                    @RequestParam(required = false) Integer numberOfLessons) {
        List<Courses> courses = coursesRepo.search(coursesName, description, instructor, duration, numberOfLessons);

        System.out.println("Name: " + coursesName);
        System.out.println("Description: " + description);
        System.out.println("Instructor: " + instructor);
        System.out.println("Duration: " + duration);
        System.out.println("Number Of Lessons: " + numberOfLessons);
          if (courses.isEmpty()) {
                return new ResponseEntity<>("No courses found with the given criteria!" ,HttpStatus.NO_CONTENT);
          }
        System.out.println("***********************");
        return ResponseEntity.ok(courses);

    }

}
