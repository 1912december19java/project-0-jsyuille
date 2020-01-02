package com.revature;
import java.util.Scanner;
import com.revature.controller.*;
import com.revature.repository.*;
import com.revature.model.*;


/** 
 * Create an instance of your controller and launch your application.
 * 
 * Try not to have any logic at all on this class.
 */

public class Main {

	public static void main(String[] args) {
		User guest = new User();
		UserUI.display(guest);
	}
	
}
