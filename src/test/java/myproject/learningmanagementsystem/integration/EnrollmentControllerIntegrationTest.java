package myproject.learningmanagementsystem.integration;

import myproject.learningmanagementsystem.model.Course;
import myproject.learningmanagementsystem.model.Enrollment;
import myproject.learningmanagementsystem.model.Student;
import myproject.learningmanagementsystem.repository.CourseRepository;
import myproject.learningmanagementsystem.repository.EnrollmentRepository;
import myproject.learningmanagementsystem.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
public class EnrollmentControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateEnrollment() throws Exception {
        Student student = studentRepository.save(new Student("Guzanfer", "Smtiya", "guzanfer.Smtiya@gmail.com", LocalDate.of(2000, 5, 15), LocalDate.now()));
        Course course = courseRepository.save(new Course("Java Basics", "Intro to Java", LocalDate.now(), LocalDate.now().plusMonths(1)));
        String enrollmentJson = """
                {
                    "student": {"studentId": %d},
                    "course": {"courseId": %d},
                    "enrollmentDate": "2024-01-01"
                }
                """.formatted(student.getStudentId(), course.getCourseId());
        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enrollmentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.studentId").value(student.getStudentId()))
                .andExpect(jsonPath("$.course.courseId").value(course.getCourseId()));
    }

    @Test
    void testGetAllEnrollments() throws Exception {
        Student student = studentRepository.save(new Student("Farhan", "Ali", "farhan.ali@gmail.com", LocalDate.of(1998, 3, 12), LocalDate.now()));
        Course course = courseRepository.save(new Course("Spring Boot", "Learn Spring Boot", LocalDate.now(), LocalDate.now().plusMonths(2)));
        enrollmentRepository.save(new Enrollment(student, course, LocalDate.now()));
        mockMvc.perform(get("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1)); // Checks that one enrollment is returned
    }

    @Test
    void testGetEnrollmentById() throws Exception {
        Student student = studentRepository.save(new Student("Mobeen", "Ali", "mobeen.ali@gmail.com", LocalDate.of(1997, 8, 30), LocalDate.now()));
        Course course = courseRepository.save(new Course("REST APIs", "Learn REST APIs", LocalDate.now(), LocalDate.now().plusMonths(3)));
        Enrollment enrollment = enrollmentRepository.save(new Enrollment(student, course, LocalDate.now()));
        mockMvc.perform(get("/api/enrollments/" + enrollment.getEnrollmentId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.studentId").value(student.getStudentId()))
                .andExpect(jsonPath("$.course.courseId").value(course.getCourseId()));
    }

    @Test
    void testUpdateEnrollment() throws Exception {
        Student student = studentRepository.save(new Student("Mobeen", "Ali", "mobeen.ali@gmail.com", LocalDate.of(1997, 8, 30), LocalDate.now()));
        Course course = courseRepository.save(new Course("REST APIs", "Learn REST APIs", LocalDate.now(), LocalDate.now().plusMonths(3)));
        Enrollment enrollment = enrollmentRepository.save(new Enrollment(student, course, LocalDate.now()));
        Course newCourse = courseRepository.save(new Course("Advanced Java", "Learn Advanced Java", LocalDate.now(), LocalDate.now().plusMonths(4)));
        String updatedEnrollmentJson = """
            {
                "student": {"studentId": %d},
                "course": {"courseId": %d},
                "enrollmentDate": "2024-02-01"
            }
            """.formatted(student.getStudentId(), newCourse.getCourseId());
        mockMvc.perform(put("/api/enrollments/" + enrollment.getEnrollmentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEnrollmentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student.studentId").value(student.getStudentId()))
                .andExpect(jsonPath("$.course.courseId").value(newCourse.getCourseId()))
                .andExpect(jsonPath("$.enrollmentDate").value("2024-02-01"));
    }

    @Test
    void testDeleteEnrollment() throws Exception {
        Student student = studentRepository.save(new Student("Mobeen", "Ali", "mobeen.ali@gmail.com", LocalDate.of(1993, 3, 8), LocalDate.now()));
        Course course = courseRepository.save(new Course("Microservices", "Learn Microservices", LocalDate.now(), LocalDate.now().plusMonths(3)));
        Enrollment enrollment = enrollmentRepository.save(new Enrollment(student, course, LocalDate.now()));
        mockMvc.perform(delete("/api/enrollments/" + enrollment.getEnrollmentId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/enrollments/" + enrollment.getEnrollmentId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

