package telran.students;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.students.dto.*;
import telran.students.service.StudentsService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentsSubjectsMarksApplicationTests {
	
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	StudentsService studentsService;
	
	@Test
	void contextLoads() {
		assertNotNull(mockMvc);
	}
	
	@Test
	@Order(1)
	void dbLoad() {
		
		studentsService.addStudent(new Student(1, "Moshe"));
		studentsService.addStudent(new Student(2, "Sara"));
		studentsService.addStudent(new Student(3, "Vasya"));
		
		studentsService.addSubject(new Subject(1, "React"));
		studentsService.addSubject(new Subject(2, "Java"));
		
		studentsService.addMark(new Mark(1, 1, 100));
		studentsService.addMark(new Mark(1, 2, 100));
		studentsService.addMark(new Mark(2, 1, 80));
		studentsService.addMark(new Mark(2, 2, 80));
		studentsService.addMark(new Mark(3, 2, 60));
	}

	@Test
//	@Sql("testDB.sql")
	void bestStudents() throws Exception {
		var resJson = mockMvc.perform(MockMvcRequestBuilders.get("/students/best"))
				.andReturn().getResponse().getContentAsString();
		var arr = mapper.readValue(resJson, String[].class);
		assertEquals(1, arr.length);
		assertEquals(arr[0], "Moshe,100.0");
	}
	
	@Test
	void bestTopStudents() throws Exception {
		var resJson = mockMvc.perform(MockMvcRequestBuilders.get("/students/best?amount=1"))
				.andReturn().getResponse().getContentAsString();
		var arr = mapper.readValue(resJson, String[].class);
		assertEquals(1, arr.length);
		assertEquals(arr[0], "Moshe,100");
	}
	
	@Test
	void worstMarks() {
		
	}
}
