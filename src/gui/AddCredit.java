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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import myti.User;

public class AddCredit extends Main{
	
	User obj;

	public void addcredit(Stage primaryStage,Scene scene,HashMap<String, User> user_list,String output_file)
	{
	    Button button2 = new Button("Back to main Menu");
		button2.setLayoutY(220);
		button2.setOnAction(e -> primaryStage.setScene(scene));
		
    	Button btEXIT = new Button("Cancel");
		btEXIT.setLayoutY(220);
		btEXIT.setLayoutX(150);
        btEXIT.setOnAction(e-> { 
            System.exit(0);
        });
        
    	Button btSAVE = new Button("Save");
    	btSAVE.setLayoutY(220);
    	btSAVE.setLayoutX(230);
    	
        Label m = new Label();
    	m.setLayoutX(30);
        m.setText("User");
		
        Line line = new Line(0, 210, 400, 210);
        line.setStrokeWidth(2);
        
        List<String> user_idlist = new ArrayList<String>();
        for(String key : user_list.keySet())
        	user_idlist.add(key);
        
		ListView<String> list = new ListView<String>();
		ObservableList<String> items =FXCollections.observableArrayList (user_idlist);
		list.setLayoutY(20);
		list.setItems(items);
		list.setPrefWidth(100);
		list.setPrefHeight(70);
		
		Label credit1 = new Label("Enter credit:");
		credit1.setLayoutY(100);
		
		TextField credittext = new TextField ();
		credittext.setLayoutX(70);
		credittext.setLayoutY(100);
	    
    	TextArea msg = new TextArea();
    	msg.setLayoutY(160);
    	msg.setPrefWidth(200);
    	msg.setPrefHeight(30);
		
		Button bt_addcredit = new Button("Add credit");
		bt_addcredit.setLayoutY(130);
		bt_addcredit.setLayoutX(50);
					
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	     obj = user_list.get(list.getSelectionModel().selectedItemProperty().getValue());
    	    }
    	});

		bt_addcredit.setOnAction(e -> {	
			addCredit(credittext,msg,list);	 
		});
		
		btSAVE.setOnAction(e-> { 
			if(!(obj == null || obj.user_credit == 0))
			{
				File1 f = new File1();
				f.writeCredit(output_file,obj);
			}
        });
		
		Pane root3 = new Pane();
		root3.setStyle("-fx-background-color: PAPAYAWHIP");
		root3.getChildren().addAll(list,credit1,credittext,bt_addcredit,button2,btEXIT,msg,btSAVE,m,line);
		
	    Scene scene3 = new Scene(root3, 400, 250);
		primaryStage.setScene(scene3);
	    primaryStage.show();
	}
	public void addCredit(TextField credittext,TextArea msg,ListView<String> list)
	{
		 if(obj == null)
		 {
			 msg.setText("Select user!");
			 msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		 }
		 else if(credittext.getText() == "")
		 {
	 		msg.setText("Enter credit amount!");
		    msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
		 }
		 else
		 {
 			 double credit = 0;
 			 try 
 			 {
 				 credit = Double.valueOf(credittext.getText());
 			 }
 			 catch (Exception ex) {
 				 msg.setText("Invalid input!");
				 msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
	         }
	       
	         if(credit > CREDIT_LIMIT)
	         {
	        	 msg.setText("Sorry, the max amount of credit allowed is $100.00");
				 msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
	         }
	         else if(obj.user_credit + credit > CREDIT_LIMIT)
	         {
	        	 msg.setText("Sorry, MyTi can hold upto $100.00");
				 msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
	         }
	         else if(credit <= 0)
	         {
	        	 msg.setText("Invalid credit input!");
				 msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
	         }
	         else if(credit%5 != 0)
	         {
	        	 msg.setText("Sorry, you can only add multiples of $5.00");
				 msg.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
	         }
	         else
	         {
		        obj.user_credit += credit;
	 			msg.setText(list.getSelectionModel().selectedItemProperty().getValue()+" credit is $" + String.format("%,.2f",obj.user_credit));
				msg.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
	         }
		 }		
	}
}
