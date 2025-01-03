package myproject.learningmanagementsystem.controller;

import myproject.learningmanagementsystem.model.Course;
import myproject.learningmanagementsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Course course = courseService.getCourseById(id);
        return course != null ? ResponseEntity.ok(course) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        return updatedCourse != null ? ResponseEntity.ok(updatedCourse) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Course> patchCourse(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        Course patchedCourse = courseService.updateCoursePartially(id, updates);
        return patchedCourse != null ? ResponseEntity.ok(patchedCourse) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        boolean deleted = courseService.deleteCourse(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
