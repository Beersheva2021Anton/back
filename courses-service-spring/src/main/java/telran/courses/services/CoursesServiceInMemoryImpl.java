package telran.courses.services;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import telran.courses.api.dto.Course;
import static telran.courses.api.ApiConstants.*;

@Service
public class CoursesServiceInMemoryImpl implements CoursesService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger LOG = LoggerFactory.getLogger(CoursesServiceInMemoryImpl.class);
	
	@Value("${app.courses.fileName: courses.data}")
	private String fileName;
	
	private Map<Integer, Course> courses = new ConcurrentHashMap<>();

	@Override
	public synchronized Course addCourse(Course course) {
		
		var id = getUniqueId();
		course.id = id;
		courses.put(id, course);
		return course;
	}

	@Override
	public Course removeCourse(int id) {
		
		return courses.remove(id);
	}

	@Override
	public boolean exists(int id) {
		
		return courses.containsKey(id);
	}

	@Override
	public Course updateCourse(int id, Course newCourse) {
		
		return courses.replace(id, newCourse);
	}

	@Override
	public Course getCourse(int id) {
		
		return courses.get(id);
	}

	@Override
	public List<Course> getAllCourses() {
		
		return (List<Course>) courses.values();
	}

	@Override
	public void restore() {
		
		try (var ois = new ObjectInputStream(new FileInputStream(fileName))) {			
			courses = (Map<Integer, Course>) ois.readObject();
		} catch (FileNotFoundException e) {
			LOG.info(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	@Override
	public void save() {
		
		try (var oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
			oos.writeObject(courses);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	private int getUniqueId() {
		
		var rnd = ThreadLocalRandom.current();
		var res = 0;
		do {
			res = rnd.nextInt(MIN_ID, MAX_ID);
		}
		while (exists(res));
		return res;
	}
}
