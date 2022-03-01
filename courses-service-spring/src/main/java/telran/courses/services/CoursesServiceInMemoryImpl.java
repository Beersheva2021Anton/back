package telran.courses.services;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import telran.courses.api.dto.Course;
import telran.courses.api.dto.MessagingObj;

import static telran.courses.api.ApiConstants.*;

@Service
public class CoursesServiceInMemoryImpl implements CoursesService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private Logger LOG = LoggerFactory.getLogger(CoursesServiceInMemoryImpl.class);
	private Map<Integer, Course> courses = new ConcurrentHashMap<>();
	
	@Value("${app.courses.fileName: courses.data}")
	private String fileName;
	
	@Autowired
	private SimpMessagingTemplate smt;

	@Override
	public synchronized Course addCourse(Course course) {
		
		var id = getUniqueId();
		course.id = id;
		courses.put(id, course);
		smt.convertAndSend("/topic/courses", new MessagingObj("added", id));
		return course;
	}

	@Override
	public Course removeCourse(int id) {
		
		var res = courses.remove(id);
		if (res != null) {
			smt.convertAndSend("/topic/courses", new MessagingObj("removed", id));
		}		
		return res;
	}

	@Override
	public boolean exists(int id) {
		
		return courses.containsKey(id);
	}

	@Override
	public Course updateCourse(int id, Course newCourse) {
		
		var res = courses.replace(id, newCourse);
		if (res != null) {
			smt.convertAndSend("/topic/courses", new MessagingObj("updated", id));
		}
		return res;
	}

	@Override
	public Course getCourse(int id) {
		
		return courses.get(id);
	}

	@Override
	public List<Course> getAllCourses() {
		
		return courses.values().stream().toList();
	}

	@Override
	public void restore() {
		
		try (var ois = new ObjectInputStream(new FileInputStream(fileName))) {			
			courses = (Map<Integer, Course>) ois.readObject();
			LOG.info("Courses have been restored from file {}", fileName);
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
			LOG.info("Courses have been saved into file {}", fileName);
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
