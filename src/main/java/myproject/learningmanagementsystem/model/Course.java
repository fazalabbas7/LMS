package myproject.learningmanagementsystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String courseName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    public Course() {
    }

    public Course(String courseName, String description, LocalDate startDate, LocalDate endDate) {
        this.courseName = courseName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Course(int i1, String description, LocalDate now, LocalDate now1) {
        this.courseId = i1;
        this.description = description;
        this.startDate = now;
        this.endDate = now1;
    }

    public Course(int i, String introductionToTesting) {
            this.courseId = i;
        this.description = introductionToTesting;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';

    }

    public void setId(int i) {
        this.courseId = i;
    }

    public void setName(String testCourse) {
        this.courseName = testCourse;

    }

    public void thenReturn(Course course) {
        this.courseId = course.getCourseId();
        this.courseName = course.getCourseName();
        this.description = course.getDescription();
        this.startDate = course.getStartDate();
        this.endDate = course.getEndDate();
    }
}
