package myproject.learningmanagementsystem.service;
import myproject.learningmanagementsystem.model.Course;
import myproject.learningmanagementsystem.repository.CourseRepository;
import myproject.learningmanagementsystem.service.CourseService;
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
class CourseServiceTest {
    @InjectMocks
    CourseService courseService;
    @Mock
    CourseRepository courseRepository;
    @Test
    void getAllCourses() {
        Course course = new Course("Introduction to Spring Boot", "Learn the fundamentals of Spring Boot", LocalDate.now(), LocalDate.now().plusMonths(1));
        course.setCourseId(1);
        when(courseRepository.findAll()).thenReturn(List.of(course));
        List<Course> courses = courseService.getAllCourses();
        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        assertEquals(1, courses.size());
        assertEquals("Introduction to Spring Boot", courses.get(0).getCourseName());
        assertEquals("Learn the fundamentals of Spring Boot", courses.get(0).getDescription());
    }
    @Test
    void getCourseById() {
        int courseId = 1;
        Course course = new Course("Java Basics", "A complete guide to Java programming", LocalDate.now(), LocalDate.now().plusMonths(1));
        course.setCourseId(courseId);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Course foundCourse = courseService.getCourseById(courseId);
        assertNotNull(foundCourse);
        assertEquals(courseId, foundCourse.getCourseId());
        assertEquals("Java Basics", foundCourse.getCourseName());
        assertEquals("A complete guide to Java programming", foundCourse.getDescription());
    }
    @Test
    void createCourse() {
        Course newCourse = new Course("Advanced Spring Boot", "Dive deeper into Spring Boot", LocalDate.now(), LocalDate.now().plusMonths(2));
        when(courseRepository.save(any(Course.class))).thenReturn(newCourse);
        Course createdCourse = courseService.createCourse(newCourse);
        assertNotNull(createdCourse);
        assertEquals(newCourse.getCourseName(), createdCourse.getCourseName());
        assertEquals(newCourse.getDescription(), createdCourse.getDescription());
    }
    @Test
    void updateCourse() {
        int courseId = 1;
        Course existingCourse = new Course("RESTful Web Services", "Learn how to create RESTful APIs", LocalDate.now(), LocalDate.now().plusMonths(1));
        existingCourse.setCourseId(courseId);
        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseRepository.save(any(Course.class))).thenReturn(existingCourse);
        Course updatedCourse = courseService.updateCourse(courseId, existingCourse);
        assertNotNull(updatedCourse);
        assertEquals(courseId, updatedCourse.getCourseId());
        assertEquals("RESTful Web Services", updatedCourse.getCourseName());
        assertEquals("Learn how to create RESTful APIs", updatedCourse.getDescription());
    }
    @Test
    void updateCoursePartially() {
        int courseId = 1;
        Course existingCourse = new Course("Database Management", "Learn about database systems", LocalDate.now(), LocalDate.now().plusMonths(1));
        existingCourse.setCourseId(courseId);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        Map<String, Object> updates = new HashMap<>();
        updates.put("courseName", "Database Management 101");
        updates.put("description", "An introduction to database systems");
        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.<Course>getArgument(0));
        Course updatedCourse = courseService.updateCoursePartially(courseId, updates);
        assertNotNull(updatedCourse);
        assertEquals("Database Management 101", updatedCourse.getCourseName());
        assertEquals("An introduction to database systems", updatedCourse.getDescription());
        assertEquals(courseId, updatedCourse.getCourseId());
    }

    @Test
    void deleteCourse() {
        int courseId = 1;
        when(courseRepository.existsById(courseId)).thenReturn(true);
        boolean isDeleted = courseService.deleteCourse(courseId);
        assertTrue(isDeleted);
        verify(courseRepository, times(1)).deleteById(courseId);
    }
    @Test
    void deleteNonExistingCourse() {
        int courseId = 1;
        when(courseRepository.existsById(courseId)).thenReturn(false);
        boolean isDeleted = courseService.deleteCourse(courseId);
        assertFalse(isDeleted);
        verify(courseRepository, never()).deleteById(courseId);
    }
}

