package assignment3;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import static java.lang.Character.isDigit;
import java.text.DecimalFormat;

/**
 * Class that handles all back end operations of the program
 *
 * @author neelg
 */
public class Portfolio implements ActionListener {

    /**
     * array list with all the investments
     */
    private static ArrayList<Investment> investments;

    /**
     * hash map that contains all the keywords for search and the locations of
     * the investments in which the keywords are found
     */
    public static HashMap< String, ArrayList< Integer>> hmap;

    /**
     * window for different commands
     */
    public static Window bWin;

    /**
     * index used to navigate from one investment to the previous or next
     * investment
     */
    public static int updateIndex;

    /**
     * file name where the portfolio data is to be loaded from and saved to
     */
    public static String file;
    DecimalFormat df = new DecimalFormat("#.##");

    /**
     * main method that opens the main window to start the program
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();
        if (args.length != 1) {
            System.out.println("Error: invalid amount of arguments entered in command line! A file name was not specified.");
            return;
        }
        file = args[0];
        updateIndex = 0;
        investments = new ArrayList<>();
        hmap = new HashMap<>();
        portfolio.load(file);
        bWin = new Window();
        portfolio.save(file);
    }

    /**
     * method that listens to actions and calls appropriate methods
     *
     * @param e action command
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Quit")) {
            save(file);
            System.exit(0);
        } else if (action.equals("Sell")) {
            bWin.currentPanel.setVisible(false);
            bWin.remove(bWin.currentPanel);
            bWin.currentPanel = bWin.sellPanel();
            bWin.add(bWin.currentPanel);
            bWin.currentPanel.setVisible(true);
        } else if (action.equals("Update")) {
            bWin.currentPanel.setVisible(false);
            bWin.remove(bWin.currentPanel);
            bWin.currentPanel = bWin.updatePanel();
            bWin.add(bWin.currentPanel);
            bWin.currentPanel.setVisible(true);
            updateIndex = 0;
            bWin.st.setEditable(false);
            bWin.nt.setEditable(false);
            if (!investments.isEmpty()) {
                Investment c = investments.get(updateIndex);
                bWin.st.setText(c.getSymbol());
                bWin.nt.setText(c.getName());
                bWin.pt.setText(Double.toString(c.getPrice()));
            } else {
                bWin.mssg.append("No investments in the portfolio.\n");
            }
        } else if (action.equals("Get gain")) {
            bWin.currentPanel.setVisible(false);
            bWin.remove(bWin.currentPanel);
            bWin.currentPanel = bWin.ggPanel();
            bWin.add(bWin.currentPanel);
            bWin.currentPanel.setVisible(true);
            getGain();
        } else if (action.equals("Search")) {
            bWin.currentPanel.setVisible(false);
            bWin.remove(bWin.currentPanel);
            bWin.currentPanel = bWin.searchPanel();
            bWin.add(bWin.currentPanel);
            bWin.currentPanel.setVisible(true);
        } else if (action.equals("Buy")) {
            bWin.currentPanel.setVisible(false);
            bWin.remove(bWin.currentPanel);
            bWin.currentPanel = bWin.buyPanel();
            bWin.add(bWin.currentPanel);
            bWin.currentPanel.setVisible(true);
        } else if (action.equals("BuyBtn")) {
            buyInvestment((String) bWin.comboType.getSelectedItem());
        } else if (action.equals("SellBtn")) {
            sellInvestment();
        } else if (action.equals("ResetBtn")) {
            clearTextfields();
        } else if (action.equals("SearchBtn")) {
            bWin.mssg.setText("");
            searchInput();
        } else if (action.equals("SaveBtn")) {
            if (!investments.isEmpty()) {
                updateInvestment(updateIndex);
            }
        } else if (action.equals("PrevBtn")) {
            if (!investments.isEmpty() && updateIndex > 0) {
                updateIndex--;
                Investment c = investments.get(updateIndex);
                bWin.st.setText(c.getSymbol());
                bWin.nt.setText(c.getName());
                bWin.pt.setText(Double.toString(c.getPrice()));
            } else {
                bWin.mssg.append("No previous investment found.\n");
            }
        } else if (action.equals("NextBtn")) {
            if (!investments.isEmpty() && updateIndex < (investments.size() - 1)) {
                updateIndex++;
                Investment c = investments.get(updateIndex);
                bWin.st.setText(c.getSymbol());
                bWin.nt.setText(c.getName());
                bWin.pt.setText(Double.toString(c.getPrice()));
            } else {
                bWin.mssg.append("No next investment found.\n");
            }
        }
    }

    /**
     * clears all the text fields in the window
     */
    public void clearTextfields() {
        bWin.st.setText("");
        bWin.nt.setText("");
        bWin.qt.setText("");
        bWin.pt.setText("");
        bWin.ggt.setText("");
        bWin.kt.setText("");
        bWin.lpt.setText("");
        bWin.hpt.setText("");
    }

