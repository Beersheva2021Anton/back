package telran.students.jpa.repo;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import telran.students.jpa.entities.*;
import telran.students.service.IntervalMarks;
import telran.students.service.StudentSubjectMark;

public interface MarksRepo extends JpaRepository<MarkJpa, Integer> {

	List<StudentSubjectMark> findByStudentNameAndSubjectSubject(String name, String subject);

	@Query("select s.name, avg(m.mark) from MarkJpa m join m.student s group by s.name" + 
			" having avg(m.mark) > (select avg(mark) from MarkJpa) order by avg(m.mark) desc")
	List<String> findBestStudents();
	
	@Query(value = "select name, avg(mark) from marks join students on student_stid=stid" + 
			" group by name order by avg(mark) desc limit :nStudents", 
			nativeQuery = true)
	List<String> findNBestStudents(@Param("nStudents") int nStudents);
	
	@Query(value = "select name as studentName, subject as subjectSubject mark from marks " +
			"join students on stid=student_stid " + 
			"join subjects on suid=subject_suid " +
			"where stid in (select stid from marks join students on student_stid=stid " +
			"group by stid order by avg(mark) limit :nStudents",
			nativeQuery = true)
	List<StudentSubjectMark> findMarksForNWorstStudents(@Param("nStudents") int nStudents);
	
	@Query(value = "select mark/:interval * :interval as min, :interval * (mark/:interval + 1) -1 as max, " + 
			"count(*) as occurrences from marks group by min, max order by min",
			nativeQuery = true)
	List<IntervalMarks> findMarksDistribution(@Param("interval") int interval);
}
