package myproject.learningmanagementsystem.integration;

import myproject.learningmanagementsystem.model.Student;
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
public class StudentControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private StudentRepository studentRepository;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void testCreateStudent() throws Exception {
        String studentJson = """
                {
                    "firstName": "Muzamil",
                    "lastName": "Khalid",
                    "email": "muzamil.khalid@gmail.com",
                    "dateOfBirth": "2000-01-01",
                    "enrollmentDate": "2024-01-01"
                }
                """;
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Muzamil"))
                .andExpect(jsonPath("$.lastName").value("Khalid"));
    }

    @Test
    void testGetAllStudents() throws Exception {
        studentRepository.save(new Student("Asman", "Khalid", "asman.khalid@gmail.com", LocalDate.of(1999, 5, 15), LocalDate.now()));
        studentRepository.save(new Student("Ahmad", "Ali", "ahmad.ali@gmail.com", LocalDate.of(2001, 8, 25), LocalDate.now()));
        mockMvc.perform(get("/api/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetStudentById() throws Exception {
        Student student = studentRepository.save(new Student("Abid", "Umir", "abid.umir@gmail.com", LocalDate.of(2002, 3, 18), LocalDate.now()));
        mockMvc.perform(get("/api/students/" + student.getStudentId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("abid.umir@gmail.com"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = studentRepository.save(new Student("Hassan", "Murtaza", "hassan.murtaza@gmail.com", LocalDate.of(1998, 7, 11), LocalDate.now()));
        String updatedStudentJson = """
                {
                    "firstName": "Hassan Updated",
                    "lastName": "Murtaza Updated",
                    "email": "hassan.murtaza@gmail.com",
                    "dateOfBirth": "1998-07-11",
                    "enrollmentDate": "2024-01-10"
                }
                """;
        mockMvc.perform(put("/api/students/" + student.getStudentId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedStudentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Hassan Updated"))
                .andExpect(jsonPath("$.lastName").value("Murtaza Updated"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        Student student = studentRepository.save(new Student("Hassan", "Murtaza", "hassan.murtaza@gmail.com", LocalDate.of(1998, 7, 11), LocalDate.now()));
        mockMvc.perform(delete("/api/students/" + student.getStudentId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
