package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.Park;
import com.techelevator.projects.model.Site;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	
	// This generically displays any array list of options and generically returns whichever option was chosen
	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);	
		}
		
		return choice;
	}


	
	public Object getChoiceFromOptions(Object[] options, String currentAmountEntered) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options,currentAmountEntered);
			choice = getChoiceFromUserInput(options);
			if(Integer.parseInt((String) choice)==0) { System.exit(0);}
		}
		return choice;
	}

	public void displayParkInfo(Park parkObject) {
		System.out.println("Park Information Screen");
		System.out.println(parkObject.getName());
		System.out.println(parkObject.getLocation());
		System.out.println(parkObject.getEstablish_date());
		System.out.println(parkObject.getArea());
		System.out.println(parkObject.getVisitors());
		System.out.println(parkObject.getDescription());
		
	}

	public void displayCampgroundInfo(ArrayList<Campground> campgroundList, Park park) {
		System.out.println("Park Campgrounds");
		System.out.println(park.getName()+" National Park Campgrounds");
		System.out.println("   Name \t\tOpen \tClose \tDaily Fee");
		for(int i = 0; i< campgroundList.size(); i++) {	
			int optionNum = i+1;
			out.println(optionNum+") "+(campgroundList.get(i).getName() + "\t\t" + getMonthInName(campgroundList.get(i).getOpen_from_mm()) + "\t" + getMonthInName(campgroundList.get(i).getOpen_to_mm())  + "\t" + campgroundList.get(i).getDaily_fee()));
		}	
		System.out.println();
	}

	public Campground campgroundReservationMenu( ArrayList<Campground> arrayList) {
			while (true) {
				System.out.println();
				System.out.println();
				System.out.println("Search for Campground Reservation");
				System.out.println("   Name \t\tOpen \tClose \tDaily Fee");
				for(int i = 0; i< arrayList.size(); i++) {	
					int optionNum = i+1;
					System.out.println(optionNum+") "+(arrayList.get(i).getName() + "\t\t" + getMonthInName(arrayList.get(i).getOpen_from_mm()) + "\t" + getMonthInName(arrayList.get(i).getOpen_to_mm())  + "\t" + arrayList.get(i).getDaily_fee()));
				}	
				// Send the concatenated campground info strings to getChoiceFromOptions() and let them choose
				System.out.println();
				System.out.println("Which campground (enter 0 to cancel)");
				String userInput = in.nextLine();
				if (Integer.parseInt(userInput) <= arrayList.size()) {
					Campground theirChosenCampgroundObject = new Campground();
					theirChosenCampgroundObject = arrayList.get(Integer.parseInt(userInput)-1);
					return theirChosenCampgroundObject;
				}
			}
	}
		
	
	public void displayAvailableSites(ArrayList<Site> siteList) {
			
		System.out.println("Results Matching Your Search Criteria");
		System.out.println("Site No. \tMax Occup. \tAccessible? \tMax RV Length \tUtility \tCost");
		for(int i = 0; i< siteList.size(); i++) {	
			System.out.println(siteList.get(i).getSite_number());
			System.out.print(siteList.get(i).getMax_occupancy());
			System.out.print(siteList.get(i).isAccessible());
			System.out.print(siteList.get(i).getMax_rv_length());
			System.out.print(siteList.get(i).isUtilities());
			System.out.print(siteList.get(i).getTotal_amount());
			System.out.println();
		}	
	}



	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if(selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch(NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if(choice == null) {
			out.println("\n*** "+userInput+" is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	// Prints the menu options to the screen
	private void displayMenuOptions(Object[] options, String currentAmountEntered) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		out.print(currentAmountEntered + "\n");
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
	
	
	public LocalDate receiveDateFromUser(String message) {
		
		String[] dateArray;
		
		LocalDate date;
		
		System.out.println(message);
		
		String userInput = in.nextLine();
		
		// dateArray[0] is  two digit month, [1] is two digit day, [2] is 4 digit yeaer
		dateArray = userInput.split("/");;
		
		// year, month, day LocalDate.of(int, int, int)
		
		date = LocalDate.of(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1])); 

		return date;
		
	}
	
	
	public String getMonthInName(int mm) {
		
		if(mm==1) {return "January";}
		if(mm==2) {return "February";}
		if(mm==3) {return "March";}
		if(mm==4) {return "April";}
		if(mm==5) {return "May";}
		if(mm==6) {return "June";}
		if(mm == 7) {return "July";}
		if(mm == 8) {return "August";}
		if(mm == 9) {return "September";}
		if(mm == 10) {return "October";}
		if(mm == 11) {return "November";}
		return "December";
		
	}
	
	
}
