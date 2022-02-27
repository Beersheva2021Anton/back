package telran.courses.services;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
	public Course addCourse(Course course) {
		
		var id = getUniqueId();
		course.id = id;
		var res = courses.put(id, course);
		if (res == null) {
			LOG.warn("Course with id '{}' already exists", id);
		} else {
			LOG.info("Course with id '{}' was added", id);
		}
		return res;
	}

	@Override
	public Course removeCourse(int id) {
		
		var res = courses.remove(id);
		if (res == null) {
			LOG.warn("Course with id '{}' doesn't exist", id);
		} else {
			LOG.info("Course with id '{}' was removed", id);
		}
		return res;
	}

	@Override
	public boolean exists(int id) {
		
		return courses.containsKey(id);
	}

	@Override
	public Course updateCourse(int id, Course newCourse) {
		
		var res = courses.computeIfPresent(id, (k,v) -> newCourse);
		if (res == null) {
			LOG.warn("Course with id '{}' doesn't exist", id);
		}else {
			LOG.info("Course with id '{}' was updated", id);
		}
		return res;
	}

	@Override
	public Course getCourse(int id) {
		
		var res = courses.get(id);
		if (res == null) {
			LOG.warn("Course with id '{}' doesn't exist", id);
		}
		return res;
	}

	@Override
	public List<Course> getAllCourses() {
		
		return (List<Course>) courses.values();
	}

	@Override
	public void restore() {
		
		try {
			var ois = new ObjectInputStream(new FileInputStream(fileName));
			courses = (Map<Integer, Course>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	@Override
	public void save() {
		
		try {
			var oos = new ObjectOutputStream(new FileOutputStream(fileName));
			oos.writeObject(courses);
			oos.close();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	private int getUniqueId() {
		
		var res = 0;
		while (true) {
			res = MIN_ID + (int) Math.random() * (MAX_ID - MIN_ID + 1);
			if (!exists(res)) {
				break;
			}
		}
		return res;
	}
}
