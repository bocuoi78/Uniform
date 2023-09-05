package dng.vku.yu.uniform_vku.service;

import dng.vku.yu.uniform_vku.model.entity.Student;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudent();
    List<Student> getStudents(Date fromTime, Date toTime);
    void saveStudent(Student student);
    void deleteStudent(Long id);
    void pay(Long id);
    void receive(Long id);
    Optional<Student> findStudentByVerificationCode(String verificationCode);
    Optional<Student> findStudentById(Long id);
}
