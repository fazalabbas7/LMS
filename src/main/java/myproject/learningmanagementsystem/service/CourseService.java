package myproject.learningmanagementsystem.service;

import myproject.learningmanagementsystem.model.Course;
import myproject.learningmanagementsystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }
    public Course updateCourse(int id, Course course) {
        if (courseRepository.existsById(id)) {
            course.setCourseId(id);
            return courseRepository.save(course);
        }
        return null;
    }

    public Course updateCoursePartially(int id, Map<String, Object> updates) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            if (updates.containsKey("courseName")) {
                course.setCourseName((String) updates.get("courseName"));
            }
            if (updates.containsKey("description")) {
                course.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("startDate")) {
                course.setStartDate((LocalDate) updates.get("startDate"));
            }
            if (updates.containsKey("endDate")) {
                course.setEndDate((LocalDate) updates.get("endDate"));
            }

            return courseRepository.save(course);
        }
        return null;
    }

    public boolean deleteCourse(int id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;

    }
}
