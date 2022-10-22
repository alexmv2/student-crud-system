package es.alexmv.student.crud.app.service;

import java.util.List;

import es.alexmv.student.crud.app.entity.Student;

public interface IStudentService {

	List<Student> getAllStudents();

	Student saveStudent(Student student);

	Student getStudentById(Long id);
	
	void deleteStudenById(Long id);

}
