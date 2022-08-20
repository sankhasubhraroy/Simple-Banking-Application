package com.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BankingApplication {
    public static void main(String[] args) throws IOException {

        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String user_id;
        int password;
        int choice;

        while (true) {
            System.out.println("\n---------- Welcome to Digital Banking Portal ----------\n");
            System.out.println("1) Create Account");
            System.out.println("2) Login");
            System.out.println("3) Exit");

            try {
                System.out.print("\n     Enter Input: ");
                choice = Integer.parseInt(sc.readLine());

                switch (choice) {
                    case 1:
                        try {
                            System.out.print("     Enter Unique Username: ");
                            user_id = sc.readLine();
                            System.out.print("     Enter New Password: ");
                            password = Integer.parseInt(sc.readLine());

                            if (BankDetails.createAccount(user_id, password)) {
                                System.out.println("     Kindly Login / Sign in For More Services!\n");
                            } else {
                                System.out.println("     Choose Another Username!\n");
                            }
                        } catch (Exception e) {
                            System.out.println("     Enter Valid Data. Insertion Failed!\n");
                        }
                        break;

                    case 2:
                        try {
                            System.out.print("     Enter  Username:");
                            user_id = sc.readLine();
                            System.out.print("     Enter  Password:");
                            password = Integer.parseInt(sc.readLine());

                            if (BankDetails.loginAccount(user_id, password)) {
                                System.out.println("     Logout Successfully!\n");
                            } else {
                                System.out.println("     Wrong Username or Password. Login Failed!\n");
                            }
                        } catch (Exception e) {
                            System.out.println("     Enter Valid Data. Login Failed!\n");
                        }

                        break;

                    case 3:
                        break;

                    default:
                        System.out.println("\n     Invalid Entry!\n");
                }

                if (choice == 3) {
                    System.out.println("\n\n     Exited Successfully!\n\n---------- Thank You, Visit Us Again ----------");
                    break;
                }
            } catch (Exception e) {
                System.out.println("\n     Enter Valid Entry!");
            }
        }
        sc.close();
    }
}
