package com.techelevator.projects.model.jdbc;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;


import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;


import com.techelevator.projects.model.Reservation;
import com.techelevator.projects.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate; 
	public JDBCReservationDAO(DataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	
	}
	


	
	@Override
	public int saveReservation(Reservation reservationObject) {
		
		ArrayList<Reservation> reservationList = new ArrayList <Reservation>();
		
	//	Number reservation_ID = jdbcTemplate. .update(sqlCreateReservation, reservationObject.getSite_id(), reservationObject.getName(), reservationObject.getFrom_date(), reservationObject.getTo_date());

           // convert Number to Int using ((Number) key).intValue()
  //      return (int) reservation_ID; 
        
        // insert query
        String sqlCreateReservation = "INSERT INTO reservation (site_id, name, from_date, to_date) VALUES (?, ?, ?, ?)";
          
        // this is the key holder
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        // the name of the generated column (you can track more than one column)
        String id_column = "reservation_id";
        
        // the update method takes an implementation of PreparedStatementCreator which could be a lambda
        jdbcTemplate.update(con -> {
          PreparedStatement ps = con.prepareStatement(sqlCreateReservation, new String[]{id_column});
          ps.setInt(1, reservationObject.getSite_id());
          ps.setString(2, reservationObject.getName());
          ps.setDate(3, Date.valueOf(reservationObject.getFrom_date()));
          ps.setDate(4, Date.valueOf(reservationObject.getTo_date()));
          return ps;
        }
        , keyHolder);

        // after the update executed we can now get the value of the generated ID
        int id = (int) keyHolder.getKeys().get(id_column);
        return id;
        
        
	/*	String name = reservationObject.getName();
		int site_id = reservationObject.getSite_id();
		String sqlGetReservation = "SELECT reservation_id from reservation WHERE name = ? AND site_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservation, name, site_id);
		
		while(results.next()) {
			
			Reservation newReservationObject = createReservationObject(results);
			
			reservationList.add(newReservationObject);
		
		}
		return reservationList.get(reservationList.size()-1);
	*/		
		
		
	}
	
	private Reservation createReservationObject(SqlRowSet results) {
		
		Reservation reservationObject = new Reservation();
		
		reservationObject.setReservation_id(results.getInt("reservation_id"));
		reservationObject.setSite_id(results.getInt("site_id"));
		reservationObject.setName(results.getString("name"));
		reservationObject.setFrom_date(results.getDate("from_date").toLocalDate());
		reservationObject.setTo_date(results.getDate("to_date").toLocalDate());
		reservationObject.setCreate_date(results.getDate("create_date").toLocalDate());
		
		return reservationObject;
		
	}
		

	
}
