package com.th.controller;

import com.th.entity.Courses;
import com.th.entity.Lessons;
import com.th.repository.CoursesRepository;
import com.th.repository.LessonsRepository;
import com.th.req.CoursesCreateReq;
import com.th.req.CoursesUpdateReq;
import com.th.req.LessonsCreateReq;
import com.th.req.LessonsUpdateReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("lessons")
@RestController
public class LessonsController {

    private final LessonsRepository lessonsRepo;

    public LessonsController(LessonsRepository lessonsRepo) {
        this.lessonsRepo = lessonsRepo;
    }

    @GetMapping
    public ResponseEntity<?> getAllLessons(Pageable pageable) {
        System.out.println(pageable);
        Page<Lessons> lessons = lessonsRepo.findAll(pageable);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<Lessons> lessons = lessonsRepo.findById(id);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>("Lessons ID not found: " + id, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(lessons.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LessonsCreateReq lessonsCreateReq) {
        String lessonsName= lessonsCreateReq.getLessonsName();
        Optional<Lessons> opLessons = lessonsRepo.findByLessonsName(lessonsName);
        if (opLessons.isPresent()) {
            return new ResponseEntity<>("Lessons Name already exists: " + lessonsName, HttpStatus.CONFLICT);
        }
        Lessons lessons = new Lessons();
        lessons.setLessonsName(lessonsCreateReq.getLessonsName());;
        lessons.setDescription(lessonsCreateReq.getDescription());
        lessons.setDuration(lessonsCreateReq.getDuration());

        lessonsRepo.save(lessons);
        System.out.println("***********************");
        System.out.println("Created Successfully!");
        System.out.println(lessonsCreateReq);
        return new ResponseEntity<>("Created successFully!", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody LessonsUpdateReq lessonsUpdateReq) {
        String lessonsName = lessonsUpdateReq.getLessonsName();
        // ***** Kiểm tra ID có tồn tại không
        Optional<Lessons> existingLessonsOpt = lessonsRepo.findById(lessonsUpdateReq.getId());
        if (existingLessonsOpt.isEmpty()) {
            return new ResponseEntity<>("Course ID not found: " + lessonsUpdateReq.getId(),HttpStatus.NOT_FOUND);
        }
        // ***** Kiểm tra trùng tên, nhưng loại trừ chính bản ghi đang sửa
        Optional<Lessons> nameConflict = lessonsRepo.findByLessonsName(lessonsUpdateReq.getLessonsName());
        if (nameConflict.isPresent() && !nameConflict.get().getId().equals(lessonsUpdateReq.getId())) {
            return new ResponseEntity<>("Course name already exists: " + lessonsUpdateReq.getLessonsName(),HttpStatus.CONFLICT);
        }
        Lessons lessons = existingLessonsOpt.get();
        lessons.setId(lessonsUpdateReq.getId());
        lessons.setLessonsName(lessonsUpdateReq.getLessonsName());;
        lessons.setDescription(lessonsUpdateReq.getDescription());
        lessons.setDuration(lessonsUpdateReq.getDuration());
        lessonsRepo.save(lessons);
        System.out.println("***********************");
        System.out.println("Update Successfully!");
        System.out.println(lessonsUpdateReq);
        return new ResponseEntity<>("Update Successfully!", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
//       ***** Kiểm tra ID có tồn tại không
        if (!lessonsRepo.existsById(id)) {
            return new ResponseEntity<>("Lessons ID not found: " + id,HttpStatus.NOT_FOUND);
        }
        lessonsRepo.deleteById(id);
        System.out.println("***********************");
        System.out.println("Deleted Successfully!");
        return new ResponseEntity<>("Deleted Successfully!", HttpStatus.OK);
    }

//    @GetMapping("getListLessons")
//    public ResponseEntity<?> getListLessons() {
//        List<Lessons> lessons = lessonsRepo.getListLessons();
//        if (lessons.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of(
//                    "message", "No lessons found."
//            ));
//        }
//        return new ResponseEntity<>(courses, HttpStatus.OK);
//    }

    @GetMapping("search")
    public ResponseEntity<?> getAll(@RequestParam(required = false) String lessonsName,
                                    @RequestParam(required = false) String description,
                                    @RequestParam(required = false) Integer duration) {
        List<Courses> courses = lessonsRepo.search(lessonsName, description, duration);

        System.out.println("Name: " + lessonsName);
        System.out.println("Description: " + description);
        System.out.println("Duration: " + duration);
          if (courses.isEmpty()) {
                return new ResponseEntity<>("No lessons found with the given criteria!" ,HttpStatus.NO_CONTENT);
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
