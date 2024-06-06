package com.edu.controller;

import com.edu.model.Teacher;
import com.edu.repository.CourseRepository;
import com.edu.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Returns ALL TEACHERS.
     *
     * GET
     * http://localhost:8081/teachers
     *
     * RESPONSE
     * [
     *     {
     *         "id": 2,
     *         "name": "Pavle Pavic"
     *     },
     *     {
     *         "id": 3,
     *         "name": "Mika Mikic "
     *     }
     * ]
     *
     */
    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }


    /**
     * Returns TEACHER with specified id.
     *
     * GET
     * http://localhost:8081/teachers/2
     * RESPONSE
     * {
     *     "id": 2,
     *     "name": "Pavic Pavle"
     * }
     *
     */
    @GetMapping("/teachers/{id}")
    public Teacher getTeacherById(@PathVariable long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        return teacher;
    }

    /** Creates TEACHER.
     *
     *  POST  http://localhost:8081/teachers
     *  BODY
     *  {
     *      "name": "Mitar Miric"
     *  }
     *
     *   RESPONSE
     *  {
     *     "id": 4,
     *     "name": "Mitar Miric""
     *  }
     */
    @PostMapping("/teachers")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(new Teacher(teacher.getName()));
    }


    /**
     * Updates TEACHER with specified id
     *
     *  PUT
     *  http://localhost:8081/teachers/4
     *  BODY
     *  {
     *     "name": "Mitar Mika "
     * }
     *
     *  RESPONSE
     *  {
     *     "id": 4,
     *     "name": "Mitar Mika "
     * }
     *
     */
    @PutMapping("/teachers/{id}")
    public Teacher updateTeacher(@PathVariable long id, @RequestBody Teacher teacher) {
        Teacher teacherOpt = new Teacher();
        if (teacherRepository.findById(id).isPresent()) {
            teacherOpt = teacherRepository.findById(id).get();
            teacherOpt.setName(teacher.getName());
            return teacherRepository.save(teacherOpt);
        }
        else {
            System.out.println("Teacher with id: " + id + " is not found.");
            return null;
        }
    }


    /**
     * Deletes TEACHER with specified id and its COURSE (if exists).
     *
     *  DELETE
     *  http://localhost:8081/teachers/2
     *
     */
    @DeleteMapping("/teachers/{id}")
    public void deleteTeacher(@PathVariable long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        }
        if (teacherRepository.findById(id).isPresent()) {
            teacherRepository.deleteById(id);
        }
        else
            System.out.println("Teacher with id: " + id + " is not found.");
    }
}
