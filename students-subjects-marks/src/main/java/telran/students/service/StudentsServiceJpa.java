package telran.students.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.students.dto.*;
import telran.students.jpa.entities.*;
import telran.students.jpa.repo.*;

@Service
public class StudentsServiceJpa implements StudentsService {

	private StudentsRepo students;
	private SubjectsRepo subjects;
	private MarksRepo marks;

	@PersistenceContext
	EntityManager em;

	@Autowired
	public StudentsServiceJpa(StudentsRepo students, SubjectsRepo subjects, MarksRepo marks) {
		this.students = students;
		this.subjects = subjects;
		this.marks = marks;
	}

	@Override
	public void addStudent(Student student) {

		students.save(StudentJpa.build(student));
	}

	@Override
	public void addSubject(Subject subject) {

		subjects.save(SubjectJpa.build(subject));
	}

	@Override
	@Transactional
	public Mark addMark(Mark mark) {

		var studentJpa = students.findById(mark.stid).orElse(null);
		var subjectJpa = subjects.findById(mark.suid).orElse(null);

		if (studentJpa != null && subjectJpa != null) {
			var markJpa = new MarkJpa(mark.mark, studentJpa, subjectJpa);
			marks.save(markJpa);
			return mark;
		}
		return null;
	}

	@Override
	public List<StudentSubjectMark> getMarksOfStudentForSubject(String studentName, String subject) {

		return marks.findByStudentNameAndSubjectSubject(studentName, subject);
	}

	@Override
	public List<String> getBestStudents() {

		return marks.findBestStudents();
	}

	@Override
	public List<String> getNBestStudents(int nStudents) {

		return marks.findNBestStudents(nStudents);
	}

	@Override
	public List<Student> getNBestStudentsForSubject(int nStudents, String subject) {
		
		return students.findNBestStudentsForSubject(nStudents, subject).stream()
				.map(jpa -> jpa.getStudentDto()).toList();
	}

	@Override
	public List<StudentSubjectMark> getMarksOfWorstStudents(int nStudents) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IntervalMarks> marksDistribution(int interval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> jpqlQuery(String jpql) {

		var query = em.createNativeQuery(jpql);
		return getResult(query);
	}

	private List<String> simpleRequest(List<Object> res) {
		return res.stream().map(Object::toString).toList();
	}

	private List<String> multiProjectionRequest(List<Object[]> res) {
		return res.stream().map(Arrays::deepToString).toList();
	}

	@Override
	public List<String> nativeQuery(String query) {
		var nativeQuery = em.createNativeQuery(query);
		return getResult(nativeQuery);
	}

	private List<String> getResult(Query query) {
		var res = query.getResultList();
		if (res.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		return res.get(0).getClass().isArray() ? multiProjectionRequest(res) : simpleRequest(res);
	}

	@Override
	@Transactional
	public List<Student> removeStudents(int avgMark, int nMarks) {		
		var listJpa = students.findStudentsForDeletion(avgMark, nMarks);
		listJpa.forEach(students::delete);
		return listJpa.stream().map(StudentJpa::getStudentDto).toList();
	}

}
