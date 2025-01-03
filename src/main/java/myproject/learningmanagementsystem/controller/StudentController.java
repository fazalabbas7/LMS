package myproject.learningmanagementsystem.controller;

import myproject.learningmanagementsystem.model.Student;
import myproject.learningmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
        Student student = studentService.getStudentById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(id, student);
        return updatedStudent != null ? ResponseEntity.ok(updatedStudent) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable("id") int id, @RequestBody Map<String, Object> updates) {
        Student patchedStudent = studentService.updateStudentPartially(id, updates);
        return patchedStudent != null ? ResponseEntity.ok(patchedStudent) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") int id) {
        boolean deleted = studentService.deleteStudent(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
