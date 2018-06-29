package com.techelevator.projects.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface SiteDAO {

	/**
	 * Get available sites for a specific campground from the datastore.
	 * 
	 * @return available sites as objects in a List
	 */
	public ArrayList<Site> getAvailableSites(int campground_id, LocalDate arrival_date, LocalDate depart_date);

}