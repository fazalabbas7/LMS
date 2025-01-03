package myproject.learningmanagementsystem.exception;

public class EnrollmentNotFoundException extends RuntimeException {

    public EnrollmentNotFoundException(String id) {
        super("Enrollment not found with ID: " + id);
    }
}
