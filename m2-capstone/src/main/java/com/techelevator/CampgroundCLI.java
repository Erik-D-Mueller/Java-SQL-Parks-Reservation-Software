package com.techelevator;
import com.techelevator.view.*;

import java.awt.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.CampgroundDAO;
import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.ParkDAO;

import com.techelevator.projects.model.ReservationDAO;
import com.techelevator.projects.model.SiteDAO;
import com.techelevator.projects.model.jdbc.JDBCCampgroundDAO;


import com.techelevator.projects.model.jdbc.JDBCParkDAO;

import com.techelevator.projects.model.jdbc.JDBCReservationDAO;
import com.techelevator.projects.model.jdbc.JDBCSiteDAO;
//import com.techelevator.projects.view.Menu;


public class CampgroundCLI {

	private Menu menu;
	private ParkDAO parkDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	private CampgroundDAO campgroundDAO;
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		//dataSource.setPassword("postgres1");
		

		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();


		
	}

	// Declares the main menu options as strings
		private static final String MAIN_MENU_OPTION_VIEW_CAMPGROUND = "View Campgrounds";
		private static final String MAIN_MENU_SEARCH_FOR_RESERVATION = "Search for Reservation";
		private static final String MAIN_MENU_RETURN_TO_PREVIOUS_SCREEN = "Return to Previous Screen";
		private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_VIEW_CAMPGROUND, 
				MAIN_MENU_SEARCH_FOR_RESERVATION, 
				MAIN_MENU_RETURN_TO_PREVIOUS_SCREEN};
		
		
		
		
	public CampgroundCLI(DataSource dataSource) {
	
		
this.menu = new Menu(System.in, System.out);
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		
		parkDAO = new JDBCParkDAO(dataSource);
		
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		
		siteDAO = new JDBCSiteDAO(dataSource);
		
		reservationDAO = new JDBCReservationDAO(dataSource);
		
	
	}
	
	public void run() {

		
		while(true) {
			
			ArrayList<Park> parkList = new ArrayList<Park>();
			
			parkList = parkDAO.getAllParks();
			
			
			String[] parkNameArray = new String[parkList.size()+1];
			
			
			parkNameArray = convertObjectListToNamesArray(parkList);

			//Sends the names of parks, receives their choice as a string
			String parkChoice = (String)menu.getChoiceFromOptions(parkNameArray);
			
			Park theirChosenParkObject = new Park();
			
			// This loop grabs the correct park object based on the name they chose
			for(int i =0; i < parkList.size(); i++) {
				if( parkList.get(i).getName().contains(parkChoice) ) {
					theirChosenParkObject = parkList.get(i);
				}
			}
				
			
			// Displays all the info about the park	
			menu.displayParkInfo( theirChosenParkObject );
				
			String mainMenuChoice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			
			ArrayList<Campground> campgroundList = new ArrayList<Campground>();
			
			if( mainMenuChoice == "View Campgrounds") {
				
				campgroundList = campgroundDAO.getAllCampgroundsInParkId( theirChosenParkObject.getPark_id());
							
			}
			
			// Send the list of campground objects to the menu method to be display them and return the chosen campground object
			Campground theirChosenCampgroundObject = menu.displayAndGetTheirCampgroundChoice(campgroundList);
			
				
			}
			
	
			
		
		
		
		
		
		
		
		
		
		
		
	}
		
		// Takes an ArrayList of Park objects, returns an array of strings of their names
		private String[] convertObjectListToNamesArray(ArrayList<Park> parkList) {
			
			String[] parkNameArray = new String[parkList.size()+1];

			for(int i =0; i < parkList.size(); i++) {
				
				parkNameArray[i] = parkList.get(i).getName();

			}
			
			parkNameArray[parkList.size()] = "quit";
			return parkNameArray;
		}
			
		
	}

