package myti;

import java.util.ArrayList;

public class TravelPass {
	public String pass_type;
	public String day;
	public double cost;
	public int arrival_time;
	public int depart_time;
	public String from_station;
	public String to_station;
	
	// compute zone based on from and to station zones
	public int computeZone(String from_station,String to_station,ArrayList<Station> station_list)
	{
		int[] zones = new int[3];
		int count = 0;
		for(int i=0;i<station_list.size();i++)
		{
			if(station_list.get(i).name.equals(from_station))
			{
				zones[count] = station_list.get(i).zone;
				count++;
			}
			if(station_list.get(i).name.equals(to_station))
			{
				zones[count] = station_list.get(i).zone;
				count++;
			}
		}
		if(zones[0] != zones[1])
		{
			return 12;
		}
		else if(zones[0] == zones[1])
		{
			return zones[0];
		}
		return 0;	
	}
	// get cost of zone based on arrival and depart time
	public double getZoneCost(int zone,int length)
	{
		if(zone == 1)
		{
			if(this.arrival_time - this.depart_time <= 200 || length == 0)
			{
				Zone1_2hours z = new Zone1_2hours();
				this.pass_type = "Zone1_2hours";
				return z.GetCost();
			}
			else
			{
				Zone1_allday z = new Zone1_allday();
				this.pass_type = "Zone1_allday";
				return z.GetCost();
			}
		}
		else if(zone == 2)
		{
			if(this.arrival_time - this.depart_time <= 200 || length == 0)
			{
				Zone2_2hours z = new Zone2_2hours();
				this.pass_type = "Zone2_2hours";
				return z.GetCost();
			}
			else
			{
				Zone2_allday z = new Zone2_allday();						
				this.pass_type = "Zone2_allday";
				return z.GetCost();
			}
		}
		else if (zone == 12)
		{
			if(this.arrival_time - this.depart_time <= 200 || length == 0)
			{
				Zone12_2hours z = new Zone12_2hours();
				this.pass_type = "Zone12_2hours";
				return z.GetCost();
			}
			else
			{
				Zone12_allday z = new Zone12_allday();
				this.pass_type = "Zone12_allday";
				return z.GetCost();
			}
		}
		return 0;
	}
	
	public boolean updateUserCredit(User obj,double cost)
	{
		if(obj.user_credit >= cost)
		{
			System.out.println(obj.user_credit);
			obj.user_credit -= cost;
			System.out.println(obj.user_credit);
			return true;
		}
		else
		{
			System.out.println("Insufficient credit! Please recharge MyTi!");
			return false;
		}	
	}
	
	public void updateJourneyInfo(User obj,ArrayList<Station> station_list)
	{
	if(updateUserCredit(obj,this.cost))
	{	
		if(obj instanceof SeniorMyTi)
			((SeniorMyTi)obj).AddUserJourney(this);
		else if(obj instanceof JuniorMyTi)  			
			((JuniorMyTi)obj).AddUserJourney(this);
		else if(obj instanceof FullMyTi)   			
			((FullMyTi)obj).AddUserJourney(this);
		// Update station stats
		// add countStartstation and countEnd station
		for(int i=0;i<station_list.size();i++)
		{
			if(station_list.get(i).name.equals(this.from_station))
			{
				station_list.get(i).CountStartStattion();
			}
			if(station_list.get(i).name.equals(this.to_station))
			{
				station_list.get(i).CountEndStattion();
			}
		}
	}
	}

