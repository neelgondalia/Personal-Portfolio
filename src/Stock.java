package assignment3;

import java.text.DecimalFormat;

/**
 * class that defines a stock, which is an extension of investment
 *
 * @author neelg
 */
public class Stock extends Investment {

    private final double commission = 9.99;
    private double bookValue;

    /**
     * Stock constructor with no parameters
     */
    public Stock() {
        super();
    }

    /**
     * Stock constructor with parameters
     *
     * @param s symbol
     * @param n name
     * @param q quantity
     * @param p price
     * @throws Exception if invalid input is passed to constructor
     */
    public Stock(String s, String n, String q, String p) throws Exception {
        super(s, n, q, p);
        this.bookValue = Integer.parseInt(q) * Double.parseDouble(p) + commission;
    }
    
    /**
     * computes updated book value after previous stock is bought again or sold
     * or loaded from file
     *
     * @param quantity quantity of stock
     * @param price price of the stock
     * @param command what command is using this method
     */
    @Override
    public void setNewBV(int quantity, double price, int command) {      
        switch (command) {
            //buy command
            case 0:
                this.bookValue += (quantity * price) + commission;
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
     * computes gain of the stock
     *
     * @return gain of the stock
     */
    @Override
    public double getGain() {
        return (getQuantity() * getPrice()) - commission - bookValue;
    }

    /**
     * computes payments received depending on the quantity of stock sold
     *
     * @param q quantity of stock sold
     * @return payments received
     */
    @Override
    public double getPayments(int q) {
        return (q * getPrice()) - commission;
    }

    /**
     * prints the stock
     *
     * @return String containing all the info of stock
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return super.toString() + "Book value: " + df.format(bookValue) + "\n";
    }

    /**
     * checks if the object to be compared is the same as this stock
     *
     * @param o object to be compared
     * @return true if the object to be compared and stock are the same
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
