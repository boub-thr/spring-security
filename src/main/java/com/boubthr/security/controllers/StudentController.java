package com.boubthr.security.controllers;

import com.boubthr.security.entities.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final List<Student> students = Arrays.asList(
            new Student(1L, "Ahmed Ali"),
            new Student(2L,"james albert"),
            new Student(3L, "sarah shine")
    );

    @GetMapping("/{idStudent}")
    public Student getStudent(@PathVariable Long idStudent){
        Student reqStudent = students.stream()
                    .filter(std -> std.getId() == idStudent)
                .findAny()
                .orElseThrow(() -> new IllegalStateException(""));
        return reqStudent;
    }


}
