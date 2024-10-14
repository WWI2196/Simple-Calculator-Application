package calculatorapp;

import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;

public class CalculatorApp extends JFrame  
{  
    public boolean setClear = true;  
    double number, memValue;  
    char op;  
    boolean isSimpleCalculator = true;
    boolean lastActionWasOperation = false;

    String digitButtonText[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "." };  
    String operatorButtonText[] = {"/", "sqrt", "*", "-", "+", "=" };  
    String memoryButtonText[] = {"MC", "M", "M-", "M+" };  
    String specialButtonText[] = {"C", "CE" };  
  
    MyDigitButton digitButton[] = new MyDigitButton[digitButtonText.length];  
    MyOperatorButton operatorButton[] = new MyOperatorButton[operatorButtonText.length];  
    MyMemoryButton memoryButton[] = new MyMemoryButton[memoryButtonText.length];  
    MySpecialButton specialButton[] = new MySpecialButton[specialButtonText.length];  
  
    Label displayLabel = new Label("0", Label.RIGHT);  
    Label memLabel = new Label(" ", Label.RIGHT);  

    // for programming calculator components and buttons
    JRadioButton binaryButton = new JRadioButton("Binary");
    JRadioButton octalButton = new JRadioButton("Octal");
    JRadioButton decimalButton = new JRadioButton("Decimal");
    JRadioButton hexButton = new JRadioButton("Hexadecimal");
    ButtonGroup baseGroup = new ButtonGroup();
    JTextArea conversionArea = new JTextArea(5, 40);
    String hexDigitButtonText[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    MyDigitButton hexDigitButton[] = new MyDigitButton[hexDigitButtonText.length];

    final int FRAME_WIDTH = 500, FRAME_HEIGHT = 600;  
    final int HEIGHT = 30, WIDTH = 30, H_SPACE = 10, V_SPACE = 10;  
    final int TOPX = 30, TOPY = 50;

    //set the window for the calculator application
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
    

    // menu bar to switch between simple calculator and programmable calculator
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

    // to set the components for the simple and programmable calcutotors by removing unwanted buttons
    private void removeAllComponents() {
        getContentPane().removeAll();
    }


    // add the butttons and componenets needed for the simple calculator 
    private void addSimpleCalculatorComponents() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        displayLabel.setBounds(TOPX, TOPY, 240, HEIGHT);
        displayLabel.setBackground(Color.LIGHT_GRAY);  
        displayLabel.setForeground(Color.WHITE);  
        mainPanel.add(displayLabel);  

        memLabel.setBounds(TOPX, TOPY + HEIGHT + V_SPACE, WIDTH, HEIGHT);  
        mainPanel.add(memLabel);  

        // for memory buttons  
        int tempX = TOPX, y = TOPY + 2 * (HEIGHT + V_SPACE);  
        for (int i = 0; i < memoryButton.length; i++)  
        {  
            memoryButton[i] = new MyMemoryButton(tempX, y, WIDTH, HEIGHT, memoryButtonText[i], this);
            memoryButton[i].setBackground(Color.BLUE);
            memoryButton[i].setForeground(Color.WHITE);  
            mainPanel.add(memoryButton[i]);
            y += HEIGHT + V_SPACE;  
        }

        // for special Buttons (C & CE)  
        tempX = TOPX + 1 * (WIDTH + H_SPACE);  
        y = TOPY + 1 * (HEIGHT + V_SPACE);  
        for (int i = 0; i < specialButton.length; i++)  
        {  
            specialButton[i] = new MySpecialButton(tempX, y, WIDTH * 2, HEIGHT, specialButtonText[i], this);  
            specialButton[i].setForeground(Color.RED);  
            mainPanel.add(specialButton[i]);
            tempX = tempX + 2 * WIDTH + H_SPACE;  
        }

        // for number buttons 
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
  
        // for operator buttons  
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
    
    // method to check for consecutive operations
     boolean isConsecutiveOperation(String operation) {
        if (lastActionWasOperation) {
            if (operation.equals("-")) {
                // Allow minus after an operation for negative numbers
                displayLabel.setText(displayLabel.getText() + operation);
                lastActionWasOperation = false;
                return true;
            } else if (!operation.equals("=")) {
                displayLabel.setText("Error");
                setClear = true;
                lastActionWasOperation = false;
                return true;
            }
        }
        lastActionWasOperation = !operation.equals("=");
        return false;
    }

    // to set programming calculator components
    private void addProgrammingCalculatorComponents() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        displayLabel.setBounds(TOPX, TOPY, 440, HEIGHT);
        displayLabel.setBackground(Color.LIGHT_GRAY);  
        displayLabel.setForeground(Color.BLACK);  
        mainPanel.add(displayLabel);

        // for the selection radio buttons
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

        // action listeners to radio buttons
        ActionListener baseListener = e -> updateEnabledButtons();
        binaryButton.addActionListener(baseListener);
        octalButton.addActionListener(baseListener);
        decimalButton.addActionListener(baseListener);
        hexButton.addActionListener(baseListener);

        // conversion area
        conversionArea.setBounds(TOPX, TOPY + 2 * (HEIGHT + V_SPACE), 440, 100);
        conversionArea.setEditable(false);
        mainPanel.add(conversionArea);

        // buttons for hexadecimal digits
        int tempX = TOPX, y = TOPY + 3 * (HEIGHT + V_SPACE) + 100;
        for (int i = 0; i < hexDigitButtonText.length; i++)  
        {  
            hexDigitButton[i] = new MyDigitButton(tempX, y, WIDTH, HEIGHT, hexDigitButtonText[i], this);  
            hexDigitButton[i].setForeground(Color.BLUE);  
            mainPanel.add(hexDigitButton[i]);
            tempX += WIDTH + H_SPACE;  
            if ((i + 1) % 4 == 0) { tempX = TOPX; y += HEIGHT + V_SPACE; }  
        }  
        
        // operator buttons for + and *
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
    
    // display the number converstion in the converstion area
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

    // function to get the option selected by the radio buttons
    int getSelectedBase() {
        if (binaryButton.isSelected()) return 2;
        if (octalButton.isSelected()) return 8;
        if (decimalButton.isSelected()) return 10;
        if (hexButton.isSelected()) return 16;
        return 10;
    }


    // set the output text
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


// class to execute the number buttons pressed 
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


// class to execute the operator buttons pressed   
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
        
        if (cl.isConsecutiveOperation(opText)) {
            return;
        }

        cl.setClear = true;  

        if (cl.isSimpleCalculator) {
            handleSimpleCalculator(opText);
        } else {
            handleProgrammableCalculator(opText);
        }
    }

