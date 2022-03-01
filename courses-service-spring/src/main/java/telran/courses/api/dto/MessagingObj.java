package telran.courses.api.dto;

import java.io.Serializable;

public class MessagingObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessagingObj(String action, int id) {
		
		this.action = action;
		this.id = id;
	}
	
	private String action;	
	private int id;
	
	public String getAction() {
		return action;
	}
	
	public int getId() {
		return id;
	}
}
