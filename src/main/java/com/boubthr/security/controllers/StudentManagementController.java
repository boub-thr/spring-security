package com.boubthr.security.controllers;

import com.boubthr.security.entities.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/vi/students")
public class StudentManagementController {

    private final List<Student> students = new ArrayList<Student>(Arrays.asList(
            new Student(1L, "Ahmed Ali"),
            new Student(2L,"james albert"),
            new Student(3L, "sarah shine")
    ));

    @GetMapping
    public  List<Student> getAllStudents(){
        return students ;
    }

    @GetMapping("/{idStudent}")
    public Student getStudent(@PathVariable  Long idStudent){
        return extractStudent(idStudent);
    }

    @PostMapping
    public void registerNewStudent(@RequestBody  Student student){
        students.add(student);
    }

    @PutMapping()
    public void updateStudent(@RequestBody Student student){
        Student studentToUpdate = extractStudent(student.getId());
        int indexOfStudentToUpdate = students.indexOf(studentToUpdate);
        students.get(indexOfStudentToUpdate).setName(student.getName());
        students.get(indexOfStudentToUpdate).setId(student.getId());
    }

    @DeleteMapping("/{idStudent}")
    public void deleteStudent(@PathVariable Long idStudent){
        Student student = extractStudent(idStudent);
        students.remove(student);
    }

    private Student extractStudent(Long idStudent) {
        return students.stream()
                .filter(std -> std.getId() == idStudent)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student not Found"));
    }
}
