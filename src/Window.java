package assignment3;

import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GUI implementation for all the commands of the portfolio
 *
 * @author neelg
 */
public class Window extends JFrame {

    /**
     * current panel that is being displayed
     */
    protected static JPanel currentPanel;

    /**
     * main panel that contains the menu and the command panel depending on the
     * command
     */
    protected JPanel main;

    /**
     * panel that contains the top half of the window
     */
    protected JPanel top;

    /**
     * panel that contains the bottom half of the window
     */
    protected JPanel bottom;

    /**
     * panel that contains the left side of the top half of the window
     */
    protected JPanel topleft;

    /**
     * panel that contains the right side of the top half of the window
     */
    protected JPanel topright;

    /**
     * reset text fields button
     */
    protected JButton resetBtn;

    /**
     * buy investment button
     */
    protected JButton buyBtn;

    /**
     * sell investment button
     */
    protected JButton sellBtn;

    /**
     * previous investment button
     */
    protected JButton prevBtn;

    /**
     * next investment button
     */
    protected JButton nextBtn;

    /**
     * save updated investment button
     */
    protected JButton saveBtn;

    /**
     * search for investments button
     */
    protected JButton searchBtn;

    /**
     * panel title label
     */
    protected JLabel message;

    /**
     * output box title label
     */
    protected JLabel message2;

    /**
     * blank label
     */
    private JLabel dummyLabel;

    /**
     * second blank label
     */
    private JLabel dummyLabel2;

    /**
     * type of investment label
     */
    protected JLabel type;

    /**
     * symbol of investment label
     */
    protected JLabel symbol;

    /**
     * name of investment label
     */
    protected JLabel name;

    /**
     * quantity of investment label
     */
    protected JLabel quantity;

    /**
     * price of investment label
     */
    protected JLabel price;

    /**
     * total gain of investment label
     */
    protected JLabel totalGain;

    /**
     * keywords of an investment name label
     */
    protected JLabel keyWords;

    /**
     * low price of an investment price range label
     */
    protected JLabel lowP;

    /**
     * high price of an investment price range label
     */
    protected JLabel highP;

    /**
     * drop down menu to choose type of investment
     */
    protected JComboBox<String> comboType;

    /**
     * blank text field
     */
    private JTextField dummy;

    /**
     * second blank text field
     */
    private JTextField dummy2;

    /**
     * third blank text field
     */
    private JTextField dummy3;

    /**
     * symbol text field
     */
    protected JTextField st;

    /**
     * name text field
     */
    protected JTextField nt;

    /**
     * quantity text field
     */
    protected JTextField qt;

    /**
     * price text field
     */
    protected JTextField pt;

    /**
     * get gain text field
     */
    protected JTextField ggt;

    /**
     * keywords text field
     */
    protected JTextField kt;

    /**
     * low price text field
     */
    protected JTextField lpt;

    /**
     * high price text field
     */
    protected JTextField hpt;

    /**
     * text area for messages and search results and individual gains
     */
    protected JTextArea mssg;

    /**
     * scroll bars for the text area of messages
     */
    protected JScrollPane scrollOut;
    Portfolio tempP = new Portfolio();

