package calculatorapp;

import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*; // For JMenuBar, JMenu, JMenuItem, JRadioButton, etc.

public class CalculatorApp extends JFrame  
{  
    public boolean setClear = true;  
    double number, memValue;  
    char op;  
    boolean isSimpleCalculator = true;

    String digitButtonText[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "+/-", "." };  
    String operatorButtonText[] = {"/", "sqrt", "*", "%", "-", "1/X", "+", "=" };  
    String memoryButtonText[] = {"MC", "MR", "MS", "M+" };  
    String specialButtonText[] = {"Backspc", "C", "CE" };  
  
    MyDigitButton digitButton[] = new MyDigitButton[digitButtonText.length];  
    MyOperatorButton operatorButton[] = new MyOperatorButton[operatorButtonText.length];  
    MyMemoryButton memoryButton[] = new MyMemoryButton[memoryButtonText.length];  
    MySpecialButton specialButton[] = new MySpecialButton[specialButtonText.length];  
  
    Label displayLabel = new Label("0", Label.RIGHT);  
    Label memLabel = new Label(" ", Label.RIGHT);  

    // Programming calculator components
    JRadioButton binaryButton = new JRadioButton("Binary");
    JRadioButton octalButton = new JRadioButton("Octal");
    JRadioButton decimalButton = new JRadioButton("Decimal");
    JRadioButton hexButton = new JRadioButton("Hexadecimal");
    ButtonGroup baseGroup = new ButtonGroup();
    JTextArea conversionArea = new JTextArea(5, 40);
    String hexDigitButtonText[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    MyDigitButton hexDigitButton[] = new MyDigitButton[hexDigitButtonText.length];

    final int FRAME_WIDTH = 500, FRAME_HEIGHT = 525;  
    final int HEIGHT = 30, WIDTH = 30, H_SPACE = 10, V_SPACE = 10;  
    final int TOPX = 30, TOPY = 50;

    public CalculatorApp(String frameText)  
    {  
        super(frameText);  

        setLayout(new BorderLayout());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        createMenuBar();
        addSimpleCalculatorComponents();

        setVisible(true);  
    }
    

    // Create the menu bar to switch between calculators
    private void createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu calculatorMenu = new Menu("Calculator Menu");
        MenuItem simpleCalculatorItem = new MenuItem("Simple Calculator");
        MenuItem programmingCalculatorItem = new MenuItem("Programming Calculator");

        simpleCalculatorItem.addActionListener(e -> {
            isSimpleCalculator = true;
            removeAllComponents();
            addSimpleCalculatorComponents();
            validate();
            repaint();
        });

        programmingCalculatorItem.addActionListener(e -> {
            isSimpleCalculator = false;
            removeAllComponents();
            addProgrammingCalculatorComponents();
            validate();
            repaint();
        });

        calculatorMenu.add(simpleCalculatorItem);
        calculatorMenu.add(programmingCalculatorItem);
        menuBar.add(calculatorMenu);
        setMenuBar(menuBar);
    }
    
    /*private void removeAllComponents() {
        removeAll();
    }*/
    
    private void removeAllComponents() {
        removeAll();
    }


    // Simple Calculator components
    private void addSimpleCalculatorComponents() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        displayLabel.setBounds(TOPX, TOPY, 240, HEIGHT);
        displayLabel.setBackground(Color.LIGHT_GRAY);  
        displayLabel.setForeground(Color.WHITE);  
        mainPanel.add(displayLabel);  

        memLabel.setBounds(TOPX, TOPY + HEIGHT + V_SPACE, WIDTH, HEIGHT);  
        mainPanel.add(memLabel);  

        // Memory Buttons  
        int tempX = TOPX, y = TOPY + 2 * (HEIGHT + V_SPACE);  
        for (int i = 0; i < memoryButton.length; i++)  
        {  
            memoryButton[i] = new MyMemoryButton(tempX, y, WIDTH, HEIGHT, memoryButtonText[i], this);  
            memoryButton[i].setForeground(Color.RED);  
            mainPanel.add(memoryButton[i]);
            y += HEIGHT + V_SPACE;  
        }

