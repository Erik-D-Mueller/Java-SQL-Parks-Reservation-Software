package com.techelevator.projects.model.jdbc;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Reservation;

public class JDBCReservationDAO {

	
	

	
	
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
		

	
}
