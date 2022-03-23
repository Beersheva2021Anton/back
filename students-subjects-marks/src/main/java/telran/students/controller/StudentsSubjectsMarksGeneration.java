package telran.students.controller;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import telran.students.dto.*;
import telran.students.service.StudentsService;

//@Component
public class StudentsSubjectsMarksGeneration {

	@Autowired
	private StudentsService studentsService;
	
	private static Logger LOG = LoggerFactory.getLogger(StudentsSubjectsMarksGeneration.class);
	
	private String names[] = {"Abraham", "Sarah", "Itshak", "Rahel", "Asaf", "Yacob","Rivka", "Yosef",
			"Benyanim", "Dan", "Ruben", "Moshe", "Aron", "Yehashua", "David", "Solomon", "Nefertity",
			"Naftaly", "Natan","Asher"};
	private String subjects[] = {"Java core", "Java Technologies", 
			"Spring Data", "Spring Security", "Spring Cloud", "CSS", "HTML", "JS", "React", "Material-UI"};
	
	@Value("${app.generation.marks.amount: 100}")
	private int nMarks;
	
	@PostConstruct
	public void createDB() {
		
		addStudents();
		addSubjects();
		addMarks();
		LOG.info("Created {} random marks in DB", nMarks);
	}

	private void addMarks() {
		
		IntStream.range(0, nMarks).forEach(i -> addOneMark());
	}
	
	private void addOneMark() {
		
		int stid = getRandomInteger(1, names.length);
		int suid = getRandomInteger(1, subjects.length);
		int mark = getRandomInteger(60, 100);
		
		studentsService.addMark(new Mark(stid, suid, mark));
	}

	private void addSubjects() {
		
		IntStream.range(0, subjects.length).forEach(i -> {
			studentsService.addSubject(new Subject(i + 1, subjects[i]));
		});
	}

	private void addStudents() {
		
		IntStream.range(0, names.length).forEach(i -> {
			studentsService.addStudent(new Student(i + 1, names[i]));
		});
	}
	
	private int getRandomInteger(int min, int max) {
		
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}
