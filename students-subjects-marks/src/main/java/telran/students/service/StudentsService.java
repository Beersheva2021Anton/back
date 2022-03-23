package telran.students.service;

import java.util.List;

import telran.students.dto.*;

public interface StudentsService {

	void addStudent(Student student);
	
	void addSubject(Subject subject);
	
	Mark addMark(Mark mark);
	
	List<StudentSubjectMark> getMarksOfStudentForSubject(String studentName, String subject);
	
	List<String> getBestStudents(); // returns names of students having average mark greater than common average mark
	
	List<String> getNBestStudents(int nStudents);
	
	List<Student> getNBestStudentsForSubject(int nStudents, String subject);
	
	List<StudentSubjectMark> getMarksOfWorstStudents(int nStudents);
	
	List<IntervalMarks> marksDistribution(int interval);
	
	List<String> jpqlQuery(String jpql);
	
	List<String> nativeQuery(String query);
	
	List<Student> removeStudents(int avgMark, int nMarks);
}
