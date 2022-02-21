package com.scm.ftn.student;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /* managed by lombok @allArgsConstructor
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }*/

    @GetMapping
    public List<Student> getAllStudents() {
        //throw new IllegalStateException("Oops Error");
        return studentService.getAllStudents();
    }

   /* @GetMapping
    public List<Student> getAllStudents() {
        List<Student> students = Arrays.asList(
                new Student(1L, "Kamila", "Kamila@ftn.uns.ac", Gender.FEMALE),
                new Student(2L, "Kamila", "Kamila@ftn.uns.ac", Gender.FEMALE)
        );
        return students;
    }*/

    @PostMapping
    public void addStudent(@Valid @RequestBody Student student){
        studentService.addStudent(student);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
}
