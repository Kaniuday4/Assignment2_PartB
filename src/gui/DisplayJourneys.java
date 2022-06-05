package gui;

import java.util.HashMap
;
import java.util.TreeMap;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import myti.User;

public class DisplayJourneys {
	public void displayjourney(Stage primaryStage,Scene scene,HashMap<String, User> user_list)
	{
		Button button2 = new Button("Back to main Menu");
    	button2.setLayoutY(240);
    	button2.setOnAction(e -> primaryStage.setScene(scene));
        
    	Button btEXIT = new Button("Cancel");
    	btEXIT.setLayoutX(140);
    	btEXIT.setLayoutY(240);
        btEXIT.setOnAction(e-> { 
            System.exit(0);
        });
        
    	Button btSAVE = new Button("Save");
    	btSAVE.setLayoutX(220);
    	btSAVE.setLayoutY(240);
    	
        Line line = new Line(0, 220, 500, 220);
        line.setStrokeWidth(2);
        
    	Button btprintall = new Button("Print all purchases");
    	btprintall.setLayoutX(10);
    	btprintall.setLayoutY(180);
    	
    	TextArea msg = new TextArea();
    	msg.setLayoutY(20);
    	msg.setLayoutX(10);
    	msg.setPrefWidth(450);
    	msg.setPrefHeight(150);
       
    	btprintall.setOnAction(e -> {
    		display(msg,user_list);
        });
        
        Pane root2 = new Pane();
		root2.setStyle("-fx-background-color: PAPAYAWHIP");
        root2.getChildren().addAll(button2,btEXIT,btSAVE,btprintall,msg,line);
        
        Scene scene6 = new Scene(root2, 500, 300);
    	primaryStage.setScene(scene6);
        primaryStage.show();
	}
	// display function to display all user journeys
	public void display(TextArea msg,HashMap<String, User> user_list)
	{
		TreeMap<String, User> sorted = new TreeMap<>();
        sorted.putAll(user_list);
        msg.setWrapText(true);     // New line of the text exceeds the text area
		msg.setPrefRowCount(30);
		for (HashMap.Entry<String, User> entry : sorted.entrySet())
        {
        	User obj = entry.getValue();
        	 	    		
			for(int i=0;i<obj.UserHistory.size();i++)
			{
				msg.appendText(obj.first_name+" travelled from "+obj.UserHistory.get(i).from_station+" to "+obj.UserHistory.get(i).to_station+" on "+obj.UserHistory.get(i).day+" departing at "+obj.UserHistory.get(i).depart_time+" arriving at "+ obj.UserHistory.get(i).arrival_time+
						" using "+obj.UserHistory.get(i).pass_type.split("_", 2)[0]+" "+obj.UserHistory.get(i).pass_type.split("_", 2)[1]+" pass costing $"+String.format("%,.2f",obj.UserHistory.get(i).cost)+"\n");
				msg.appendText("---------------------------------------------------------------------------");
			}	
        }
	}
}
