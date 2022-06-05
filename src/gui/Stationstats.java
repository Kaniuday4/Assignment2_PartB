package gui;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import myti.Station;

public class Stationstats {
	public void displayjourney(Stage primaryStage,Scene scene,ArrayList<Station> station_list)
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
        
        Line line = new Line(0, 220, 500, 220);
        line.setStrokeWidth(2);
        
    	Button btSAVE = new Button("Save");
    	btSAVE.setLayoutX(220);
    	btSAVE.setLayoutY(240);
    	Button btprintall = new Button("Print all station stats");
    	btprintall.setLayoutX(10);
    	btprintall.setLayoutY(180);
    	
    	TextArea msg = new TextArea();
    	msg.setLayoutY(20);
    	msg.setLayoutX(10);
    	msg.setPrefWidth(450);
    	msg.setPrefHeight(150);
    	
    	btprintall.setOnAction(e -> {
    		for(int i = 0;i < station_list.size();i++)
    		{
    			msg.appendText(station_list.get(i).startStationCount+" journeys started and " + station_list.get(i).endStationCount+" journeys ended at "+ station_list.get(i).name+" station\n");
    		}
        });
    	
        Pane root2 = new Pane();
    	root2.setStyle("-fx-background-color: PAPAYAWHIP");
        root2.getChildren().addAll(button2,btEXIT,btSAVE,btprintall,msg,line);
        
        Scene scene6 = new Scene(root2, 500, 300);
    	primaryStage.setScene(scene6);
        primaryStage.show();
	}
}
