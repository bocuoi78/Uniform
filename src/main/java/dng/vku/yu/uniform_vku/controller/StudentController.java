package dng.vku.yu.uniform_vku.controller;

import dng.vku.yu.uniform_vku.model.entity.Student;
import dng.vku.yu.uniform_vku.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        List<Student> students = studentService.getAllStudent();

        model.addAttribute("students", students);

        return "list";
    }

    @RequestMapping(value = "add")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        return "add";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editStudent(@RequestParam("id") Long studentId, Model model) {
        Optional<Student> studentEdit = studentService.findStudentById(studentId);
        studentEdit.ifPresent(student -> model.addAttribute("student", student));
        return "edit";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Student student) {
        studentService.saveStudent(student);
        return "redirect:/edit?id=" + student.getId();
    }

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String pay(@RequestParam("id") Long studentId, Model model) {
        studentService.pay(studentId);
        return "redirect:/";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String findStudentByVerificationCode(@RequestParam("code") String studentCode, Model model) {
        Optional<Student> studentCheck = studentService.findStudentByVerificationCode(studentCode);
        studentCheck.ifPresent(student -> model.addAttribute("student", student));
        return "check";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteStudent(@RequestParam("id") Long studentId, Model model) {
        studentService.deleteStudent(studentId);
        return "redirect:/list";
    }

    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    public String receive(@RequestParam("id") Long studentId, Model model, HttpServletRequest request) {
        studentService.receive(studentId);
//        String request_text = request.getRequestURL().toString().split("/")[3];
        return "redirect:/list";
    }
}