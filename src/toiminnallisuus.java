
import java.sql.*;
import java.util.ArrayList;

public class toiminnallisuus {
	
	//Tietokannan osoite
	private static String URL = "jdbc:mysql://sql7.freemysqlhosting.net/sql7313255";
	//Tunnukset sis‰‰nkirjautumiseen
	private static String username = "sql7313255";
	private static String psswd = "2ECxRfSKHN";
	
	public static void connectionTest(){
		
		try{
			
			Connection yhteys = DriverManager.getConnection(URL, username, psswd);
			
			System.out.println("Connection test.\nConnection established successfully.");
			
			//Suljetaan yhteys
			yhteys.close();
			
		}
		catch (SQLException e) {
			System.out.println("Connection error.");
			System.out.println(e);
		}
		
	}
	
	
	//METODI TIEDON TIETOKANTAAN TALLENTAMISEEN
	
	private static String fullName;
	private static String age;
	private static String phoneNro;
	private static String address;
	
	
	public static void saveToDB() {
		
		try {
			
			//Yhteys tietokantaan
			Connection connection = DriverManager.getConnection(URL, username, psswd);
			//Statement-olio 
			Statement statement = connection.createStatement();
			//SQL-kysely tietokantaan, joka tallentaa tiedot skeemaan
			statement.executeUpdate("INSERT INTO members (fullName,age,phoneNro,address) VALUES ('" + fullName + "','" + age + "','" + phoneNro + "','" + address +"');");
			
			System.out.println("Row added successfully.");
			
			//Suljetaan yhteys
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("Error!");
			System.out.println(e);
		}
		
	}
	
	
	
	//METODI TIETOKANNASTA POISTAMISEEN
	
	public static void deleteFromDB() {
		
		String column1 = TietokantaGUI.getConvertedCD1();
		String column2 = TietokantaGUI.getConvertedCD2();
		String column3 = TietokantaGUI.getConvertedCD3();
		String column4 = TietokantaGUI.getConvertedCD4();
		
		try {
			
			//Yhteys tietokantaan
			Connection connection = DriverManager.getConnection(URL, username, psswd);
			//Statement-olio 
			Statement statement = connection.createStatement();
			//SQL-kysely tietokantaan, joka poistaa rivin tietokannasta
			statement.executeUpdate("DELETE FROM members WHERE fullName='" + column1 + "' AND age='" + column2 + "' AND phoneNro='" + column3
			+ "' AND address='" + column4 + "';");
			
			//Suljetaan yhteys
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("Error in deleting row.");
			System.out.println(e);
		}
	}


	
	//GETTERIT JA SETTERIT
	public String getFullName() {
		return fullName;
	}


	public void setFullame(String fullName) {
		toiminnallisuus.fullName = fullName;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		toiminnallisuus.age = age;
	}


	public String getPhoneNro() {
		return phoneNro;
	}


	public void setPhoneNro(String phoneNro) {
		toiminnallisuus.phoneNro = phoneNro;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		toiminnallisuus.address = address;
	}


	public static String getURL() {
		return URL;
	}


	public static void setURL(String uRL) {
		URL = uRL;
	}


	public static String getUsername() {
		return username;
	}


	public static void setUsername(String username) {
		toiminnallisuus.username = username;
	}


	public static String getPsswd() {
		return psswd;
	}


	public static void setPsswd(String psswd) {
		toiminnallisuus.psswd = psswd;
	}
	
}
