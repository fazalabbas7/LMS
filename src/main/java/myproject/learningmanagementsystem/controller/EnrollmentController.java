package myproject.learningmanagementsystem.controller;

import myproject.learningmanagementsystem.model.Enrollment;
import myproject.learningmanagementsystem.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;
    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable int id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        return enrollment != null ? ResponseEntity.ok(enrollment) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentService.createEnrollment(enrollment);
    }

// Using @PutMapping for updating an enrollment
@PutMapping("/{id}")
public ResponseEntity<Enrollment> updateEnrollment(@PathVariable int id, @RequestBody Enrollment enrollment) {
    Enrollment updatedEnrollment = enrollmentService.updateEnrollment(id, enrollment);
    return updatedEnrollment != null ? ResponseEntity.ok(updatedEnrollment) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable int id) {
        boolean deleted = enrollmentService.deleteEnrollment(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
