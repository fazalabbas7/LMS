package myproject.learningmanagementsystem.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDate enrollmentDate;
   
    public Student() {
    }
    
    public Student(String firstName, String lastName, String email, LocalDate dateOfBirth, LocalDate enrollmentDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.enrollmentDate = enrollmentDate;
    }

    public Student(int i, String johnDoe, String mail) {
        this.studentId = i;
        this.firstName = johnDoe;
        this.email = mail;
    }

    public Student(long l, String aliHassan) {
        this.studentId = (int) l;
        this.firstName = aliHassan;
    }

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setId(int id) {
        this.studentId = id;
    }
  
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
   
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
   
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
   
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
  
    public void setName(String firstName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }

    public int getId() {
        return 1;

    }

    public void setFullName(String johnDoe) {
        this.firstName = johnDoe;
        this.lastName = "Doe";
    }
}


