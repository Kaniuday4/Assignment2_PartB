package myti;

public interface UserInterface
{	
	double discountRate = 0;
	
	public void AddUserJourney(TravelPass t);
	
	public double getDiscount();
	
	public void setDiscount(double new_discount);	
		
	public double getCredit();	
}

