package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.Site;

public class JDBCSiteDAO {

	private JdbcTemplate jdbcTemplate;   // What does this line do?

	public JDBCSiteDAO(DataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	
	}
	
	ArrayList<Site> siteList = new ArrayList<Site>();
	

	//Need campground id, start date and enddate
	
	String sqlGetAvailableSites = "SELECT s.site_id, s.max_occupancy, s.accessible, s.max_rv_length, s.utilities, c.daily_fee FROM site s\n" + 
			"JOIN campground c ON ? = s.campground_id\n" + 
			"JOIN reservation r ON s.site_id = r.site_id\n" + 
			"WHERE c.campground_id = ? AND s.site_id NOT IN\n" + 
			"(SELECT r.site_id from reservation r\n" + 
			"WHERE (r.from_date, r.to_date) OVERLAPS (?::DATE, ?::DATE));";
	
	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSites, null,null,null,null);
	
	
	//SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkById, id);
	
	
	while(results.next()) {
		
		Site siteObject = createSiteObject(results);
		
		siteList.add(siteObject);
	}
	
	return parkList;



	private long site_id;
	private long campground_id;
	private int site_number;
	private int max_occupancy;
	private boolean accessible;
	private int max_rv_length;
	private boolean utilities;

	
	
private Site createSiteObject(SqlRowSet results) {
	Site siteObject = new Site();
	
	siteObject.setSite_id(results.getInt("side_id"));
	siteObject.setCampground_id(results.getInt("campground_id"));
	siteObject.setSite_number(results.getInt("site_number"));
	siteObject.setMax_occupancy(results.getInt("max_occupancy"));
	siteObject.setAccessible(results.getBoolean("accessible"));
	siteObject.setMax_rv_length(results.getInt("max_rv_length"));
	siteObject.setUtilities(results.getBoolean("utilities"));
	
	return siteObject;
	
}
	
	
	
	
	
	
	
}



