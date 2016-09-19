
/**
 * A customer at the bank with specified details.
 * 
 * @Nadeem Subhan 
 */

public class Customer implements Comparable<Customer>
{
    // instance variables
    private String lastName;
    private String firstName;
    private int SIN;
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    private String savingsAccountBalance;
    private String chequingAccountBalance;
    private String creditCardBalance;
    private SavingsAccount savings;
    private ChequingAccount chequing;
    private CreditCard credit;

    // constructors
    /**
     * Creates a customer with the default characteristics.
     */
    public Customer()
    {
        lastName = "";
        firstName = "";
        SIN = 0;
        birthYear = 0;
        birthMonth = 0;
        birthDay = 0;
        savingsAccountBalance = "none";
        chequingAccountBalance = "none";
        creditCardBalance = "none";

    }

    /**
     * Creates a customer with the specified characteristics.
     */
    public Customer(String lastName, String firstName, int SIN, int birthYear, int birthMonth, int birthDay,
    String savingsAccountBalance, String chequingAccountBalance, String creditCardBalance)
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.SIN = SIN;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.savingsAccountBalance = savingsAccountBalance;
        this.chequingAccountBalance = chequingAccountBalance;
        this.creditCardBalance = creditCardBalance;      

        if (!savingsAccountBalance.equals("none") )
        {
            savings = new SavingsAccount (Double.parseDouble(savingsAccountBalance));
        }

        if (!chequingAccountBalance.equals("none") )
        {
            chequing = new ChequingAccount (Double.parseDouble(chequingAccountBalance));
        }

        if (!creditCardBalance.equals("none") )
        {
            credit = new  CreditCard (Double.parseDouble(creditCardBalance));
        }

    }

    // accessors 
    /**
     * @return lastName the last name of this customer
     */
    public String getLastName()
    {
        return lastName;
    }
    
    
    /**
     * @return firstName the first name of this customer
     */

    public String getFirstName()
    {
        return firstName;
    }

    
    /**
     * @return SIN the SIN of this customer
     */
    public int getSIN()
    {
        return SIN;
    }
    

    /**
     * @return birthYear the birth year of this customer
     */
    public int getBirthYear()
    {
        return birthYear;
    }
    
    
    /**
     * @return birthMonth the birth month of this customer
     */

    public int getBirthMonth()
    {
        return birthMonth;
    }
    
    
    /**
     * @return birthDay the birth day of this customer
     */

    public int getBirthDay()
    {
        return birthDay;
    }

    
    /**
     * @return savingsAccountBalance the balance of the savings account of this customer
     */
    
    public String getSavingsAccountBalance()
    {
        return savingsAccountBalance;
    }
    
    
    /**
     * @return ChequingAccountBalance the chequing account balance of this customer
     */

    public String getChequingAccountBalance()
    {
        return chequingAccountBalance;
    }
    
    
    /**
     * @return creditCardBalance the balance of the credit card account of this customer
     */

    public String getCreditCardBalance()
    {
        return creditCardBalance;
    }
    
    
    /**
     * @return savings the savings account object of this customer
     */

    public SavingsAccount getSavingsAccount()
    {
        return savings;
    }
    
    
    /**
     * @return chequing the chequing account object of this customer
     */

    public ChequingAccount getChequingAccount()
    {
        return chequing;
    }
    
    
    /**
     * @return credit the credit card object of this customer
     */

    public CreditCard getCreditCard()
    {
        return credit;
    }

    // mutators
    
    /**
     * Sets the last name of this customer.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    
    /**
     * Sets the first name of this customer.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    
    /**
     * Sets the SIN of this customer.
     */

    public void setSIN(int SIN)
    {
        this.SIN = SIN;
    }
    
    
    /**
     * Sets the birth year of this customer.
     */

    public  void  setBirthYear(int birthYear)
    {
        this.birthYear = birthYear;
    }
    
    
    /**
     * Sets the birth month of this customer.
     */

    public  void  setBirthMonth(int birthMonth)
    {
        this.birthMonth = birthMonth;
    }
    
    
    /**
     * Sets the birth day of this customer.
     */

    public  void  setBirthDay(int birthDay)
    {
        this.birthDay = birthDay;
    }
    
    
    /**
     * Sets the savings account balance of this customer.
     */

    public  void setSavingsAccountBalance(String savingsAccountBalance)
    {
        this.savingsAccountBalance = savingsAccountBalance;

        if (!savingsAccountBalance.equals("none") )
        {
            savings.setBalance(Double.parseDouble(savingsAccountBalance));
        }

    }
    
    
    /**
     * Sets the chequing account balance of this customer.
     */

    public  void setChequingAccountBalance(String chequingAccountBalance)
    {
        this.chequingAccountBalance = chequingAccountBalance;

        if (!chequingAccountBalance.equals("none") )
        {
            chequing.setBalance(Double.parseDouble(chequingAccountBalance));
        }

    }
    
    
    /**
     * Sets the credit card balance of this customer.
     */

    public  void setCreditCardBalance(String creditCardBalance)
    {
        this.creditCardBalance = creditCardBalance;  
        
        if (!creditCardBalance.equals("none") )
        {
        credit.setBalance(Double.parseDouble(creditCardBalance));
    }
        
    }
    
    
    /**
     * Adds to the savings account activity of the savings account of this customer.
     */

    public void addSavingsActivity (String activity)
    {
        savings.addActivity(activity);
    }
    
    
    /**
     * Adds to the chequing account activity of the chequing account of this customer.
     */

    public void addChequingActivity (String activity)
    {
        chequing.addActivity(activity);
    }
    
    
    /**
     * Adds to the credit card account activity of the credit card account of this customer.
     */
    
    public void addCreditActivity (String activity)
    {
        credit.addActivity(activity);
    }
    
    
    
    /**
     * Enables the chequing account of this customer to process a cheque.
     */
    
    public void processCheque (Double amount)
    {
        chequing.processCheque(amount);
    }

    
    /**
     * Returns a string representation of this customer.
     */

    public String toString()
    {
        return"\nLast name: " + lastName +
        "\nFirst name: " + firstName +
        "\nSIN: " + SIN+
        "\nBirth year: " + birthYear+
        "\nBirth month: " + birthMonth+
        "\nBirth day: " + birthDay+
        "\nSavings account balance: " + savingsAccountBalance+
        "\nChequing account balance: " + chequingAccountBalance+
        "\nCredit card balance: " + creditCardBalance;       

    }
    
    
    /**
     * Returns an integer value positive when this customer comes before the other customer,
     * negative when this customer comes after the other customer, and zero if both customers
     * are identical.
     */

    @Override
    public int compareTo(Customer otherName) {
        if(this.lastName.compareTo(otherName.getLastName() ) == 0) {
            return this.firstName.compareTo(otherName.getFirstName() );
        }
        else {
            return this.lastName.compareTo(otherName.getLastName() );
        }
    }

}