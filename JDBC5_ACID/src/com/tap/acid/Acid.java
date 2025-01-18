package com.tap.acid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Acid {

	private static final String url = "jdbc:mysql://localhost:3306/jdbc_class";
	private static final String username = "root";
	private static final String pwd = "root";
	
	private static String SQL_QUERY = "update employee set salary = salary + ? where name = ?";
	
	private static Connection con;
	private static PreparedStatement stmt;
	
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
				
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,username,pwd);
			
			transaction();
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}
		
	public static void transaction() throws Exception{
						
		con.setAutoCommit(false);
		
		System.out.println("Enter the sender's name");
		String sender = scan.next();
		
		System.out.println("Enter the reciever's name");
		String reciever = scan.next();
		
		System.out.println("Enter the amount");
		int amount = scan.nextInt();
		
		int res1 = update(-amount,sender);
		int res2 = update(amount,reciever);
	
		if(confirm(res1,res2)) {
			
			con.commit();
			System.out.println("Transcation successfull");
		}
		else {
			
			con.rollback();
			System.out.println("Transaction unsuccessfull");
		}
	}

	public static int update(int amount,String reciever) throws Exception{
		
		stmt = con.prepareStatement(SQL_QUERY);
		stmt.setInt(1, amount);
		stmt.setString(2, reciever);
		
		int res = stmt.executeUpdate();
		return res;
	}
	
	
	public static boolean confirm(int res1, int res2){
		
		System.out.println("Do you want to confirm this Transaction [yes/no]");
		String choice = scan.next();
		return choice.equalsIgnoreCase("yes") && res1 == 1 && res2 == 1;
	}

}