        // Special Buttons  
        tempX = TOPX + 1 * (WIDTH + H_SPACE);  
        y = TOPY + 1 * (HEIGHT + V_SPACE);  
        for (int i = 0; i < specialButton.length; i++)  
        {  
            specialButton[i] = new MySpecialButton(tempX, y, WIDTH * 2, HEIGHT, specialButtonText[i], this);  
            specialButton[i].setForeground(Color.RED);  
            mainPanel.add(specialButton[i]);
            tempX = tempX + 2 * WIDTH + H_SPACE;  
        }

        // Digit Buttons  
        int digitX = TOPX + WIDTH + H_SPACE;  
        int digitY = TOPY + 2 * (HEIGHT + V_SPACE);  
        tempX = digitX;  
        y = digitY;  
        for (int i = 0; i < digitButton.length; i++)  
        {  
            digitButton[i] = new MyDigitButton(tempX, y, WIDTH, HEIGHT, digitButtonText[i], this);  
            digitButton[i].setForeground(Color.BLUE);  
            mainPanel.add(digitButton[i]);
            tempX += WIDTH + H_SPACE;  
            if ((i + 1) % 3 == 0) { tempX = digitX; y += HEIGHT + V_SPACE; }  
        }  
  
        // Operator Buttons  
        int opsX = digitX + 2 * (WIDTH + H_SPACE) + H_SPACE;  
        int opsY = digitY;  
        tempX = opsX;  
        y = opsY;  
        for (int i = 0; i < operatorButton.length; i++)  
        {  
            tempX += WIDTH + H_SPACE;  
            operatorButton[i] = new MyOperatorButton(tempX, y, WIDTH, HEIGHT, operatorButtonText[i], this);  
            operatorButton[i].setForeground(Color.RED);  
            mainPanel.add(operatorButton[i]);
            if ((i + 1) % 2 == 0) { tempX = opsX; y += HEIGHT + V_SPACE; }  
        }

        add(mainPanel, BorderLayout.CENTER);
    }

    // Programming Calculator components
    private void addProgrammingCalculatorComponents() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        displayLabel.setBounds(TOPX, TOPY, 440, HEIGHT);
        displayLabel.setBackground(Color.LIGHT_GRAY);  
        displayLabel.setForeground(Color.BLACK);  
        mainPanel.add(displayLabel);

        // Base selection radio buttons
        JPanel basePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        basePanel.setBounds(TOPX, TOPY + HEIGHT + V_SPACE, 440, HEIGHT);
        basePanel.add(binaryButton);
        basePanel.add(octalButton);
        basePanel.add(decimalButton);
        basePanel.add(hexButton);
        baseGroup.add(binaryButton);  
        baseGroup.add(octalButton);  
        baseGroup.add(decimalButton);  
        baseGroup.add(hexButton);  
        decimalButton.setSelected(true);
        mainPanel.add(basePanel);

        // Add action listeners to radio buttons
        ActionListener baseListener = e -> updateEnabledButtons();
        binaryButton.addActionListener(baseListener);
        octalButton.addActionListener(baseListener);
        decimalButton.addActionListener(baseListener);
        hexButton.addActionListener(baseListener);

        // Conversion area
        conversionArea.setBounds(TOPX, TOPY + 2 * (HEIGHT + V_SPACE), 440, 100);
        conversionArea.setEditable(false);
        mainPanel.add(conversionArea);

        // Digit Buttons for Hexadecimal support (0-9, A-F)
        int tempX = TOPX, y = TOPY + 3 * (HEIGHT + V_SPACE) + 100;
        for (int i = 0; i < hexDigitButtonText.length; i++)  
        {  
            hexDigitButton[i] = new MyDigitButton(tempX, y, WIDTH, HEIGHT, hexDigitButtonText[i], this);  
            hexDigitButton[i].setForeground(Color.BLUE);  
            mainPanel.add(hexDigitButton[i]);
            tempX += WIDTH + H_SPACE;  
            if ((i + 1) % 4 == 0) { tempX = TOPX; y += HEIGHT + V_SPACE; }  
        }  
        
        // Operator Buttons for + and *
        y += HEIGHT + V_SPACE;
        operatorButton[0] = new MyOperatorButton(TOPX, y, WIDTH, HEIGHT, "+", this);  
        operatorButton[1] = new MyOperatorButton(TOPX + WIDTH + H_SPACE, y, WIDTH, HEIGHT, "*", this);  
        mainPanel.add(operatorButton[0]);
        mainPanel.add(operatorButton[1]);

        // CL and = buttons
        specialButton[0] = new MySpecialButton(TOPX + 2 * (WIDTH + H_SPACE), y, WIDTH, HEIGHT, "CL", this);
        operatorButton[2] = new MyOperatorButton(TOPX + 3 * (WIDTH + H_SPACE), y, WIDTH, HEIGHT, "=", this);
        mainPanel.add(specialButton[0]);
        mainPanel.add(operatorButton[2]);

        updateEnabledButtons();
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void updateEnabledButtons() {
        int base = getSelectedBase();
        for (int i = 0; i < hexDigitButton.length; i++) {
            hexDigitButton[i].setEnabled(i < base);
        }
    }
    
    // Move these methods inside the CalculatorApp class
    public void displayConversions(String value) {
        try {
            int base = getSelectedBase();
            long decimalValue = Long.parseLong(value, base);

            conversionArea.setText(
                "Decimal: " + decimalValue + "\n" +
                "Binary: " + Long.toBinaryString(decimalValue) + "\n" +
                "Octal: " + Long.toOctalString(decimalValue) + "\n" +
                "Hexadecimal: " + Long.toHexString(decimalValue).toUpperCase()
            );
        } catch (NumberFormatException e) {
            conversionArea.setText("Invalid input for the selected base.");
        }
    }

    // Function to get the base selected by the radio buttons
    int getSelectedBase() {
        if (binaryButton.isSelected()) return 2;
        if (octalButton.isSelected()) return 8;
        if (decimalButton.isSelected()) return 10;
        if (hexButton.isSelected()) return 16;
        return 10;
    }


    // Format output
    static String getFormattedText(double temp)  
    {  
        String resText = "" + temp;  
        if (resText.lastIndexOf(".0") > 0)  
            resText = resText.substring(0, resText.length() - 2);  
        return resText;  
    }

    public static void main(String[] args)  
    {  
        SwingUtilities.invokeLater(() -> new CalculatorApp("Calculator - Java"));  
    }  
}

