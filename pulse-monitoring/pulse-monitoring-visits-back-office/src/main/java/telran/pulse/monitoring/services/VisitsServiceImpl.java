package telran.pulse.monitoring.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import telran.pulse.monitoring.dto.VisitData;
import telran.pulse.monitoring.entities.Doctor;
import telran.pulse.monitoring.entities.Patient;
import telran.pulse.monitoring.entities.Visit;
import telran.pulse.monitoring.repo.DoctorRepository;
import telran.pulse.monitoring.repo.PatientRepository;
import telran.pulse.monitoring.repo.VisitRepository;

@Service
public class VisitsServiceImpl implements VisitsService {

	DoctorRepository doctorRepository;
	PatientRepository patientRepository;
	VisitRepository visitRepository;
	
	public VisitsServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository,
			VisitRepository visitRepository) {
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.visitRepository = visitRepository;
	}

	@Override
	@Transactional
	public void addPatient(int patientId, String name) {
		if (patientRepository.existsById(patientId)) {
			throw new IllegalArgumentException("Duplicated patient ID: " + patientId);
		}
		var patient = new Patient(patientId, name);
		patientRepository.save(patient);
	}

	@Override
	@Transactional
	public void addDoctor(String email, String name) {
		if (doctorRepository.existsById(email)) {
			throw new IllegalArgumentException("Duplicated doctor EMAIL: " + email);
		}
		var doctor = new Doctor(email, name);
		doctorRepository.save(doctor);
	}

	@Override
	@Transactional
	public void addVisit(int patientId, String email, LocalDateTime dateTime) {
		var patient = patientRepository.findById(patientId).orElse(null);
		if (patient == null) {
			throw new IllegalArgumentException("Patient not found: " + patientId);
		}
		var doctor = doctorRepository.findById(email).orElse(null);
		if (doctor == null) {
			throw new IllegalArgumentException("Doctor not found: " + email);
		}
		
		Visit visit = new Visit(dateTime, doctor, patient);
		visitRepository.save(visit);
	}

	@Override
	public List<VisitData> getVisits(int patientId, LocalDateTime from, LocalDateTime to) {
		
		return visitRepository.getVisitsPatientDates(patientId, from, to);
	}

}
