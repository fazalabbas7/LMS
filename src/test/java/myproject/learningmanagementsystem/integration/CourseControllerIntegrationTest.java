package myproject.learningmanagementsystem.integration;

import myproject.learningmanagementsystem.model.Course;
import myproject.learningmanagementsystem.repository.CourseRepository;
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
public class CourseControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private CourseRepository courseRepository;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateCourse() throws Exception {
        String courseJson = """
                {
                    "courseName": "Spring Boot Basics",
                    "description": "Learn the basics of Spring Boot",
                    "startDate": "2024-01-01",
                    "endDate": "2024-02-01"
                }
                """;
        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Spring Boot Basics"))
                .andExpect(jsonPath("$.description").value("Learn the basics of Spring Boot"));
    }

    @Test
    void testGetAllCourses() throws Exception {
        courseRepository.save(new Course("Java Basics", "Intro to Java", LocalDate.now(), LocalDate.now().plusMonths(1)));
        courseRepository.save(new Course("Advanced Java", "Deep dive into Java", LocalDate.now(), LocalDate.now().plusMonths(2)));
        mockMvc.perform(get("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetCourseById() throws Exception {
        Course course = courseRepository.save(new Course("REST APIs", "Learn RESTful APIs", LocalDate.now(), LocalDate.now().plusMonths(1)));
        mockMvc.perform(get("/api/courses/" + course.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("REST APIs"));
    }

    @Test
    void testUpdateCourse() throws Exception {
        Course course = courseRepository.save(new Course("Microservices", "Learn microservices", LocalDate.now(), LocalDate.now().plusMonths(1)));
        String updatedCourseJson = """
                {
                    "courseName": "Microservices Updated",
                    "description": "Learn updated microservices",
                    "startDate": "2024-01-15",
                    "endDate": "2024-03-15"
                }
                """;
        mockMvc.perform(put("/api/courses/" + course.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCourseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Microservices Updated"))
                .andExpect(jsonPath("$.description").value("Learn updated microservices"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        Course course = courseRepository.save(new Course("Delete Me", "To be deleted", LocalDate.now(), LocalDate.now().plusMonths(1)));
        mockMvc.perform(delete("/api/courses/" + course.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/courses/" + course.getCourseId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