    /**
     * method that gathers the search criteria from the user and passes it to
     * the search method
     */
    public void searchInput() {
        String symbol = bWin.st.getText();
        String keywords = bWin.kt.getText();
        String lower = bWin.lpt.getText();
        String higher = bWin.hpt.getText();
        double l = -1;
        double h = -1;
        if (!ifNumeric(lower)) {
            bWin.mssg.append("ERROR: INVALID INPUT FOR LOWER PRICE.\n");
            return;
        }
        if (!ifNumeric(higher)) {
            bWin.mssg.append("ERROR: INVALID INPUT FOR HIGHER PRICE.\n");
            return;
        }

        if (!("".equals(lower))) {
            l = Double.parseDouble(lower);
        }
        if (!("".equals(higher))) {
            h = Double.parseDouble(higher);
        }
        if (l != -1 && h != -1 && l > h) {
            bWin.mssg.append("The lower price is higher than the higher price, therefore they will be switched around.\n\n");
            double t = l;
            l = h;
            h = t;
        }
        search(symbol, keywords, l, h);
    }

    /**
     * method that searches for investments given the search criteria
     *
     * @param s symbol
     * @param k keywords
     * @param l low price
     * @param h high price
     */
    public void search(String s, String k, double l, double h) {
        ArrayList< Integer> range = new ArrayList<>(), symbol = new ArrayList<>(), words = new ArrayList<>();

        if (!("".equals(s))) {
            for (int i = 0; i < investments.size(); i++) {
                if (investments.get(i).getSymbol().toLowerCase().equals(s.toLowerCase())) {
                    symbol.add(i);
                }
            }
        }
        if (l != -1 || h != -1) {
            for (int i = 0; i < investments.size(); i++) {
                if (inPriceRange(investments.get(i).getPrice(), l, h)) {
                    range.add(i);
                }
            }
        }
        if (!("".equals(k))) {
            words = keywordsMatch(k);
        }

        if ("".equals(s) && "".equals(k) && l == -1 && h == -1) //No search criteria entered
        {
            if (investments.isEmpty()) {
                bWin.mssg.append("No investments are currently in your portfolio.\n");
            }
            for (int j = 0; j < investments.size(); j++) {
                bWin.mssg.append(investments.get(j).toString() + "\n");
            }
        } else if (!("".equals(s)) && "".equals(k) && l == -1 && h == -1) //only symbol entered
        {
            printList(symbol);
        } else if (("".equals(s)) && !("".equals(k)) && l == -1 && h == -1) //only keywords
        {
            printList(words);
        } else if (("".equals(s)) && "".equals(k) && (l != -1 || h != -1)) //only range
        {
            printList(range);
        } else if (("".equals(s)) && !("".equals(k)) && (l != -1 || h != -1) && words != null && range != null) { //range and keywords
            range.retainAll(words);
            printList(range);
        } else if (!("".equals(s)) && "".equals(k) && (l != -1 || h != -1) && symbol != null && range != null) { //range and symbol
            symbol.retainAll(range);
            printList(symbol);
        } else if (!("".equals(s)) && !("".equals(k)) && l == -1 && h == -1 && symbol != null && words != null) { //keywords and symbol
            symbol.retainAll(words);
            printList(symbol);
        } else if (!("".equals(s)) && !("".equals(k)) && (l != -1 || h != -1) && symbol != null && words != null && range != null) { //all three entered
            symbol.retainAll(range);
            symbol.retainAll(words);
            printList(symbol);
        }
    }

    /**
     * method that prints the investments given the indexes of the investments
     * to be printed
     *
     * @param s array list of indexes of investments to be printed
     */
    public void printList(ArrayList< Integer> s) {
        if (s != null && !s.isEmpty()) {
            for (int i = 0; i < s.size(); i++) {
                bWin.mssg.append(investments.get(s.get(i)).toString() + "\n");
            }
        } else {
            bWin.mssg.append("No search results found matching your criteria.\n");
        }
    }

    /**
     * method that finds all the matches for keywords
     *
     * @param k keywords
     * @return ArrayList of all the indexes of the matched investments given the
     * keywords
     */
    public ArrayList< Integer> keywordsMatch(String k) {
        String l = k.toLowerCase();
        String[] s = l.split(" ");
        ArrayList< Integer> get = new ArrayList<>();

        if (hmap.containsKey(s[0])) {
            get = hmap.get(s[0]);
        } else {
            return null;
        }

        if (get == null) {
            return null;
        } else {
            int i = 1;
            while (i < s.length) {
                ArrayList< Integer> get2 = hmap.get(s[i]);
                if (get2 == null) {
                    return null;
                }
                get.retainAll(get2);
                i++;
            }
        }
        if (get == null) {
            return null;
        }
        return get;
    }

