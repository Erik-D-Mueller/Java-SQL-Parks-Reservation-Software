package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.techelevator.projects.model.Campground;
import com.techelevator.projects.model.Park;

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
		}
		return choice;
	}

public void displayParkInfo(Park parkObject) {
	
	System.out.println(parkObject.getName());
	System.out.println(parkObject.getLocation());
	System.out.println(parkObject.getEstablish_date());
	System.out.println(parkObject.getArea());
	System.out.println(parkObject.getVisitors());
	System.out.println(parkObject.getDescription());
	
}

public void displayCampgrounds( ArrayList<Campground> arrayList) {
	
	System.out.println("Name		Open		Close		Daily Fee");
	
	String[] campgroundInfoArray = new String[arrayList.size()];
	
	// This concatenates all the campground information into one long String, before sending it to the menu disploay method
	for(int i = 0; i< arrayList.size(); i++) {
		
		campgroundInfoArray[i] = (" " + arrayList.get(i).getName() + "\t" + getMonthInName(arrayList.get(i).getOpen_from_mm()) + "\t" + getMonthInName(arrayList.get(i).getOpen_to_mm())  + "\t" + arrayList.get(i).getDaily_fee() + " ");
	
		String campgroundInfoAnswer = getChoiceFromOptions(campgroundInfoArray); 
		
		
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
