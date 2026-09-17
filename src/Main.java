import models.Account;
import models.User;
import services.AccountService;
import services.CustomerService;
import services.LoginService;
import services.TransactionService;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import utils.CLITheme;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.ansi;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        CLITheme.showLogo();

        Scanner scanner = new Scanner(System.in);

        CLITheme.showTitle("LOGIN");
        System.out.println("Please enter your credentials to continue.");

        System.out.print("Enter ID: ");
        String id = scanner.next();

        System.out.print("Enter Password: ");
        String password = scanner.next();

        LoginService loginService = new LoginService();
        CustomerService customerService = new CustomerService();

        Optional<User> loggedIn = loginService.login(id, password);

        if (loggedIn.isPresent()) {

            User user = loggedIn.get();

            CLITheme.success(
                    "Welcome " + user.getFirstName() + " " + user.getLastName()
            );

            if (user.getRole().equals("B")) {

                CLITheme.showTitle("BANKER MENU");

                CLITheme.option("1", "Add New Customer");
                CLITheme.option("2", "Exit");

                System.out.print("\nSelect an option: ");
                String option = scanner.next();

                if (option.equals("1")) {

                    CLITheme.showTitle("ADD NEW CUSTOMER");

                    String customerId = customerService.generateCustomerId();

                    System.out.print("Enter Customer First Name: ");
                    String customerFirstName = scanner.next();

                    System.out.print("Enter Customer Last Name: ");
                    String customerLastName = scanner.next();

                    System.out.print("Enter Customer Password: ");
                    String customerPassword = scanner.next();

                    System.out.println("\nChoose Customer Account Type:");

                    CLITheme.option("1", "Checking Account");
                    CLITheme.option("2", "Savings Account");
                    CLITheme.option("3", "Both");

                    System.out.print("\nSelect an option: ");
                    String accountChoice = scanner.next();

                    String accountType;
                    String checkingAccountId = "";
                    String savingsAccountId = "";

                    double checkingAccountBalance = 0;
                    double savingsAccountBalance = 0;

                    String checkingCardType = "";
                    String savingsCardType = "";

                    if (accountChoice.equals("1")) {

                        accountType = "CHECKING";
                        checkingAccountId = customerService.generateAccountId();

                        System.out.print("Enter Checking Account Balance: ");
                        checkingAccountBalance = scanner.nextDouble();

                        System.out.println("\nChoose Debit Card Type:");

                        CLITheme.option("1", "Platinum");
                        CLITheme.option("2", "Titanium");
                        CLITheme.option("3", "Mastercard");

                        System.out.print("\nSelect an option: ");
                        String cardChoice = scanner.next();

                        switch (cardChoice) {

                            case "1":
                                checkingCardType = "Platinum";
                                break;

                            case "2":
                                checkingCardType = "Titanium";
                                break;

                            case "3":
                                checkingCardType = "Mastercard";
                                break;

                            default:
                                CLITheme.error("Please select a valid debit card type.");
                                return;
                        }

                    } else if (accountChoice.equals("2")) {

                        accountType = "SAVINGS";
                        savingsAccountId = customerService.generateAccountId();

                        System.out.print("Enter Savings Account Balance: ");
                        savingsAccountBalance = scanner.nextDouble();

                        System.out.println("\nChoose Debit Card Type:");

                        CLITheme.option("1", "Platinum");
                        CLITheme.option("2", "Titanium");
                        CLITheme.option("3", "Mastercard");

                        System.out.print("\nSelect an option: ");
                        String cardChoice = scanner.next();

                        switch (cardChoice) {

                            case "1":
                                savingsCardType = "Platinum";
                                break;

                            case "2":
                                savingsCardType = "Titanium";
                                break;

                            case "3":
                                savingsCardType = "Mastercard";
                                break;

                            default:
                                CLITheme.error("Please select a valid debit card type.");
                                return;
                        }

                    } else if (accountChoice.equals("3")) {

                        accountType = "BOTH";

                        checkingAccountId = customerService.generateAccountId();

                        System.out.print("Enter Checking Account Balance: ");
                        checkingAccountBalance = scanner.nextDouble();

                        System.out.println("\nChoose Checking Debit Card Type:");

                        CLITheme.option("1", "Platinum");
                        CLITheme.option("2", "Titanium");
                        CLITheme.option("3", "Mastercard");

                        System.out.print("\nSelect an option: ");
                        String checkingCardChoice = scanner.next();

                        switch (checkingCardChoice) {

                            case "1":
                                checkingCardType = "Platinum";
                                break;

                            case "2":
                                checkingCardType = "Titanium";
                                break;

                            case "3":
                                checkingCardType = "Mastercard";
                                break;

                            default:
                                CLITheme.error("Please select a valid debit card type.");
                                return;
                        }

                        do {
                            savingsAccountId =
                                    customerService.generateAccountId();
                        } while (savingsAccountId.equals(checkingAccountId));

                        System.out.print("Enter Savings Account Balance: ");
                        savingsAccountBalance = scanner.nextDouble();

                        System.out.println("\nChoose Savings Debit Card Type:");

                        CLITheme.option("1", "Platinum");
                        CLITheme.option("2", "Titanium");
                        CLITheme.option("3", "Mastercard");

                        System.out.print("\nSelect an option: ");
                        String cardChoice = scanner.next();

                        switch (cardChoice) {

                            case "1":
                                savingsCardType = "Platinum";
                                break;

                            case "2":
                                savingsCardType = "Titanium";
                                break;

                            case "3":
                                savingsCardType = "Mastercard";
                                break;

                            default:
                                CLITheme.error("Please select a valid debit card type.");
                                return;
                        }

                    } else {

                        CLITheme.error("Please select a valid account type.");
                        return;
                    }

                    customerService.addCustomer(
                            customerId,
                            customerFirstName,
                            customerLastName,
                            customerPassword,
                            accountType,
                            checkingAccountId,
                            checkingAccountBalance,
                            checkingCardType,
                            savingsAccountId,
                            savingsAccountBalance,
                            savingsCardType
                    );

                    CLITheme.success("Customer created successfully.");

                } else if (option.equals("2")) {

                    CLITheme.goodbye(user.getFirstName());
                    System.exit(0);

                } else {

                    CLITheme.error("Invalid option. Please try again.");
                }

            } else if (user.getRole().equals("C")) {

                AccountService accountService = new AccountService();

                ArrayList<Account> accounts =
                        accountService.getAccounts(user.getId());

                if (accounts.isEmpty()) {

                    CLITheme.error("No accounts were found for your profile.");
                    return;
                }

                Account selectedAccount;

                if (accounts.size() == 1) {

                    selectedAccount = accounts.get(0);

                } else {

                    CLITheme.showTitle("SELECT ACCOUNT");

                    CLITheme.option("1", "Checking Account");
                    CLITheme.option("2", "Savings Account");

                    System.out.print("\nSelect an option: ");
                    String selectAccountOption = scanner.next();

                    if (selectAccountOption.equals("1")) {

                        selectedAccount = accounts.get(0);

                    } else if (selectAccountOption.equals("2")) {

                        selectedAccount = accounts.get(1);

                    } else {

                        CLITheme.error("Invalid option. Please try again.");
                        return;
                    }
                }

                CLITheme.showTitle("CUSTOMER MENU");

                CLITheme.option("1", "Deposit");
                CLITheme.option("2", "Withdraw");
                CLITheme.option("3", "Transfer");
                CLITheme.option("4", "Change Password");
                CLITheme.option("5", "View Transaction History");
                CLITheme.option("6", "View Account Statement");
                CLITheme.option("7", "Exit");

                System.out.print("\nSelect an option: ");
                String transactionOption = scanner.next();

                if (transactionOption.equals("1")) {

                    CLITheme.showTitle("DEPOSIT");

                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();

                    accountService.deposit(
                            selectedAccount.getAccountId(),
                            depositAmount
                    );

                } else if (transactionOption.equals("2")) {

                    CLITheme.showTitle("WITHDRAW");

                    System.out.print("Enter withdraw amount: ");
                    double withdrawAmount = scanner.nextDouble();

                    accountService.withdraw(
                            selectedAccount.getAccountId(),
                            withdrawAmount
                    );

                } else if (transactionOption.equals("3")) {

                    CLITheme.showTitle("TRANSFER");

                    CLITheme.option("1", "Transfer to my other account");
                    CLITheme.option("2", "Transfer to another customer account");
                    CLITheme.option("3", "Exit");

                    System.out.print("\nSelect an option: ");
                    String transferAccountChoice = scanner.next();

                    if (transferAccountChoice.equals("1")) {

                        if (accounts.size() < 2) {

                            CLITheme.error("You don't have another account.");
                            return;
                        }

                        Account toAccount;

                        if (selectedAccount == accounts.get(0)) {

                            toAccount = accounts.get(1);

                        } else {

                            toAccount = accounts.get(0);
                        }

                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();

                        accountService.transfer(
                                selectedAccount.getAccountId(),
                                toAccount.getAccountId(),
                                transferAmount
                        );

                    } else if (transferAccountChoice.equals("2")) {

                        System.out.print("Enter the receiver account ID: ");
                        String receiverAccountId = scanner.next();

                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();

                        accountService.transfer(
                                selectedAccount.getAccountId(),
                                receiverAccountId,
                                transferAmount
                        );

                    } else if (transferAccountChoice.equals("3")) {

                        CLITheme.goodbye(user.getFirstName());

                    } else {

                        CLITheme.error("Invalid option. Please try again.");
                        return;
                    }

                } else if (transactionOption.equals("4")) {

                    CLITheme.showTitle("CHANGE PASSWORD");

                    System.out.print("Enter new password: ");
                    String newPassword = scanner.next();

                    System.out.print("Confirm password: ");
                    String confirmPassword = scanner.next();

                    if (newPassword.equals(confirmPassword)) {

                        customerService.changePassword(
                                user.getId(),
                                newPassword
                        );

                        CLITheme.success("Password changed successfully.");

                    } else {

                        CLITheme.error("Passwords do not match.");
                    }

                } else if (transactionOption.equals("5")) {

                    TransactionService transactionService =
                            new TransactionService();

                    CLITheme.showTitle("TRANSACTION HISTORY");

                    CLITheme.option("1", "All Transactions");
                    CLITheme.option("2", "Today");
                    CLITheme.option("3", "Yesterday");
                    CLITheme.option("4", "Last Week");
                    CLITheme.option("5", "Last 7 Days");
                    CLITheme.option("6", "Last Month");
                    CLITheme.option("7", "Last 30 Days");

                    System.out.print("\nSelect a filter: ");
                    String filterOptions = scanner.next();

                    switch (filterOptions) {

                        case "1":
                            transactionService.displayTransaction(id);
                            break;

                        case "2":
                            transactionService.filterTransactions(
                                    user,
                                    "today"
                            );
                            break;

                        case "3":
                            transactionService.filterTransactions(
                                    user,
                                    "yesterday"
                            );
                            break;

                        case "4":
                            transactionService.filterTransactions(
                                    user,
                                    "lastWeek"
                            );
                            break;

                        case "5":
                            transactionService.filterTransactions(
                                    user,
                                    "last7days"
                            );
                            break;

                        case "6":
                            transactionService.filterTransactions(
                                    user,
                                    "lastMonth"
                            );
                            break;

                        case "7":
                            transactionService.filterTransactions(
                                    user,
                                    "last30days"
                            );
                            break;

                        default:
                            CLITheme.error("Invalid option. Please try again.");
                            break;
                    }

                } else if (transactionOption.equals("6")) {

                    CLITheme.showTitle("ACCOUNT STATEMENT");

                    TransactionService transactionService =
                            new TransactionService();

                    transactionService.displayAccountStatement(
                            user,
                            selectedAccount
                    );

                } else if (transactionOption.equals("7")) {

                    CLITheme.goodbye(user.getFirstName());

                } else {

                    CLITheme.error("Invalid option. Please try again.");
                    return;
                }
            }

        } else {

            CLITheme.error("Invalid ID or password.");
        }
    }
}
