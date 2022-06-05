package gui;
import myti.Zone1_2hours;

import myti.Zone12_2hours;
import myti.Zone1_allday;
import myti.Zone12_allday;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fileio.File1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import myti.FullMyTi;
import myti.JuniorMyTi;
import myti.SeniorMyTi;
import myti.Station;
import myti.User;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Main extends Application {
	// Set user Myti credit limit
	final double CREDIT_LIMIT = 100.0;
	final int ALLOWED_MULTIPLE = 5;

	// default station names
	final String[] station_names = {
    		  "Central", 
    		  "Flagstaff", 
    		  "Richmond", 
    		  "Lilydale",
    		  "Epping"
    		};

	// default corresponding station zones
	final int[] station_zones = {1,1,1,2,2}; 
	final String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
    
	// default user information
	List<String> user_ids = new ArrayList<String>();
	List<String> user_type = new ArrayList<String>();
	List<String> user_firstnames = new ArrayList<String>();
	List<String> user_lastnames = new ArrayList<String>();
	List<String> user_emailid = new ArrayList<String>();
	List<Double> user_credit = new ArrayList<Double>();

	// Hashmap to store user details
    HashMap<String, User> user_list = new HashMap<String, User>();
    // Arraylist to store station details
    ArrayList<Station> station_list = new ArrayList<Station>();
    
@Override
public void start(Stage primaryStage) {
	
	// Get input arguments input.txt and output.txt
	Parameters parameters = getParameters();
	List<String> files = parameters.getUnnamed();
	String input_file = files.get(0);
	String output_file = files.get(1);
	
	File1 f = new File1();
	// initialize file by copying contents of input to output file
	f.initFile(input_file,output_file);
	// initialize station details
	init_default_stations();
	// initialize user details and travel pass prices from file
	init_default_users(output_file);
	init_default_prices(output_file);

	// Display menu items
	MenuItem menuItem1 = new MenuItem("Buy a travel pass");
    MenuItem menuItem2 = new MenuItem("Charge MyTi card");
    MenuItem menuItem3 = new MenuItem("Show remaining Credit");
    MenuItem menuItem4 = new MenuItem("Update Price");
    MenuItem menuItem5 = new MenuItem("Display Journeys");
    MenuItem menuItem6 = new MenuItem("Add new user");
    MenuItem menuItem7 = new MenuItem("Show station statistics");
    
    MenuButton menuButton = new MenuButton("Menu Options", null, menuItem1, menuItem2, menuItem3, menuItem4, menuItem5, menuItem6, menuItem7);
    menuButton.setLayoutX(30);
    menuButton.setLayoutY(20);
    
	Button btEXIT = new Button("Cancel");
	btEXIT.setLayoutX(30);
	btEXIT.setLayoutY(210);
    btEXIT.setOnAction(e-> { 
    System.exit(0);
    });
    
    Line line = new Line(0, 200, 300, 200);
    line.setStrokeWidth(2);
    
    Pane root = new Pane();
    root.getChildren().addAll(menuButton,btEXIT,line);
	root.setStyle("-fx-background-color: PAPAYAWHIP");

    Scene scene = new Scene(root, 300, 250);
	primaryStage.setTitle("MyTi System");
    primaryStage.setScene(scene);
    primaryStage.show();
    
    // Menu item 1 computes buying travel pass
    menuItem1.setOnAction(event -> {
    	BuyTravelPass travelpass = new BuyTravelPass();
    	travelpass.travelPass(primaryStage,scene,user_list,station_list,output_file);
    });
    
    // Menu item 2 adds credit to user account
    menuItem2.setOnAction(event -> {
    	AddCredit addcredit = new AddCredit();
    	addcredit.addcredit(primaryStage,scene,user_list,output_file);	
    });
    
    // Menu item 3 computes remaining credit of the user
    menuItem3.setOnAction(event -> {
    	RemainingCredit credit = new RemainingCredit();
    	credit.remainingcredit(primaryStage,scene,user_list);	
    }); 
    
    // Menu item 4 updates price of travel pass
    menuItem4.setOnAction(event -> {
    	UpdatePrice update = new UpdatePrice();
    	update.updateprice(primaryStage,scene,output_file);	
    }); 
    
    // Menu item 5 displays all journeys
    menuItem5.setOnAction(event -> {
    	DisplayJourneys display = new DisplayJourneys();
    	display.displayjourney(primaryStage,scene,user_list);	
    }); 
    
    // Menu item 6 adds new user 
    menuItem6.setOnAction(event -> {
    	AddUser user = new AddUser();
    	user.adduser(primaryStage,scene,user_list,output_file);	   	
    });
    
    // Menu item 7 displays station statistics
    menuItem7.setOnAction(event -> {
    	Stationstats stat = new Stationstats();
    	stat.displayjourney(primaryStage,scene,station_list);	
    });       
}

public void init_default_stations()
{	
    for(int i=0;i<station_names.length;i++)
    {
    	Station s = new Station(station_names[i],station_zones[i]);
    	station_list.add(s);
    }
}

public void init_default_prices(String filename)
{	
	try 
	{	
		// read file and set travel pass type and cost
		Path path = Paths.get(filename);
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    for(String s: lines)
		{		
	    	if(!s.equals("#users") || !s.equals("#prices"))
	    	{
	    		String[] word = s.split(":");
	    		if(word[0].equals("2Hour"))
	    		{
	    			if(word[1].equals("Zone1"))
	    				Zone1_2hours.cost = Double.parseDouble(word[2]);
	    			if(word[1].equals("Zone12"))
	    				Zone12_2hours.cost = Double.parseDouble(word[2]);
	    		}
	    		if(word[0].equals("AllDay"))
	    		{
	    			if(word[1].equals("Zone1"))
	    				Zone1_allday.cost = Double.parseDouble(word[2]);
	    			if(word[1].equals("Zone12"))
	    				Zone12_allday.cost = Double.parseDouble(word[2]);	
	    		}
	    	}
		}
	}
	catch(java.io.IOException e)
	{
		System.out.println("Read file failed:" + e);
	} 
}

public void init_default_users(String filename)
{
	try 
	{
		// read file and set user details
		Path path = Paths.get(filename);
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    int i =0;
	    for(String s: lines)
		{	
	    	if(s.equals("#users"))
	    		continue;
	    	if(s.equals("#prices"))
	    		 break;
	    	String[] word = s.split(":");
	    	user_ids.add(word[0]);
	    	String[] name = word[2].split(" ");
	    	user_type.add(word[1]);
	    	user_firstnames.add(name[0]);
	    	user_lastnames.add(name[1]);
	    	user_emailid.add(word[3]);
	    	user_credit.add(Double.parseDouble(word[4]));
	
	    	if(user_type.get(i).equals("senior"))
			{
				SeniorMyTi s1 = new SeniorMyTi(user_ids.get(i),user_type.get(i),user_firstnames.get(i),user_lastnames.get(i), user_emailid.get(i),user_credit.get(i));
				user_list.put(user_ids.get(i),s1);
			}
			else if(user_type.get(i).equals("junior"))
			{
				JuniorMyTi j = new JuniorMyTi(user_ids.get(i),user_type.get(i),user_firstnames.get(i),user_lastnames.get(i), user_emailid.get(i),user_credit.get(i));
				user_list.put(user_ids.get(i),j);
			}
			else if(user_type.get(i).equals("adult"))
			{
				FullMyTi f = new FullMyTi(user_ids.get(i),user_type.get(i),user_firstnames.get(i),user_lastnames.get(i), user_emailid.get(i),user_credit.get(i));
				user_list.put(user_ids.get(i),f);
			}
	    	i+=1;
		}
	}
	// If file read has error, throw exception
	catch(java.io.IOException e)
	{
		System.out.println("Read file failed:" + e);
	}
    
}

public static void main(String[] args) {
Application.launch(args);
}
}