/*******************************************/  

// Digit Button Class  
class MyDigitButton extends Button implements ActionListener  
{  
    CalculatorApp cl;  
  
    MyDigitButton(int x, int y, int width, int height, String cap, CalculatorApp clc)  
    {  
        super(cap);  
        setBounds(x, y, width, height);  
        this.cl = clc;  
        this.cl.add(this);  
        addActionListener(this);  
    }  

    public void actionPerformed(ActionEvent ev)  
    {  
        String tempText = ((MyDigitButton) ev.getSource()).getLabel();  

        if (cl.setClear) {  
            cl.displayLabel.setText(tempText);  
            cl.setClear = false;  
        }  
        else  
            cl.displayLabel.setText(cl.displayLabel.getText() + tempText);  

        if (!cl.isSimpleCalculator) {
            cl.displayConversions(cl.displayLabel.getText());
        }
    }  
}

/*******************************************/  

// Operator Button Class  
class MyOperatorButton extends Button implements ActionListener  
{  
    CalculatorApp cl;  
  
    MyOperatorButton(int x, int y, int width, int height, String cap, CalculatorApp clc)  
    {  
        super(cap);  
        setBounds(x, y, width, height);  
        this.cl = clc;  
        this.cl.add(this);  
        addActionListener(this);  
    }  

    public void actionPerformed(ActionEvent ev)  
    {  
        String opText = ((MyOperatorButton) ev.getSource()).getLabel();  
        cl.setClear = true;  

        if (cl.isSimpleCalculator) {
            handleSimpleCalculator(opText);
        } else {
            handleProgrammableCalculator(opText);
        }
    }

