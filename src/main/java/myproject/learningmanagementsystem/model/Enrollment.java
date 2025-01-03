package myproject.learningmanagementsystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollmentId;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @Column(nullable = false)
    private LocalDate enrollmentDate;
    public Enrollment() {}
    public Enrollment(Student student, Course course, LocalDate enrollmentDate) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
    }

    public Enrollment(int i, int i1, int i2) {
    }

    public Enrollment(Student o, int i, int i1) {
        this.student = o;
        this.enrollmentId = i;
        this.course = new Course(i1, "N/A", LocalDate.now(), LocalDate.now());
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }
    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", student=" + (student != null ? student.getFullName() : "N/A") +
                ", course=" + (course != null ? course.getCourseName() : "N/A") +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }

    public void setId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getId() {
        return 1;
    }

    public void setCourseId(int i) {
        int integer = 1;
        this.course.setId(integer);
    }

    public <integer> void setStudentId(integer i) {
        int integer = 1;
        this.student.setId(integer);
    }
}
