package dng.vku.yu.uniform_vku.service.impl;

import dng.vku.yu.uniform_vku.model.entity.Student;
import dng.vku.yu.uniform_vku.repository.StudentRepository;
import dng.vku.yu.uniform_vku.service.StudentService;
import dng.vku.yu.uniform_vku.util.ShaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudent() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public void saveStudent(Student student) {
            String pText = student.getName()+"."+student.getPhone();
            String algorithm = "SHA3-256";
            byte[] shaInBytes = ShaUtil.digest(pText.getBytes(StandardCharsets.UTF_8), algorithm);
            student.setVerificationCode(ShaUtil.bytesToHex(shaInBytes));
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }
}
