package telran.students.jpa.entities;

import javax.persistence.*;
import telran.students.dto.*;

@Entity
@Table(name="subjects")
public class SubjectJpa {

	@Id
	public int suid;
	
	@Column(unique = true, nullable = false)
	public String subject;
	
	public static SubjectJpa build(Subject subject) {
		
		var subjectJpa = new SubjectJpa();
		subjectJpa.suid = subject.suid;
		subjectJpa.subject = subject.subject;
		return subjectJpa;
	}
	
	public Subject getSubjectDto() {
		
		var subject = new Subject();
		subject.suid = suid;
		subject.subject = this.subject;
		return subject;
	}
}