    /**
     * method that checks if the investment price is in the price range provided
     * by user
     *
     * @param price price of investment
     * @param l lower limit of range
     * @param h higher limit of range
     * @return boolean value that returns true if price is in the price range
     */
    public boolean inPriceRange(double price, double l, double h) {
        if (l == -1) {
            if (price <= h) // if price is less than or equal to higher range
            {
                return true;
            }
        } else if (h == -1) {
            if (price >= l) // if price is greater than or equal to lower range
            {
                return true;
            }
        } else if (l == -1 && h == -1) {
            return false;
        } else if (l <= price && h >= price) {
            return true;
        }

        return false;
    }

    /**
     * method to buy an investment of type stock or mutual fund
     *
     * @param type type of investment
     */
    public void buyInvestment(String type) {
        String symbol = bWin.st.getText();
        String name = bWin.nt.getText();
        String quantity = bWin.qt.getText();
        String price = bWin.pt.getText();
        int copyFound = 0;
        Investment s = null;
        try {
            if ("Stock".equals(type)) {
                s = new Stock(symbol, name, quantity, price);
                copyFound = checkCopy("assignment3.Stock", symbol);
                bWin.mssg.append("You wish to purchase stock " + s.getSymbol() + "...\n");
                if (copyFound == -1) {
                    bWin.mssg.append("ERROR: MUTUAL FUND WITH THE SAME SYMBOL FOUND.\nTHEREFORE THIS STOCK WILL NOT BE ADDED TO THE PORTFOLIO.\n");
                    return;
                }
            } else if ("Mutual Fund".equals(type)) {
                s = new MutualFund(symbol, name, quantity, price);
                copyFound = checkCopy("assignment3.MutualFund", symbol);
                bWin.mssg.append("You wish to purchase mutual fund " + s.getSymbol() + "...\n");
                if (copyFound == -1) {
                    bWin.mssg.append("ERROR: STOCK WITH THE SAME SYMBOL FOUND.\nTHEREFORE THIS MUTUAL FUND WILL NOT BE ADDED TO THE PORTFOLIO.\n");
                    return;
                }
            }
            if (copyFound != -100 && copyFound != -1) {
                s = investments.get(copyFound);
                s.setPrice(price);
                s.setQuantity(Integer.toString(s.getQuantity() + Integer.parseInt(quantity)));
                s.setNewBV(Integer.parseInt(quantity), Double.parseDouble(price), 0);
                bWin.mssg.append("Previous entry of this investment found in the portfolio. Investment will be updated!\n" + s.toString() + "\n");
				clearTextfields();
                return;
            }
            hashIt(name.toLowerCase(), investments.size());
            investments.add(s);
            bWin.mssg.append(s.getSymbol() + ",bought!\n" + s.toString() + "\n");
            clearTextfields();
        } catch (Exception ex) {
            bWin.mssg.append(ex.getMessage());
        }
    }

    /**
     * method to sell an investment of type stock or mutual fund
     */
    public void sellInvestment() {
        try {
            String symbol = bWin.st.getText();
            String quantity = bWin.qt.getText();
            String price = bWin.pt.getText();

            Investment dummy = new Stock(symbol, "dummy", quantity, price);

            int index = getIndex(symbol);
            if (index == -1) {
                return;
            }
            Investment c = investments.get(index);
            if (c.getQuantity() < Integer.parseInt(quantity)) {
                bWin.mssg.append("ERROR: The quantity of " + c.getSymbol() + " you wish to sell is greater than the quantity present in the portfolio.\nThe quantity present in the portfolio is: " + c.getQuantity() + "\n");
                return;
            } else if (c.getQuantity() == Integer.parseInt(quantity)) {
                bWin.mssg.append("You are selling all quantity of " + c.getSymbol() + ". It will be removed from the portfolio.\n");
                bWin.mssg.append("Payments recieved: " + c.getPayments(Integer.parseInt(quantity)) + "\n");
                investments.remove(index);
                reHash();
                clearTextfields();
                return;
            }
            c.setNewBV(Integer.parseInt(quantity), 0, 1);
            c.setQuantity(Integer.toString(c.getQuantity() - Integer.parseInt(quantity)));
            bWin.mssg.append("Transaction successful!\nQuantity sold of " + c.getSymbol() + ": " + quantity + "   Quantity of " + c.getSymbol() + " remaining in the portfolio: " + c.getQuantity() + "\n");
            bWin.mssg.append("Payments recieved: " + c.getPayments(Integer.parseInt(quantity)) + "\n");
            clearTextfields();
        } catch (Exception ex) {
            bWin.mssg.append(ex.getMessage());
        }
    }

