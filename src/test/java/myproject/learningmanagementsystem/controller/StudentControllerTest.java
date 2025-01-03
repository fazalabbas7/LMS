package myproject.learningmanagementsystem.controller;

import myproject.learningmanagementsystem.model.Student;
import myproject.learningmanagementsystem.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class StudentControllerTest {
    @InjectMocks
    private StudentController studentController;
    @Mock
    private StudentService studentService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllStudents() {
        List<Student> students = Arrays.asList(new Student(), new Student());
        when(studentService.getAllStudents()).thenReturn(students);
        List<Student> result = studentController.getAllStudents();
        assertEquals(2, result.size());
    }
    @Test
    void getStudentById_ReturnsStudent() {
        int studentId = 1;
        Student student = new Student();
        when(studentService.getStudentById(studentId)).thenReturn(student);
        ResponseEntity<Student> response = studentController.getStudentById(studentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }
    @Test
    void getStudentById_NotFound() {
        int studentId = 1;
        when(studentService.getStudentById(studentId)).thenReturn(null);
        ResponseEntity<Student> response = studentController.getStudentById(studentId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void createStudent() {
        Student student = new Student();
        when(studentService.createStudent(any(Student.class))).thenReturn(student);
        Student result = studentController.createStudent(student);
        assertNotNull(result);
        verify(studentService).createStudent(student);
    }
    @Test
    void updateStudent() {
        int studentId = 1;
        Student student = new Student();
        when(studentService.updateStudent(eq(studentId), any(Student.class))).thenReturn(student);
        ResponseEntity<Student> response = studentController.updateStudent(studentId, student);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student, response.getBody());
    }
    @Test
    void updateStudent_NotFound() {
        int studentId = 1;
        Student student = new Student();
        when(studentService.updateStudent(eq(studentId), any(Student.class))).thenReturn(null);
        ResponseEntity<Student> response = studentController.updateStudent(studentId, student);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void patchStudent() {
        int studentId = 1;
        Map<String, Object> updates = new HashMap<>();
        Student patchedStudent = new Student();
        when(studentService.updateStudentPartially(eq(studentId), any(Map.class))).thenReturn(patchedStudent);
        ResponseEntity<Student> response = studentController.patchStudent(studentId, updates);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patchedStudent, response.getBody());
    }
    @Test
    void patchStudent_NotFound() {
        int studentId = 1;
        Map<String, Object> updates = new HashMap<>();
        when(studentService.updateStudentPartially(eq(studentId), any(Map.class))).thenReturn(null);
        ResponseEntity<Student> response = studentController.patchStudent(studentId, updates);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void deleteStudent() {
        int studentId = 1;
        when(studentService.deleteStudent(studentId)).thenReturn(true);
        ResponseEntity<Void> response = studentController.deleteStudent(studentId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    void deleteStudent_NotFound() {
        int studentId = 1;
        when(studentService.deleteStudent(studentId)).thenReturn(false);
        ResponseEntity<Void> response = studentController.deleteStudent(studentId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
