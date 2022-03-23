package telran.students.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import telran.students.dto.*;
import telran.students.service.*;

@RestController
@RequestMapping("/students")
public class StudentsRestController {

	@Autowired
	private StudentsService studentsService;

	@GetMapping("/subject/mark")
	public List<StudentSubjectMark> getStudSubjMark(String name, String subj) {
		return studentsService.getMarksOfStudentForSubject(name, subj);
	}

	@GetMapping("/best")
	public List<String> getBestStudents(
			@RequestParam(required = false, defaultValue = "0", name = "amount") int nStudents) {
		return nStudents == 0 
				? studentsService.getBestStudents() 
						: studentsService.getNBestStudents(nStudents);
	}
	
	@GetMapping("/best/subject")
	public List<Student> getNBestStudentsForSubject(@RequestParam(name="amount") int nStudents, 
			@RequestParam(name="subj") String subject) {
		return studentsService.getNBestStudentsForSubject(nStudents, subject);
	}

	@PostMapping("/query")
	public List<String> getQueryResult(@RequestBody QueryDto queryDto) {
		return queryDto.type == QueryType.JPQL 
				? studentsService.jpqlQuery(queryDto.query) 
						: studentsService.nativeQuery(queryDto.query);
	}
	
	@GetMapping("/worst/marks")
	public List<StudentSubjectMark> getMarksForNWorstStudents(@RequestParam(name="amount") int nStudents) {
		return studentsService.getMarksOfWorstStudents(nStudents);
	}
	
	@GetMapping("/marks/distribution")
	public List<IntervalMarks> getMarksDistribution(int interval) {
		return studentsService.marksDistribution(interval);
	}
	
	@DeleteMapping("/worst/marks")
	public List<Student> deleteStudentsByMarks(int avgMark, int nMarks) {
		return studentsService.removeStudents(avgMark, nMarks);
	}
}
