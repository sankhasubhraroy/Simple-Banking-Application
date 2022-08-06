package com.bank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class BankDetails {
    private static final int NULL = 0;
    static Connection con = connection.getConnection();
    static String sql;

    public static boolean createAccount(String name, int passCode) {
        try {
            // Account validation
            if (name == null || passCode == NULL) {
                System.out.println("     All Field Required!");
                return false;
            }
            // SQL query
            Statement st = con.createStatement();
            sql = "INSERT INTO customer(cname,balance,pass_code) values('" + name + "',1000," + passCode + ")";

            // Execution
            if (st.executeUpdate(sql) == 1) {
                System.out.println("\n     " + name + ", Your Account Created Successfully!");
                return true;
            }
            // return
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("\n     Account Creation Failed. Account Already Exists!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean loginAccount(String user_id, int password) {
        try {
            // Account validation
            if (user_id == null || password == NULL) {
                System.out.println("\n     All Field Required!");
                return false;
            }
            // SQL query
            sql = "select * from customer where cname='" + user_id + "' and pass_code=" + password;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            // Execution
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

            if (rs.next()) {
                // after login menu driven interface method

                int choice;
                int amount;
                int senderAc = rs.getInt("ac_no");

                int receiveAc;
                while (true) {
                    try {
                        System.out.println("\nWelcome, " + rs.getString("cname") + "\n");
                        System.out.println("1) Transfer Money");
                        System.out.println("2) View Balance");
                        System.out.println("3) Logout");

                        System.out.print("\n     Enter Choice: ");
                        choice = Integer.parseInt(sc.readLine());
                        if (choice == 1) {
                            System.out.print("     Enter Receiver  A/c No: ");
                            receiveAc = Integer.parseInt(sc.readLine());
                            System.out.print("     Enter Amount: ");
                            amount = Integer.parseInt(sc.readLine());

                            if (BankDetails.transferMoney(senderAc, receiveAc, amount)) {
                                System.out.println("     Money Sent Successfully!\n");
                            } else {
                                System.out.println("\n     Transaction Failed!\n");
                            }
                        } else if (choice == 2) {

                            BankDetails.getBalance(senderAc);
                        } else if (choice == 3) {
                            break;
                        } else {
                            System.out.println("\n     Enter Valid input!\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return false;
            }
            // return
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("     Username Not Available!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getBalance(int acNo) {
        try {

            // SQL query
            sql = "select * from customer where ac_no=" + acNo;
            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery(sql);
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%12s %12s %12s\n", "Account No", "Name", "Balance");

            // Execution

            while (rs.next()) {
                System.out.printf("%12d %12s %10d.00\n", rs.getInt("ac_no"), rs.getString("cname"), rs.getInt("balance"));
            }
            System.out.println("-----------------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean transferMoney(int sender_ac, int receiver_ac, int amount) throws SQLException {
        // Account validation
        if (receiver_ac == NULL || amount == NULL) {
            System.out.println("\n     All Field Required!");
            return false;
        }
        try {
            con.setAutoCommit(false);
            sql = "select * from customer where ac_no=" + sender_ac;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getInt("balance") < amount) {
                    System.out.println("\n     Insufficient Balance!");
                    return false;
                }
            }

            Statement st = con.createStatement();

            // debit
            con.setSavepoint();

            sql = "update customer set balance=balance-" + amount + " where ac_no=" + sender_ac;
            if (st.executeUpdate(sql) == 1) {
                System.out.println("\n     Amount Debited!");
            }

            // credit
            sql = "update customer set balance=balance+" + amount + " where ac_no=" + receiver_ac;
            st.executeUpdate(sql);

            con.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        }
        // return
        return false;
    }
}
