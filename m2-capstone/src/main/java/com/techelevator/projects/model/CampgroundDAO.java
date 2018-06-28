package com.techelevator.projects.model;

import java.util.ArrayList;
import java.util.List;

public interface CampgroundDAO {

	/**
	 * Get all parks from the datastore.
	 * 
	 * @return all Parks as objects in a List
	 */
	public List<Campground> getAllCampgrounds();


	/**
	 * Get a department from the datastore that belongs to the given id.
	 * 
	 * @param id the department id to get from the datastore
	 * @return a filled out department object
	 */
	public Campground getCampgroundById(int id);
	
	public ArrayList<Campground> getAllCampgroundsInParkId(int id);

}