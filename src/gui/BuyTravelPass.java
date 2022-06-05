package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import fileio.File1;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import myti.Station;
import myti.TravelPass;
import myti.User;

public class BuyTravelPass extends Main{
    
	User obj;
	int flag = 0;
    
	public void travelPass(Stage primaryStage,Scene scene,HashMap<String, User> user_list,ArrayList<Station> station_list,String output_file)
	{

    	Button button2 = new Button("Back to main Menu");
    	button2.setLayoutY(220);
    	button2.setOnAction(e -> primaryStage.setScene(scene));
    	
    	Text t = new Text();
    	t.setText("User");
        t.setLayoutY(12);
        
    	Text t1 = new Text();
    	t1.setText("From");
        t1.setLayoutX(100);    	
        t1.setLayoutY(12);
        
        Text t2 = new Text();
    	t2.setText("To");
        t2.setLayoutX(200);    	
        t2.setLayoutY(12);
        
        Text t3 = new Text();
    	t3.setText("Day");
        t3.setLayoutX(320);    	
        t3.setLayoutY(30);
        
        Text t4 = new Text();
    	t4.setText("Start");
        t4.setLayoutX(320);    	
        t4.setLayoutY(70); 
        
        Text t5 = new Text();
    	t5.setText("End");
        t5.setLayoutX(320);    	
        t5.setLayoutY(120);
        
        List<String> user_idlist = new ArrayList<String>();
        for(String key : user_list.keySet())
        	user_idlist.add(key);

    	ListView<String> list_user = new ListView<String>();
    	ObservableList<String> items =FXCollections.observableArrayList (user_idlist);
    	list_user.setItems(items);
    	list_user.setPrefWidth(100);
    	list_user.setPrefHeight(70);
    	list_user.setLayoutY(20);
    	
    	ListView<String> list_fromstation = new ListView<String>();
    	ObservableList<String> items1 =FXCollections.observableArrayList (super.station_names);
    	list_fromstation.setItems(items1);
    	list_fromstation.setPrefWidth(100);
    	list_fromstation.setPrefHeight(70);
    	list_fromstation.setLayoutY(20);
    	list_fromstation.setLayoutX(100);
    	
    	ListView<String> list_tostation = new ListView<String>();
    	list_tostation.setItems(items1);
    	list_tostation.setPrefWidth(100);
    	list_tostation.setPrefHeight(70);
    	list_tostation.setLayoutY(20);
    	list_tostation.setLayoutX(200);
    	
    	ListView<String> list_days = new ListView<String>();
    	ObservableList<String> items3 =FXCollections.observableArrayList (super.days);
    	list_days.setItems(items3);
    	list_days.setPrefWidth(100);
    	list_days.setPrefHeight(20);
    	list_days.setLayoutY(15);
    	list_days.setLayoutX(350);
    	
    	TextField textField = new TextField ();
    	textField.setLayoutY(55);
    	textField.setLayoutX(350);
    	textField.setPrefWidth(100);
    	
    	TextField textField1 = new TextField ();
    	textField1.setLayoutY(100);
    	textField1.setLayoutX(350);
    	textField1.setPrefWidth(100);
    	
    	TextArea textField2 = new TextArea();
    	textField2.setLayoutY(150);
    	textField2.setLayoutX(150);
    	textField2.setPrefWidth(200);
    	textField2.setPrefHeight(50);

    	Button bt_purchase = new Button("Purchase");
    	bt_purchase.setLayoutY(150);
    	bt_purchase.setLayoutX(50);
        
        Line line = new Line(0, 210, 500, 210);
        line.setStrokeWidth(2);
 
        list_user.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	    	obj = user_list.get(list_user.getSelectionModel().selectedItemProperty().getValue());
    	    }
    	});
    	
        bt_purchase.setOnAction(actionEvent -> {
        	TravelPass travelpass = new TravelPass();
        	travelpass.from_station = list_fromstation.getSelectionModel().selectedItemProperty().getValue();
        	travelpass.to_station = list_tostation.getSelectionModel().selectedItemProperty().getValue();
        	travelpass.day = list_days.getSelectionModel().selectedItemProperty().getValue();
        	String depart_time = textField.getText();
        	String arrival_time = textField1.getText();
        	
        	if(obj == null)
        	{
            	textField2.setText("Enter user id!");
    			textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        	}
        	else if(travelpass.from_station == null)
        	{
            	textField2.setText("Enter from station!");
    			textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        	}
        	else if(travelpass.to_station == null || travelpass.to_station.equals(travelpass.from_station))
        	{
            	textField2.setText("Enter valid to station!"); 
    			textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        	}
        	else if(travelpass.day == null)
        	{
                textField2.setText("Enter day!"); 
    			textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        	}
        	else
        	{
        	if(depart_time == "")
        	{
                textField2.setText("Enter start time!"); 
    			textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        	}
        	else if(!depart_time.equals(""))
        	{
        		try 
        		{
        			travelpass.depart_time = Integer.parseInt(textField.getText());
    				String digit = textField.getText().substring(textField.getText().length()-2, textField.getText().length());
    	            int number = Integer.parseInt(digit);
        			if(travelpass.depart_time < 0 ||  travelpass.depart_time>2359 || String.valueOf(travelpass.depart_time).length()<2 || String.valueOf(travelpass.depart_time).length()>4 || number > 59)
        			{
                        textField2.setText("Enter valid start time!");
    	  				textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        			}
        			else if(arrival_time == "")
        			{
                        textField2.setText("Enter end time!");
    	  				textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        			}
                	else if(!arrival_time.equals(""))
                	{
                		try 
                		{
        	    			travelpass.arrival_time = Integer.parseInt(textField1.getText());
        					String digit1 = textField1.getText().substring(textField1.getText().length()-2, textField1.getText().length());
        		            int number1 = Integer.parseInt(digit1);
        	    			if(travelpass.arrival_time < 0 ||  travelpass.arrival_time>2359 || String.valueOf(travelpass.arrival_time).length()<2 || String.valueOf(travelpass.arrival_time).length()>4 || travelpass.arrival_time<=travelpass.depart_time || number1>59)
        	    			{   
        	    				textField2.setText("Enter valid end time!");
        		  				textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        	    			}
        	    			else
        	    			{
        	    				flag = 1;
	        	        		String message = travelpass.computeBestTravelPassCost(station_list,obj); 
	        	        		textField2.setWrapText(true);     // New line of the text exceeds the text area
	        	        		textField2.setPrefRowCount(10);
	        	        		textField2.setText(message);
	        	  				textField2.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
        	    			}
        	    		}
        	  			catch (Exception ex) {
        	  				textField2.setText("Enter valid end time!");
        	  				textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        	 	        }
                	}
        		}
      			catch (Exception ex) {
      				textField2.setText("Enter valid start time!");
      				textField2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
     	        }
        		
        	}
        	
        	}
    	});

    	Button btCANCEL = new Button("Cancel");
    	btCANCEL.setLayoutY(220);
    	btCANCEL.setLayoutX(150);
    	btCANCEL.setOnAction(e-> { 
            System.exit(0);
        });
        
    	Button btSAVE = new Button("Save");
    	btSAVE.setLayoutY(220);
    	btSAVE.setLayoutX(220);
    	btSAVE.setOnAction(e-> { 
    		if(flag == 1)
    		{
    			File1 f = new File1();
    			f.writeCredit(output_file,obj);
    		}
        });
        
        Pane root2 = new Pane();
    	root2.setStyle("-fx-background-color: PAPAYAWHIP");
    	root2.getChildren().addAll(list_user,list_fromstation,list_tostation,list_days,button2);
    	root2.getChildren().addAll(t,t1,t2,t3,t4,t5,line);
    	root2.getChildren().addAll(textField,textField1,textField2);
    	root2.getChildren().addAll(bt_purchase,btCANCEL,btSAVE);
    	
        Scene scene2 = new Scene(root2, 500, 250);
    	primaryStage.setScene(scene2);
        primaryStage.show(); 
	}
	
}