	public String computeBestTravelPassCost(ArrayList<Station> station_list,User obj)
	{
		System.out.println(this.from_station+this.to_station+this.day+obj.UserHistory.size());
		int zone = computeZone(this.from_station,this.to_station,station_list);
		String message;
		// if user type is Senior (concession user) and travels on Sunday, travel pass is free of cost
		if(obj.type.equals("Senior") && this.day.equals("Sun"))
		{
			this.cost = 0;
			this.pass_type = "Zone"+Integer.toString(zone)+"_"+"-";
			updateJourneyInfo(obj,station_list);
			return "Concession ticket for "+obj.id+ " costing $0.0 valid till 23.59";
		}
		int length = obj.UserHistory.size();
		double discount = 1;
		if(obj instanceof SeniorMyTi)
			discount = ((SeniorMyTi)obj).getDiscount();
		else if(obj instanceof JuniorMyTi)
			discount = ((JuniorMyTi)obj).getDiscount();
		// if the journey is the first journey in the day, then default 2 hour pass is taken
		if(length == 0)
		{
			this.cost = getZoneCost(zone,0)*discount;
			if(obj.type.equals("senior") || obj.equals("junior"))
				message = "zone" + zone + " 2 hours " + "(Concession) travel pass purchased for "+obj.id+" for $"+String.format("%.2f", this.cost)+ " valid until "+(this.depart_time+200);
			else
				message = "zone" + zone + " 2 hours " + "travel pass purchased for "+obj.id+" for $"+String.format("%.2f", this.cost)+ " valid until "+(this.depart_time+200);
		}
		else
		{
			String last_day = obj.UserHistory.get(length-1).day;
			String last_pass_type = obj.UserHistory.get(length-1).pass_type;
			String last_pass_duration =  last_pass_type.split("_", 2)[1];
			String last_zone_number = last_pass_type.split("_", 2)[0];

			int last_zone = Integer.parseInt(last_pass_type.substring(last_zone_number.indexOf("zone")+5 , last_zone_number.length()));
			// if new journey takes place on the same day as the previous journey, then current pass is updated
			if(last_day.equals(this.day) && zone == last_zone)
			{
				int last_depart_time =  obj.UserHistory.get(length-1).depart_time;
				int last_arrival_time =  obj.UserHistory.get(length-1).arrival_time;
				
				System.out.println(last_depart_time);
				System.out.println(last_arrival_time);

				if(this.arrival_time - last_depart_time <=200 && last_pass_duration.equals("2hours"))
				{
					System.out.println("enter");
					this.pass_type = obj.UserHistory.get(length-1).pass_type;
					this.cost = 0;
					updateJourneyInfo(obj,station_list);
					return "Current 2 hour pass still valid";
				}
				else if(last_pass_duration.equals("allday"))
				{
					this.pass_type = obj.UserHistory.get(length-1).pass_type;
					this.cost = 0;
					updateJourneyInfo(obj,station_list);
					return "Current All day pass still valid";
				}
				else
					message = sameDayPass(zone, discount,obj);
			}
			else
			{
				this.cost = getZoneCost(zone,1)*discount;
				if(obj.type.equals("senior") || obj.equals("junior"))
					message = "zone" + zone + " 2 hours " + "(Concession) travel pass purchased for "+obj.id+" for $"+String.format("%.2f", this.cost)+ " valid until "+(this.depart_time+200);
				else
					message = "zone" + zone + " 2 hours " + "travel pass purchased for "+obj.id+" for $"+String.format("%.2f", this.cost)+ " valid until "+(this.depart_time+200);
			}
		}
		updateJourneyInfo(obj,station_list);
		return message;
	}
	// If previous pass is on the same day as current journey, get best cost based on two criteria
	// 1. if new 2 hour pass cost is higher than all day pass, all day pass is chosen
	// 2. if new 2 hour pass cost is lower than all day pass, 2 hour pass is chosen
	public String sameDayPass(int zone,double discount,User obj)
	{
		String message;
		if(zone == 1)
		{
			Zone1_2hours z12 = new Zone1_2hours();
			Zone1_allday z1allday = new Zone1_allday();
			if(z12.GetCost() < z1allday.GetCost()-z12.GetCost())
			{
				this.cost = z12.GetCost()*discount;
				message = "zone" + zone + " 2 hours " + "travel pass purchased for "+obj.id+" for $"+String.format("%.2f", this.cost)+ " valid until "+(this.depart_time+200);
			}
			else
			{
				this.cost = (z1allday.GetCost()-z12.GetCost())*discount;
				this.pass_type = "Zone1_allday";
				message = "zone" + zone + " All day " + "travel pass purchased for "+obj.id+" for $"+String.format("%.2f", this.cost)+ " valid until "+"2359";

			}
		}
		else if(zone == 2)
		{
			Zone2_2hours z22 = new Zone2_2hours();
			Zone2_allday z2allday = new Zone2_allday();
			if(z22.GetCost() < z2allday.GetCost()-z22.GetCost())
			{
				this.cost = z22.GetCost()*discount;
				message = "zone" + zone + " 2 hours " + "travel pass purchased for "+obj.id+" for $"+String.format("%.2f", this.cost)+ " valid until "+(this.depart_time+200);
			}
			else
			{
				this.cost = (z2allday.GetCost()-z22.GetCost())*discount;
				this.pass_type = "Zone2_allday";
				message = "zone" + zone + " All day " + "travel pass purchased for "+obj.id+" for $"+String.format("%.2f", this.cost)+ " valid until "+"2359";
			}			
		}
		else
		{
			Zone12_2hours z122 = new Zone12_2hours();
			Zone12_allday z12allday = new Zone12_allday();
			if(z122.GetCost() < z12allday.GetCost()-z122.GetCost())
			{
				this.cost = z122.GetCost()*discount;
				message = "zone" + zone + " 2 hours " + "travel pass purchased for "+obj.id+" for $"+String.format("%.2f", this.cost)+ " valid until "+(this.depart_time+200);
			}
			else
			{
				this.cost = (z12allday.GetCost()-z122.GetCost())*discount;
				this.pass_type = "Zone12_allday";
				message = "zone" + zone + " All day " + "travel pass purchased for "+obj.id+" for $"+String.format("%. 2f", this.cost)+ " valid until "+"2359";
			}
		}
		return message;
	}

}
