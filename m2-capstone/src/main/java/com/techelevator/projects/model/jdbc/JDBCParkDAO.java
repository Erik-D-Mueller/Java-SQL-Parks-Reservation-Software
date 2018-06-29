package com.techelevator.projects.model.jdbc;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;   // What does this line do?

	public JDBCParkDAO(DataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	
	}
	
	/**
	 * Get all parks from the datastore.
	 * 
	 * @return all Parks as objects in a List
	 */
	@Override
	public ArrayList<Park> getAllParks(){
		
		ArrayList<Park> parkList = new ArrayList<Park>();
		
		String sqlGetAllParks = "SELECT * from park";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		
		while(results.next()) {
			
			Park parkObject = createParkObject(results);
			
			parkList.add(parkObject);
		}
		
		return parkList;
		
		
	}

	/**
	 * Get a department from the datastore that belongs to the given id.
	 * 
	 * @param id the department id to get from the datastore
	 * @return a filled out department object
	 */
	public Park getParkById(Long id) {
		
		String sqlGetParkById = "SELECT * from park where park_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkById, id);
		
		Park parkObject = createParkObject(results);
			
		return parkObject;
		
	}
	
	
	@Override
	public Park getParkByName(String name) {
		
		
		String sqlGetParkByName = "SELECT * from park where name LIKE (?)";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetParkByName, name);
		
		Park parkObject = createParkObject(results);
			
		return parkObject;
	
		
	}
	

	private Park createParkObject(SqlRowSet results) {
		
		Park parkObject1 = new Park();		
		
		parkObject1.setPark_id(results.getInt("park_id"));
		parkObject1.setName(results.getString("name"));
		parkObject1.setLocation(results.getString("location"));
		parkObject1.setEstablish_date(results.getDate("establish_date"));
		parkObject1.setArea( results.getInt("area"));
		parkObject1.setVisitors( results.getInt("visitors")  );
		parkObject1.setDescription( results.getString("description"));
		
		return parkObject1;
	}

	@Override
	public Park getParkById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
	

	
	
}