    private void handleSimpleCalculator(String opText) {
        double temp = Double.parseDouble(cl.displayLabel.getText());  

        if (opText.equals("1/x"))  
        {  
            try { 
                double tempd = 1 / temp; 
                cl.displayLabel.setText(CalculatorApp.getFormattedText(tempd)); 
            }  
            catch (ArithmeticException excp) { 
                cl.displayLabel.setText("Divide by 0."); 
            }  
            return;  
        }  
        if (opText.equals("sqrt"))  
        {  
            try { 
                double tempd = Math.sqrt(temp); 
                cl.displayLabel.setText(CalculatorApp.getFormattedText(tempd)); 
            }  
            catch (ArithmeticException excp) { 
                cl.displayLabel.setText("Invalid input for sqrt."); 
            }  
            return;  
        }  

        if (!opText.equals("="))  
        {  
            cl.number = temp;  
            cl.op = opText.charAt(0);  
            return;  
        }  

        switch (cl.op)  
        {  
            case '+': temp += cl.number; break;  
            case '-': temp = cl.number - temp; break;  
            case '*': temp *= cl.number; break;  
            case '%':  
                try { temp = cl.number % temp; }  
                catch (ArithmeticException excp) { cl.displayLabel.setText("Divide by 0."); return; }  
                break;  
            case '/':  
                try { temp = cl.number / temp; }  
                catch (ArithmeticException excp) { cl.displayLabel.setText("Divide by 0."); return; }  
                break;  
        }  
        cl.displayLabel.setText(CalculatorApp.getFormattedText(temp));  
    }

    private void handleProgrammableCalculator(String opText) {
    if (opText.equals("=")) {
        calculate();
    } else {
        try {
            cl.number = Long.parseLong(cl.displayLabel.getText(), cl.getSelectedBase());
            cl.op = opText.charAt(0);
            cl.setClear = true;
        } catch (NumberFormatException e) {
            cl.displayLabel.setText("Error");
        }
    }
}

    private void calculate() {
        try {
            int base = cl.getSelectedBase();
            long currentValue = Long.parseLong(cl.displayLabel.getText(), base);
            long result = 0;

            switch (cl.op) {
                case '+':
                    result = (long) (cl.number + currentValue);
                    break;
                case '*':
                    result = (long) (cl.number * currentValue);
                    break;
                default:
                    cl.displayLabel.setText("Invalid operation");
                    return;
            }

            cl.displayLabel.setText(Long.toString(result, base).toUpperCase());
            cl.displayConversions(cl.displayLabel.getText());
        } catch (NumberFormatException e) {
            cl.displayLabel.setText("Error");
        }
    }
}

/*******************************************/  

// Memory Button Class  
class MyMemoryButton extends Button implements ActionListener  
{  
    CalculatorApp cl;  
  
    MyMemoryButton(int x, int y, int width, int height, String cap, CalculatorApp clc)  
    {  
        super(cap);  
        setBounds(x, y, width, height);  
        this.cl = clc;  
        this.cl.add(this);  
        addActionListener(this);  
    }  

    // Handle Memory Button Action  
    public void actionPerformed(ActionEvent ev)  
    {  
        String memOp = ((MyMemoryButton) ev.getSource()).getLabel();  
        double currentValue = Double.parseDouble(cl.displayLabel.getText());  

        switch (memOp) {  
            case "MC":  
                cl.memValue = 0.0;  
                cl.memLabel.setText(" ");  
                break;  
            case "M+":  
                cl.memValue += currentValue;  
                cl.displayLabel.setText("");  
                cl.memLabel.setText("M");  
                break;  
            case "M-":  
                cl.memValue -= currentValue;  
                cl.displayLabel.setText("");  
                cl.memLabel.setText("M");  
                break;  
            case "M":  
                cl.displayLabel.setText(CalculatorApp.getFormattedText(cl.memValue));  
                break;  
        }  
    }  
}

/*******************************************/  

// Special Button Class  
class MySpecialButton extends Button implements ActionListener  
{  
    CalculatorApp cl;  
  
    MySpecialButton(int x, int y, int width, int height, String cap, CalculatorApp clc)  
    {  
        super(cap);  
        setBounds(x, y, width, height);  
        this.cl = clc;  
        this.cl.add(this);  
        addActionListener(this);  
    }  

    public void actionPerformed(ActionEvent ev)  
    {  
        String opText = ((MySpecialButton) ev.getSource()).getLabel();  

        if (opText.equals("Backspc")) {
            String tempText = cl.displayLabel.getText();
            if (tempText.length() > 0) {
                tempText = tempText.substring(0, tempText.length() - 1);
                if (tempText.isEmpty()) tempText = "0";
                cl.displayLabel.setText(tempText);
                if (!cl.isSimpleCalculator) {
                    cl.displayConversions(tempText);
                }
            }
        } else if (opText.equals("C") || opText.equals("CE")) {
            cl.displayLabel.setText("0");
            cl.setClear = true;
            if (!cl.isSimpleCalculator) {
                cl.displayConversions("0");
            }
        }
    }
}
