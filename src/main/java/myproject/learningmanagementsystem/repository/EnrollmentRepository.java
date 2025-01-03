package myproject.learningmanagementsystem.repository;

import myproject.learningmanagementsystem.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
}
