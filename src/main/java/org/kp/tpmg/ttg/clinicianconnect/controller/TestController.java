package org.kp.tpmg.ttg.clinicianconnect.controller;

import java.sql.SQLException;
import java.util.List;

import org.kp.tpmg.ttg.clinicianconnect.dao.TestDAO;
import org.kp.tpmg.ttg.clinicianconnect.model.DataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@Autowired
	public TestDAO dao;
	@RequestMapping("/test")
	public List<String> customerInformation() throws SQLException {
		return dao.isData();
		
	}
	@Autowired
	private DataConfig dataConfig;
	
	@GetMapping("/config-reload")
	public String configAutoReload() {
		return dataConfig.getMessage();
	}
	
	

}
