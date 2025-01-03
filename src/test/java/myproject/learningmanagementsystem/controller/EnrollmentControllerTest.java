package myproject.learningmanagementsystem.controller;

import myproject.learningmanagementsystem.model.Enrollment;
import myproject.learningmanagementsystem.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnrollmentControllerTest {
    @InjectMocks
    private EnrollmentController enrollmentController;
    @Mock
    private EnrollmentService enrollmentService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEnrollments() {
        List<Enrollment> enrollments = Arrays.asList(new Enrollment(), new Enrollment());
        when(enrollmentService.getAllEnrollments()).thenReturn(enrollments);
        List<Enrollment> result = enrollmentController.getAllEnrollments();
        assertEquals(2, result.size());
    }
    @Test
    void getEnrollmentById_ReturnsEnrollment() {
        int enrollmentId = 1;
        Enrollment enrollment = new Enrollment();
        when(enrollmentService.getEnrollmentById(enrollmentId)).thenReturn(enrollment);
        ResponseEntity<Enrollment> response = enrollmentController.getEnrollmentById(enrollmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(enrollment, response.getBody());
    }
    @Test
    void getEnrollmentById_NotFound() {
        int enrollmentId = 1;
        when(enrollmentService.getEnrollmentById(enrollmentId)).thenReturn(null);
        ResponseEntity<Enrollment> response = enrollmentController.getEnrollmentById(enrollmentId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void createEnrollment() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentService.createEnrollment(any(Enrollment.class))).thenReturn(enrollment);
        Enrollment result = enrollmentController.createEnrollment(enrollment);
        assertNotNull(result);
        verify(enrollmentService).createEnrollment(enrollment);
    }
    @Test
    void deleteEnrollment() {
        int enrollmentId = 1;
        when(enrollmentService.deleteEnrollment(enrollmentId)).thenReturn(true);
        ResponseEntity<Void> response = enrollmentController.deleteEnrollment(enrollmentId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    void deleteEnrollment_NotFound() {
        int enrollmentId = 1;
        when(enrollmentService.deleteEnrollment(enrollmentId)).thenReturn(false);
        ResponseEntity<Void> response = enrollmentController.deleteEnrollment(enrollmentId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
