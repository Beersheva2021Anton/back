package telran.pulse.monitoring.controller;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.*;

import telran.pulse.monitoring.dto.*;
import telran.pulse.monitoring.services.VisitsService;

@RestController
@RequestMapping("/visits")
public class VisitsController {

	@Autowired
	VisitsService visitsService;
	
	@PostMapping("/patients")
	PatientData addPatient(@RequestBody PatientData patientData) {
		visitsService.addPatient(patientData.patientId, patientData.patientName);
		return patientData;
	}
	
	@PostMapping("/doctors")
	DoctorData addDoctor(@RequestBody DoctorData doctor) {
		visitsService.addDoctor(doctor.email, doctor.doctorName);
		return doctor;
	}
	
	@PostMapping()
	Map<String, Object> addVisit(@RequestBody Map<String, Object> visit) {
		visitsService.addVisit(
				(int)visit.get("patientId"), 
				(String)visit.get("doctorEmail"), 
				(LocalDateTime)visit.get("date"));
		return visit;
	}
	
	@GetMapping("/{patientId}")
	List<VisitData> getVisitsPatientDates(@PathVariable int patientId, 
			@DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime from, 
			@DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime to) {
		return visitsService.getVisits(patientId, from, to);
	}
}
