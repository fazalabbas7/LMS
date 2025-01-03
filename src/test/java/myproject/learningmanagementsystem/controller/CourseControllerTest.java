package myproject.learningmanagementsystem.controller;

import myproject.learningmanagementsystem.model.Course;
import myproject.learningmanagementsystem.service.CourseService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CourseControllerTest {
    @InjectMocks
    private CourseController courseController;
    @Mock
    private CourseService courseService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllCourses() {
        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseService.getAllCourses()).thenReturn(courses);
        List<Course> result = courseController.getAllCourses();
        assertEquals(2, result.size());
    }
    @Test
    void getCourseById_ReturnsCourse() {
        int courseId = 1;
        Course course = new Course();
        when(courseService.getCourseById(courseId)).thenReturn(course);
        ResponseEntity<Course> response = courseController.getCourseById(courseId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
    }
    @Test
    void getCourseById_NotFound() {
        int courseId = 1;
        when(courseService.getCourseById(courseId)).thenReturn(null);
        ResponseEntity<Course> response = courseController.getCourseById(courseId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void createCourse() {
        Course course = new Course();
        when(courseService.createCourse(any(Course.class))).thenReturn(course);
        Course result = courseController.createCourse(course);
        assertNotNull(result);
        verify(courseService).createCourse(course);
    }
    @Test
    void updateCourse() {
        int courseId = 1;
        Course course = new Course();
        when(courseService.updateCourse(eq(courseId), any(Course.class))).thenReturn(course);
        ResponseEntity<Course> response = courseController.updateCourse(courseId, course);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(course, response.getBody());
    }
    @Test
    void updateCourse_NotFound() {
        int courseId = 1;
        Course course = new Course();
        when(courseService.updateCourse(eq(courseId), any(Course.class))).thenReturn(null);
        ResponseEntity<Course> response = courseController.updateCourse(courseId, course);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void patchCourse() {
        int courseId = 1;
        Map<String, Object> updates = new HashMap<>();
        Course patchedCourse = new Course();
        when(courseService.updateCoursePartially(eq(courseId), any(Map.class))).thenReturn(patchedCourse);
        ResponseEntity<Course> response = courseController.patchCourse(courseId, updates);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patchedCourse, response.getBody());
    }
    @Test
    void patchCourse_NotFound() {
        int courseId = 1;
        Map<String, Object> updates = new HashMap<>();
        when(courseService.updateCoursePartially(eq(courseId), any(Map.class))).thenReturn(null);
        ResponseEntity<Course> response = courseController.patchCourse(courseId, updates);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void deleteCourse() {
        int courseId = 1;
        when(courseService.deleteCourse(courseId)).thenReturn(true);
        ResponseEntity<Void> response = courseController.deleteCourse(courseId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    void deleteCourse_NotFound() {
        int courseId = 1;
        when(courseService.deleteCourse(courseId)).thenReturn(false);
        ResponseEntity<Void> response = courseController.deleteCourse(courseId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
