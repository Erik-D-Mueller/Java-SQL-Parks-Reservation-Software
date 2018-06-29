package com.techelevator;
import com.techelevator.view.*;

import java.awt.List;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.CampgroundDAO;
import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.ParkDAO;

import com.techelevator.projects.model.ReservationDAO;
import com.techelevator.projects.model.Site;
import com.techelevator.projects.model.SiteDAO;
import com.techelevator.projects.model.jdbc.JDBCCampgroundDAO;
//import com.techelevator.projects.model.jdbc.JDBCDepartmentDAO;
//import com.techelevator.projects.model.jdbc.JDBCEmployeeDAO;
import com.techelevator.projects.model.jdbc.JDBCParkDAO;
//import com.techelevator.projects.model.jdbc.JDBCProjectDAO;
import com.techelevator.projects.model.jdbc.JDBCReservationDAO;
import com.techelevator.projects.model.jdbc.JDBCSiteDAO;
//import com.techelevator.projects.view.Menu;
//import com.techelevator.projects.view.Menu;


public class CampgroundCLI {

	private Menu menu;
	private ParkDAO parkDAO;
	private ReservationDAO reservationDAO;
	private SiteDAO siteDAO;
	private CampgroundDAO campgroundDAO;
	private ArrayList<Site> availableSiteList = new ArrayList<Site>();
	
	// Declares the main menu options as strings
	private static final String MAIN_MENU_OPTION_VIEW_CAMPGROUND = "View Campgrounds";
	private static final String MAIN_MENU_SEARCH_FOR_RESERVATION = "Search for Reservation";
	private static final String MAIN_MENU_RETURN_TO_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_VIEW_CAMPGROUND, 
			MAIN_MENU_SEARCH_FOR_RESERVATION, 
			MAIN_MENU_RETURN_TO_PREVIOUS_SCREEN};
	private static final String CAMPGROUND_RESERVATION_OPTION_SEARCH_AVAILABLE = "Search for Available Reservation";
	private static final String[] CAMPGROUND_RESERVATION_MENU_OPTIONS = new String[] { CAMPGROUND_RESERVATION_OPTION_SEARCH_AVAILABLE, 
			MAIN_MENU_RETURN_TO_PREVIOUS_SCREEN};
	private static final String[] RESERVATION_MENU_CAMPGROUND = new String[] {"Which campground (enter 0 to cancel)"};
	//private static final String[] RESERVATION_MENU_ARRIVALDATE = new String[] {"What is the arrival date __/__/____"};
	//private static final String[] RESERVATION_MENU_DEPARTDATE = new String[] {"What is the departure date __/__/____"};

	
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		//dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
		
	}

		
	public CampgroundCLI(DataSource dataSource) {
		
		this.menu = new Menu(System.in, System.out);
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
				
				// Send the list of campgrounds to the menu method to be displayed'
				menu.displayCampgroundInfo(campgroundList, theirChosenParkObject);
				
				
				// Send option to search for available reservations to menu method
				String checkReservationMenuChoice = (String)menu.getChoiceFromOptions(CAMPGROUND_RESERVATION_MENU_OPTIONS);
				
				// Check then act on menu response of Search for Available Reservations
				
				Campground theirChosenCampgroundObject=null;
				LocalDate reservation_Chosen_ArrivalDate=null;
				LocalDate reservation_Chosen_DepartureDate=null;
				
				if(checkReservationMenuChoice == "Search for Available Reservation") {
					
					
					theirChosenCampgroundObject = menu.campgroundReservationMenu(campgroundList);
					
					reservation_Chosen_ArrivalDate = menu.receiveDateFromUser(" What is the arrival date? __/__/____ ");
					
					reservation_Chosen_DepartureDate = menu.receiveDateFromUser(" What is the departure date? __/__/____ ");
					
				
					
					availableSiteList = siteDAO.getAvailableSites(theirChosenCampgroundObject.getCampground_id(), reservation_Chosen_ArrivalDate, reservation_Chosen_DepartureDate);
					
					menu.displayAvailableSites(availableSiteList);
					
					
			}
			// Send the list of campgrounds to the menu method to be displayed'
		
			
			// Send the list of campground objects to the menu method to be display them and return the chosen campground object
			// Campground theirChosenCampgroundObject = menu.displayAndGetTheirCampgroundChoice(campgroundList);
			
			//NEED CHECK FOR "RETURN TO PREVIOUS SCREEN" RESPONSE 
			
			
				
				
				
			}
			//Check for site availability
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

