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
//        Student newStudent = new Student();
//        newStudent.setName(student.getName());
//        newStudent.setPhone(student.getPhone());
//        newStudent.setGender(student.getGender());
//        newStudent.setBirthday(student.getBirthday());
//        newStudent.setClass_name(student.getClass_name());
//        newStudent.setSize(student.getSize());
//        newStudent.setPaid(student.getPaid());
//        newStudent.setReceived(student.getReceived());
//        newStudent.setNote(student.getNote());
        Student savedStudent = studentRepository.save(student);
        String pText = savedStudent.getId().toString();
        String algorithm = "SHA3-256";
        byte[] shaInBytes = ShaUtil.digest(pText.getBytes(StandardCharsets.UTF_8), algorithm);
        savedStudent.setVerificationCode(ShaUtil.bytesToHex(shaInBytes));
        studentRepository.save(savedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public void pay(Long id) {
        System.out.println(!studentRepository.findById(id).get().getPaid());
        studentRepository.findById(id).get().setPaid(!studentRepository.findById(id).get().getPaid());
    }

    @Override
    public void receive(Long id) {
        Student student = studentRepository.findById(id).get();
        student.setReceived(!student.getReceived());
        studentRepository.save(student);
    }

    public Optional<Student> findStudentByVerificationCode(String verificationCode) {
        return studentRepository.findByVerificationCode(verificationCode);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }
}
