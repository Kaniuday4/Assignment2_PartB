package fileio;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import myti.User;

public class File1 {
	public void initFile(String input_file,String output_file)
	{	
		Path path = Paths.get(output_file);
		if (Files.notExists(path)) 
			update_output_file(input_file,output_file);
	}
	
	public void update_output_file(String input_file, String output_file)
	{
		try 
		{
			FileReader fin = new FileReader(input_file);  
			FileWriter fout = new FileWriter(output_file, true);  
		    int c;  
			while ((c = fin.read()) != -1) 
			{  
			   fout.write(c);  
			}  
		    fin.close();  
			fout.close(); 
		}
		catch(java.io.IOException e)
		{
			System.out.println("Write file failed:" + e);
		} 
	}
	public void writeCredit(String output_file,User obj)
	{
		// update credit details of user in output file
		try 
		{
			Path path = Paths.get(output_file);
	        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	        int count = 0;
	        for(String s: lines)
			{
	        	if(s.equals("#users"))
	        	{
	        		count+=1;
	        		continue;
	        	}
	        	if(s.equals("#prices"))
	        	{
	        		count+=1;
	        		break;
	        	}
				String[] word = s.split(":");	    			
				if(word[0].equals(obj.id))
				{
	    	        lines.set(count,obj.id+":"+obj.type+":"+obj.first_name+" "+obj.last_name+":"+obj.email+":"+String.format("%,.2f",obj.user_credit));
	    	        Files.write(path, lines, StandardCharsets.UTF_8);
				}
				count+=1;
			}
		}
    	// If file read has error, throw exception
		catch(java.io.IOException except)
		{
			System.out.println("Write file failed:" + except);
		}	
	}
	public void writePrice(String output_file,String time,String zone,double new_cost)
	{
		// update travel pass prices in output file
		try 
		{
			Path path = Paths.get(output_file);
	        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	        int count = 0;
	        for(String s: lines)
			{
	        	if(s.equals("#users"))
	        	{
	        		count+=1;
	        		continue;
	        	}
	        	if(s.equals("#prices"))
	        	{
	        		count+=1;
	        		continue;
	        	}
				String[] word = s.split(":");	
				if(word[0].equals(time) && word[1].equals(zone))
				{
	    	        lines.set(count,word[0]+":"+word[1]+":"+String.format("%,.2f",new_cost));
	    	        Files.write(path, lines, StandardCharsets.UTF_8);
				}
				count+=1;
			}
		}
    	// If file read has error, throw exception
		catch(java.io.IOException except)
		{
			System.out.println("Write file failed:" + except);
		}		
	}
	
}
