
import java.io.*;
import java.text.*;
import java.util.*;

public class Hotel2 {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<String> menuItems = new ArrayList<String>();
    private static ArrayList<Double> menuPrices = new ArrayList<Double>();
    private static HashMap<String, String> users = new HashMap<String, String>();
    private static HashMap<String, ArrayList<String>> orders = new HashMap<String, ArrayList<String>>();
    private static final String USERS_FILE = "users.txt";

    public static void main(String[] args) {
        // Load users from file
        loadUsersFromFile();

        // Populate menu items and prices
        menuItems.add("Dosa"); menuPrices.add(32.0);
        menuItems.add("Burger"); menuPrices.add(70.0);
        menuItems.add("Pizza"); menuPrices.add(120.0);
        menuItems.add("Pepsi"); menuPrices.add(30.0);
        menuItems.add("Fries"); menuPrices.add(60.0);
        menuItems.add("Salad"); menuPrices.add(80.0);


        boolean loggedIn = false;
String currentUser = "";
while (!loggedIn) {
    System.out.println("Welcome to the Hotel Billing System");
    System.out.println("1. Login");
    System.out.println("2. Register");
    System.out.print("Enter your choice (1 or 2): ");
    int choice = input.nextInt();
    input.nextLine();  // consume the newline character

    switch (choice) {
        case 1:
            currentUser = login();
            if (currentUser != null) {
                loggedIn = true;
                System.out.println("Logged in as " + currentUser);
            } else {
                System.out.println("Invalid username or password");
            }
            break;
        case 2:
            registerUser();
            break;
        default:
            System.out.println("Invalid choice");
    }
}

// Display menu
displayMenu();


        // Place order
        ArrayList<String> customerOrder = new ArrayList<String>();
        int itemNumber = -1;  // initialize with a non-zero value
        while (itemNumber != 0) {
            System.out.print("Enter item number (0 to stop): ");
            itemNumber = input.nextInt();
            if (itemNumber == 0) {
                break;
            } else if (itemNumber < 1 || itemNumber > menuItems.size()) {
                System.out.println("Invalid item number");
            } else {
                customerOrder.add(menuItems.get(itemNumber - 1));
                System.out.println(menuItems.get(itemNumber - 1) + " added to order");
            }
        }

        // Add order to order history
        orders.put(currentUser, customerOrder);
        generateBill(currentUser, customerOrder);

       // Generate bill
    }


    private static void generateBill(String currentUser, ArrayList<String> customerOrder) {
        String hotelName = "Bhojanalya";
        String servedBy = "admin";
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        DecimalFormat df = new DecimalFormat("0.00\u20B9");
        double total = customerOrder.stream().mapToDouble(item -> menuPrices.get(menuItems.indexOf(item))).sum();
    
        String line = "+----------------------------------------+";
        String blank = "|                                        |";
        System.out.println(line);
        System.out.println("|" + centerText(hotelName, 40) + "|");
        System.out.println(line);
        System.out.println("|" + centerText(date, 40) + "|");
        System.out.println("|" + centerText("Served by: " + servedBy, 40) + "|");
        System.out.println(line);
        System.out.println("|" + centerText("Bill for " + currentUser, 40) + "|");
        System.out.println(line);
        System.out.println("| SNo| Item    | qty     | Price (₹)     |");
        System.out.println(line);
        ArrayList<String> sortedOrder = new ArrayList<>();
        for (String item : menuItems) {
            if (customerOrder.contains(item)) {
                sortedOrder.add(item);
            }
        }
    
        int serial = 1;
        String fileName = "bill" + serial + ".txt";
        while (new File(fileName).exists()) {
            serial++;
            fileName = "bill" + serial + ".txt";
        }
    
        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            writer.println(line);
            writer.println("|" + centerText(hotelName, 40) + "|");
            writer.println(line);
            writer.println("|" + centerText(date, 40) + "|");
            writer.println("|" + centerText("Served by: " + servedBy, 40) + "|");
            writer.println(line);
            writer.println("|" + centerText("Bill for " + currentUser, 40) + "|");
            writer.println(line);
    
            for (String item : sortedOrder) {
                int index = menuItems.indexOf(item);
                String serialStr = String.format("%-3d", serial);
                writer.println("| " + serialStr + String.format("%-17s", item) + "| " + String.format("%10s", df.format(menuPrices.get(index))) + "       |");
                writer.println(line);
                serial++;
            }
    
            writer.println(blank);
            writer.println("|" + centerText("Total: " + df.format(total), 40) + "|");
            writer.println(blank);
            writer.println(line);
        } catch (IOException e) {
            System.out.println("Failed to save bill to file: " + e.getMessage());
        }
    
