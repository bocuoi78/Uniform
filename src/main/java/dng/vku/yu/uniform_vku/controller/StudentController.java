package dng.vku.yu.uniform_vku.controller;

import dng.vku.yu.uniform_vku.model.entity.Student;
import dng.vku.yu.uniform_vku.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Student> students = studentService.getAllStudent();

        model.addAttribute("students", students);

        return "index";
    }

    @RequestMapping(value = "add")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        return "addStudent";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editStudent(@RequestParam("id") Long studentId, Model model) {
        Optional<Student> studentEdit = studentService.findStudentById(studentId);
        studentEdit.ifPresent(student -> model.addAttribute("student", student));
        return "editStudent";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Student student) {
        studentService.saveStudent(student);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteStudent(@RequestParam("id") Long studentId, Model model) {
        studentService.deleteStudent(studentId);
        return "redirect:/";
    }
}