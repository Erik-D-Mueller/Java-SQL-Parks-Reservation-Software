package com.techelevator.projects.model;

import java.util.List;

public interface SiteDAO {

	/**
	 * Get available sites for a specific campground from the datastore.
	 * 
	 * @return available sites as objects in a List
	 */
	public List<Site> getAvailablesites();


	/**
	 * Get available sites across the entire park system from the datastore.
	 * 
	 * @return available sites as objects in a List
	 */
	public Park getParkById(Long id);
}