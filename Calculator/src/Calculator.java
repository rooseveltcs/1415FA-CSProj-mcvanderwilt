import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Calculator {

	JFrame frame;
	JPanel panel;
	JTextField baseNum;
	JTextField exponent;
	JButton operation;
	JLabel output;
	JLabel errorTxt;
	
	String inputA;
	String inputB;	
	
	double base;
	double exp;
	
	public static void main (String[] args){
		Calculator calc = new Calculator();
	}
	
	public Calculator() {
		//Frame
		frame = new JFrame();
		frame.setSize(500, 250);
		
		//Panel
		panel = new JPanel(new BorderLayout());
		frame.add(panel);
		
		//Input base
		baseNum = new JTextField("Input base number (a) : ", 20);//WARNING: if changing language of message, consult TxtFldListener
		baseNum.addActionListener(new TxtFldListener());
		panel.add(baseNum, BorderLayout.WEST);
		
		//Input exp
		exponent = new JTextField("Input exponent (b) : ", 20);//WARNING: if changing language of message, consult TxtFldListener
		exponent.addActionListener(new TxtFldListener());
		panel.add(exponent, BorderLayout.CENTER);
		
		//Button operation
		operation = new JButton("a^b");
		operation.addActionListener(new ButtonListener());
		panel.add(operation, BorderLayout.EAST);
		
		//Label output
		output = new JLabel("Output: ");
		panel.add(output, BorderLayout.SOUTH);
		
		//Label error message
		errorTxt = new JLabel("Please press enter after typing input.");
		panel.add(errorTxt, BorderLayout.NORTH);
		
		frame.setVisible(true);
	}
	
	private class TxtFldListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JTextField eventSource = (JTextField) e.getSource();

			try {
		    	if (eventSource.equals(baseNum)){
			    	inputA = eventSource.getText();
			    	inputA = inputA.replace("Input base number (a) : ", "");
			    	
			    	base = Double.parseDouble(inputA);

		    	} else if (eventSource.equals(exponent)) {
		    		inputB = eventSource.getText();
		    		inputB = inputB.replace("Input exponent (b) : ", "");

		    		exp = Double.parseDouble(inputB);

		    	}
		    } catch (NumberFormatException err) {
		    	errorTxt.setText("Error: Input in both text fields must be one entry of type double or int.");
		    	errorTxt.setForeground(Color.RED);
		    }
		}
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (inputA != null && inputB != null){
				output.setText(base + "^" + exp + " = " + Math.pow(base, exp));
				//output.setForeground(Color.GREEN);
			} else {
				errorTxt.setText("Error: Please enter input in both text fields before completing operation");
		    	errorTxt.setForeground(Color.RED);
			}
		}
	}
}