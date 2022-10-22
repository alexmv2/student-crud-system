package es.alexmv.student.crud.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.alexmv.student.crud.app.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
