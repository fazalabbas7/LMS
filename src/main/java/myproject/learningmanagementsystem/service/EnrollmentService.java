package myproject.learningmanagementsystem.service;

import myproject.learningmanagementsystem.exception.EnrollmentNotFoundException;
import myproject.learningmanagementsystem.model.Enrollment;
import myproject.learningmanagementsystem.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(int id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found with ID: " + id));
    }
    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public boolean deleteEnrollment(int id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Enrollment updateEnrollment(int enrollmentId, Enrollment updatedEnrollment) {
        Enrollment existingEnrollment = getEnrollmentById(enrollmentId);
        existingEnrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
        existingEnrollment.setStudent(updatedEnrollment.getStudent());
        existingEnrollment.setCourse(updatedEnrollment.getCourse());
        return enrollmentRepository.save(existingEnrollment);
    }
    public Enrollment updateEnrollmentPartially(int enrollmentId, Map<String, LocalDate> partialUpdate) {
        Enrollment existingEnrollment = getEnrollmentById(enrollmentId);
        if (partialUpdate.containsKey("enrollmentDate")) {
            existingEnrollment.setEnrollmentDate(partialUpdate.get("enrollmentDate"));
        }
        return enrollmentRepository.save(existingEnrollment);
    }
}
