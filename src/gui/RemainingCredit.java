package gui;

import java.util.ArrayList;

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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import myti.User;

public class RemainingCredit extends Main {

	User obj;
	public void remainingcredit(Stage primaryStage,Scene scene,HashMap<String, User> user_list)
	{
		Button button2 = new Button("Back to main Menu");
    	button2.setLayoutY(220);
    	button2.setOnAction(e -> primaryStage.setScene(scene));
        
    	Button btEXIT = new Button("Cancel");
    	btEXIT.setLayoutX(150);
    	btEXIT.setLayoutY(220);
        btEXIT.setOnAction(e-> { 
            System.exit(0);
        });
        
    	Button btSAVE = new Button("Save");
    	btSAVE.setLayoutX(230);
    	btSAVE.setLayoutY(220);
        
        Line line = new Line(0, 210, 300, 210);
        line.setStrokeWidth(2);
        
    	Button btcredit = new Button("Check credit");
    	btcredit.setLayoutX(10);
    	btcredit.setLayoutY(100);

        List<String> user_idlist = new ArrayList<String>();
        for(String key : user_list.keySet())
        	user_idlist.add(key);
        
        Label m = new Label();
    	m.setLayoutX(30);
        m.setText("User");
        
    	ListView<String> list = new ListView<String>();
    	ObservableList<String> items =FXCollections.observableArrayList (user_idlist);
    	list.setItems(items);
    	list.setLayoutY(20);
    	list.setPrefWidth(100);
    	list.setPrefHeight(70);
    	
    	TextArea msg = new TextArea();
    	msg.setLayoutY(150);
    	msg.setPrefWidth(150);
    	msg.setPrefHeight(20);
    	
    	list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	    	obj = user_list.get(list.getSelectionModel().selectedItemProperty().getValue());
    	    }
    	});
    	
    	btcredit.setOnAction(e -> {
    		addCredit(msg,list);
    	});

        Pane root2 = new Pane();
    	root2.setStyle("-fx-background-color: PAPAYAWHIP");
    	root2.getChildren().addAll(list,msg,btSAVE,btcredit,m,line,button2,btEXIT);
    	
        Scene scene4 = new Scene(root2, 300, 250);
    	primaryStage.setScene(scene4);
        primaryStage.show();	
	}
	public void addCredit(TextArea msg,ListView<String> list)
	{
		if(obj == null)
		{
    		msg.setText("Enter user id!");
			msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		}
		else
		{
	    	msg.setWrapText(true); 
			msg.setPrefRowCount(10);
			msg.setText(list.getSelectionModel().selectedItemProperty().getValue()+" credit is $" + String.format("%,.2f",obj.user_credit));
			msg.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");

		}
	}
}
