package telran.students.jpa.repo;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import telran.students.jpa.entities.StudentJpa;

public interface StudentsRepo extends JpaRepository<StudentJpa, Integer> {

	@Query(value = "select stid, name from marks join students on student_stid=stid" + 
			" join subjects on subject_suid=suid where subject=:subject" +
			" group by stid, name, subject order by avg(mark) desc limit :nStudents", 
			nativeQuery = true)
	List<StudentJpa> findNBestStudentsForSubject(@Param("nStudents") int nStudents, 
			@Param("subject") String subject);

//	@Modifying
//	@Query(value = "delete from StudentJpa where stid in (select student.stid stid from MarkJpa " + 
//			"group by student.stid having avg(mark) < :avgMark and count(*) < :nMarks)", 
//			nativeQuery = false)
//	int deleteStudents(@Param("avgMark") double avgMark, @Param("nMarks") long nMarks);
	
	@Query("select s from StudentJpa s where stid in (select student.stid stid from MarkJpa " + 
			"group by student.stid having avg(mark) < :avgMark and count(*) < :nMarks)")
	List<StudentJpa> findStudentsForDeletion(@Param("avgMark") double avgMark, 
			@Param("nMarks") long nMarks);
}
