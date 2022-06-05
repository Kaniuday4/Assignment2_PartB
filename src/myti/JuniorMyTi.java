package myti;

public class JuniorMyTi extends User implements UserInterface{
	
	private double discountRate = 0.5;

	public JuniorMyTi(String id,String type, String first_name,String last_name, String email,double user_credit)
	{
		this.id = id;
		this.type = type;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.user_credit = user_credit;
	}
	public void AddUserJourney(TravelPass t)
	{
		UserHistory.add(t);
	}
	public double getDiscount()
	{
		return discountRate;
	}
	public void setDiscount(double new_discount)
	{
		discountRate = new_discount;
	}
	public double getCredit()
	{
		return this.user_credit;
	}

}
