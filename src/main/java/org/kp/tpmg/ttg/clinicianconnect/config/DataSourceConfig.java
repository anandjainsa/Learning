package org.kp.tpmg.ttg.clinicianconnect.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	
	@Value("${db.conf.path}")
	private String filePath;

	@Bean
	public DataSource dataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		Properties pros=getDataSourceConfig();
		dataSourceBuilder.driverClassName(pros.getProperty("driverClassName"));
        dataSourceBuilder.url(pros.getProperty("url"));
        dataSourceBuilder.username(pros.getProperty("username"));
        dataSourceBuilder.password(pros.getProperty("password"));
        return dataSourceBuilder.build();
	}
	private Properties getDataSourceConfig() {
		Properties props=new Properties();
		try {
			InputStream input=new FileInputStream(filePath);
			props.load(input);
		} catch (IOException e) {
			
		}
		return props;
	}
}
