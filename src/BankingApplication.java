import java.util.Scanner;

class BankDetails {
    private String acc_no;
    private String acc_holder_name;
    private String acc_type;
    private long balance;
    Scanner sc = new Scanner(System.in);

    //method to open new account
    public void openAccount() {
        System.out.print("Enter Account No: ");
        acc_no = sc.next();
        System.out.print("Enter Account type: ");
        acc_type = sc.next();
        sc.nextLine();   //this line read the leftover and throw it away.
        System.out.print("Enter Account holder Name: ");
        acc_holder_name = sc.nextLine();
        System.out.print("Enter Balance: ");
        balance = sc.nextLong();
    }

    //method to display account details
    public void displayAccountDetails() {
        System.out.println("Name of Account holder: " + acc_holder_name);
        System.out.println("Account no.: " + acc_no);
        System.out.println("Account type: " + acc_type);
        System.out.println("Balance: " + balance);
    }

    //method to deposit money
    public void deposit() {
        long amount;
        System.out.println("Enter the amount you want to deposit: ");
        amount = sc.nextLong();
        balance = balance + amount;
    }

    //method to withdraw money
    public void withdraw() {
        long amount;
        System.out.println("Enter the amount you want to withdraw: ");
        amount = sc.nextLong();
        if (balance >= amount) {
            balance = balance - amount;
            //System.out.println("Transaction Successful");
            System.out.println("Balance after withdrawal: " + balance);
        } else {
            System.out.println("Your balance is less than " + amount + "\tTransaction failed...!!");
        }
    }

    //method to search an account number
    public boolean search(String acc_no) {
        if (this.acc_no.equals(acc_no)) {
            displayAccountDetails();
            return (true);
        }
        return (false);
    }
}

public class BankingApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //create initial accounts  
        System.out.print("How many number of customers do you want to register? ");
        int n = sc.nextInt();
        BankDetails[] customer = new BankDetails[n];
        for (int i = 0; i < customer.length; i++) {
            customer[i] = new BankDetails();
            customer[i].openAccount();
        }
        // loop runs until number 5 is not pressed to exit  
        int option;
        do {
            System.out.println("\n ---Welcome to Online Banking System---");
            System.out.println("1. Display all account details \n 2. Search by Account number\n 3. Deposit the amount \n 4. Withdraw the amount \n 5.Exit Application");
            System.out.println("Enter your choice: ");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    for (BankDetails bankDetails : customer) {
                        bankDetails.displayAccountDetails();
                    }
                    break;
                case 2:
                    System.out.print("Enter account no. you want to search: ");
                    String ac_no = sc.next();
                    boolean found = false;
                    for (BankDetails bankDetails : customer) {
                        found = bankDetails.search(ac_no);
                        if (found) {
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 3:
                    System.out.print("Enter Account no. : ");
                    ac_no = sc.next();
                    found = false;
                    for (BankDetails details : customer) {
                        found = details.search(ac_no);
                        if (found) {
                            details.deposit();
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 4:
                    System.out.print("Enter Account No : ");
                    ac_no = sc.next();
                    found = false;
                    for (BankDetails bankDetails : customer) {
                        found = bankDetails.search(ac_no);
                        if (found) {
                            bankDetails.withdraw();
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 5:
                    System.out.println("Thank You");
                    break;
            }
        } while (option != 5);
    }
}