package myproject.learningmanagementsystem.service;

import myproject.learningmanagementsystem.model.Enrollment;
import myproject.learningmanagementsystem.repository.EnrollmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EnrollmentServiceTest {
    @InjectMocks
    EnrollmentService enrollmentService;
    @Mock
    EnrollmentRepository enrollmentRepository;
    @Test
    void getAllEnrollments() {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(LocalDate.now());
        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        assertNotNull(enrollments);
        assertFalse(enrollments.isEmpty());
        assertEquals(1, enrollments.size());
    }
    @Test
    void getEnrollmentById() {
        int enrollmentId = 1;
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);
        enrollment.setEnrollmentDate(LocalDate.now());
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        Enrollment foundEnrollment = enrollmentService.getEnrollmentById(enrollmentId);
        assertNotNull(foundEnrollment, "Enrollment should not be null");
        assertEquals(enrollmentId, foundEnrollment.getId(), "Enrollment ID should match");
        assertEquals(enrollment.getEnrollmentDate(), foundEnrollment.getEnrollmentDate(), "Enrollment dates should match");
    }
    @Test
    void createEnrollment() {
        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setEnrollmentDate(LocalDate.now());
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(newEnrollment);
        Enrollment createdEnrollment = enrollmentService.createEnrollment(newEnrollment);
        assertNotNull(createdEnrollment);
        assertEquals(newEnrollment.getEnrollmentDate(), createdEnrollment.getEnrollmentDate());
    }
    @Test
    void updateEnrollment() {
        int enrollmentId = 1;
        Enrollment existingEnrollment = new Enrollment();
        existingEnrollment.setId(enrollmentId);
        existingEnrollment.setEnrollmentDate(LocalDate.now().minusDays(5));
        Enrollment updatedEnrollment = new Enrollment();
        updatedEnrollment.setId(enrollmentId);
        updatedEnrollment.setEnrollmentDate(LocalDate.now());
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(existingEnrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(updatedEnrollment);
        Enrollment result = enrollmentService.updateEnrollment(enrollmentId, updatedEnrollment);
        assertNotNull(result, "Updated enrollment should not be null");
        assertEquals(enrollmentId, result.getId(), "Enrollment ID should match");
        assertEquals(updatedEnrollment.getEnrollmentDate(), result.getEnrollmentDate(), "Enrollment dates should match");
    }
    @Test
    void updateEnrollmentPartially() {
        int enrollmentId = 1;
        Enrollment existingEnrollment = new Enrollment();
        existingEnrollment.setId(enrollmentId);
        existingEnrollment.setEnrollmentDate(LocalDate.now().minusDays(5));
        var partialUpdate = Map.of("enrollmentDate", LocalDate.now());
        when(enrollmentRepository.findById(enrollmentId)).thenReturn(Optional.of(existingEnrollment));
        existingEnrollment.setEnrollmentDate(LocalDate.now());
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(existingEnrollment);
        Enrollment result = enrollmentService.updateEnrollmentPartially(enrollmentId, partialUpdate);
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getEnrollmentDate());
    }
    @Test
    void deleteEnrollment() {
        int enrollmentId = 1;
        when(enrollmentRepository.existsById(enrollmentId)).thenReturn(true);
        boolean isDeleted = enrollmentService.deleteEnrollment(enrollmentId);
        assertTrue(isDeleted);
        verify(enrollmentRepository, times(1)).deleteById(enrollmentId);
    }
    @Test
    void deleteNonExistingEnrollment() {
        int enrollmentId = 1;
        when(enrollmentRepository.existsById(enrollmentId)).thenReturn(false);
        boolean isDeleted = enrollmentService.deleteEnrollment(enrollmentId);
        assertFalse(isDeleted);
        verify(enrollmentRepository, never()).deleteById(enrollmentId);
    }
}
