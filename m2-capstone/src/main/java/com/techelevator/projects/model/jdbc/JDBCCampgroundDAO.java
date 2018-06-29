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

	private Campground createCampgroundObject(SqlRowSet results) {
		
		Campground campgroundObject1 = new Campground();		
		
		campgroundObject1.setCampground_id(results.getInt("campground_id"));
		campgroundObject1.setPark_id(results.getInt("park_id"));
		campgroundObject1.setName(results.getString("name"));
		campgroundObject1.setOpen_from_mm(results.getInt("open_from_mm"));
		campgroundObject1.setOpen_to_mm(results.getInt("open_to_mm"));
		campgroundObject1.setDaily_fee( results.getFloat("daily_fee"));
		
		return campgroundObject1;
	}
}
