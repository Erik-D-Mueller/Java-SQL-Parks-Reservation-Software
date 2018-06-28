package com.techelevator.projects.model;

import java.util.List;

public interface ReservationDAO {

	/**
	 * Get all parks from the datastore.
	 * 
	 * @return all Parks as objects in a List
	 */
	public List<Park> getAllParks();


	/**
	 * Get a department from the datastore that belongs to the given id.
	 * 
	 * @param id the department id to get from the datastore
	 * @return a filled out department object
	 */
	public Park getParkById(Long id);
}