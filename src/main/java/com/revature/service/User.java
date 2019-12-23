package com.revature.service;

import java.util.*;

public class User {

	private String username;
	private String password;

	public void login() {

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter a username: ");
		setUsername(scan.nextLine());
		
		System.out.println("Enter a password: ");
		setPassword(scan.nextLine());

		scan.close();

	}

	public static void register() {
		System.out.println("This is the Registry");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
