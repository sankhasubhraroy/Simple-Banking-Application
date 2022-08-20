package com.bank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class BankDetails {
    private static final int NULL = 0;
    static Connection con = connection.connect();
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
            // Possible Exceptions
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
                // After login menu driven interface method

                int choice;
                int amount;
                int originalAc = rs.getInt("ac_no");
                int receiverAc;

                while (true) {
                    try {
                        System.out.println("\nWelcome, " + rs.getString("cname") + "\n");
                        System.out.println("1) Transfer Money");
                        System.out.println("2) Deposit Money");
                        System.out.println("3) Withdraw Money");
                        System.out.println("4) View Balance");
                        System.out.println("5) Logout");

                        System.out.print("\n     Enter Choice: ");
                        choice = Integer.parseInt(sc.readLine());

                        if (choice == 1) {
                            System.out.print("     Enter Receiver  A/c No: ");
                            receiverAc = Integer.parseInt(sc.readLine());
                            System.out.print("     Enter Amount: ");
                            amount = Integer.parseInt(sc.readLine());

                            if (BankDetails.transferMoney(originalAc, receiverAc, amount)) {
                                System.out.println("     Money Transferred Successfully!\n");
                            } else {
                                System.out.println("\n     Transaction Failed!\n");
                            }
                        } else if (choice == 2) {
                            System.out.print("     Enter Amount: ");
                            amount = Integer.parseInt(sc.readLine());

                            if (BankDetails.depositMoney(originalAc, amount)) {
                                System.out.println("     Money Deposited Successfully!\n");
                            } else {
                                System.out.println("\n     Transaction Failed!\n");
                            }
                        } else if (choice == 3) {
                            System.out.print("     Enter Amount: ");
                            amount = Integer.parseInt(sc.readLine());

                            if (BankDetails.withdrawMoney(originalAc, amount)) {
                                System.out.println("     Money Withdrawn Successfully!\n");
                            } else {
                                System.out.println("\n     Transaction Failed!\n");
                            }
                        } else if (choice == 4) {
                            BankDetails.getBalance(originalAc);
                        } else if (choice == 5) {
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
            return true;

        // Possible Exceptions
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

            //Formatted Printing
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

            // Checking Available Balance
            if (rs.next()) {
                if (rs.getInt("balance") < amount) {
                    System.out.println("\n     Insufficient Balance!");
                    return false;
                }
            }

            Statement st = con.createStatement();

            // Deducting the money
            con.setSavepoint();

            sql = "update customer set balance=balance-" + amount + " where ac_no=" + sender_ac;
            if (st.executeUpdate(sql) == 1) {
                System.out.println("\n     Amount Debited from A/c " + sender_ac);
            }

            // Adding the money
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

    public static boolean depositMoney(int acc_no, int amount) throws SQLException {
        // Account validation
        if (amount == NULL) {
            System.out.println("\n     All Field Required!");
            return false;
        }
        try {
            con.setAutoCommit(false);
            con.setSavepoint();

            // Adding the money
            Statement st = con.createStatement();
            sql = "update customer set balance=balance+" + amount + " where ac_no=" + acc_no;

            if (st.executeUpdate(sql) == 1) {
                System.out.println("\n     A/c " + acc_no + " credited by Rs. " + amount);
            }

            con.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
        }
        // return
        return false;
    }

    public static boolean withdrawMoney(int acc_no, int amount) throws SQLException {
        // Account validation
        if (amount == NULL) {
            System.out.println("\n     All Field Required!");
            return false;
        }
        try {
            con.setAutoCommit(false);

            sql = "select * from customer where ac_no=" + acc_no;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //Checking Available Balance
            if (rs.next()) {
                if (rs.getInt("balance") < amount) {
                    System.out.println("\n     Insufficient Balance!");
                    return false;
                }
            }


            // Deducting the money
            con.setSavepoint();

            Statement st = con.createStatement();
            sql = "update customer set balance=balance-" + amount + " where ac_no=" + acc_no;

            if (st.executeUpdate(sql) == 1) {
                System.out.println("\n     A/c " + acc_no + " debited by Rs. " + amount);
            }

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
