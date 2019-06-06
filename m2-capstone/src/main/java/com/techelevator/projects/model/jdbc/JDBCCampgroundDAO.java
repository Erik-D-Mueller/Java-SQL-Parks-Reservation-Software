package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.CampgroundDAO;


public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getAllCampgrounds() {
		
		ArrayList<Campground> campgroundList = new ArrayList<Campground>();
		
		String sqlGetAllCampgrounds = "SELECT * from campground";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgrounds);
		
		while(results.next()) {
			
			Campground campgroundObject = createCampgroundObject(results);
			
			campgroundList.add(campgroundObject);
		}
		
		return campgroundList;
	}


	public ArrayList<Campground> getAllCampgroundsInParkId(int id) {
		
		ArrayList<Campground> campgroundList = new ArrayList<Campground>();
		
		String sqlGetAllCampgroundsInParkID = "SELECT * from campground WHERE park_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllCampgroundsInParkID, id);

		while(results.next()) {
			
			Campground campgroundObject = createCampgroundObject(results);
			
			campgroundList.add(campgroundObject);
		}
		
		return campgroundList;
		
	}
	
	
	
	
	
	
	@Override
	public Campground getCampgroundById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	private Campground createCampgroundObject(SqlRowSet resultsObtained) {
		
		Campground campgroundObjectOne = new Campground();		
		
		campgroundObjectOne.setOpen_from_mm(resultsObtained.getInt("open_from_mm"));
		campgroundObjectOne.setDaily_fee( resultsObtained.getFloat("daily_fee"));
		campgroundObjectOne.setCampground_id(resultsObtained.getInt("campground_id"));
		campgroundObjectOne.setOpen_to_mm(resultsObtained.getInt("open_to_mm"));
		campgroundObjectOne.setPark_id(resultsObtained.getInt("park_id"));
		campgroundObjectOne.setName(resultsObtained.getString("name"));
		
		return campgroundObjectOne;
	}
}
