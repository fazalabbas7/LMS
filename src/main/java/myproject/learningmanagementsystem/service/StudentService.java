package myproject.learningmanagementsystem.service;

import myproject.learningmanagementsystem.model.Student;
import myproject.learningmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    private int studentId;
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student getStudentById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
    public Student updateStudent(int id, Student student) {

        if (studentRepository.existsById(id)) {

            student.setId(id);

            return studentRepository.save(student);
        }

        throw new RuntimeException("Student not found with id: " + id);
    }

    public Student updateStudentPartially(int id, Map<String, Object> updates) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            if (updates.containsKey("name")) {
                student.setName((String) updates.get("name"));
            }
            if (updates.containsKey("email")) {
                student.setEmail((String) updates.get("email"));
            }
            return studentRepository.save(student);
        }
        throw new RuntimeException("Student not found with id: " + id);
    }
    public boolean deleteStudent(int id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Student createStudentById(int StudentId, Student newStudent) {
        studentId = StudentId;
        return newStudent;
    }
    public Student updateStudentId(int studentId, Student updatedStudent) {
        this.studentId = studentId;
        return updatedStudent;
    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
