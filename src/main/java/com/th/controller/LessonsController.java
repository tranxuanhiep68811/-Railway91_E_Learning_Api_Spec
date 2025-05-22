package com.th.controller;

import com.th.DTO.LessonsDTO;
import com.th.entity.Courses;
import com.th.entity.Lessons;
import com.th.repository.LessonsRepository;
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
        Page<LessonsDTO> lessons = lessonsRepo.findAllLessonsDTO(pageable);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<Lessons> lessons = lessonsRepo.findById(id);
        if (lessons.isEmpty()) {
            return new ResponseEntity<>("Lessons ID not found: " + id, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(lessons.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LessonsCreateReq lessonsCreateReq) {
        String lessonName= lessonsCreateReq.getLessonsName();
        Optional<Lessons> opLessons = lessonsRepo.findByLessonsName(lessonName);
        if (opLessons.isPresent()) {
            return new ResponseEntity<>("Lessons Name already exists: " + lessonName, HttpStatus.CONFLICT);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody LessonsUpdateReq lessonsUpdateReq) {

        Optional<Lessons> opLessons = lessonsRepo.findById(id);
        if (opLessons.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy sản phẩm với ID: " + id, HttpStatus.NOT_FOUND);
        } else
            if (lessonsUpdateReq.getLessonsName() == null  || lessonsUpdateReq.getDescription().trim().isEmpty()) {
            return new ResponseEntity<>("Tên bài học không được để trống!", HttpStatus.BAD_REQUEST);
        } else
            if (lessonsUpdateReq.getDuration() <= 0 || lessonsUpdateReq.getDuration() == null) {
            return new ResponseEntity<>("Số giờ bài học không được để trống!", HttpStatus.BAD_REQUEST);
        } else
            if (lessonsUpdateReq.getDescription() == null || lessonsUpdateReq.getDescription().trim().isEmpty()) {
            return new ResponseEntity<>("Mô tả bài học không được để trống!", HttpStatus.BAD_REQUEST);
        }

        Lessons lessons = opLessons.get();
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
        System.out.println("***********************");
        return ResponseEntity.ok(courses);

    }

}
