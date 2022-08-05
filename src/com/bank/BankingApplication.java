package com.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BankingApplication {
    public static void main(String[] args) //main class of bank
            throws IOException {

        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String user_id;
        int password;
        //int ac_no;
        int choice;

        while (true) {
            System.out.println("\n ->||    Welcome to InBank    ||<- \n");
            System.out.println("1)Create Account");
            System.out.println("2)Login Account");

            try {
                System.out.print("\n    Enter Input:"); //user input
                choice = Integer.parseInt(sc.readLine());

                switch (choice) {
                    case 1:
                        try {
                            System.out.print("Enter Unique UserName:");
                            user_id = sc.readLine();
                            System.out.print("Enter New Password:");
                            password = Integer.parseInt(sc.readLine());

                            if (BankDetails.createAccount(user_id, password)) {
                                System.out.println("MSG : Account Created Successfully!\n");
                            } else {
                                System.out.println("ERR : Account Creation Failed!\n");
                            }
                        } catch (Exception e) {
                            System.out.println(" ERR : Enter Valid Data::Insertion Failed!\n");
                        }
                        break;

                    case 2:
                        try {
                            System.out.print("Enter  UserName:");
                            user_id = sc.readLine();
                            System.out.print("Enter  Password:");
                            password = Integer.parseInt(sc.readLine());

                            if (BankDetails.loginAccount(user_id, password)) {
                                System.out.println("MSG : Logout Successfully!\n");
                            } else {
                                System.out.println("ERR : login Failed!\n");
                            }
                        } catch (Exception e) {
                            System.out.println(" ERR : Enter Valid Data::Login Failed!\n");
                        }

                        break;

                    default:
                        System.out.println("Invalid Entry!\n");
                }

                if (choice == 5) {
                    System.out.println("Exited Successfully!\n\n Thank You :)");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Enter Valid Entry!");
            }
        }
        sc.close();
    }
}
