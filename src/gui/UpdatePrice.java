package gui;

import fileio.File1;
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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import myti.Zone12_2hours;
import myti.Zone12_allday;
import myti.Zone1_2hours;
import myti.Zone1_allday;
import myti.Zone2_2hours;
import myti.Zone2_allday;

public class UpdatePrice {
	String zone = null;
	String time = null;
	double new_cost = 0;

	public void updateprice(Stage primaryStage,Scene scene,String output_file)
	{
		Button button2 = new Button("Back to main Menu");
    	button2.setLayoutY(240);
    	button2.setOnAction(e -> primaryStage.setScene(scene));
        
    	Button btCANCEL = new Button("Cancel");
    	btCANCEL.setLayoutY(240);
    	btCANCEL.setLayoutX(150);
    	btCANCEL.setOnAction(e-> { 
            System.exit(0);
        });
        
    	Button btSAVE = new Button("Save");
    	btSAVE.setLayoutY(240);
    	btSAVE.setLayoutX(220);
    	
        Line line = new Line(0, 220, 350, 220);
        line.setStrokeWidth(2);
    	
    	Text t = new Text();
    	t.setText("Zone");
        t.setLayoutY(12); 
        
    	Text t1 = new Text();
    	t1.setText("Type");
        t1.setLayoutX(100);    	
        t1.setLayoutY(12);    	
    	
    	ListView<String> list = new ListView<String>();
    	ObservableList<String> items =FXCollections.observableArrayList ("Zone1", "Zone2","Zone12");
    	list.setItems(items);
    	list.setLayoutY(20);
    	list.setPrefWidth(100);
    	list.setPrefHeight(70);
    	
    	ListView<String> list1 = new ListView<String>();
    	ObservableList<String> items1 =FXCollections.observableArrayList ("2Hour", "AllDay");
    	list1.setItems(items1);
    	list1.setLayoutY(20);
    	list1.setLayoutX(100);
    	list1.setPrefWidth(100);
    	list1.setPrefHeight(70);
    	
    	Label cost = new Label("Enter new price:");
    	cost.setLayoutY(100);
    	
    	TextField costtext = new TextField ();
    	costtext.setLayoutX(100);
    	costtext.setLayoutY(100);
    	
    	TextArea msg = new TextArea();
    	msg.setLayoutY(170);
    	msg.setLayoutX(30);
    	msg.setPrefWidth(150);
    	msg.setPrefHeight(20);
    	
    	Button bt_update = new Button("Update price");
    	bt_update.setLayoutY(130);
    	bt_update.setLayoutX(50);
    	
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	     zone = list.getSelectionModel().selectedItemProperty().getValue();
    	    }
    	});
		
		list1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	     time = list1.getSelectionModel().selectedItemProperty().getValue();
    	    }
    	});
		
    	bt_update.setOnAction(e -> {
    		update(msg,costtext,list,list1);
    	});
    	
    	btSAVE.setOnAction(e -> {
    		File1 f = new File1();
    		f.writePrice(output_file, time, zone, new_cost);	
    	});
    	
        Pane root2 = new Pane();
    	root2.setStyle("-fx-background-color: PAPAYAWHIP");
    	root2.getChildren().addAll(button2,btSAVE,btCANCEL,list,list1,t,t1,bt_update,costtext,cost,msg,line);
    	
        Scene scene5 = new Scene(root2, 350, 280);
    	primaryStage.setScene(scene5);
        primaryStage.show();
	}
	public void update(TextArea msg,TextField costtext,ListView<String> list,ListView<String> list1)
	{
		// check if any of user input is null,if null show message to enter field
		if(zone == null)
		{
    		msg.setText("Enter zone!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else if(time == null)
		{
    		msg.setText("Enter type!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else
		{
    		try 
    		{
    			new_cost = Double.valueOf(costtext.getText());
    		}
    		catch (Exception ex) {
  				msg.setText("Invalid cost!");
  				msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
	        }
    		if (new_cost == 0)
    		{
  				msg.setText("Invalid cost!");
  				msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
    		}
    		else
    		{
	    		if(zone.equals("Zone1") && time.equals("2Hour"))
	    			Zone1_2hours.cost = new_cost;
	    		
	    		if(zone.equals("Zone1") && time.equals("AllDay"))
	    			Zone1_allday.cost = new_cost;
	    		
	    		if(zone.equals("Zone2") && time.equals("2Hour"))
	    			Zone2_2hours.cost = new_cost;
	    		
	    		if(zone.equals("Zone2") && time.equals("AllDay"))
	    			Zone2_allday.cost = new_cost;
	    		
	    		if(zone.equals("Zone12") && time.equals("2Hour"))
	    			Zone12_2hours.cost = new_cost;
	    		
	    		if(zone.equals("Zone12") && time.equals("AllDay"))
	    			Zone12_allday.cost = new_cost;
	    		
	    		msg.setWrapText(true);     // New line of the text exceeds the text area
	    		msg.setPrefRowCount(10);
	    		msg.setText(list.getSelectionModel().selectedItemProperty().getValue()+" " + list1.getSelectionModel().selectedItemProperty().getValue()+" cost updated to $"+String.format("%,.2f",new_cost));
	    		msg.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
	
	    	}
		}
	}
}
