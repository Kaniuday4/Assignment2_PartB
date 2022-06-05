package myti;

public class Station {
	public String name;
	public int zone;
	public int startStationCount;
	public int endStationCount;
	
	public Station(String name,int zone)
	{
		this.name = name;
		this.zone = zone;
	}
	public void CountStartStattion()
	{
		this.startStationCount+=1;
	}
	public void CountEndStattion()
	{
		this.endStationCount+=1;
	}
	public int getStartStattionCount()
	{
		return startStationCount;
	}
	public int getEndStattionCount()
	{
		return startStationCount;
	}
	public void printStationStats()
	{
		System.out.printf("%10s   | %20s       | %20s", this.name, this.startStationCount,this.endStationCount);
	}
}
