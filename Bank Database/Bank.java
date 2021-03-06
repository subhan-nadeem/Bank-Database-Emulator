import java.io.*;
import java.util.*;
/**
 * The database of a bank.
 * 
 * @Nadeem Subhan
 */
public class Bank
{

    // class fields 
    private final static int MAXIMUM_NUMBER_OF_CUSTOMERS = 49; 
    private final static int ADD_CUSTOMER = 1;
    private final static int DELETE_CUSTOMER = 2;
    private final static int SORT_CUSTOMER_NAME = 3;
    private final static int SORT_CUSTOMER_SIN = 4;
    private final static int DISPLAY_CUSTOMER_SUMMARY = 5;
    private final static int FIND_CUSTOMER_NAME = 6;
    private final static int FIND_CUSTOMER_SIN = 7;
    private final static int QUIT = 8;    

    // instance variables
    private static int numberOfValidCustomers = 0; 
    private static int errorCounter = 0; // reset error counter // used to ensure valid data entries    
    private static Scanner sc = new Scanner(System.in);
    private static String fileName;// file name input by user
    private static File file; // filetype used to write to data file
    private static Customer[] customer = new Customer[MAXIMUM_NUMBER_OF_CUSTOMERS]; // main array of customers

    /**
     * Displays the main menu options.
     */

    public static void displayMenu()
    {
        System.out.println("\nWelcome to the VP bank.");
        System.out.println("-----------------------");
        System.out.println("Please choose an action from the following: ");
        System.out.println("    1: Add a customer");
        System.out.println("    2: Delete a customer");
        System.out.println("    3: Sort customers by last name, first name");
        System.out.println("    4: Sort customers by SIN");
        System.out.println("    5: Display customer summary (name, SIN)");
        System.out.println("    6: Find profile by last name, first name");
        System.out.println("    7: Find profile by SIN");
        System.out.println("    8: Quit");
    }

    /**
     * Executes the main menu options.
     */

    public static void executeOptions ()
    {
        displayMenu();
        int option = 0;
        // Enter option block
        errorCounter = 0; // reset error counter                    
        do
        {
            if (errorCounter == 1)
            {
                System.out.println("Invalid option number entered! Please enter a new value.");
                sc.nextLine(); // flushes string and enables user to enter new value
            } // end of if (errorCounter == 1)
            errorCounter = 0; // reset error counter
            System.out.print ("Enter your menu option: "); // prompt user for input of menu option

            try
            {
                option = sc.nextInt(); // get input from user

                if (option < 0 || option > 8)
                {
                    errorCounter = 1; // error has been detected
                }
            } // end of try

            catch (InputMismatchException e) // catch illegal datatype entered by user
            {            
                errorCounter = 1; // error has been detected
            } // end of catch

        }while (errorCounter == 1); // if error exists, prompt user for another entry

        System.out.println ("\n");

        switch (option)
        {
            case ADD_CUSTOMER:
            if (numberOfValidCustomers > MAXIMUM_NUMBER_OF_CUSTOMERS) // cannot be exceeded
            {
                System.out.println("The bank is at capacity! Additional customers cannot be added.");
                executeOptions(); // return to menu
            }
            else
            {
                addCustomer();                
                rewriteDataFile(); // refresh data file with new entries // write new customer to file
                loadCustomers(); // reload list of customers
                executeOptions(); // return to menu
            }
            break; // end of case

            case DELETE_CUSTOMER:
            deleteCustomer(); // find and delete customer
            rewriteDataFile(); // refresh data file with new entries // delete customer from file
            loadCustomers(); // reload list of customers
            executeOptions(); // return to menu // return to menu
            break; // end of case

            case SORT_CUSTOMER_NAME:
            sortCustomersByName(); // sort customer
            rewriteDataFile(); // refresh data file with new entries // confirm sort in data file
            executeOptions(); // return to menu // return to menu
            break; // end of case

            case SORT_CUSTOMER_SIN:
            sortCustomersBySIN();
            rewriteDataFile(); // refresh data file with new entries
            executeOptions(); // return to menu            
            break; // end of case

            case DISPLAY_CUSTOMER_SUMMARY:
            displayCustomerSummary();
            executeOptions(); // return to menu
            break; // end of case

            case FIND_CUSTOMER_NAME:
            findCustomerByName();
            rewriteDataFile(); // refresh data file with new entries
            executeOptions(); // return to menu
            break; // end of case

            case FIND_CUSTOMER_SIN:
            findCustomerBySIN();            
            rewriteDataFile(); // refresh data file with new entries
            executeOptions(); // return to menu
            break; // end of case

            case QUIT:
            System.out.println("Quitting program now.");
            break; // end of case

        }
    }

    /**
     * Adds a customer to the database.
     */

