package telran.students.jpa.entities;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import telran.students.dto.*;

@Entity
@Table(name="students")
public class StudentJpa {

	@Id
	public int stid;
	
	@Column(unique = true, nullable = false)
	public String name;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	List<MarkJpa> marks;
	
	public static StudentJpa build(Student student) {
		
		var studentJpa = new StudentJpa();
		studentJpa.stid = student.stid;
		studentJpa.name = student.name;
		return studentJpa;
	}
	
	public Student getStudentDto() {
		
		var student = new Student();
		student.stid = stid;
		student.name = name;
		return student;
	}
}
