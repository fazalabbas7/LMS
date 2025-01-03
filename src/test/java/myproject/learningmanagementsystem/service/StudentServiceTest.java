package myproject.learningmanagementsystem.service;

import myproject.learningmanagementsystem.model.Student;
import myproject.learningmanagementsystem.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceTest {
    @InjectMocks
    StudentService studentService;
    @Mock
    StudentRepository studentRepository;
    @Test
    void getAllStudents() {
        Student student = new Student();
        student.setFirstName("Humara");
        student.setLastName("Ashiq");
        student.setEmail("humara.ashiq@gmail.com");
        student.setDateOfBirth(LocalDate.of(1998, 5, 3));
        student.setEnrollmentDate(LocalDate.of(2024, 10, 16));
        when(studentRepository.findAll()).thenReturn(List.of(student));
        List<Student> allStudents = studentService.getAllStudents();
        assertNotNull(allStudents);
        assertFalse(allStudents.isEmpty());
        assertEquals(1, allStudents.size());
        assertEquals("Humara", allStudents.get(0).getFirstName());
    }
    @Test
    void getStudentById() {
        int studentId = 1;
        Student student = new Student();
        student.setId(studentId);
        student.setFirstName("Humara");
        student.setLastName("Ashiq");
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Student foundStudent = studentService.getStudentById(studentId);
        assertNotNull(foundStudent);
        assertEquals(studentId, foundStudent.getId());
        assertEquals("Humara", foundStudent.getFirstName());
    }
    @Test
    void createStudentById() {
        int studentId = 1;
        Student newStudent = new Student();
        newStudent.setFirstName("Kinza");
        newStudent.setLastName("Bilal");
        newStudent.setEmail("kinza.bilal@gmail.com");
        newStudent.setDateOfBirth(LocalDate.of(1999, 9, 5));
        newStudent.setEnrollmentDate(LocalDate.of(2024, 10, 15));
        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);
        Student createdStudent = studentService.createStudentById(studentId, newStudent);
        assertNotNull(createdStudent);
        assertEquals(studentId, createdStudent.getId());
        assertEquals("Kinza", createdStudent.getFirstName());
        assertEquals("Bilal", createdStudent.getLastName());
        assertEquals("kinza.bilal@gmail.com", createdStudent.getEmail());
    }
    @Test
    void updateStudent() {
        int studentId = 1;
        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setFirstName("Kinza");
        existingStudent.setLastName("Bilal");
        existingStudent.setEmail("kinza.bilal@gmail.com");
        existingStudent.setDateOfBirth(LocalDate.of(1998, 5, 3));
        existingStudent.setEnrollmentDate(LocalDate.of(2022, 10, 3));
        Student updatedStudent = new Student();
        updatedStudent.setId(studentId);
        updatedStudent.setFirstName("Qasim");
        updatedStudent.setLastName("Ali");
        updatedStudent.setEmail("qasim.ali@gmail.com");
        updatedStudent.setDateOfBirth(LocalDate.of(1998, 5, 3));
        updatedStudent.setEnrollmentDate(LocalDate.of(2022, 10, 16));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);
        Student result = studentService.updateStudentId(studentId, updatedStudent);
        assertNotNull(result);
        assertEquals(studentId, result.getId());
        assertEquals("Qasim", result.getFirstName());
        assertEquals("Ali", result.getLastName());
        assertEquals("qasim.ali@gmail.com", result.getEmail());
        assertEquals(LocalDate.of(1998, 5, 3), result.getDateOfBirth());
        assertEquals(LocalDate.of(2022, 10, 16), result.getEnrollmentDate());
    }
    @Test
    void updateStudentPartially() {
        int studentId = 1;
        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setFirstName("Humara");
        existingStudent.setLastName("Ashiq");
        Map<String, Object> partialUpdate = new HashMap<>();
        partialUpdate.put("firstName", "PartiallyUpdatedName");
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        existingStudent.setFirstName("PartiallyUpdatedName");
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);
        Student result = studentService.updateStudentPartially(studentId, partialUpdate);
        assertNotNull(result);
        assertEquals("PartiallyUpdatedName", result.getFirstName());
        assertEquals("Ashiq", result.getLastName());
    }
    @Test
    void deleteStudent() {
        int studentId = 1;
        when(studentRepository.existsById(studentId)).thenReturn(true);
        boolean isDeleted = studentService.deleteStudent(studentId);
        assertTrue(isDeleted);
        verify(studentRepository, times(1)).deleteById(studentId);
    }
    @Test
    void deleteNonExistingStudent() {
        int studentId = 1;
        when(studentRepository.existsById(studentId)).thenReturn(false);
        boolean isDeleted = studentService.deleteStudent(studentId);
        assertFalse(isDeleted);
        verify(studentRepository, never()).deleteById(studentId);
    }
}

