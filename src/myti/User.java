package myti;

import java.util.ArrayList;

public class User {
	public String id;
	public String type;
	public String first_name;
	public String last_name;
	public String email;
	public double user_credit;
	public ArrayList<TravelPass> UserHistory = new ArrayList<TravelPass>();
}
