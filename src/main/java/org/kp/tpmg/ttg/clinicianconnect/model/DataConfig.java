package org.kp.tpmg.ttg.clinicianconnect.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "myconfig")
public class DataConfig{

	/**
	 * 
	 */
	
	
    private String message = "default message";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 

}