    public static void addCustomer()
    {
        String lastName;
        String firstName;
        int SIN = 0;
        int birthYear = 0;
        int birthMonth = 0;
        int birthDay = 0;
        String savingsAccountBalance = "";
        String chequingAccountBalance = "";
        String creditCardBalance = "";

        System.out.print("Enter new customer's last name: ");
        lastName = sc.next(); // prompt user for entry

        System.out.print("Enter new customer's first name: ");
        firstName = sc.next(); // prompt user for entry

        // Enter SIN number block
        errorCounter = 0; // reset error counter                    
        do
        {
            if (errorCounter == 1)
            {
                System.out.println("Invalid SIN entered! Please enter a new value.");
                sc.nextLine(); // flushes string and enables user to enter new value
            } // end of if (errorCounter == 1)
            errorCounter = 0; // reset error counter
            System.out.print("Enter new customer's SIN: ");

            try
            {
                SIN = sc.nextInt(); // get input from user
            } // end of try

            catch (InputMismatchException e) // catch illegal datatype entered by user
            {            
                errorCounter = 1; // error has been detected
            } // end of catch

            if (SIN <= 0)
            {
                errorCounter = 1; // error has been detected
            }

        }while (errorCounter == 1); // if error exists, prompt user for another entry

        // Enter birth year block
        errorCounter = 0; // reset error counter                    
        do
        {
            if (errorCounter == 1)
            {
                System.out.println("Invalid birth year entered! Please enter a new value.");
                sc.nextLine(); // flushes string and enables user to enter new value
            } // end of if (errorCounter == 1)
            errorCounter = 0; // reset error counter
            System.out.print("Enter new customer's birth year: ");

            try
            {
                birthYear = sc.nextInt(); // get input from user
            } // end of try

            catch (InputMismatchException e) // catch illegal datatype entered by user
            {            
                errorCounter = 1; // error has been detected
            } // end of catch

            if (birthYear <= 0 || birthYear > 2014)
            {
                errorCounter = 1; // error has been detected
            }

        }while (errorCounter == 1); // if error exists, prompt user for another entry

        // Enter birth month block
        errorCounter = 0; // reset error counter                    
        do
        {
            if (errorCounter == 1)
            {
                System.out.println("Invalid birth month entered! Please enter a new value.");
                sc.nextLine(); // flushes string and enables user to enter new value
            } // end of if (errorCounter == 1)
            errorCounter = 0; // reset error counter
            System.out.print("Enter new customer's birth month: ");

            try
            {
                birthMonth = sc.nextInt(); // get input from user
            } // end of try

            catch (InputMismatchException e) // catch illegal datatype entered by user
            {            
                errorCounter = 1; // error has been detected
            } // end of catch

            if (birthMonth <= 0 || birthMonth > 12)
            {
                errorCounter = 1; // error has been detected
            }

        }while (errorCounter == 1); // if error exists, prompt user for another entry

        // Enter birth day block
        errorCounter = 0; // reset error counter                    
        do
        {
            if (errorCounter == 1)
            {
                System.out.println("Invalid birth day entered! Please enter a new value.");
                sc.nextLine(); // flushes string and enables user to enter new value
            } // end of if (errorCounter == 1)
            errorCounter = 0; // reset error counter
            System.out.print("Enter new customer's birth day: ");

            try
            {
                birthDay = sc.nextInt(); // get input from user
            } // end of try

            catch (InputMismatchException e) // catch illegal datatype entered by user
            {            
                errorCounter = 1; // error has been detected
            } // end of catch

            if (birthDay < 1 || birthDay > 31)
            {
                errorCounter = 1; // error has been detected
            }

        }while (errorCounter == 1); // if error exists, prompt user for another entry

        // Enter savings account balance block
        errorCounter = 0; // reset error counter                    
        do
        {

            if (errorCounter == 1)
            {
                System.out.println("Invalid savings account balance entered! Please enter a new value.");
                sc.nextLine(); // flushes string and enables user to enter new value
            } // end of if (errorCounter == 1)
            errorCounter = 0; // reset error counter
            System.out.print("Enter new customer's savings account balance (or 'none'): ");

            savingsAccountBalance = sc.next(); // prompt user for entry

            if ( !savingsAccountBalance.equals("none") )
            {
                try
                {

                    Double.parseDouble(savingsAccountBalance); 
                }

                catch (NumberFormatException e) // catch illegal datatype entered by user
                {            
                    errorCounter = 1; // error has been detected
                } // end of catch
            }

        }while (errorCounter == 1); // if error exists, prompt user for another entry

        // calculate user age
        int age = getAge ( new Date(birthYear - 1900, birthMonth - 1, birthDay) );

        if (age < 18)
        {
            System.out.println("New customer is under 18 years of age;\nchequing account and credit card account not permitted.");
            chequingAccountBalance = "none";
            creditCardBalance = "none";
        }
        else
        {

            // Enter chequing account balance block
            errorCounter = 0; // reset error counter                    
            do
            {

                if (errorCounter == 1)
                {
                    System.out.println("Invalid chequing account balance entered! Please enter a new value.");
                    sc.nextLine(); // flushes string and enables user to enter new value
                } // end of if (errorCounter == 1)
                errorCounter = 0; // reset error counter
                System.out.print("Enter new customer's chequing account balance (or 'none'): ");

                chequingAccountBalance = sc.next(); // prompt user for entry

                if ( !chequingAccountBalance.equals("none") )
                {
                    try
                    {
                        Double.parseDouble(chequingAccountBalance); 
                    }

                    catch (NumberFormatException e) // catch illegal datatype entered by user
                    {            
                        errorCounter = 1; // error has been detected
                    } // end of catch
                }

            }while (errorCounter == 1); // if error exists, prompt user for another entry

            // Enter credit card account balance
            errorCounter = 0; // reset error counter                    
            do
            {

                if (errorCounter == 1)
                {
                    System.out.println("Invalid credit card account balance entered! Please enter a new value.");
                    sc.nextLine(); // flushes string and enables user to enter new value
                } // end of if (errorCounter == 1)
                errorCounter = 0; // reset error counter
                System.out.print("Enter new customer's credit card account balance (or 'none'): ");

                creditCardBalance = sc.next(); // prompt user for entry

                if ( !creditCardBalance.equals("none") )
                {
                    try
                    {
                        Double.parseDouble(creditCardBalance); 
                    }

                    catch (NumberFormatException e) // catch illegal datatype entered by user
                    {            
                        errorCounter = 1; // error has been detected
                    } // end of catch
                }

            }while (errorCounter == 1); // if error exists, prompt user for another entry

        } // end of if (age < 18)

        // add valid customer to array
        customer[numberOfValidCustomers] = new Customer (lastName, firstName, SIN, birthYear, birthMonth, birthDay, 
            savingsAccountBalance, chequingAccountBalance, creditCardBalance);
        numberOfValidCustomers++;

        System.out.println("New customer successfully added!");
    }

    /**
     * Rewrites the data file with valid customer entries.
     */

    public static void rewriteDataFile()
    {
        try
        {
            // rewrite data file     
            FileWriter writer = new FileWriter(fileName, false);

            for (int k = 0; k < numberOfValidCustomers; k++){
                writer.write(customer[k].getLastName() + "\r\n");
                writer.write(customer[k].getFirstName() + "\r\n");
                writer.write(customer[k].getSIN() + "\r\n");
                writer.write(customer[k].getBirthYear() + "\r\n");
                writer.write(customer[k].getBirthMonth() + "\r\n");
                writer.write(customer[k].getBirthDay() + "\r\n");
                writer.write(customer[k].getSavingsAccountBalance() + "\r\n");
                writer.write(customer[k].getChequingAccountBalance() + "\r\n");
                writer.write(customer[k].getCreditCardBalance() + "\r\n");      

            } // end of for (int k = 0; k < numberOfValidCustomers; k++){
            writer.close(); // end of file writing
        }

        catch (Exception e) // catch illegal datatype entered by user
        {
        }
    }

    /**
     * Prompts for a data file and checks to ensure its existence.
     */

    public static void findDataFile()
    {
        do{
            errorCounter = 0; // reset error counter

            System.out.print("Enter the name of the data file: ");
            fileName = sc.nextLine(); // flushes string and enables user to enter new value

            file = new File(fileName);  

            if (!file.isFile() )
            {
                System.out.println("File does not exist! Enter a new file name.");
                errorCounter = 1; // error has been detected
            }

        }while (errorCounter == 1); // if error exists, prompt user for another entry

    }       

    /**
     * Loads all customers saved on file and ensures validity of entries.
     */

