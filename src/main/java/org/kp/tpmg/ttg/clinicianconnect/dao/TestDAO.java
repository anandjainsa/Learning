package org.kp.tpmg.ttg.clinicianconnect.dao;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class TestDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public static final String CC_DBO_CC_USERS = "[CCUsers]";

	public static final String GET_NICK_NAME_FROM_CCUSERS_BASED_ON_NUID = "select LocationCode from ClinicianConnect.dbo.LocationMap";


	public List<String> isData() throws SQLException {
		System.out.println("jdbcTemplate"+jdbcTemplate.getDataSource().getConnection());
		
		List<String> result = jdbcTemplate.queryForList(GET_NICK_NAME_FROM_CCUSERS_BASED_ON_NUID,String.class);

		System.out.println("---nickname"+result);
		return result;	
		
	}
}