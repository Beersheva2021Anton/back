package telran.courses.api.dto;

import javax.validation.constraints.*;
import static telran.courses.api.ApiConstants.*;

import java.io.Serializable;

public class Course implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Integer id;
	
	@NotEmpty
	public String name;
	
	@NotEmpty
	public String lecturer;
	
	@Min(MIN_HOURS) @Max(MAX_HOURS)
	public int hoursNum;
	
	@Min(MIN_COST) @Max(MAX_COST)
	public int cost;
	
	public CourseType type;
	
	@Size(min = 1, max = 2)
	public String[] dayEvening;
	
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}.*")
	public String startAt;
}
