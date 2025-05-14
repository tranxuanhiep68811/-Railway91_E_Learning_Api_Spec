package com.th.controller;

import com.th.entity.Courses;
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
        Page<Courses> courses = coursesRepo.findAll(pageable);
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

    @PutMapping
    public ResponseEntity<?> update(@RequestBody CoursesUpdateReq coursesUpdateReq) {
        String coursesName = coursesUpdateReq.getCoursesName();
        // ***** Kiểm tra ID có tồn tại không
        Optional<Courses> existingCourseOpt = coursesRepo.findById(coursesUpdateReq.getId());
        if (existingCourseOpt.isEmpty()) {
            return new ResponseEntity<>("Course ID not found: " + coursesUpdateReq.getId(),HttpStatus.NOT_FOUND);
        }
        // ***** Kiểm tra trùng tên, nhưng loại trừ chính bản ghi đang sửa
        Optional<Courses> nameConflict = coursesRepo.findByCoursesName(coursesUpdateReq.getCoursesName());
        if (nameConflict.isPresent() && !nameConflict.get().getId().equals(coursesUpdateReq.getId())) {
            return new ResponseEntity<>("Course name already exists: " + coursesUpdateReq.getCoursesName(),HttpStatus.CONFLICT);
        }
        Courses courses = existingCourseOpt.get();
        courses.setId(coursesUpdateReq.getId());
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

//    @GetMapping("getListCourses")
//    public ResponseEntity<?> getListCourses() {
//        List<Courses> courses = coursesRepo.getListCourses();
//        if (courses.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
//                    "message", "No courses found."
//            ));
//        }
//        return new ResponseEntity<>(courses, HttpStatus.OK);
//    }

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

//        return ResponseEntity.ok("Get data success!");
        System.out.println("***********************");
        return ResponseEntity.ok(courses);

    }


//    Cách 1:
//    @GetMapping("getProductById")
//    public ResponseEntity<?> getProductById(@RequestParam Integer id) {
//        Product product = productRepo.getProductById(id);
//        return ResponseEntity.ok(product);
//    }

//    Cách 2:
//    @GetMapping("getProductById/{id}")
//    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
//        Courses courses = productRepo.getProductById(id);
//        return ResponseEntity.ok(courses);
//    }
}
