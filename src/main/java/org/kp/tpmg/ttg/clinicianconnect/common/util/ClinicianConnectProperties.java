package org.kp.tpmg.ttg.clinicianconnect.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClinicianConnectProperties {

	private static final Logger LOGGER = LogManager.getLogger(ClinicianConnectProperties.class);

	private ClinicianConnectProperties() {
		/*
		 * Empty
		 */
	}

	public static FileInputStream readFile(String resourcesPath) {

		File file = new File(resourcesPath);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LOGGER.error("ERROR in readFile in ClinicianConnectProperties is: " + e.getMessage(), e);
		}
		return fileInput;
	}

}