    /**
     * method that computes net gain of the portfolio
     */
    public void getGain() {
        double net = 0;
        for (int i = 0; i < investments.size(); i++) {
            bWin.mssg.append(investments.get(i).getSymbol() + " gain: " + df.format(investments.get(i).getGain()) + "\n");
            net += investments.get(i).getGain();
        }
        String netFinal = df.format(net);
        bWin.ggt.setText(netFinal);
    }

    /**
     * method that updates the price of an investment
     *
     * @param index where the investment is located in the array list
     */
    public void updateInvestment(int index) {
        Investment c = investments.get(index);
        try {
            c.setPrice(bWin.pt.getText());
            bWin.mssg.append("Price of " + c.getSymbol() + " is updated to " + c.getPrice() + ".\n");
            bWin.pt.setText(Double.toString(c.getPrice()));
        } catch (Exception ex) {
            bWin.mssg.append(ex.getMessage());
        }
    }

    /**
     * method that gets the index of an investment given the symbol
     *
     * @param s symbol
     * @return index where the investment is located
     */
    public int getIndex(String s) {
        String x = s.toLowerCase();
        for (int i = 0; i < investments.size(); i++) {
            if (x.equals(investments.get(i).getSymbol().toLowerCase())) {
                return i;
            }
        }
        bWin.mssg.append("The investment is not in the portfolio.\n");
        return -1;
    }

    /**
     * method that checks if a copy of an investment is found
     *
     * @param type type of investment
     * @param symbol symbol of investment
     * @return index of the previous investment with the same symbol if found
     */
    public int checkCopy(String type, String symbol) {
        for (int i = 0; i < investments.size(); i++) {
            Investment temp = investments.get(i);
            String name = temp.getClass().getCanonicalName();

            if (!name.equals(type) && symbol.equals(temp.getSymbol())) {
                return -1;
            }
            if (name.equals(type) && symbol.equals(temp.getSymbol())) {
                return i;
            }
        }
        return -100;
    }

    /**
     * method that checks if a string is numeric
     *
     * @param s string to be examined
     * @return true if string is numeric
     */
    public boolean ifNumeric(String s) {
        char[] arr;
        arr = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (!(isDigit(arr[i]))) {
                if (!(arr[i] == '.')) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * method that stores the name of an investment using hash map
     *
     * @param name name of an investment
     * @param index index of the investment in the investments array list
     */
    public void hashIt(String name, int index) {
        String[] nameSplit = name.split(" ");
        for (String nameSplit1 : nameSplit) {
            ArrayList< Integer> prev = hmap.get(nameSplit1);
            if (prev == null) {
                prev = new ArrayList<>();
                prev.add(index);
                hmap.put(nameSplit1, prev);
            } else if (!prev.contains(index)) {
                prev.add(index);
            }
        }
    }

    /**
     * method that rehashes the names of investments after an investment is
     * removed from investments array list
     */
    public void reHash() {
        hmap.clear();
        for (int i = 0; i < investments.size(); i++) {
            hashIt(investments.get(i).getName().toLowerCase(), i);
        }
    }

    /**
     * method that loads the data from a given file
     *
     * @param fileName name of the file that the data is to be loaded from
     */
    public void load(String fileName) {
        BufferedReader reader;
        investments.clear();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String symbol = reader.readLine().split(": ")[1];
                String name = reader.readLine().split(": ")[1];
                String quantity = reader.readLine().split(": ")[1];
                String price = reader.readLine().split(": ")[1];
                String bv = reader.readLine().split(": ")[1];
                Investment n = null;
                try {
                    switch (line) {
                        case "Stock":
                            n = new Stock(symbol, name, quantity, price);
                            break;
                        case "Mutual Fund":
                            n = new MutualFund(symbol, name, quantity, price);
                        default:
                            break;
                    }
                    n.setNewBV(0, Double.parseDouble(bv), 2);
                    investments.add(n);
                    line = reader.readLine();
                } catch (Exception ex) {
                    System.out.println("ERROR WHILE LOADING THE FILE...\n" + ex.getMessage());
                }
            }
            reader.close();
        } catch (IOException f) {
            System.out.println("File, " + fileName + ", not found, so it will be created!");
            save(fileName);
        }
    }

    /**
     * method that saves the data to a given file
     *
     * @param fileName name of the file that the data is saved to
     */
    public void save(String fileName) {
        BufferedWriter w;
        try {
            w = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < investments.size(); i++) {
                if (investments.get(i) instanceof Stock) {
                    w.write("Stock\n");
                } else if (investments.get(i) instanceof MutualFund) {
                    w.write("Mutual Fund\n");
                }
                w.write(investments.get(i).toString());
            }

            w.close();
        } catch (IOException f) {
            System.out.println("Failed to write to " + fileName);
        }
    }
}