    public static void loadCustomers ()
    {

        try
        {               
            int numberOfLines = 0; // number of lines in file
            int numberOfAllCustomers; // all customers good and bad
            String temp; // temporary string for testing

            // Initializes the data file and file readers    
            FileReader fr = new FileReader(file);   
            FileReader reading = new FileReader (fileName); 
            BufferedReader count = new BufferedReader (fr); // first reader for confirming number of lines in fle
            BufferedReader reader = new BufferedReader(reading); // second reader for sortng through all customers

            temp = count.readLine(); 

            // Calculates the number of lines in the file
            while (temp != null)
            {
                numberOfLines ++; // counts number of lines
                temp = count.readLine(); // reads the next line
            }

            // Ensures number of lines is divisible by 9
            // If lines are not divisible by 3, it subtracts the excessive amount from the total number of lines
            if (numberOfLines % 9 == 1)
            {
                numberOfLines = numberOfLines - 1;
            } // end of if (numberOfLines % 9 = 1)

            if (numberOfLines % 9 == 2)
            {
                numberOfLines = numberOfLines - 2;
            } // end of if (numberOfLines % 9 = 2)

            if (numberOfLines % 9 == 3)
            {
                numberOfLines = numberOfLines - 3;
            } // end of if (numberOfLines % 9 = 3)

            if (numberOfLines % 9 == 4)
            {
                numberOfLines = numberOfLines - 4;
            } // end of if (numberOfLines % 9 = 4)

            if (numberOfLines % 9 == 5)
            {
                numberOfLines = numberOfLines - 5;
            } // end of if (numberOfLines % 9 = 5)

            if (numberOfLines % 9 == 6)
            {
                numberOfLines = numberOfLines - 6;
            } // end of if (numberOfLines % 9 = 6)

            if (numberOfLines % 9 == 7)
            {
                numberOfLines = numberOfLines - 7;
            } // end of if (numberOfLines % 9 = 7)

            if (numberOfLines % 9 == 8)
            {
                numberOfLines = numberOfLines - 8;
            } // end of if (numberOfLines % 9 = 8)

            numberOfAllCustomers = (numberOfLines / 9); // Calculates number of all customers valid and invalid

            numberOfValidCustomers = 0;
            for (int i = 0; i < numberOfAllCustomers; i++)
            {
                errorCounter = 0; // reset error counter

                // reads the current customer's listed values                
                String lastNameTemp = reader.readLine(); 
                String firstNameTemp = reader.readLine();        
                String SINTemp = reader.readLine();
                String birthYearTemp = reader.readLine();
                String birthMonthTemp = reader.readLine();
                String birthDayTemp = reader.readLine();
                String savingsAccountBalanceTemp = reader.readLine();                
                String chequingAccountBalanceTemp = reader.readLine();                
                String creditCardBalanceTemp = reader.readLine();

                // checks to make sure the customer's entered values are valid 
                try
                {
                    if (Integer.parseInt(SINTemp) < 0)
                    {
                        errorCounter = 1; // error has been detected
                    }
                }

                catch (NumberFormatException ex)
                {
                    errorCounter = 1; // error has been detected
                }

                try
                {
                    if (Integer.parseInt(birthYearTemp) < 0 || Integer.parseInt(birthYearTemp) > 2014)
                    {
                        errorCounter = 1; // error has been detected
                    }
                }

                catch (NumberFormatException ex)
                {

                    errorCounter = 1; // error has been detected
                }

                try
                {
                    if (Integer.parseInt(birthMonthTemp) < 0 || Integer.parseInt(birthMonthTemp) > 12)
                    {
                        errorCounter = 1; // error has been detected
                    }
                }

                catch (NumberFormatException ex)
                {

                    System.out.println("error 5");
                    errorCounter = 1; // error has been detected
                }

                try
                {
                    if (Integer.parseInt(birthDayTemp) < 0 || Integer.parseInt(birthDayTemp) > 31)
                    {
                        errorCounter = 1; // error has been detected
                    }
                }

                catch (NumberFormatException ex)
                {
                    errorCounter = 1; // error has been detected
                }

                try
                {
                    if (!savingsAccountBalanceTemp.equals("none") )
                    {
                        Double.parseDouble(savingsAccountBalanceTemp);
                    }
                }

                catch (NumberFormatException ex)
                {
                    errorCounter = 1; // error has been detected
                }

                try
                {
                    if (!chequingAccountBalanceTemp.equals("none") )
                    {
                        Double.parseDouble(chequingAccountBalanceTemp);
                    }
                }

                catch (NumberFormatException ex)
                {
                    errorCounter = 1; // error has been detected
                }

                try
                {
                    if (!creditCardBalanceTemp.equals("none") )
                    {
                        Double.parseDouble(creditCardBalanceTemp);
                    }
                }

                catch (NumberFormatException ex)
                {
                    errorCounter = 1; // error has been detected
                }

                if (errorCounter == 0)
                {
                    customer[numberOfValidCustomers] = 
                    new Customer(lastNameTemp, firstNameTemp, Integer.parseInt(SINTemp), Integer.parseInt(birthYearTemp),
                        Integer.parseInt(birthMonthTemp), Integer.parseInt(birthDayTemp), savingsAccountBalanceTemp, 
                        chequingAccountBalanceTemp, creditCardBalanceTemp);
                    numberOfValidCustomers++;

                }// end of if (errorCounter == 0)

            } // end of for (int i = 0; i < numberOfAllCustomers; i++)

        } // end of try

        catch (InputMismatchException e) // catch illegal datatype entered by user
        {
            System.out.println ("Invalid entry! Exiting program now.");
            sc.nextLine(); // flushes string and enables user to enter new value
        } // end of catch

        catch (IOException exception)
        {
            System.out.println ("IO exception! Exiting program now.");
            sc.nextLine(); // flushes string and enables user to enter new value
        } // end of catch

        System.out.println("Number of valid customers registered: " + numberOfValidCustomers);

    }

    /**
     * Calculates the age of a customer.
     */

