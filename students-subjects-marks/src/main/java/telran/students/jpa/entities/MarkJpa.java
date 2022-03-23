package telran.students.jpa.entities;

import javax.persistence.*;

@Entity
@Table(name="marks")
public class MarkJpa {

	@Id
	@GeneratedValue
	public int id;
	
	public int mark;
	
	@ManyToOne
	public StudentJpa student;
	
	@ManyToOne
	public SubjectJpa subject;

	public MarkJpa(int mark, StudentJpa student, SubjectJpa subject) {
		this.mark = mark;
		this.student = student;
		this.subject = subject;
	}
	
	public MarkJpa() {
	}
}
