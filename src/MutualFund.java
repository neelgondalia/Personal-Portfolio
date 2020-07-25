package assignment3;

import java.text.DecimalFormat;

/**
 * class that defines a mutual fund, which is an extension of investment
 *
 * @author neelg
 */
public class MutualFund extends Investment {

    private final double redemption = 45.00;
    private double bookValue;

    /**
     * constructor for mutual fund with no parameters
     */
    public MutualFund() {
        super();
    }

    /**
     * constructor for mutual fund with parameters
     *
     * @param s symbol
     * @param n name
     * @param q quantity
     * @param p price
     * @throws Exception if invalid input is passed to constructor
     */
    public MutualFund(String s, String n, String q, String p) throws Exception {
        super(s, n, q, p);
        bookValue = Integer.parseInt(q) * Double.parseDouble(p);
    }

    /**
     * computes updated book value after previous fund is bought again or sold
     *
     * @param quantity quantity of the fund
     * @param price price of the fund
     * @param command what command is using this method
     */
    @Override
    public void setNewBV(int quantity, double price, int command) {
        switch (command) {
            //buy command
            case 0:
                this.bookValue += (quantity * price);
                break;
            //sell command
            case 1:
                this.bookValue *= (((double) getQuantity() - (double) quantity) / (double) getQuantity());     //quantity represents the quantity to be sold
                break;
            //load from file
            case 2:
                this.bookValue = price;       //price parameter represents the bookvalue obtained from the file
                break;
            default:
                break;
        }
    }

    /**
     * computes gain of the mutual fund
     *
     * @return gain of the fund
     */
    @Override
    public double getGain() {
        return (getQuantity() * getPrice()) - redemption - bookValue;
    }

    /**
     * computes payments received based on the quantity sold
     *
     * @param q quantity sold
     * @return payments received
     */
    @Override
    public double getPayments(int q) {
        return (q * getPrice()) - redemption;
    }

    /**
     * Prints the mutual fund
     *
     * @return String that contains all the info of the mutual fund
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return super.toString() + "Book value: " + df.format(bookValue) + "\n";
    }

    /**
     * checks if the object to be compared is the same as the mutual fund
     *
     * @param o object to be compared
     * @return true if object to be compared and the mutual fund are the same
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != getClass()) {
            return false;
        }
        return super.equals(o);
    }
}