        System.out.println(blank);
        System.out.println("|" + centerText("Total: " + df.format(total), 40) + "|");
        System.out.println(blank);
        System.out.println(line);
    }
    

    // private static void generateBill(String currentUser, ArrayList<String> customerOrder) {
    //     String hotelName = "Bhojanalya";
    //     String servedBy = "admin";
    //     String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    //     DecimalFormat df = new DecimalFormat("0.00\u20B9");
    //     double total = customerOrder.stream().mapToDouble(item -> menuPrices.get(menuItems.indexOf(item))).sum();
    
    //     String line = "+----------------------------------------+";
    //     String blank = "|                                        |";
    //     System.out.println(line);
    //     System.out.println("|" + centerText(hotelName, 40) + "|");
    //     System.out.println(line);
    //     System.out.println("|" + centerText(date, 40) + "|");
    //     System.out.println("|" + centerText("Served by: " + servedBy, 40) + "|");
    //     System.out.println(line);
    //     System.out.println("|" + centerText("Bill for " + currentUser, 40) + "|");
    //     System.out.println(line);
    
    //     ArrayList<String> sortedOrder = new ArrayList<>();
    //     for (String item : menuItems) {
    //         if (customerOrder.contains(item)) {
    //             sortedOrder.add(item);
    //         }
    //     }
    
    //     int serial = 1;
    //     try (PrintWriter out = new PrintWriter("/Users/mehrotra/Desktop/bill_final" + ".txt")) {
    //         for (String item : sortedOrder) {
    //             int index = menuItems.indexOf(item);
    //             String serialStr = String.format("%-3d", serial);
    //             out.println("| " + serialStr + String.format("%-17s", item) + "| " + String.format("%10s", df.format(menuPrices.get(index))) + "       |");
    //             out.println(line);
    //             serial++;
    //         }
    
    //         out.println(blank);
    //         out.println("|" + centerText("Total: " + df.format(total), 40) + "|");
    //         out.println(blank);
    //         out.println(line);
    //     } catch (FileNotFoundException e) {
    //         e.printStackTrace();
    //     }
    
    //     System.out.println("Bill generated successfully and saved in bill" + serial + ".txt");
    // }

    

    // private static void generateBill(String currentUser, ArrayList<String> customerOrder) {
    //     String hotelName = "Bhojanalya";
    //     String servedBy = "admin";
    //     String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    //     DecimalFormat df = new DecimalFormat("0.00\u20B9");
    //     double total = customerOrder.stream().mapToDouble(item -> menuPrices.get(menuItems.indexOf(item))).sum();
    
    //     String line = "+----------------------------------------+";
    //     String blank = "|                                        |";
    //     System.out.println(line);
    //     System.out.println("|" + centerText(hotelName, 40) + "|");
    //     System.out.println(line);
    //     System.out.println("|" + centerText(date, 40) + "|");
    //     System.out.println("|" + centerText("Served by: " + servedBy, 40) + "|");
    //     System.out.println(line);
    //     System.out.println("|" + centerText("Bill for " + currentUser, 40) + "|");
    //     System.out.println(line);
    //     System.out.println("| SNo| Item            | Price (₹)       |");
    //     System.out.println(line);
        
    
    //     ArrayList<String> sortedOrder = new ArrayList<>();
    //     for (String item : menuItems) {
    //         if (customerOrder.contains(item)) {
    //             sortedOrder.add(item);
    //         }
    //     }
    
    //     int serial = 1;
    //     for (String item : sortedOrder) {
    //         int index = menuItems.indexOf(item);
    //         String serialStr = String.format("%-3d", serial);
    //         System.out.println("| "+serialStr +"|" +String.format("%-17s", item) + "| " + String.format("%10s", df.format(menuPrices.get(index))) + "      |");
    //         System.out.println(line);
    //         serial++;
    //     }
    
    //     System.out.println(blank);
    //     System.out.println("|" + centerText("Total: " + df.format(total), 40) + "|");
    //     System.out.println(blank);
    //     System.out.println(line);
    // }
    
    private static String centerText(String text, int width) {
        int length = text.length();
        int diff = width - length;
        int pad1 = diff / 2;
        int pad2 = diff - pad1;
        return " ".repeat(pad1) + text + " ".repeat(pad2);
    }
    
    private static String login() {
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        if (isValidUser(username, password)) {
            return username;
        } else {
            return null;
        }
    }
    
    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = input.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists");
            return;
        }
        System.out.print("Enter password: ");
        String password = input.nextLine();
        users.put(username, password);
        System.out.println("Registration successful");
        saveUsersToFile();
    }
    
    private static void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                users.put(parts[0], parts[1]);
                line = reader.readLine();
            }
        } catch (IOException e) {
            // ignore and continue with empty user list
        }
    }
    
    private static void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving user data");
        }
    }

    private static void displayMenu() {
        String hotelName = "Bhojanalya";
        System.out.println("+--------------------------------------+");
        System.out.println("|              " + hotelName + "              |");
        System.out.println("+----+-----------------+---------------+");
        System.out.println("| SNo| Item            | Price (₹)     |");
        System.out.println("+----+-----------------+---------------+");
        int counter = 1;
        for (int i = 0; i < menuItems.size(); i++) {
            int spaceCount = 16 - menuItems.get(i).length();
            int priceSpaceCount = 8 - String.format("%.2f", menuPrices.get(i)).length();
            String spaces = new String(new char[spaceCount]).replace('\0', ' ');
            String priceSpaces = new String(new char[priceSpaceCount]).replace('\0', ' ');
            System.out.println("| " + String.format("%-3s", counter) + "| " + menuItems.get(i) + spaces + "| " + priceSpaces + String.format("%.2f", menuPrices.get(i)) + "\u20B9" + "     |");
            counter++;
        }
        System.out.println("+----+-----------------+---------------+");
    }
    
    
    private static boolean isValidUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}