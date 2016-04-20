import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;

/*
Kevin Cramsey
2 July 2014
CIS 163 AA
Module #12 Programming Challenge
RetailPriceCalculator.java
*/

public class RetailPriceCalculator 
{
	private JFrame frame;
	private final int FRAME_WIDTH = 340;
	private final int FRAME_HEIGHT = 240;
	private JPanel panel;
	private JLabel lblWholeSaleCost, lblMarkupPercent;
	private JTextField txtWholeSaleCost, txtMarkupPercent;
	private final int TXT_WIDTH = 200;
	private final int TXT_HEIGHT = 20;
	private JButton btnCalculate, btnExit;
	private ButtonListener btnListener;
	private boolean errorDetected;
	
	public RetailPriceCalculator()
	{
		frame = new JFrame("Retail Price Calculator");
		panel = new JPanel();
		lblWholeSaleCost = new JLabel("Wholesale Cost:");
		lblMarkupPercent = new JLabel("Markup Percent:");
		txtWholeSaleCost = new JTextField();
		txtMarkupPercent = new JTextField();
		btnCalculate = new JButton("Calculate");
		btnExit = new JButton("Exit");
		btnListener = new ButtonListener();
		errorDetected = false;
		
		createAndShowGUI();
	}
	
	private void createAndShowGUI()
	{
		txtWholeSaleCost.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));
		txtMarkupPercent.setPreferredSize(new Dimension(TXT_WIDTH, TXT_HEIGHT));
		
		btnExit.setPreferredSize(btnCalculate.getPreferredSize());
		btnCalculate.addActionListener(btnListener);
		btnExit.addActionListener(btnListener);
		
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 30));
		panel.add(lblWholeSaleCost);
		panel.add(txtWholeSaleCost);
		panel.add(lblMarkupPercent);
		panel.add(txtMarkupPercent);
		panel.add(btnCalculate);
		panel.add(btnExit);
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
	private double CalculateTotal()
	{
		String temp1 = txtWholeSaleCost.getText();
		String temp2 = txtMarkupPercent.getText();
		double toReturn = 0;
		
		try
		{
			double wholeSaleCost = Double.parseDouble(temp1);
			double markupPercent = Double.parseDouble(temp2);
		
			toReturn = wholeSaleCost * ((markupPercent / 100) + 1);  //convert markupPercent to a decimal percent (e.g. 25% = 0.25, 50% = 0.50, 100% = 1.0, 200% = 2.0, etc)
		}
		catch(NumberFormatException e)
		{
			errorDetected = true;
			JOptionPane.showMessageDialog(null, "Error - invalid input." + 
												"\nPlease enter a number for wholesale cost (don't include $ symbol)." +
												"\nPlease enter a number for percentage (don't include % symbol)",
												"Error", JOptionPane.ERROR_MESSAGE); //this line sets JOptionPane dialog's title and message type
		}

		return toReturn;
	}
	
	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == btnCalculate)
			{
				double cost = CalculateTotal();
				if(errorDetected == false)  //only show this dialog if the user entered valid input
				{
					DecimalFormat df = new DecimalFormat("0.00");
					JOptionPane.showMessageDialog(null, "The total cost is: $" + df.format(cost), "Total", JOptionPane.INFORMATION_MESSAGE);
				}
				errorDetected = false;  //reset error detection flag
			}
			else  //exit button
			{
				System.exit(0);
			}
		}
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()  //running swing on the event dispatch thread
		{
			@Override
			public void run()
			{
				new RetailPriceCalculator();
			}
		});
	}
}