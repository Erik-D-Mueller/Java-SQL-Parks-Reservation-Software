package com.techelevator.projects.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.Site;
import com.techelevator.projects.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;   // What does this line do?

	public JDBCSiteDAO(DataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	
	}
	
	public ArrayList<Site> getAvailableSites(int campground_id, LocalDate arrival_date, LocalDate depart_date) {
		ArrayList<Site> availableSiteList = new ArrayList<Site>();
		String sqlGetAvailableSites = "SELECT distinct site.site_id, site.campground_id, site.site_number, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities, (?::date - ?::date)*campground.daily_fee AS total_fee\n" + 
				"FROM site JOIN campground ON site.campground_id = campground.campground_id WHERE site.campground_id = ? AND site.site_id\n" + 
				"NOT IN\n" + 
				"(SELECT site.site_id FROM site\n" + 
				"JOIN reservation ON reservation.site_id = site.site_id\n" + 
				"WHERE ((to_date(?, 'YYYY/MM/DD')) <= reservation.to_date AND (to_date(?, 'YYYY/MM/DD')) >= reservation.from_date))";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSites, depart_date, arrival_date, campground_id, arrival_date, depart_date);
		while(results.next()) {
			Site siteObject = createSiteObject(results);
			availableSiteList.add(siteObject);
		}
		
		return availableSiteList;
	}

	
	
	private Site createSiteObject(SqlRowSet results) {
		Site siteObject = new Site();
		
		siteObject.setSite_id(results.getInt("site_id"));
		siteObject.setCampground_id(results.getInt("campground_id"));
		siteObject.setSite_number(results.getInt("site_number"));
		siteObject.setMax_occupancy(results.getInt("max_occupancy"));
		siteObject.setAccessible(results.getBoolean("accessible"));
		siteObject.setMax_rv_length(results.getInt("max_rv_length"));
		siteObject.setUtilities(results.getBoolean("utilities"));
		siteObject.setTotal_amount("total_fee");
		
		return siteObject;
	
}
	
	
	
	
	
	
	
}



