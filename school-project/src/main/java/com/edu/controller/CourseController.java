package com.edu.controller;

import com.edu.model.Course;
import com.edu.model.Teacher;
import com.edu.repository.CourseRepository;
import com.edu.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * Returns ALL COURSES with TEACHERS!
     *
     * POSTMAN:
     *
     * GET  http://localhost:8081/courses
     * RESPONSE
     * [
     * {
     * "id": 2,
     * "courseName": "Marketing",
     * "teacher": {
     * "id": 2,
     * "name": "Pavic Pavle"
     * }
     * }
     * ]
     */
    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    /**
     * Returns COURSE  with specified id and its TEACHER (if exists).
     *
     * GET http://localhost:8081/courses/2
     * RESPONSE
     * {
     * "id": 2,
     * "courseName": "Marketing",
     * "teacher": {
     * "id": 2,
     * "name": "Pavic Pavle"
     * }
     * }
     */
    @GetMapping("/courses/{id}")
    public Course getCoursesById(@PathVariable long id) {
        if (courseRepository.findById(id).isPresent()) {
            return courseRepository.findById(id).get();
        } else {
            System.out.println("Course with id: " + id + " is not found.");
            return null;
        }
    }

    /**
     * Creates new COURSE for specified TEACHER - id.
     *
     * POST
     * http://localhost:8081/teachers/4/courses
     * BODY
     * {
     * "courseName": "Java Core"
     * }
     *
     * RESPONSE
     * {
     * "id": 4,
     * "courseName": "Java Core",
     * "teacher": {
     * "id": 4,
     * "name": "Mitar Miric"
     * }
     * }
     */
    @Transactional
    @PostMapping("/teachers/{id}/courses")
    public Course createCourse(@PathVariable long id, @RequestBody Course course) {
        Teacher teacher = new Teacher();
        if (teacherRepository.findById(id).isPresent()) {
            teacher = teacherRepository.findById(id).get();
            course.setTeacher(teacher);
            return courseRepository.save(course);
        } else {
            System.out.println("Teacher with id: " + id + " is not found.");
            return null;
        }
    }

    /**
     * Updates COURSE with specified id.
     *
     * PUT
     * http://localhost:8081/courses/2
     *
     * BODY
     * {
     * "courseName": "Marketing Advanced"
     * }
     *
     * RESPONSE
     * {
     * "id": 2,
     * "courseName": "Marketing Advanced",
     * "teacher": {
     * "id": 2,
     * "name": "Pavic Pavle"
     * }
     * }
     */
    @Transactional
    @PutMapping("/courses/{id}")
    public Course updateeCourse(@PathVariable Long id, @RequestBody Course course) {
        if (courseRepository.findById(id).isPresent()) {
            Course crs = courseRepository.findById(id).get();
            crs.setCourseName(course.getCourseName());
            return crs;
        } else {
            System.out.println("Course with id: " + id + " is not found..");
            return null; // The specified Course is not found.
        }
    }

    /**
     * Delete COURSE with specified id
     *
     * DELETE
     * http://localhost:8081/courses/2
     */
    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable Long id) {
        if (courseRepository.findById(id).isPresent()) {
            courseRepository.deleteById(id);
            System.out.println("Course with id: " + id + " deleted");
        } else
            System.out.println("Course with id: " + id + " is not found.");
    }

    /**
     * Deletes ONLY COURSE for SPECIFIED teacher (id)
     *
     * DELETE
     * http://localhost:8081/teachers/delete/4/course
     */
    @DeleteMapping("/teachers/delete/{id}/course")
    public void deleteTeacher(@PathVariable Long id) {
        if (teacherRepository.findById(id).isPresent()) {
            courseRepository.deleteById(id);
        } else System.out.println("Teacher with id: " + id + " is not found.");
    }

}


