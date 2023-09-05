package dng.vku.yu.uniform_vku.controller;

import dng.vku.yu.uniform_vku.model.entity.Student;
import dng.vku.yu.uniform_vku.service.StudentService;
import dng.vku.yu.uniform_vku.util.ExcelGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping("/list-all")
    public String listAll(Model model) {
        List<Student> students = studentService.getAllStudent();
        model.addAttribute("students", students);
        return "list-all";
    }

    @RequestMapping("/list")
    public String list(
            Model model,
            @RequestParam (required = false) String fromTime,
            @RequestParam (required = false) String toTime
    ) throws ParseException {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyy");
        LocalDateTime now = LocalDateTime.now();
        if (fromTime==null | Objects.equals(fromTime, "")) fromTime = date.format(now);
        if (toTime==null | Objects.equals(toTime, "")) toTime = date.format(now);
        Date fromTimeF = new SimpleDateFormat("dd/MM/yyy HH:mm:ss").parse(fromTime + " 00:00:00");
        Date toTimeF = new SimpleDateFormat("dd/MM/yyy HH:mm:ss").parse(toTime + " 23:59:59");
        List<Student> students = studentService.getStudents(fromTimeF, toTimeF);
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
        return "redirect:/list";
    }

    @RequestMapping("/export")
    public String exportIntoExcelFile(
            HttpServletResponse response,
            @RequestParam (required = false) String fromTime,
            @RequestParam (required = false) String toTime
    ) throws IOException, ParseException {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyy");
        LocalDateTime now = LocalDateTime.now();
        if (fromTime==null | Objects.equals(fromTime, "")) fromTime = date.format(now);
        if (toTime==null | Objects.equals(toTime, "")) toTime = date.format(now);
        Date fromTimeF = new SimpleDateFormat("dd/MM/yyy HH:mm:ss").parse(fromTime + " 00:00:00");
        Date toTimeF = new SimpleDateFormat("dd/MM/yyy HH:mm:ss").parse(toTime + " 23:59:59");

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=uniform" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List <Student> listOfStudents = studentService.getStudents(fromTimeF, toTimeF);
        ExcelGenerator generator = new ExcelGenerator(listOfStudents);
        generator.generateExcelFile(response);
        return "redirect:/list";
    }

    @RequestMapping("/export-all")
    public String exportAllIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=uniform" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Student> listOfStudents = studentService.getAllStudent();
        ExcelGenerator generator = new ExcelGenerator(listOfStudents);
        generator.generateExcelFile(response);
        return "redirect:/list-all";
    }
}