package dng.vku.yu.uniform_vku.service;

import dng.vku.yu.uniform_vku.model.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudent();

    void saveStudent(Student student);

    void deleteStudent(Long id);

    Optional<Student> findStudentById(Long id);
}