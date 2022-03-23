package telran.students.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.students.jpa.entities.SubjectJpa;

public interface SubjectsRepo extends JpaRepository<SubjectJpa, Integer> {

}
