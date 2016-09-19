
/**
 * A credit card account that is a part of a bank database.
 * 
 * @Nadeem Subhan
 */
public class CreditCard
{
    // instance fields
    private double balance;
    private boolean isOpen;
    private String[] accountActivity = {"", "", "", "", ""}; // initialize 5 empty slots

    
    /**
     * Constructs a credit card account with a balance.
     */
    public CreditCard (double balance)
    {
        this.balance = balance;
        isOpen = true;
    }

    /**
     * Purchase with a designated amount of money from the account.
     */
    public void purchase(double amountPurchase)
    {
        balance = balance - amountPurchase;
    }

    /**
     * Deposits a designated amount of money to the account.
     */

    public void deposit (double amountDeposit)
    {
        balance = balance + amountDeposit;
    }

    //accessors

    /**
     * Gets the balance of this account.
     */

    public double getBalance()
    {
        return balance;
    }

    /**
     * Gets the status of this account.
     */

    public boolean getStatus()
    {
        return isOpen;
    }

    /**
     * Gets the recent activity of this account.
     */

    public void getActivity()
    {
        System.out.println("Recent credit card activity: ");
        for (int i = 0; i < 5; i++)
        {
            System.out.println((i+1)+ ". "+ accountActivity[i]);
        }

    }

    // mutators
    /**
     * Sets the status of this account.
     */
    public void setStatus(boolean isOpen)
    {
        this.isOpen = isOpen;
    }

    /**
     * Sets the balance of this account.
     */

    public void setBalance(double balance)
    {
        this.balance = balance;
        isOpen = true;
    }

    /**
     * Adds recent account activity.
     */
    public void addActivity(String activity)
    {
    {
        // shift each array element down one (elimnate last value)
        accountActivity[4] = accountActivity[3];
        accountActivity[3] = accountActivity[2];
        accountActivity[2] = accountActivity[1];
        accountActivity[1] = accountActivity[0];
        accountActivity[0] = activity;

    }

    }

}