    public static int getAge(Date dateOfBirth) {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year   
        if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
        (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
        (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
            age--;
        }

        return age;
    }

    /**
     * Deletes a customer given a number and given all three account types do not exist.
     */
    public static boolean deleteCustomerGivenNumber (int customerNumber){

        if (customer[customerNumber].getSavingsAccountBalance().equals("none") && customer[customerNumber].getChequingAccountBalance().equals("none") &&
        customer[customerNumber].getCreditCardBalance().equals("none") ){
            System.out.println("Customer does not have any accounts! Deleting customer...");
            if (numberOfValidCustomers == 1)
            {
                customer = new Customer[MAXIMUM_NUMBER_OF_CUSTOMERS];
                numberOfValidCustomers = 0;
                System.out.println("Customer successfully deleted!");
            }
            else
            {
                for (int a = 0; a < numberOfValidCustomers; a ++)
                {                           

                    if (a >= customerNumber && customerNumber != numberOfValidCustomers){
                        customer[a] = customer [a + 1];
                    }
                }                        

                numberOfValidCustomers --;
                System.out.println("Customer successfully deleted!");
            }
            return true;
        }
        return false;

    }

    /**
     * Deletes the designated customer.
     */

    public static void deleteCustomer()
    {
        int option = 0; // option to delete
        int customerNumberToDelete = -1; // initializes the number of the customer to delete in the array
        boolean found = false; // boolean that turns true if customer is found

        System.out.println("Options (deleting a customer)");
        System.out.println("-----------------------");
        System.out.println("1. Find customer to delete by name");        
        System.out.println("2. Find customer to delete by SIN");
        System.out.println("3. Return to main menu.");

        // Enter option block
        errorCounter = 0; // reset error counter                    
        do
        {
            if (errorCounter == 1)
            {
                System.out.println("Invalid option number entered! Please enter a new value.");
                sc.nextLine(); // flushes string and enables user to enter new value
            } // end of if (errorCounter == 1)
            errorCounter = 0; // reset error counter
            System.out.print ("Enter your menu option: "); // prompt user for input of menu option

            try
            {
                option = sc.nextInt(); // get input from user

                if (option < 0 || option > 3)
                {
                    errorCounter = 1; // error has been detected
                }
            } // end of try

            catch (InputMismatchException e) // catch illegal datatype entered by user
            {            
                errorCounter = 1; // error has been detected
            } // end of catch

            switch (option)
            {
                // delete by name
                case 1:
                String lastNameDelete;
                String firstNameDelete;

                System.out.print("\nEnter the last name of the customer you wish to delete: ");
                lastNameDelete = sc.next(); // prompt user for entry               

                System.out.print("\nEnter the first name of the customer you wish to delete: ");
                firstNameDelete = sc.next(); // prompt user for entry

                for (int z = 0; z < numberOfValidCustomers; z ++)
                {
                    if ( customer[z].getLastName().equals(lastNameDelete) && customer[z].getFirstName().equals(firstNameDelete) )
                    {
                        found = true;
                        customerNumberToDelete = z;
                    }
                }                

                if (!found)
                {
                    System.out.println("Customer not found! Returning to the main menu now.");
                }

                else
                {

                    // if there is only one entry in the array, initialize new array
                    if (numberOfValidCustomers == 1)
                    {
                        customer = new Customer[MAXIMUM_NUMBER_OF_CUSTOMERS];
                        numberOfValidCustomers = 0;                        
                        System.out.println("Customer successfully deleted!");
                    }
                    else
                    {
                        for (int a = 0; a < numberOfValidCustomers; a ++)
                        {                           

                            // Shifts all entry in the array down to replace the deleted customer
                            // if the deleted customer is the last entry, reduce array size by 1
                            if (a >= customerNumberToDelete && customerNumberToDelete != numberOfValidCustomers){
                                customer[a] = customer [a + 1];
                            }
                        }                        

                        numberOfValidCustomers --;
                        System.out.println("Customer successfully deleted!");

                    } // end of if (numberOfValidCustomers == 1)

                } // end of if (!found)

                break; // end of case

                case 2:
                int SINToDelete = 0;
                errorCounter = 0; // reset error counter  

                do
                {
                    if (errorCounter == 1)
                    {
                        System.out.println("Invalid SIN entered! Please enter a new value.");
                        sc.nextLine(); // flushes string and enables user to enter new value
                    } // end of if (errorCounter == 1)
                    errorCounter = 0; // reset error counter
                    System.out.print("Enter the SIN of the customer you wish to delete: ");

                    try
                    {
                        SINToDelete = sc.nextInt(); // get input from user
                    } // end of try

                    catch (InputMismatchException e) // catch illegal datatype entered by user
                    {            
                        errorCounter = 1; // error has been detected
                    } // end of catch

                    if (SINToDelete <= 0)
                    {
                        errorCounter = 1; // error has been detected
                    }

                }while (errorCounter == 1); // if error exists, prompt user for another entry

                for (int z = 0; z < numberOfValidCustomers; z ++)
                {
                    if ( customer[z].getSIN() == SINToDelete)
                    {
                        found = true;
                        customerNumberToDelete = z;
                    }
                }                

                if (!found)
                {
                    System.out.println("Customer not found! Returning to the main menu now.");
                }

                else
                {

                    if (numberOfValidCustomers == 1)
                    {
                        customer = new Customer[MAXIMUM_NUMBER_OF_CUSTOMERS];
                        numberOfValidCustomers = 0;                        
                        System.out.println("Customer successfully deleted!");
                    }
                    else
                    {
                        for (int a = 0; a < numberOfValidCustomers; a ++)
                        {                           

                            if (a >= customerNumberToDelete && customerNumberToDelete != numberOfValidCustomers){
                                customer[a] = customer [a + 1];
                            }
                        }                        

                        numberOfValidCustomers --;
                        System.out.println("Customer successfully deleted!");

                    } // end of if (numberOfValidCustomers == 1)

                } // end of if (!found)

                break; // end of case
                case 3:
                break; // end of case
            }

        }while (errorCounter == 1); // if error exists, prompt user for another entry

    }

    /**
     * Sorts all customers in alphabetical order by last name, first name.
     */

    public static void sortCustomersByName()
    {       
        Customer temp;

        for(int i = 0; i < numberOfValidCustomers; i++)
        {
            for(int j = 1; j < (numberOfValidCustomers -i); j++)
            {
                //if customer[j-1] > customer[j], swap the elements
                if(customer[j-1].compareTo(customer[j]) > 0)
                {
                    temp = customer[j-1];
                    customer[j-1]=customer[j];
                    customer[j]=temp;
                }
            }
        }

        System.out.println("Customers successfully sorted by name!");
    }

    /**
     * Sorts all customers by SIN in ascending order.
     */

    public static void sortCustomersBySIN()
    {
        Customer temp;

        for(int i = 0; i < numberOfValidCustomers; i++)
        {
            for(int j = 1; j < (numberOfValidCustomers -i); j++)
            {
                //if customer[j-1] > customer[j], swap the elements
                if (customer[j-1].getSIN() > customer[j].getSIN() )
                {
                    temp = customer[j-1];
                    customer[j-1]=customer[j];
                    customer[j]=temp;
                }
            }
        }
        System.out.println("Customers successfully sorted by SIN!");
    }  

    /**
     * Displays the name and SIN of all customers.
     */

    public static void displayCustomerSummary()
    { 
        for (int j = 0; j < numberOfValidCustomers; j++)
        {
            System.out.println("Customer #" + (j+1) +": ");
            System.out.println("Name (last name, first name): " + customer[j].getLastName() + ", " + customer[j].getFirstName() );
            System.out.println("SIN: " + customer[j].getSIN() + "\n");
        } // end of for (int j = 0; j < numberOfValidCustomers; j++)
    }

    /**
     * Finds a customer by name.
     */

    public static void findCustomerByName()
    {
        String lastNameFind;
        String firstNameFind;
        int customerNumberFound = 0;
        boolean found = false;

        System.out.print("\nEnter the last name of the customer you wish to find: ");
        lastNameFind = sc.next(); // prompt user for entry               

        System.out.print("\nEnter the first name of the customer you wish to find: ");
        firstNameFind = sc.next(); // prompt user for entry

        for (int z = 0; z < numberOfValidCustomers; z ++)
        {
            if ( customer[z].getLastName().equals(lastNameFind) && customer[z].getFirstName().equals(firstNameFind) )
            {
                found = true;
                customerNumberFound = z;
            } // end of if ( customer[z].getLastName().equals(lastNameFind) && customer[z].getFirstName().equals(firstNameFind) )
        }

        if (!found)
        {                    
            System.out.println("Customer not found! Returning to the main menu now.");
        }
        else
        {
            executeProfileServiceMenu(customerNumberFound);
        }
    }

    /**
     * Finds a customer by SIN.
     */

    public static void findCustomerBySIN()
    {
        boolean found = false;
        int SINToFind = 0;
        errorCounter = 0; // reset error counter  
        int customerNumberFound = 0;

        do
        {
            if (errorCounter == 1)
            {
                System.out.println("Invalid SIN entered! Please enter a new value.");
                sc.nextLine(); // flushes string and enables user to enter new value
            } // end of if (errorCounter == 1)
            errorCounter = 0; // reset error counter
            System.out.print("Enter the SIN of the customer you wish to find: ");

            try
            {
                SINToFind = sc.nextInt(); // get input from user
            } // end of try

            catch (InputMismatchException e) // catch illegal datatype entered by user
            {            
                errorCounter = 1; // error has been detected
            } // end of catch

            if (SINToFind <= 0)
            {
                errorCounter = 1; // error has been detected
            }

        }while (errorCounter == 1); // if error exists, prompt user for another entry

        for (int z = 0; z < numberOfValidCustomers; z ++)
        {
            if ( customer[z].getSIN() == SINToFind)
            {
                found = true;
                customerNumberFound = z;
            }
        }                

        if (!found)
        {
            System.out.println("Customer not found! Returning to the main menu now.");
        }
        else
        {
            executeProfileServiceMenu(customerNumberFound);
        }
    }

    /**
     * Executes the profile service submenu once a profile is called.
     */

    public static void executeProfileServiceMenu(int customerNumber)
    {
        int option = 0;

        System.out.println("Accessing profile of " + customer[customerNumber].getLastName() + ", " + customer[customerNumber].getFirstName()+"...");

        // display menu
        System.out.println("PROFILE SERVICE MENU");
        System.out.println("-----------------------");
        System.out.println("     1: View account activity");
        System.out.println("     2: Deposit");
        System.out.println("     3: Withdraw");
        System.out.println("     4: Process cheque");
        System.out.println("     5: Process purchase");
        System.out.println("     6: Process payment");
        System.out.println("     7: Transfer funds");
        System.out.println("     8: Open account or issue card");
        System.out.println("     9: Cancel account or card");
        System.out.println("    10: Return to main menu");

        // Enter option block
        errorCounter = 0; // reset error counter                    
        do
        {
            if (errorCounter == 1)
            {
                System.out.println("Invalid option number entered! Please enter a new value.");
                sc.nextLine(); // flushes string and enables user to enter new value
            } // end of if (errorCounter == 1)
            errorCounter = 0; // reset error counter
            System.out.print ("Enter your menu option: "); // prompt user for input of menu option

            try
            {
                option = sc.nextInt(); // get input from user

                if (option < 0 || option > 10)
                {
                    errorCounter = 1; // error has been detected
                }
            } // end of try

            catch (InputMismatchException e) // catch illegal datatype entered by user
            {            
                errorCounter = 1; // error has been detected
            } // end of catch

        }while (errorCounter == 1); // if error exists, prompt user for another entry

        switch (option)
        {
            case 1: // view account activity
            System.out.println("Select which account's activity you would to view: ");
            System.out.println("1. Savings account");
            System.out.println("2. Chequing account");
            System.out.println("3. Credit card");

            int option2 = 0;
            // Enter option block
            errorCounter = 0; // reset error counter                    
            do
            {
                if (errorCounter == 1)
                {
                    System.out.println("Invalid option number entered! Please enter a new value.");
                    sc.nextLine(); // flushes string and enables user to enter new value
                } // end of if (errorCounter == 1)
                errorCounter = 0; // reset error counter
                System.out.print("Enter your menu option: "); // prompt user for input of menu option

                try
                {
                    option2 = sc.nextInt(); // get input from user

                    if (option2 < 0 || option2 > 3)
                    {
                        errorCounter = 1; // error has been detected
                    }
                } // end of try

                catch (InputMismatchException e) // catch illegal datatype entered by user
                {            
                    errorCounter = 1; // error has been detected
                } // end of catch

            }while (errorCounter == 1); // if error exists, prompt user for another entry           

            switch (option2)
            { 
                case 1:
                if (customer[customerNumber].getSavingsAccountBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    customer[customerNumber].getSavingsAccount().getActivity();
                    System.out.println("Returning to submenu.");
                }
                break; // end of case

                case 2:
                if (customer[customerNumber].getChequingAccountBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    customer[customerNumber].getChequingAccount().getActivity();
                    System.out.println("Returning to submenu.");
                }
                break; // end of case

                case 3:
                if (customer[customerNumber].getCreditCardBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    customer[customerNumber].getCreditCard().getActivity();
                    System.out.println("Returning to submenu.");
                }
                break; // end of case

            }
            executeProfileServiceMenu(customerNumber); // return to submenu
            break; // end of case

            case 2: // deposit
            System.out.println("Select which account you would like to deposit to: ");
            System.out.println("1. Savings account");
            System.out.println("2. Chequing account");

            int option3 = 0;
            // Enter option block
            errorCounter = 0; // reset error counter                    
            do
            {
                if (errorCounter == 1)
                {
                    System.out.println("Invalid option number entered! Please enter a new value.");
                    sc.nextLine(); // flushes string and enables user to enter new value
                } // end of if (errorCounter == 1)
                errorCounter = 0; // reset error counter
                System.out.print("Enter your menu option: "); // prompt user for input of menu option

                try
                {
                    option3 = sc.nextInt(); // get input from user

                    if (option3 < 0 || option > 2)
                    {
                        errorCounter = 1; // error has been detected
                    }
                } // end of try

                catch (InputMismatchException e) // catch illegal datatype entered by user
                {            
                    errorCounter = 1; // error has been detected
                } // end of catch

            }while (errorCounter == 1); // if error exists, prompt user for another entry   

            switch (option3){
                case 1: // savings account
                String amount = ""; // reinitialize user-input string of amount // reinitialize user-input string
                if (customer[customerNumber].getSavingsAccountBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter the amount you would like to deposit: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            if (Double.parseDouble(amount) < 0)
                            {
                                errorCounter = 1; // error has been detected
                            }
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    customer[customerNumber].setSavingsAccountBalance(String.valueOf (
                            Double.parseDouble(customer[customerNumber].getSavingsAccountBalance() ) + Double.parseDouble(amount) ) );
                    System.out.println("Deposit successful! Returning to submenu.");
                    customer[customerNumber].addSavingsActivity("Deposited $"+amount); // process transaction
                }
                break; // end of case

                case 2: // chequing account

                amount = ""; // reinitialize user-input string of amount
                if (customer[customerNumber].getChequingAccountBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter the amount you would like to deposit: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            if (Double.parseDouble(amount) < 0)
                            {
                                errorCounter = 1; // error has been detected
                            }
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    customer[customerNumber].setChequingAccountBalance(String.valueOf (
                            Double.parseDouble(customer[customerNumber].getChequingAccountBalance() ) + Double.parseDouble(amount) ) );
                    System.out.println("Deposit successful! Returning to submenu.");
                    customer[customerNumber].addChequingActivity("Deposited $"+amount); // process transaction
                }
                break; // end of case

            }
            executeProfileServiceMenu(customerNumber); // return to submenu
            break; // end of case

            case 3: // withdrawal case
            System.out.println("Select which account you would like to withdraw from: ");
            System.out.println("1. Savings account");
            System.out.println("2. Chequing account");
            System.out.println("3. Credit Card");

            int option4 = 0;
            // Enter option block
            errorCounter = 0; // reset error counter                    
            do
            {
                if (errorCounter == 1)
                {
                    System.out.println("Invalid option number entered! Please enter a new value.");
                    sc.nextLine(); // flushes string and enables user to enter new value
                } // end of if (errorCounter == 1)
                errorCounter = 0; // reset error counter
                System.out.print("Enter your menu option: "); // prompt user for input of menu option

                try
                {
                    option4 = sc.nextInt(); // get input from user

                    if (option4 < 0 || option4 > 3)
                    {
                        errorCounter = 1; // error has been detected
                    }
                } // end of try

                catch (InputMismatchException e) // catch illegal datatype entered by user
                {            
                    errorCounter = 1; // error has been detected
                } // end of catch

            }while (errorCounter == 1); // if error exists, prompt user for another entry   

            switch (option4){
                case 1: // savings account
                String amount = ""; // reinitialize user-input string of amount // reinitialize user-input string
                if (customer[customerNumber].getSavingsAccountBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter the amount you would like to withdraw: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            if (Double.parseDouble(amount) < 0)
                            {
                                errorCounter = 1; // error has been detected
                            }
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    if (Double.parseDouble(customer[customerNumber].getSavingsAccountBalance() ) - Double.parseDouble(amount) < 0){
                        System.out.println("Withdrawal exceeds account balance! Returning to submenu.");
                    }
                    else
                    {                 
                        customer[customerNumber].setSavingsAccountBalance(String.valueOf (
                                Double.parseDouble(customer[customerNumber].getSavingsAccountBalance() ) - Double.parseDouble(amount) ) );
                        System.out.println("Withdraw successful! Returning to submenu.");                        
                        customer[customerNumber].addSavingsActivity("Withdrew $"+amount); // process transaction

                    }
                }
                break; // end of case

                case 2: // chequing account

                amount = ""; // reinitialize user-input string of amount
                if (customer[customerNumber].getChequingAccountBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter the amount you would like to withdraw: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            if (Double.parseDouble(amount) < 0)
                            {
                                errorCounter = 1; // error has been detected
                            }
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    if (Double.parseDouble(customer[customerNumber].getChequingAccountBalance() ) - Double.parseDouble(amount) < 0){
                        System.out.println("Withdrawal exceeds account balance! Returning to submenu.");
                    }
                    else
                    {                 
                        customer[customerNumber].setChequingAccountBalance(String.valueOf (
                                Double.parseDouble(customer[customerNumber].getChequingAccountBalance() ) - Double.parseDouble(amount) ) );                                
                        System.out.println("Withdraw successful! Returning to submenu.");

                        customer[customerNumber].addChequingActivity("Withdrew $"+amount); // process transaction
                    }
                }
                break; // end of case

                case 3: // credit card
                amount = ""; // reinitialize user-input string of amount
                if (customer[customerNumber].getCreditCardBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter the amount you would like to withdraw: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            if (Double.parseDouble(amount) < 0)
                            {
                                errorCounter = 1; // error has been detected
                            }
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    if (Double.parseDouble(customer[customerNumber].getCreditCardBalance() ) - Double.parseDouble(amount) < 0){
                        System.out.println("Withdrawal exceeds account balance! Returning to submenu.");
                    }
                    else
                    {                 
                        customer[customerNumber].setCreditCardBalance(String.valueOf (
                                Double.parseDouble(customer[customerNumber].getCreditCardBalance() ) - Double.parseDouble(amount) ) );
                        System.out.println("Withdraw successful! Returning to submenu.");

                        customer[customerNumber].addChequingActivity("Withdrew $"+amount); // process transaction
                    }

                }
                executeProfileServiceMenu(customerNumber); // return to submenu
                break; // end of case
            }

            case 4: // process cheque
            if (customer[customerNumber].getChequingAccountBalance().equals("none"))
            {
                System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
            }
            else{

                String amount = ""; // reinitialize user-input string of amount // reinitialize user-input string
                // Enter deposit block
                errorCounter = 0; // reset error counter                    
                do
                {
                    if (errorCounter == 1)
                    {
                        System.out.println("Invalid amount entered! Please enter a new value.");
                        sc.nextLine(); // flushes string and enables user to enter new value
                    } // end of if (errorCounter == 1)
                    errorCounter = 0; // reset error counter
                    System.out.print("Enter the amount you would like to process: $"); // prompt user for entry of cash amount

                    try
                    {
                        amount = sc.next(); // prompt user for entry

                        if (Double.parseDouble(amount) < 0)
                        {
                            errorCounter = 1; // error has been detected
                        }
                    } // end of try

                    catch (InputMismatchException e) // catch illegal datatype entered by user
                    {            
                        errorCounter = 1; // error has been detected
                    } // end of catch

                }while (errorCounter == 1); // if error exists, prompt user for another entry   

                if (Double.parseDouble(customer[customerNumber].getChequingAccountBalance() ) - Double.parseDouble(amount) < 0){
                    System.out.println("Cheque exceeds account balance! Returning to submenu.");
                }
                else
                {
                    customer[customerNumber].processCheque(Double.parseDouble(amount));
                    System.out.println("Processing cheque successful! Returning to submenu.");
                    customer[customerNumber].addChequingActivity("Processed cheque with amount of $"+amount); // process transaction
                }
            }
            executeProfileServiceMenu(customerNumber); // return to submenu
            break; // end of case

            case 5: // purchase
            if (customer[customerNumber].getCreditCardBalance().equals("none"))
            {
                System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
            }
            else{

                String amount = ""; // reinitialize user-input string of amount // reinitialize user-input string
                // Enter deposit block
                errorCounter = 0; // reset error counter                    
                do
                {
                    if (errorCounter == 1)
                    {
                        System.out.println("Invalid amount entered! Please enter a new value.");
                        sc.nextLine(); // flushes string and enables user to enter new value
                    } // end of if (errorCounter == 1)
                    errorCounter = 0; // reset error counter
                    System.out.print("Enter the amount you would like to purchase: $"); // prompt user for entry of cash amount

                    try
                    {
                        amount = sc.next(); // prompt user for entry

                        if (Double.parseDouble(amount) < 0)
                        {
                            errorCounter = 1; // error has been detected
                        }
                    } // end of try

                    catch (InputMismatchException e) // catch illegal datatype entered by user
                    {            
                        errorCounter = 1; // error has been detected
                    } // end of catch

                }while (errorCounter == 1); // if error exists, prompt user for another entry   

                if (Double.parseDouble(customer[customerNumber].getCreditCardBalance() ) - Double.parseDouble(amount) < 0){
                    System.out.println("Purchase exceeds account balance! Returning to submenu.");
                }
                else
                {

                    customer[customerNumber].setCreditCardBalance(String.valueOf (
                            Double.parseDouble(customer[customerNumber].getCreditCardBalance() ) - Double.parseDouble(amount) ) );

                    System.out.println("Purchase successful! Returning to submenu.");
                    customer[customerNumber].addCreditActivity("Purchased with amount of $"+amount); // process transaction
                }
            }

            executeProfileServiceMenu (customerNumber); // return to submenu
            break; // end of case

            case 6: // payment

            System.out.println("Select which account you would like to make payment from: ");
            System.out.println("1. Savings account");
            System.out.println("2. Chequing account");

            int option5 = 0;
            // Enter option block
            errorCounter = 0; // reset error counter                    
            do
            {
                if (errorCounter == 1)
                {
                    System.out.println("Invalid option number entered! Please enter a new value.");
                    sc.nextLine(); // flushes string and enables user to enter new value
                } // end of if (errorCounter == 1)
                errorCounter = 0; // reset error counter
                System.out.print("Enter your menu option: "); // prompt user for input of menu option

                try
                {
                    option5 = sc.nextInt(); // get input from user

                    if (option5 < 0 || option5 > 2)
                    {
                        errorCounter = 1; // error has been detected
                    }
                } // end of try

                catch (InputMismatchException e) // catch illegal datatype entered by user
                {            
                    errorCounter = 1; // error has been detected
                } // end of catch

            }while (errorCounter == 1); // if error exists, prompt user for another entry   

            switch (option5){
                case 1: // savings account
                String amount = ""; // reinitialize user-input string of amount // reinitialize user-input string
                if (customer[customerNumber].getSavingsAccountBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter the amount you would like to pay: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            if (Double.parseDouble(amount) < 0)
                            {
                                errorCounter = 1; // error has been detected
                            }
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    customer[customerNumber].setSavingsAccountBalance(String.valueOf (
                            Double.parseDouble(customer[customerNumber].getSavingsAccountBalance() ) - Double.parseDouble(amount) ) );
                    System.out.println("Payment successful! Returning to submenu.");                        
                    customer[customerNumber].addSavingsActivity("Paid with amount $"+amount); // process transaction
                }
                break; // end of case

                case 2: // chequing account
                amount = ""; // reinitialize user-input string of amount
                if (customer[customerNumber].getChequingAccountBalance().equals("none")){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter the amount you would like to deposit: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            if (Double.parseDouble(amount) < 0)
                            {
                                errorCounter = 1; // error has been detected
                            }
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    customer[customerNumber].setChequingAccountBalance(String.valueOf (
                            Double.parseDouble(customer[customerNumber].getChequingAccountBalance() ) - Double.parseDouble(amount) ) );                                
                    System.out.println("Payment successful! Returning to submenu.");

                    customer[customerNumber].addChequingActivity("Paid with amount $"+amount); // process transaction
                }
                break; // end of case

            }
            executeProfileServiceMenu (customerNumber); // return to submenu
            break; // end of case

            case 7: // transfer funds
            if (customer[customerNumber].getChequingAccountBalance().equals("none") || customer[customerNumber].getSavingsAccountBalance().equals("none")){
                System.out.println("You do not have enough accounts! Returning to submenu.");
            }
            else
            {
                System.out.println("Select in which manner you'd like to transfer funds: ");
                System.out.println("1. Savings account to chequing account");
                System.out.println("2. Chequing account to savings account");

                int option6 = 0;
                // Enter option block
                errorCounter = 0; // reset error counter                    
                do
                {
                    if (errorCounter == 1)
                    {
                        System.out.println("Invalid option number entered! Please enter a new value.");
                        sc.nextLine(); // flushes string and enables user to enter new value
                    } // end of if (errorCounter == 1)
                    errorCounter = 0; // reset error counter
                    System.out.print("Enter your menu option: "); // prompt user for input of menu option

                    try
                    {
                        option6 = sc.nextInt(); // get input from user

                        if (option6 < 0 || option6 > 2)
                        {
                            errorCounter = 1; // error has been detected
                        }
                    } // end of try

                    catch (InputMismatchException e) // catch illegal datatype entered by user
                    {            
                        errorCounter = 1; // error has been detected
                    } // end of catch

                }while (errorCounter == 1); // if error exists, prompt user for another entry   

                switch (option6)
                {
                    case 1:
                    String amount = ""; // reinitialize user-input string of amount // reinitialize user-input string
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter the amount you would like to transfer: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            if (Double.parseDouble(amount) < 0)
                            {
                                errorCounter = 1; // error has been detected
                            }
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    if (Double.parseDouble(amount) > Double.parseDouble(customer[customerNumber].getSavingsAccountBalance()) ){
                        System.out.println("Transfer amount exceeds amount available in account! Returning to submenu.");
                    }
                    else
                    {

                        customer[customerNumber].setSavingsAccountBalance(String.valueOf (
                                Double.parseDouble(customer[customerNumber].getSavingsAccountBalance() ) - Double.parseDouble(amount) ) );
                        customer[customerNumber].setChequingAccountBalance(String.valueOf (
                                Double.parseDouble(customer[customerNumber].getChequingAccountBalance() ) + Double.parseDouble(amount) ) );
                        System.out.println("Transfer successful! Returning to submenu.");
                        customer[customerNumber].addSavingsActivity ("Transferred $"+amount+" to chequing.");
                        customer[customerNumber].addChequingActivity ("Received $"+amount+" from savings.");

                    }
                    break; // end of case

                    case 2:
                    amount = ""; // reinitialize user-input string of amount
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter the amount you would like to transfer: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            if (Double.parseDouble(amount) < 0)
                            {
                                errorCounter = 1; // error has been detected
                            }
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    if (Double.parseDouble(amount) > Double.parseDouble(customer[customerNumber].getChequingAccountBalance()) ){
                        System.out.println("Transfer amount exceeds amount available in account! Returning to submenu.");
                    }
                    else
                    {

                        customer[customerNumber].setSavingsAccountBalance(String.valueOf (
                                Double.parseDouble(customer[customerNumber].getSavingsAccountBalance() ) + Double.parseDouble(amount) ) );
                        customer[customerNumber].setChequingAccountBalance(String.valueOf (
                                Double.parseDouble(customer[customerNumber].getChequingAccountBalance() ) - Double.parseDouble(amount) ) );
                        System.out.println("Transfer successful! Returning to submenu.");
                        customer[customerNumber].addSavingsActivity ("Received $"+amount+" from chequing.");
                        customer[customerNumber].addChequingActivity ("Transferred $"+amount+" to savings.");

                    }

                }
            }

            executeProfileServiceMenu (customerNumber); // return to submenu
            break; // end of case

            case 8: // open account
            System.out.println("Select which account you would like to open: ");
            System.out.println("1. Savings account");
            System.out.println("2. Chequing account");
            System.out.println("3. Credit card");

            int option7 = 0;
            // Enter option block
            errorCounter = 0; // reset error counter                    
            do
            {
                if (errorCounter == 1)
                {
                    System.out.println("Invalid option number entered! Please enter a new value.");
                    sc.nextLine(); // flushes string and enables user to enter new value
                } // end of if (errorCounter == 1)
                errorCounter = 0; // reset error counter
                System.out.print("Enter your menu option: "); // prompt user for input of menu option

                try
                {
                    option7 = sc.nextInt(); // get input from user

                    if (option7 < 0 || option7 > 3)
                    {
                        errorCounter = 1; // error has been detected
                    }
                } // end of try

                catch (InputMismatchException e) // catch illegal datatype entered by user
                {            
                    errorCounter = 1; // error has been detected
                } // end of catch

            }while (errorCounter == 1); // if error exists, prompt user for another entry           

            switch (option7)
            { 
                case 1:
                if (!customer[customerNumber].getSavingsAccountBalance().equals("none") ){
                    System.out.println("Account already exists! Returning to submenu.");
                }
                else
                {
                    String amount = ""; // reinitialize user-input string of amount // reinitialize user-input string
                    // Enter deposit block
                    errorCounter = 0; // reset error counter                    
                    do
                    {
                        if (errorCounter == 1)
                        {
                            System.out.println("Invalid amount entered! Please enter a new value.");
                            sc.nextLine(); // flushes string and enables user to enter new value
                        } // end of if (errorCounter == 1)
                        errorCounter = 0; // reset error counter
                        System.out.print("Enter opening balance: $"); // prompt user for entry of cash amount

                        try
                        {
                            amount = sc.next(); // prompt user for entry

                            Double.parseDouble(amount);
                        } // end of try

                        catch (InputMismatchException e) // catch illegal datatype entered by user
                        {            
                            errorCounter = 1; // error has been detected
                        } // end of catch

                    }while (errorCounter == 1); // if error exists, prompt user for another entry   

                    System.out.println(amount);
                    customer[customerNumber].setSavingsAccountBalance(amount);
                    System.out.println("Account successfully opened. Returning to submenu.");
                }
                break; // end of case

                case 2:

                // check to see if underage
                if (getAge (new Date(customer[customerNumber].getBirthYear() - 1900, customer[customerNumber].getBirthMonth()+ 1, customer[customerNumber].getBirthDay()))
                < 18){
                    System.out.println("You must be 18 to get a chequing account. Returning to submenu.");
                }
                else
                {
                    if (!customer[customerNumber].getChequingAccountBalance().equals("none") ){
                        System.out.println("Account already exists! Returning to submenu.");
                    }
                    else
                    {
                        String amount = ""; // reinitialize user-input string of amount // reinitialize user-input string
                        // Enter deposit block
                        errorCounter = 0; // reset error counter                    
                        do
                        {
                            if (errorCounter == 1)
                            {
                                System.out.println("Invalid amount entered! Please enter a new value.");
                                sc.nextLine(); // flushes string and enables user to enter new value
                            } // end of if (errorCounter == 1)
                            errorCounter = 0; // reset error counter
                            System.out.print("Enter opening balance: $"); // prompt user for entry of cash amount

                            try
                            {
                                amount = sc.next(); // prompt user for entry
                                Double.parseDouble(amount);
                            } // end of try

                            catch (InputMismatchException e) // catch illegal datatype entered by user
                            {            
                                errorCounter = 1; // error has been detected
                            } // end of catch

                        }while (errorCounter == 1); // if error exists, prompt user for another entry   

                        customer[customerNumber].setChequingAccountBalance(amount);

                        System.out.println("Account successfully opened. Returning to submenu.");
                    }
                }
                break; // end of case

                case 3:

                // check to see if underage
                if (getAge (new Date(customer[customerNumber].getBirthYear() - 1900, customer[customerNumber].getBirthMonth()+ 1, customer[customerNumber].getBirthDay()))
                < 18){
                    System.out.println("You must be 18 to get a credit card. Returning to submenu.");
                }
                else
                {
                    if (!customer[customerNumber].getCreditCardBalance().equals("none") ){
                        System.out.println("Account already exists! Returning to submenu.");
                    }
                    else
                    {
                        String amount = ""; // reinitialize user-input string of amount // reinitialize user-input string
                        // Enter deposit block
                        errorCounter = 0; // reset error counter                    
                        do
                        {
                            if (errorCounter == 1)
                            {
                                System.out.println("Invalid amount entered! Please enter a new value.");
                                sc.nextLine(); // flushes string and enables user to enter new value
                            } // end of if (errorCounter == 1)
                            errorCounter = 0; // reset error counter
                            System.out.print("Enter opening balance: $"); // prompt user for entry of cash amount

                            try
                            {
                                amount = sc.next(); // prompt user for entry
                                Double.parseDouble(amount);
                            } // end of try

                            catch (InputMismatchException e) // catch illegal datatype entered by user
                            {            
                                errorCounter = 1; // error has been detected
                            } // end of catch

                        }while (errorCounter == 1); // if error exists, prompt user for another entry   

                        customer[customerNumber].setCreditCardBalance(amount);

                        System.out.println("Account successfully opened. Returning to submenu.");
                        break; // end of case
                    }
                }
            }
            executeProfileServiceMenu (customerNumber); // return to submenu
            break; // end of case

            case 9: // Cancel account
            System.out.println("Select which account you would like to close: ");
            System.out.println("1. Savings account");
            System.out.println("2. Chequing account");
            System.out.println("3. Credit card");

            int option8 = 0;
            // Enter option block
            errorCounter = 0; // reset error counter                    
            do
            {
                if (errorCounter == 1)
                {
                    System.out.println("Invalid option number entered! Please enter a new value.");
                    sc.nextLine(); // flushes string and enables user to enter new value
                } // end of if (errorCounter == 1)
                errorCounter = 0; // reset error counter
                System.out.print("Enter your menu option: "); // prompt user for input of menu option

                try
                {
                    option8 = sc.nextInt(); // get input from user

                    if (option8 < 0 || option8 > 3)
                    {
                        errorCounter = 1; // error has been detected
                    }
                } // end of try

                catch (InputMismatchException e) // catch illegal datatype entered by user
                {            
                    errorCounter = 1; // error has been detected
                } // end of catch

            }while (errorCounter == 1); // if error exists, prompt user for another entry           

            switch (option8)
            { 
                case 1:
                if (customer[customerNumber].getSavingsAccountBalance().equals("none") ){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    if (Double.parseDouble(customer[customerNumber].getSavingsAccountBalance()) < 0){
                        System.out.println("Balance is less than zero; account cannot be closed. Returning to submenu.");
                    }
                    else
                    {
                        customer[customerNumber].setSavingsAccountBalance("none");
                        System.out.println("Account successfully closed. Returning to submenu.");
                    }
                }
                break; // end of case

                case 2:

                if (customer[customerNumber].getChequingAccountBalance().equals("none") ){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    if (Double.parseDouble(customer[customerNumber].getChequingAccountBalance()) < 0){
                        System.out.println("Balance is less than zero; account cannot be closed. Returning to submenu.");
                    }
                    else
                    {
                        customer[customerNumber].setChequingAccountBalance("none");
                        System.out.println("Account successfully closed. Returning to submenu.");
                    }
                }

                break; // end of case

                case 3:

                if (customer[customerNumber].getCreditCardBalance().equals("none") ){
                    System.out.println("Account does not exist! Returning to submenu."); // end of if, sends user back to submenu
                }
                else
                {
                    if (Double.parseDouble(customer[customerNumber].getCreditCardBalance()) < 0){
                        System.out.println("Balance is less than zero; account cannot be closed. Returning to submenu.");
                    }
                    else
                    {
                        customer[customerNumber].setCreditCardBalance("none");
                        System.out.println("Account successfully closed. Returning to submenu.");
                    }
                }

                break; // end of case
            }
            if (deleteCustomerGivenNumber(customerNumber) == false)
            {
                executeProfileServiceMenu (customerNumber); // return to submenu
            }
            break; // end of case

            case 10:
            break; // end of case

        }
    }

    /**
     * Main method; executes the program.
     */

    public static void main(String[] argument)
    {
        findDataFile(); // prompt user for data file
        loadCustomers(); // load data file information into database
        executeOptions(); // load menu

    }
} 