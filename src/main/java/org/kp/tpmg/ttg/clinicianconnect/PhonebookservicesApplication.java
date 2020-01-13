package org.kp.tpmg.ttg.clinicianconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { 
		"org.kp.tpmg.ttg.clinicianconnect",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.exception",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.model",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.util",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.dao",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.dao.impl" })
public class PhonebookservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhonebookservicesApplication.class, args);
	}

}
