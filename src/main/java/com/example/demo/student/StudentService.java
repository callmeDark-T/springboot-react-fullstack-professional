package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public List<Student> getAllStudents(){
        return  studentRepository.findAll();
    }

    public void addStudent(Student student) {

        // check if email exist or is taken
        // Boolean isStudentWithEmailAvailable = studentRepository.findStudentByEmail(student.getEmail()).isPresent();
        // OR
        Boolean isStudentWithEmailAvailable = studentRepository.selectExistsEmail(student.getEmail());
       if (isStudentWithEmailAvailable){
           throw new BadRequestException(String.format("Email: %s already exist ", student.getEmail()));
       }
        studentRepository.save(student);
    }

    public void deleteStudentById(Long id) {
        // check if student exist
        Boolean isStudentWithIdAvailable = studentRepository.existsById(id);
        if (!isStudentWithIdAvailable){
            throw new StudentNotFoundException(String.format("Student with ID: %s does not exist ", id));
        }
        studentRepository.deleteById(id);
    }
}
