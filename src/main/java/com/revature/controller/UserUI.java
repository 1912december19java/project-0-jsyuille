package com.revature.controller;

import com.revature.service.*;
import java.util.*;

public class UserUI extends User {

	public void display() {

		System.out.println("Welcome! Press 1 to Login | Press 2 to Register");
		Scanner scn = new Scanner(System.in);
		int choice = scn.nextInt();
		
		switch (choice) {
		case 1:
			super.login();
			System.out.println("Hello " + super.getUsername() + "!");
			scn.close();
			break;
		case 2:
			super.register();
			scn.close();
			break;
		default:
			scn.close();
			return;
		}
	}
}