    /**
     * constructor with no parameters
     */
    public Window() {
        super();
        setSize(1000, 900);
        setTitle("Investment Portfolio");
        setLocationRelativeTo(null);
        setButtons();
        setMinimumSize(new Dimension(1000, 900));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                tempP.save(tempP.file);
            }
        });

        currentPanel = mssgPanel();
        add(getMenu(), BorderLayout.NORTH);
        add(currentPanel);
        setVisible(true);
    }

    /**
     * sets the buttons of the frame
     */
    public void setButtons() {
        resetBtn = new JButton("Reset");
        resetBtn.setFont(resetBtn.getFont().deriveFont(20.0f));
        resetBtn.setPreferredSize(new Dimension(100,50));
        buyBtn = new JButton("Buy");
        buyBtn.setFont(buyBtn.getFont().deriveFont(20.0f));
        buyBtn.setPreferredSize(new Dimension(100,50));
        sellBtn = new JButton("Sell");
        sellBtn.setFont(sellBtn.getFont().deriveFont(20.0f));
        sellBtn.setPreferredSize(new Dimension(100,50));
        prevBtn = new JButton("Prev");
        prevBtn.setFont(prevBtn.getFont().deriveFont(20.0f));
        prevBtn.setPreferredSize(new Dimension(100,35));
        nextBtn = new JButton("Next");
        nextBtn.setFont(nextBtn.getFont().deriveFont(20.0f));
        nextBtn.setPreferredSize(new Dimension(100,35));
        saveBtn = new JButton("Save");
        saveBtn.setFont(saveBtn.getFont().deriveFont(20.0f));
        saveBtn.setPreferredSize(new Dimension(100,35));
        searchBtn = new JButton("Search");
        searchBtn.setFont(searchBtn.getFont().deriveFont(20.0f));
        searchBtn.setPreferredSize(new Dimension(100,50));

        resetBtn.setActionCommand("ResetBtn");
        buyBtn.setActionCommand("BuyBtn");
        sellBtn.setActionCommand("SellBtn");
        prevBtn.setActionCommand("PrevBtn");
        nextBtn.setActionCommand("NextBtn");
        saveBtn.setActionCommand("SaveBtn");
        searchBtn.setActionCommand("SearchBtn");

        resetBtn.addActionListener(new Portfolio());
        buyBtn.addActionListener(new Portfolio());
        sellBtn.addActionListener(new Portfolio());
        prevBtn.addActionListener(new Portfolio());
        nextBtn.addActionListener(new Portfolio());
        saveBtn.addActionListener(new Portfolio());
        searchBtn.addActionListener(new Portfolio());
    }

    /**
     * sets the labels of the frame
     *
     * @param title panel title
     * @param boxTitle output box title
     */
    public void setLabels(String title, String boxTitle) {
        dummyLabel = new JLabel("");
        dummyLabel2 = new JLabel("");
        message = new JLabel(title);
        message.setFont(message.getFont().deriveFont(20.0f));
        message2 = new JLabel(boxTitle);
        message2.setFont(message2.getFont().deriveFont(18.0f));
        type = new JLabel("Type");
        type.setFont(type.getFont().deriveFont(15.0f));
        symbol = new JLabel("Symbol");
        symbol.setFont(symbol.getFont().deriveFont(15.0f));
        name = new JLabel("Name");
        name.setFont(name.getFont().deriveFont(15.0f));
        quantity = new JLabel("Quantity");
        quantity.setFont(quantity.getFont().deriveFont(15.0f));
        price = new JLabel("Price");
        price.setFont(price.getFont().deriveFont(15.0f));
        totalGain = new JLabel("Total Gain");
        totalGain.setFont(totalGain.getFont().deriveFont(15.0f));
        keyWords = new JLabel("Name Keywords");
        keyWords.setFont(keyWords.getFont().deriveFont(15.0f));
        lowP = new JLabel("Low Price");
        lowP.setFont(lowP.getFont().deriveFont(15.0f));
        highP = new JLabel("High Price");
        highP.setFont(highP.getFont().deriveFont(15.0f));

        dummyLabel2.setLabelFor(dummy3);
        dummyLabel.setLabelFor(dummy2);
        message.setLabelFor(dummy);
        type.setLabelFor(comboType);
        symbol.setLabelFor(st);
        name.setLabelFor(nt);
        quantity.setLabelFor(qt);
        price.setLabelFor(pt);
        totalGain.setLabelFor(ggt);
        keyWords.setLabelFor(kt);
        lowP.setLabelFor(lpt);
        highP.setLabelFor(hpt);
        dummy.setVisible(false);
        dummyLabel.setVisible(false);
        dummy2.setVisible(false);
        dummyLabel2.setVisible(false);
        dummy3.setVisible(false);
    }

    /**
     * sets the text fields of the frame
     */
    public void setTextfields() {
        comboType = new JComboBox<>();
        comboType.setPreferredSize(new Dimension(200, 35));
        comboType.setFont(comboType.getFont().deriveFont(15.0f));
        comboType.addItem("Stock");
        comboType.addItem("Mutual Fund");

        st = new JTextField("");
        st.setPreferredSize(new Dimension(200, 35));
        st.setFont(st.getFont().deriveFont(15.0f));
        nt = new JTextField("");
        nt.setPreferredSize(new Dimension(200, 35));
        nt.setFont(nt.getFont().deriveFont(15.0f));
        qt = new JTextField("");
        qt.setPreferredSize(new Dimension(200, 35));
        qt.setFont(qt.getFont().deriveFont(15.0f));
        pt = new JTextField("");
        pt.setPreferredSize(new Dimension(200, 35));
        pt.setFont(pt.getFont().deriveFont(15.0f));
        ggt = new JTextField("");
        ggt.setPreferredSize(new Dimension(200, 35));
        ggt.setFont(ggt.getFont().deriveFont(15.0f));
        kt = new JTextField("");
        kt.setPreferredSize(new Dimension(200, 35));
        kt.setFont(kt.getFont().deriveFont(15.0f));
        lpt = new JTextField("");
        lpt.setPreferredSize(new Dimension(200, 35));
        lpt.setFont(lpt.getFont().deriveFont(15.0f));
        hpt = new JTextField("");
        hpt.setPreferredSize(new Dimension(200, 35));
        hpt.setFont(hpt.getFont().deriveFont(15.0f));
        dummy = new JTextField("");
        dummy2 = new JTextField("");
        dummy3 = new JTextField("");
        mssg = new JTextArea("");
        mssg.setFont(mssg.getFont().deriveFont(14.0f));
        mssg.setEditable(false);
        ggt.setEditable(false);
    }

    /**
     * creates a menu bar for the frame
     *
     * @return menu bar
     */
    public JMenuBar getMenu() {
        JMenuBar bar = new JMenuBar();
        bar.setPreferredSize(new Dimension(900, 50));
        JMenu menu = new JMenu("Commands");
        menu.setFont(menu.getFont().deriveFont(20.0f));
        JMenuItem buy = new JMenuItem("Buy");
        buy.setFont(buy.getFont().deriveFont(20.0f));
        JMenuItem sell = new JMenuItem("Sell");
        sell.setFont(sell.getFont().deriveFont(20.0f));
        JMenuItem update = new JMenuItem("Update");
        update.setFont(update.getFont().deriveFont(20.0f));
        JMenuItem gg = new JMenuItem("Get gain");
        gg.setFont(gg.getFont().deriveFont(20.0f));
        JMenuItem s = new JMenuItem("Search");
        s.setFont(s.getFont().deriveFont(20.0f));
        JMenuItem quit = new JMenuItem("Quit");
        quit.setFont(quit.getFont().deriveFont(20.0f));
        menu.add(buy);
        menu.add(sell);
        menu.add(update);
        menu.add(gg);
        menu.add(s);
        menu.add(quit);
        bar.add(menu);

        buy.setActionCommand("Buy");
        sell.setActionCommand("Sell");
        update.setActionCommand("Update");
        gg.setActionCommand("Get gain");
        s.setActionCommand("Search");
        quit.setActionCommand("Quit");

        buy.addActionListener(new Portfolio());
        sell.addActionListener(new Portfolio());
        update.addActionListener(new Portfolio());
        gg.addActionListener(new Portfolio());
        s.addActionListener(new Portfolio());
        quit.addActionListener(new Portfolio());

        return bar;
    }

    /**
     * creates a JPanel for buy command
     *
     * @return JPanel for buy
     */
    public JPanel buyPanel() {
        setTextfields();
        setLabels("Buying an Investment", "Messages");

        top = new JPanel(new GridLayout(1, 2));
        topleft = new JPanel(new GridBagLayout());
        topright = new JPanel(new GridBagLayout());
        topleft.setPreferredSize(new Dimension(600, 400));
        topright.setPreferredSize(new Dimension(300, 400));

        GridBagConstraints j = new GridBagConstraints();
        j.fill = GridBagConstraints.HORIZONTAL;
        j.insets = new Insets(20, 10, 10, 20);
        j.gridx = 0;
        j.gridy = 0;
        topleft.add(message, j);
        j.gridx = 1;
        j.gridy = 0;
        topleft.add(dummy, j);
        j.gridx = 0;
        j.gridy = 1;
        topleft.add(type, j);
        j.gridx = 1;
        j.gridy = 1;
        topleft.add(comboType, j);
        j.gridx = 0;
        j.gridy = 2;
        topleft.add(symbol, j);
        j.gridx = 1;
        j.gridy = 2;
        topleft.add(st, j);
        j.gridx = 0;
        j.gridy = 3;
        topleft.add(name, j);
        j.gridx = 1;
        j.gridy = 3;
        topleft.add(nt, j);
        j.gridx = 0;
        j.gridy = 4;
        topleft.add(quantity, j);
        j.gridx = 1;
        j.gridy = 4;
        topleft.add(qt, j);
        j.gridx = 0;
        j.gridy = 5;
        topleft.add(price, j);
        j.gridx = 1;
        j.gridy = 5;
        topleft.add(pt, j);
        top.add(topleft);

        //used to place buttons
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 30, 15, 30);
        c.gridx = 0;
        c.gridy = 1;
        topright.add(resetBtn, c);
        c.gridx = 0;
        c.gridy = 2;
        topright.add(buyBtn, c);
        top.add(topright);

        bottom = new JPanel(new GridBagLayout());
        scrollOut = new JScrollPane(mssg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollOut.setPreferredSize(new Dimension(900, 250));
        GridBagConstraints n = new GridBagConstraints();
        n.insets = new Insets(5, 25, 25, 50);
        n.gridx = 0;
        n.gridy = 1;
        bottom.add(scrollOut, n);
        n.gridx = 0;
        n.gridy = 2;
        bottom.add(message2);

        main = new JPanel(new GridLayout(2, 1));
        main.add(top);
        main.add(bottom);
        return main;
    }

    /**
     * creates a JPanel for sell command
     *
     * @return JPanel for sell
     */
    public JPanel sellPanel() {
        setTextfields();
        setLabels("Selling an Investment", "Messages");

        top = new JPanel(new GridLayout(1, 2));
        topleft = new JPanel(new GridBagLayout());
        topright = new JPanel(new GridBagLayout());
        topleft.setPreferredSize(new Dimension(600, 400));
        topright.setPreferredSize(new Dimension(300, 400));

        GridBagConstraints j = new GridBagConstraints();
        j.fill = GridBagConstraints.HORIZONTAL;
        j.gridx = 0;
        j.gridy = 0;
        topleft.add(message, j);
        j.gridx = 1;
        j.gridy = 0;
        topleft.add(dummy, j);
        j.insets = new Insets(30, 0, 30, 0);
        j.gridx = 0;
        j.gridy = 1;
        topleft.add(symbol, j);
        j.gridx = 1;
        j.gridy = 1;
        topleft.add(st, j);
        j.gridx = 0;
        j.gridy = 2;
        topleft.add(quantity, j);
        j.gridx = 1;
        j.gridy = 2;
        topleft.add(qt, j);
        j.gridx = 0;
        j.gridy = 3;
        topleft.add(price, j);
        j.gridx = 1;
        j.gridy = 3;
        topleft.add(pt, j);
        top.add(topleft);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 30, 15, 30);
        c.gridx = 0;
        c.gridy = 1;
        topright.add(resetBtn, c);
        c.gridx = 0;
        c.gridy = 2;
        topright.add(sellBtn, c);
        top.add(topright);

        bottom = new JPanel(new GridBagLayout());
        scrollOut = new JScrollPane(mssg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollOut.setPreferredSize(new Dimension(900, 250));
        GridBagConstraints n = new GridBagConstraints();
        n.insets = new Insets(5, 25, 25, 50);
        n.gridx = 0;
        n.gridy = 1;
        bottom.add(scrollOut, n);
        n.gridx = 0;
        n.gridy = 2;
        bottom.add(message2);

        main = new JPanel(new GridLayout(2, 1));
        main.add(top);
        main.add(bottom);

        return main;
    }

    /**
     * creates a JPanel for update command
     *
     * @return JPanel for update
     */
    public JPanel updatePanel() {
        setTextfields();
        setLabels("Updating Investments", "Messages");

        top = new JPanel(new GridLayout(1, 2));
        topleft = new JPanel(new GridBagLayout());
        topright = new JPanel(new GridBagLayout());
        topleft.setPreferredSize(new Dimension(600, 400));
        topright.setPreferredSize(new Dimension(300, 400));

        GridBagConstraints j = new GridBagConstraints();
        j.fill = GridBagConstraints.HORIZONTAL;
        j.gridx = 0;
        j.gridy = 0;
        topleft.add(message, j);
        j.gridx = 1;
        j.gridy = 0;
        topleft.add(dummy, j);
        j.insets = new Insets(30, 0, 30, 0);
        j.gridx = 0;
        j.gridy = 1;
        topleft.add(symbol, j);
        j.gridx = 1;
        j.gridy = 1;
        topleft.add(st, j);
        j.gridx = 0;
        j.gridy = 2;
        topleft.add(name, j);
        j.gridx = 1;
        j.gridy = 2;
        topleft.add(nt, j);
        j.gridx = 0;
        j.gridy = 3;
        topleft.add(price, j);
        j.gridx = 1;
        j.gridy = 3;
        topleft.add(pt, j);
        top.add(topleft);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 30, 15, 30);
        c.gridx = 0;
        c.gridy = 50;
        topright.add(prevBtn, c);
        c.gridx = 0;
        c.gridy = 100;
        topright.add(nextBtn, c);
        c.gridx = 0;
        c.gridy = 150;
        topright.add(saveBtn, c);
        top.add(topright);

        bottom = new JPanel(new GridBagLayout());
        scrollOut = new JScrollPane(mssg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollOut.setPreferredSize(new Dimension(900, 250));
        GridBagConstraints n = new GridBagConstraints();
        n.insets = new Insets(5, 25, 25, 50);
        n.gridx = 0;
        n.gridy = 1;
        bottom.add(scrollOut, n);
        n.gridx = 0;
        n.gridy = 2;
        bottom.add(message2);

        main = new JPanel(new GridLayout(2, 1));
        main.add(top);
        main.add(bottom);
        return main;
    }

    /**
     * creates a JPanel for get gain command
     *
     * @return JPanel for get gain
     */
    public JPanel ggPanel() {
        setTextfields();
        setLabels("Getting total gain", "Individual Gains");

        top = new JPanel(new GridLayout(1, 2));
        topleft = new JPanel(new GridLayout(6, 1));
        topright = new JPanel(new GridBagLayout());

        topleft.add(message);
        topleft.add(dummy);
        topleft.add(totalGain);
        topleft.add(ggt);
        topleft.add(dummyLabel);
        topleft.add(dummy2);
        topleft.add(dummyLabel2);
        topleft.add(dummy3);
        top.add(topleft);
        top.add(topright);

        bottom = new JPanel(new GridBagLayout());
        scrollOut = new JScrollPane(mssg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollOut.setPreferredSize(new Dimension(900, 250));
        GridBagConstraints n = new GridBagConstraints();
        n.insets = new Insets(5, 25, 25, 50);
        n.gridx = 0;
        n.gridy = 1;
        bottom.add(scrollOut, n);
        n.gridx = 0;
        n.gridy = 2;
        bottom.add(message2);

        main = new JPanel(new GridLayout(2, 1));
        main.add(top);
        main.add(bottom);
        return main;
    }

    /**
     * creates a JPanel for search command
     *
     * @return JPanel for search
     */
    public JPanel searchPanel() {
        setTextfields();
        setLabels("Searching investments", "Search results");

        top = new JPanel(new GridLayout(1, 2));
        topleft = new JPanel(new GridBagLayout());
        topright = new JPanel(new GridBagLayout());
        topleft.setPreferredSize(new Dimension(600, 400));
        topright.setPreferredSize(new Dimension(300, 400));

        GridBagConstraints j = new GridBagConstraints();
        j.fill = GridBagConstraints.HORIZONTAL;
        j.insets = new Insets(20, 10, 10, 20);
        j.gridx = 0;
        j.gridy = 0;
        topleft.add(message, j);
        j.gridx = 1;
        j.gridy = 0;
        topleft.add(dummy, j);
        j.gridx = 0;
        j.gridy = 1;
        topleft.add(symbol, j);
        j.gridx = 1;
        j.gridy = 1;
        topleft.add(st, j);
        j.gridx = 0;
        j.gridy = 2;
        topleft.add(keyWords, j);
        j.gridx = 1;
        j.gridy = 2;
        topleft.add(kt, j);
        j.gridx = 0;
        j.gridy = 3;
        topleft.add(lowP, j);
        j.gridx = 1;
        j.gridy = 3;
        topleft.add(lpt, j);
        j.gridx = 0;
        j.gridy = 4;
        topleft.add(highP, j);
        j.gridx = 1;
        j.gridy = 4;
        topleft.add(hpt, j);
        top.add(topleft);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 30, 15, 30);
        c.gridx = 0;
        c.gridy = 1;
        topright.add(resetBtn, c);
        c.gridx = 0;
        c.gridy = 2;
        topright.add(searchBtn, c);

        top.add(topright);

        bottom = new JPanel(new GridBagLayout());
        scrollOut = new JScrollPane(mssg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollOut.setPreferredSize(new Dimension(900, 250));
        GridBagConstraints n = new GridBagConstraints();
        n.insets = new Insets(5, 25, 25, 50);
        n.gridx = 0;
        n.gridy = 1;
        bottom.add(scrollOut, n);
        n.gridx = 0;
        n.gridy = 2;
        bottom.add(message2);

        main = new JPanel(new GridLayout(2, 1));
        main.add(top);
        main.add(bottom);
        return main;
    }

    /**
     * creates a JPanel for welcome message
     *
     * @return JPanel for welcome message
     */
    public JPanel mssgPanel() {
        main = new JPanel();
        JLabel introMssg = new JLabel("<html><br><br><br><br><br><br><br> Welcome to Investment Portfolio.<br><br><br>  Choose a command from the 'commands' menu to buy or sell<br>  an investment, update prices for all investments, get gain for<br>  the portfolio, search for relevant investments, or quit the<br>  program.</html>", SwingConstants.CENTER);
        introMssg.setSize(250, 250);
        introMssg.setFont(introMssg.getFont().deriveFont(25.0f));
        main.add(introMssg, BorderLayout.CENTER);
        return main;
    }
}
