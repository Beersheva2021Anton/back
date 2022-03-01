package telran.courses.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import telran.courses.api.dto.*;
import telran.courses.services.CoursesService;
import static telran.courses.api.ApiConstants.*;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;

@RestController
@RequestMapping(COURSES_MAPPING)
@CrossOrigin
public class CoursesController {

	private Logger LOG = LoggerFactory.getLogger(CoursesController.class);
	
	@Autowired
	private CoursesService coursesService;

	@PostMapping
	public Course addCourse(@RequestBody @Valid Course course) {

		LOG.debug("POST new course");
		return coursesService.addCourse(course);
	}

	@GetMapping
	public List<Course> getCourses() {

		LOG.debug("GET all courses");
		return coursesService.getAllCourses();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCourse(@PathVariable(name = "id") int id) {

		LOG.debug("GET course with id {}", id);
		var course = coursesService.getCourse(id);		
		return course == null
				? ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(String.format("Course with id '%d' not found", id))
				: ResponseEntity.ok(course);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeCourse(@PathVariable(name = "id") int id) {

		LOG.debug("DELETE course with id {}", id);
		var course = coursesService.removeCourse(id);
		if (course == null) {
			LOG.warn("Course with id {} not found", id);
		}
		return course == null
				? ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(String.format("Course with id '%d' not found", id))
				: ResponseEntity.ok(course);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCourse(@PathVariable(name = "id") int id, 
			@RequestBody @Valid Course newCourse) {

		LOG.debug("PUT course with id {}", id);
		
		if (newCourse.id != id) {
			LOG.error("Wrong id {}", newCourse.id);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong id");
		}
		
		var course = coursesService.updateCourse(id, newCourse);
		if (course == null) {
			LOG.warn("Course with id {} not found", id);
		}
		return course == null
				? ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(String.format("Course with id '%d' not found", id))
				: ResponseEntity.ok(course);
	}
	
	@PostConstruct
	public void restore() {
		
		LOG.info("Restore courses db");
		coursesService.restore();
	}
	
	@PreDestroy
	public void save() {
		
		LOG.info("Save courses db");
		coursesService.save();
	}
}
