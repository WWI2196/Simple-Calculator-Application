# Java Calculator Application

This is a Java-based graphical user interface (GUI) calculator application designed to operate in two modes: **Simple Calculator** and **Programming Calculator**. The application is implemented using Java Swing components, and it follows good coding practices and algorithms.

## Features

### Simple Calculator
The Simple Calculator mode supports basic arithmetic operations and memory functions. The operations include:
- **Digits (0-9)**: Display the selected digit in the text field.
- **Basic Operations**:
  - `+` Addition
  - `-` Subtraction
  - `×` Multiplication
  - `/` Division
  - `=` Equals (Performs the displayed operation)
- **Memory Operations**:
  - `M` Displays the current memory value.
  - `M+` Adds the displayed value to the memory.
  - `M-` Subtracts the displayed value from the memory.
  - `MC` Clears the memory.
- **Clear Operation**: `CL` Clears the content in the display field.

#### Error Handling:
The Simple Calculator identifies and handles common errors such as:
- Two consecutive operations (e.g., `10/+20`).
- Division by zero (e.g., `34/0`).
- Consecutive memory operations (e.g., `10M+M+` or `30M-M+`).

### Programming Calculator
The Programming Calculator mode is designed for working with different numerical bases:
- **Supported Bases**: Binary, Octal, Decimal, Hexadecimal.
- **Numerical Buttons**: Digits (0-9), Alphabetical buttons (A-F) for Hexadecimal.
- **Operations**: 
  - `+` Addition
  - `×` Multiplication
  - `=` Equals (Executes the operation)
- **Radio Buttons**: Select the base (Binary, Octal, Decimal, Hexadecimal) and enable the corresponding digit buttons.
- **Number Conversion**: Displays the entered number in the selected base in bold, while converting it to other bases and displaying them in regular font.
- **Clear Operation**: `CL` Clears the content in the display field.

### Menu Bar
The application allows switching between the **Simple Calculator** (default) and **Programming Calculator** modes using the menu bar.