    private void handleSimpleCalculator(String opText) {
        double temp = Double.parseDouble(cl.displayLabel.getText());  
 
        if (opText.equals("sqrt"))  
        {  
            try { 
                double tempd = Math.sqrt(temp); 
                cl.displayLabel.setText(CalculatorApp.getFormattedText(tempd)); 
            }  
            catch (ArithmeticException excp) { 
                cl.displayLabel.setText("Error"); 
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
            case '/':  
                try { 
                    if (temp == 0) throw new ArithmeticException();
                    temp = cl.number / temp; 
                }  
                catch (ArithmeticException excp) { cl.displayLabel.setText("Error"); return; }  
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

// class to execute the memory buttons pressed 
class MyMemoryButton extends Button implements ActionListener  
{  
    CalculatorApp cl;  
    private boolean lastActionWasMemory = false;
  
    MyMemoryButton(int x, int y, int width, int height, String cap, CalculatorApp clc)  
    {  
        super(cap);  
        setBounds(x, y, width, height);  
        this.cl = clc;  
        this.cl.add(this);  
        addActionListener(this);  
    }  

    public void actionPerformed(ActionEvent ev)  
    {  
        String memOp = ((MyMemoryButton) ev.getSource()).getLabel();  
        
        if (lastActionWasMemory && (memOp.equals("M+") || memOp.equals("M-"))) {
            cl.displayLabel.setText("Error");
            cl.setClear = true;
            lastActionWasMemory = false;
            return;
        }

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

        lastActionWasMemory = memOp.equals("M+") || memOp.equals("M-");
    }  
}

// class to execute the special buttons pressed  
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
        if (opText.equals("C") || opText.equals("CE")) {
            cl.displayLabel.setText("0");
            cl.setClear = true;
            if (!cl.isSimpleCalculator) {
                cl.displayConversions("0");
            }
        }
    
    }
}

