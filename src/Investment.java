package assignment3;

import static java.lang.Character.isDigit;
import java.text.DecimalFormat;

/**
 * class that defines an investment
 *
 * @author neelg
 */
public abstract class Investment {

    private String symbol;
    private String name;
    private int quantity;
    private double price;
    
    /**
     * abstract method for computing get gain
     *
     * @return gain for the investment
     */
    public abstract double getGain();

    /**
     * abstract method for computing payments received
     *
     * @param q quantity sold
     * @return payments received
     */
    public abstract double getPayments(int q);

    /**
     * abstract method for updating book value
     *
     * @param quantityAdded quantity of investment
     * @param priceUpdated price of investment
     * @param command what command is using this method
     */
    public abstract void setNewBV(int quantityAdded, double priceUpdated, int command);

    /**
     * constructor for investment with no parameters
     */
    public Investment() {
        this.symbol = "";
        this.name = "";
        this.quantity = 0;
        this.price = 0;
    }

    /**
     * constructor for investment with parameters
     *
     * @param s symbol
     * @param n name
     * @param q quantity
     * @param p price
     * @throws Exception when invalid input is passed to the constructor
     */
    public Investment(String s, String n, String q, String p) throws Exception {
        if (s == null || "".equals(s)) {
            throw new Exception("ERROR: INVALID INPUT FOR SYMBOL. TRY AGAIN.\n");
        } else {
            this.symbol = s;
        }

        if (n == null || "".equals(n)) {
            throw new Exception("ERROR: INVALID INPUT FOR NAME. TRY AGAIN.\n");
        } else {
            this.name = n;
        }

        if (q == null || "".equals(q) || !ifNumeric(q, 0) || Integer.parseInt(q) <= 0) {
            throw new Exception("ERROR: INVALID INPUT FOR QUANTITY. TRY AGAIN.\n");
        } else {
            this.quantity = Integer.parseInt(q);
        }

        if (p == null || "".equals(p) || !ifNumeric(p, 1) || Double.parseDouble(p) <= 0) {
            throw new Exception("ERROR: INVALID INPUT FOR PRICE. TRY AGAIN.\n");
        } else {
            this.price = Double.parseDouble(p);
        }
    }

    /**
     * sets the symbol of the investment
     *
     * @param s symbol
     * @throws Exception when invalid input is passed for symbol
     */
    public void setSymbol(String s) throws Exception {
        if (s == null || "".equals(s)) {
            throw new Exception("ERROR: INVALID INPUT FOR SYMBOL. TRY AGAIN.\n");
        } else {
            this.symbol = s;
        }
    }

    /**
     * sets the name of the investment
     *
     * @param n name
     * @throws Exception when invalid input is passed for name
     */
    public void setName(String n) throws Exception {
        if (n == null || "".equals(n)) {
            throw new Exception("ERROR: INVALID INPUT FOR NAME. TRY AGAIN.\n");
        } else {
            this.name = n;
        }
    }

    /**
     * sets the quantity of the investment
     *
     * @param q quantity
     * @throws Exception when invalid input is passed for quantity
     */
    public void setQuantity(String q) throws Exception {
        if (q == null || "".equals(q) || !ifNumeric(q, 0) || Integer.parseInt(q) <= 0) {
            throw new Exception("ERROR: INVALID INPUT FOR QUANTITY. TRY AGAIN.\n");
        } else {
            this.quantity = Integer.parseInt(q);
        }
    }

    /**
     * sets the price of the investment
     *
     * @param p price
     * @throws Exception when invalid input is passed for price
     */
    public void setPrice(String p) throws Exception {
        if (p == null || "".equals(p) || !ifNumeric(p, 1) || Double.parseDouble(p) <= 0) {
            throw new Exception("ERROR: INVALID INPUT FOR PRICE. TRY AGAIN.\n");
        } else {
            this.price = Double.parseDouble(p);
        }
    }

    /**
     * gets the symbol of investment
     *
     * @return symbol of the investment
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * gets the name of investment
     *
     * @return name of the investment
     */
    public String getName() {
        return name;
    }

    /**
     * gets the quantity of investment
     *
     * @return quantity of the investment
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * gets the price of investment
     *
     * @return price of the investment
     */
    public double getPrice() {
        return price;
    }

    /**
     * checks if a string is numeric
     *
     * @param s string to be checked
     * @param x checking for quantity or price
     * @return true if the string is numeric
     */
    private boolean ifNumeric(String s, int x) {

        char[] arr;
        arr = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (!(isDigit(arr[i])) && x == 1) {
                if (!(arr[i] == '.')) {
                    return false;
                }
            } else if (!(isDigit(arr[i])) && x == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if the object to be compared is the same as the investment
     *
     * @param o object to be compared
     * @return true if the object to be compared and the investment are the same
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Stock) && !(o instanceof MutualFund)) {
            return false;
        }
        Investment check = (Investment) o;
        return check.getSymbol().equals(this.symbol);
    }

    /**
     * prints the investment
     *
     * @return String containing all the info of investment
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return "Symbol: " + symbol + "\n" + "Name: " + name + "\n" + "Quantity: " + quantity + "\n" + "Price: " + df.format(price) + "\n";
    }
}
