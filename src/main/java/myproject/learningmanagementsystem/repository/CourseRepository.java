package myproject.learningmanagementsystem.repository;

import myproject.learningmanagementsystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByCourseName(String courseName);

    List<Course> findByStartDateAfter(LocalDate date);

    @Query("SELECT c FROM Course c WHERE c.startDate > :date")
    List<Course> findCoursesStartingAfter(@Param("date") LocalDate date);
}

