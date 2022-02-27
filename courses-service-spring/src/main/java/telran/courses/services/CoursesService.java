package telran.courses.services;

import java.util.List;
import telran.courses.api.dto.*;

public interface CoursesService {

	Course addCourse(Course course);
	
	Course removeCourse(int id);
	
	boolean exists(int id);
	
	Course updateCourse(int id, Course newCourse);
	
	Course getCourse(int id);
	
	List<Course> getAllCourses();
	
	void restore();
	
	void save();
}
