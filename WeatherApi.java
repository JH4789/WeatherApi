import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class WeatherApi {
	StringBuilder urlinput = new StringBuilder();
	String input = "https://api.openweathermap.org/data/2.5/weather?q=";
	String quitcheck = "";
	 String xcoordinate;
     String ycoordinate;
     String temperature;
     String humidity ;
     String windspeed ;
     String winddegree ;
     String clouds ;
     int cloudprocessing;
	boolean running = true;
	private static final String USER_AGENT = "Mozilla/5.0";
	public WeatherApi() throws IOException, InterruptedException {
		while(running == true) {
		urlinput.append(input);
		Scanner in = new Scanner(System.in);
		System.out.println("What is the name of your city? Enter quit to exit");
		String cityname = in.nextLine();
		if(cityname.equals("quit")) {
			System.out.println("Have a nice day!");
			System.exit(0);
		}
		urlinput.append(cityname);
		urlinput.append(",");
		System.out.println("What is the name of your country");
		String countryname = in.nextLine();
		urlinput.append(countryname);
		urlinput.append("&appid=");
	
		Scanner filescanner = new Scanner (new File("apicode.txt"));
		String apiCode=filescanner.nextLine();
		urlinput.append(apiCode);
		
		
			String stringurl = urlinput.toString();
			URL obj = new URL(stringurl);
	        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
	        
	        httpURLConnection.setRequestMethod("GET");
	        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
	        int responseCode = httpURLConnection.getResponseCode();
	        if(responseCode == -1) {
	        	System.out.println("Please enter valid inputs");
	        	System.exit(0);
	        }
	        BufferedReader in1 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in1 .readLine()) != null) {
	            response.append(inputLine);
	        } in1 .close();
	        String str = response.toString();
	        str = str.replaceAll("[^-?.?0-9]+", " "); 
	        
	        String[] strarray = str.trim().split(" "); 
	        if(strarray.length != 23) {
	        	System.out.println("Data may not be accurate for this city, please try again");
	        }
	        
	       
	         xcoordinate = strarray[0];
	         ycoordinate = strarray[1];
	         temperature = strarray[4];
	         humidity = strarray[9];
	       windspeed = strarray[11];
	        winddegree = strarray[12];
	        clouds = strarray[14];
	        cloudprocessing = Integer.parseInt(clouds);
	        if(cloudprocessing > 100) {
	        	clouds = strarray[13];
	        }
	        System.out.println("The coordinates of your city are: " + xcoordinate + "," + ycoordinate);
	        System.out.println("The temperature in your city is: " + temperature +" degrees Kelvin");
	        System.out.println("Humidity is at " + humidity + "%");
	        System.out.println("Wind is coming at " + winddegree + " degrees and moving at " + windspeed + " meters per second");
	        System.out.println("It is " + clouds + "% cloudy today");
	        urlinput.setLength(0);
	    
	       
		}
		
		
		
        
      
        
	

}
	
	public static void main(String args[]) throws IOException, InterruptedException {  
		new WeatherApi();
	}
}
