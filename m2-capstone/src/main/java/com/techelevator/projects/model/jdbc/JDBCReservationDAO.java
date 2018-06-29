package com.techelevator.projects.model.jdbc;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.Reservation;
import com.techelevator.projects.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;   
	public JDBCReservationDAO(DataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	
	}
	

	
	
	private Reservation createReservationObject(SqlRowSet results) {
		
		Reservation reservationObject = new Reservation();
		
		reservationObject.setReservation_id(results.getInt("reservation_id"));
		reservationObject.setSite_id(results.getInt("site_id"));
		reservationObject.setName(results.getString("name"));
		reservationObject.setFrom_date(results.getDate("from_date"));
		reservationObject.setTo_date(results.getDate("to_date"));
		reservationObject.setCreate_date(results.getDate("create_date"));
		
		return reservationObject;
		
	}

	@Override
	public List<Park> getAllParks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Park getParkById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
		

	
}
