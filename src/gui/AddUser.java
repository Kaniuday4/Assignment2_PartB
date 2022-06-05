package gui;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import myti.FullMyTi;
import myti.JuniorMyTi;
import myti.SeniorMyTi;
import myti.User;

public class AddUser {
	String user_type = null;
	String user_id = null;
	String user_firstname = null;
	String user_lastname = null;
	String user_email = null;
	double user_credit = 0;
	int flag = 0;
	
	public void adduser(Stage primaryStage,Scene scene,HashMap<String, User> user_list,String output_file)
	{
		Button button2 = new Button("Back to main Menu");
    	button2.setLayoutY(250);
    	button2.setOnAction(e -> primaryStage.setScene(scene));
    	
    	Button btEXIT = new Button("Cancel");
    	btEXIT.setLayoutX(130);
    	btEXIT.setLayoutY(250);
    	btEXIT.setOnAction(e-> { 
            System.exit(0);
        });
    	
    	Button btSAVE = new Button("Save");
    	btSAVE.setLayoutX(200);
    	btSAVE.setLayoutY(250);
    	btSAVE.setOnAction(e-> { 
            System.exit(0);
        });
    	
    	Label id = new Label("ID:");
    	Label first_name = new Label("First name:");
    	Label last_name = new Label("Last name:");
    	Label email = new Label("Email:");
    	Label type = new Label("Type:");

    	ListView<String> list = new ListView<String>();
    	ObservableList<String> items =FXCollections.observableArrayList ("adult", "junior","senior");
    	list.setItems(items);
    	list.setPrefWidth(40);
    	list.setPrefHeight(25);
    	
    	TextField textField = new TextField ();
    	TextField textField1 = new TextField ();
    	TextField textField2 = new TextField ();
    	TextField textField3 = new TextField ();
    	
    	GridPane root1 = new GridPane();
        root1.addRow(0, id, textField);  
        root1.addRow(1, first_name, textField1);  
        root1.addRow(2, last_name, textField2);  
        root1.addRow(3, email, textField3);  
        root1.addRow(4, type,list); 
        
    	Button bt_create = new Button("Create User");
    	bt_create.setLayoutY(140);
    	
        Line line = new Line(0, 240, 400, 240);
        line.setStrokeWidth(2);
        
    	TextArea msg = new TextArea();
    	msg.setLayoutY(180);
    	msg.setLayoutX(30);
    	msg.setPrefWidth(150);
    	msg.setPrefHeight(20);
        
    	// Add listener to get user type
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	     user_type = list.getSelectionModel().selectedItemProperty().getValue();
    	    }
    	});
    	
		// create button function to add new user
    	bt_create.setOnAction(e -> {
    		flag = create( textField,textField1, textField2, textField3, msg,user_list);
    	});
    	
    	// Save button function to save new user details to file
    	btSAVE.setOnAction(e-> { 
    		if(flag == 1)
    		{
	    		try 
	    		{
	    			Path path = Paths.get(output_file);
	    	        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    	        int count = 0;
	    	        for(String s: lines)
	    			{
	    	        	if(s.equals("#users"))
	    	        	{
	    	        		count+=1;
	    	        		continue;
	    	        	}
	    	        	lines.add(count, user_id+":"+user_type+":"+user_firstname+" "+user_lastname+":"+user_email+":"+String.valueOf(user_credit));
	    	        	Files.write(path, lines, StandardCharsets.UTF_8);
	    				break;
	    			}
	    		}
	        	// If file read has error, throw exception
	    		catch(java.io.IOException except)
	    		{
	    			System.out.println("Write file failed:" + except);
	    		}	
    		}
        });
		
        Pane root2 = new Pane();
    	root2.setStyle("-fx-background-color: PAPAYAWHIP");
        root2.getChildren().addAll(root1,bt_create,btEXIT,btSAVE,msg,line,button2);
       
        Scene scene6 = new Scene(root2, 400, 300);
    	primaryStage.setScene(scene6);
        primaryStage.show();
	}
	public int create(TextField textField,TextField textField1,TextField textField2,TextField textField3,TextArea msg,HashMap<String, User> user_list)
	{
		user_id = textField.getText();
		user_firstname = textField1.getText();
		user_lastname = textField2.getText();
		user_email = textField3.getText();
		user_credit = 0;
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		
		if(user_list.containsKey(user_id))
		{
			msg.setText("User id already exist!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else if(user_id == "")
		{
			msg.setText("Enter user id!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else if(user_firstname == "")
		{
			msg.setText("Enter user first name!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else if(user_lastname == "")
		{
			msg.setText("Enter user last name!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else if(user_email == "")
		{
			msg.setText("Enter user email!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else if(!user_email.matches(regex))
		{
			msg.setText("Invalid user email!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else if(user_type == null)
		{
			msg.setText("Enter user type!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else
		{
			if(user_type.equals("adult"))
			{
				FullMyTi f = new FullMyTi(user_id,user_type,user_firstname,user_lastname,user_email,user_credit);
				user_list.put(user_id,f);
			}
			else if(user_type.equals("junior"))
			{
				JuniorMyTi j = new JuniorMyTi(user_id,user_type,user_firstname,user_lastname,user_email,user_credit);
				user_list.put(user_id,j);
			}
			else if(user_type.equals("senior"))
			{
				SeniorMyTi s = new SeniorMyTi(user_id,user_type,user_firstname,user_lastname,user_email,user_credit);
				user_list.put(user_id,s);
			}	
			msg.setWrapText(true);     // New line of the text exceeds the text area
			msg.setPrefRowCount(10);
			msg.setText("User added successfully!");
			msg.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
			return 1;
		}
		return 0;
	}
}
