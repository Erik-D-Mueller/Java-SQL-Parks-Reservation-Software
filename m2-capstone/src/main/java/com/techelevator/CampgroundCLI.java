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
import com.techelevator.projects.model.Reservation;
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
			
			Park theirChosenParkObjectFromScreenOne;
			String theirChoiceFromScreenTwo;
			String theirChoiceFromScreenThree="";
			
			// Ask what park they want to look at
			theirChosenParkObjectFromScreenOne = screenOne();
			
			// Display the park info, ask what they want to do
			theirChoiceFromScreenTwo = screenTwo(theirChosenParkObjectFromScreenOne);
	
			
			if( theirChoiceFromScreenTwo.contains("View Campgrounds")) 						{  				theirChoiceFromScreenThree = screenThree(theirChosenParkObjectFromScreenOne);		}
			if( theirChoiceFromScreenTwo.contains("Search for Reservation")) 				{  				screenFour(theirChosenParkObjectFromScreenOne);										}
			if( theirChoiceFromScreenTwo.contains("Return to Previous Screen")) 			{  				theirChosenParkObjectFromScreenOne = screenOne();									}									
			

			if( theirChoiceFromScreenThree.contains("Search for Available Reservation")) 	{  				screenFour(theirChosenParkObjectFromScreenOne); 									}					
			if( theirChoiceFromScreenThree.contains("Return to Previous Screen")) 			{ 				theirChoiceFromScreenTwo = screenTwo(theirChosenParkObjectFromScreenOne); 			}
										
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
		
		
		
		// This method controls everything related to Screen One
		private Park screenOne() {
			
			ArrayList<Park> parkList = new ArrayList<Park>();
			
			parkList = parkDAO.getAllParks();
			
			String[] parkNameArray = new String[parkList.size()+1];
			
			parkNameArray = convertObjectListToNamesArray(parkList);

			System.out.println("Select a Park for Further Details");
			System.out.println();
			
			//Sends the names of parks, receives their choice as a string
			String parkChoice = (String)menu.getChoiceFromOptions(parkNameArray);
			
			Park theirChosenParkObject = new Park();
			
			// This loop grabs the correct park object based on the name they chose
				for(int i =0; i < parkList.size(); i++) {
					if( parkList.get(i).getName().contains(parkChoice) ) {
						theirChosenParkObject = parkList.get(i);
					}
				}
				
			return theirChosenParkObject;
			
		}
				
		private String screenTwo(Park theirChosenParkObject ) {
			
			System.out.println();
			
			// Displays all the info about the park	
			menu.displayParkInfo( theirChosenParkObject );
			
			String theirResponse = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			
			return theirResponse;
			
		}
		
		
		
		// Display the campgrounds in that park, ask what they want to do
		private String screenThree(Park theirChosenParkObject) {
			
			ArrayList<Campground> campgroundList = new ArrayList<Campground>();
			
			// generate a list of all the campground objects in their chosen park
			campgroundList = campgroundDAO.getAllCampgroundsInParkId( theirChosenParkObject.getPark_id());
			
			// Send the list of campgrounds to the a method in menu to be displayed'
			menu.displayCampgroundInfo(campgroundList, theirChosenParkObject);
			
			// Send option to search for available reservations to menu method
			System.out.println("Select a Command:");
			System.out.println();
						
			String theirResponse = (String)menu.getChoiceFromOptions(CAMPGROUND_RESERVATION_MENU_OPTIONS);
			
			return  theirResponse;
			
		}
			
		private void screenFour(Park theirChosenParkObject) {
			
			Reservation reservationObject = new Reservation();
			Campground theirChosenCampgroundObject;
			LocalDate reservation_Chosen_ArrivalDate;
			LocalDate reservation_Chosen_DepartureDate=null;
			
			ArrayList<Campground> campgroundList = new ArrayList<Campground>();
			
			// generate a list of all the campground objects in their chosen park
			campgroundList = campgroundDAO.getAllCampgroundsInParkId( theirChosenParkObject.getPark_id());
			
			// displays the campground list again
			theirChosenCampgroundObject = menu.campgroundReservationMenu(campgroundList);
			
			
			// get their desired arrive date
			reservation_Chosen_ArrivalDate = menu.receiveDateFromUser(" What is the arrival date? __/__/____ ");
				
			// get their desired departure date
			reservation_Chosen_DepartureDate = menu.receiveDateFromUser(" What is the departure date? __/__/____ ");
			
			reservationObject.setFrom_date(reservation_Chosen_ArrivalDate);
			
			reservationObject.setTo_date(reservation_Chosen_DepartureDate);
			
			availableSiteList = siteDAO.getAvailableSites(theirChosenCampgroundObject.getCampground_id(), reservationObject.getFrom_date(), reservationObject.getTo_date());
			
			// display available sites and capture info
			menu.displayAvailableSites(availableSiteList);
			String[] siteInfo = menu.selectSite();
			Site theirChosenSite = siteDAO.getSiteBySiteNum(Integer.parseInt(siteInfo[0]));
			int site_id = (int) theirChosenSite.getSite_id();
			reservationObject.setSite_id(site_id);
			reservationObject.setName(siteInfo[1]);
			
			/////////////////
			
			reservationObject = reservationDAO.saveReservation(reservationObject);
			System.out.println("The reservation has been made and the confirmaiton ID is " + reservationObject.getReservation_id() );
			
			System.exit(0);
			
		}
		

		}

