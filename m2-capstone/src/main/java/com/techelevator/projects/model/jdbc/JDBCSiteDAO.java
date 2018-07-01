package com.techelevator.projects.model.jdbc;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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
		String sqlTest = "Select from_date from reservation";
		int daysRented = (int) arrival_date.until(depart_date, ChronoUnit.DAYS);
		String from_Date = ""+arrival_date+"";
		String to_Date = ""+depart_date+"";
		ArrayList<Site> availableSiteList = new ArrayList<Site>();
		String sqlGetAvailableSites = "SELECT distinct site.site_id, site.campground_id, site.site_number, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities, ?*campground.daily_fee AS total_fee FROM site JOIN campground ON site.campground_id = campground.campground_id WHERE site.campground_id = ? AND site.site_id NOT IN (SELECT site.site_id FROM site JOIN reservation ON reservation.site_id = site.site_id WHERE ((to_date(?, 'YYYY/MM/DD')) <= reservation.to_date AND (to_date(?, 'YYYY/MM/DD')) >= reservation.from_date)) LIMIT 5";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableSites, daysRented, campground_id, to_Date, from_Date);
		while(results.next()) {
			Site siteObject = createSiteObject(results);
			availableSiteList.add(siteObject);
		}
		
		return availableSiteList;
	}

	public Site getSiteBySiteNum(int siteNum) {
		String sqlSiteBySiteNum = "Select site_id FROM site WHERE site_number = ?";
		Site siteBySiteNum = new Site();
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSiteBySiteNum, siteNum);
		siteBySiteNum = createSiteObject(results);
		
		return siteBySiteNum;
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
		siteObject.setTotal_amount(results.getDouble("total_fee"));
		
		return siteObject;
	
}
	
	
	
	
	
	
	
}



