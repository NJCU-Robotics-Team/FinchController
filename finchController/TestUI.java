/*
 * For the following code to work, the Finch Robot must
 * be connected to your computer. The window will not
 * popup until the robot has been connected.
 * 
 * The code is not fully complete, but all actions work.
 * Feel free to modify to make the code function better :D
 */


package finchApp;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.ExceptionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

import org.jdom.Document;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;

@SuppressWarnings("unused")
/**@author Saurabh Rathod*/
public class TestUI extends JFrame implements ChangeListener, WindowListener, ExceptionListener, Runnable 
{

	FileHandler f;
	FileWriter fw;
	
	
	JPanel n = new JPanel();
	JPanel s = new JPanel();
	JPanel e = new JPanel();
	JPanel w = new JPanel();
	JPanel c = new JPanel();
	public static JButton up, down, left, right, close;


	static final int SP_INIT = 50;    //Initial speed
	static final int SP_MIN = 0;
	static final int SP_MAX = 255;

	JScrollPane jsp = new JScrollPane(c);
	
	static ActionListener ku = new KU();
	static ActionListener kd = new KD();
	static ActionListener kl = new KL();
	static ActionListener kr = new KR();
	ActionListener shut = new SHUT();
	KeyListener rightk = new RIGHTK();
	static CaretListener cl = new CL();
	static ActionListener cp = new CP();
	
	private static final long serialVersionUID = 927L;
	
	static JSlider finchSpeed = new JSlider(JSlider.HORIZONTAL,
            SP_MIN, SP_MAX, SP_INIT);

	JTextArea spd = new JTextArea();
	
	JMenuBar finchMenu;
	JMenu fileMenu, settingsMenu, helpMenu;
	JMenuItem exitItem, aboutItem, colorItem, tempItem, objItem, virtualControlItem;

	

	//static Finch fh = new Finch();	
	public TestUI()
	{
		
		
		
		
		
		//Get the operating system's L&F
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) 
		{
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error on L&F\n", "L&F ERROR!", JOptionPane.ERROR_MESSAGE);
			
		}

		
		
		//FinchConnect fc = new FinchConnect();
		//fc.dispose();
		
		
		System.out.println("Connected to Finch Robot");
		
		callGUI();
		
		


		setTitle("Finch Control Application");
		
		
	    setIconImage(null); 
	
	    addWindowListener(this);
	    

	    spd = new JTextArea();
	    spd.setPreferredSize(new Dimension(100, 50));
		
	    getContentPane().add(n, BorderLayout.NORTH);
	    getContentPane().add(s, BorderLayout.SOUTH);
	    getContentPane().add(e, BorderLayout.EAST);
	    getContentPane().add(w, BorderLayout.WEST);
	    getContentPane().add(c, BorderLayout.CENTER);

	    


	    
	    
	up = new JButton("Forward");
	up.setFocusable(false);
	up.setMnemonic(KeyEvent.VK_UP);
	up.addActionListener(ku);
	n.add(up);
	
	down = new JButton("Reverse");
	down.setFocusable(false);
	down.setMnemonic(KeyEvent.VK_DOWN);
	down.addActionListener(kd);
		s.add(down);
	
	
	left = new JButton("Left");
	left.setFocusable(false);
	left.setMnemonic(KeyEvent.VK_LEFT);
	left.addActionListener(kl);
	w.add(left);
	
	
	right = new JButton("Right");
	right.setFocusable(false);
	right.setMnemonic(KeyEvent.VK_RIGHT);
	right.addActionListener(kr);
	e.add(right);
	
	finchSpeed.setFocusable(false);
	finchSpeed.addChangeListener(this);
	finchSpeed.setMajorTickSpacing(10);
	finchSpeed.setMinorTickSpacing(1);
	c.add(finchSpeed);
	c.add(spd, null, -1);
	spd.setVisible(true);
	spd.setEditable(false);
    jsp.setAutoscrolls(true);
	jsp.setVisible(true);
	jsp.setWheelScrollingEnabled(true);
	jsp.setPreferredSize(new Dimension(10, 50));
	spd.addCaretListener(cl);
	
	//Close Connection with Finch with this button
	close = new JButton("End Connection");

	close.setFocusable(false);
	close.addActionListener(shut);
	c.add(close, null, -1);
	
	/**Menu Bar*/
	finchMenu = new JMenuBar();
	
	//File Menu
	fileMenu = new JMenu("File		");
	fileMenu.setRolloverEnabled(true);
	finchMenu.add(fileMenu);
	
	//Settings Menu
	settingsMenu = new JMenu("Settings");
	settingsMenu.setRolloverEnabled(true);
	finchMenu.add(settingsMenu);
	
	//Set File Menu Items
	exitItem = new JMenuItem("Exit		");
	exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
	shut = new SHUT();
	exitItem.addActionListener(shut);
	fileMenu.add(exitItem);
	exitItem.setToolTipText("Terminates connection with Finch Robot.");
	
	colorItem = new JMenuItem("Set LED Color", KeyEvent.VK_L);
	colorItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
	colorItem.addActionListener(cp);
	settingsMenu.add(colorItem);
	colorItem.setToolTipText("Choose the LED light color for Finch Robot.");
	
	//Display the Menu
	setJMenuBar(finchMenu);
	
	}
	
	
	void callGUI()
	{
		setSize(400,200);
		setResizable(false);
		setLocationRelativeTo(null);
		
	}
	
	public static void goStraight(int left, int right, int duration)
	{
		//fh.setLED(255, 0, 0);//Red
		//fh.setWheelVelocities(left, right, duration);

		
	}
	
	public static void goLeft(int right, int duration)
	{
			//fh.setLED(0, 255, 0);//Green
			//fh.setWheelVelocities(0, right, duration);
	}
	
	public static void goRight(int left, int duration)
	{
			//fh.setLED(0, 0, 255);//Blue	
			//fh.setWheelVelocities(left, 0, duration);
	}
	
	public static void goReverse(int left, int right, int duration)
	{

			//fh.setLED(255, 255, 0);//Yellow
			//fh.setWheelVelocities(-left, -right, duration);
	}
	
	public void Close()
	{
		dispose();
	}
	
	
	public void stateChanged(ChangeEvent arg0) 
	{
		/**if(fh.equals(null))
		{
			this.dispose();
			
		}**/
		//else
		//{

			String mark = "";
			mark = mark.substring(4) + finchSpeed.getValue() + "\n";
			spd.append(mark);
			spd.setAutoscrolls(true);
		//}
		
	}

	static class KU implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
		{
			
			goStraight(finchSpeed.getValue(), finchSpeed.getValue(), KeyEvent.KEY_PRESSED);
			
		}
		
	}
	
	static class KD implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
		{
			goReverse(finchSpeed.getValue(), finchSpeed.getValue(), KeyEvent.KEY_PRESSED);
			
		}
		
	}
	
	static class KL implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
		{
			goLeft(finchSpeed.getValue(), KeyEvent.KEY_PRESSED);
			
		}
		
	}
	
	static class KR implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
		{
			goRight(finchSpeed.getValue(), KeyEvent.KEY_PRESSED);
			
		}
		
	}

	class SHUT implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
		{
			System.err.println("Connection Terminated");
			System.exit(0);
			
		}
		
	}
	
	static class UPK implements KeyListener
	{

		public void keyPressed(KeyEvent ke) 
		{
			while(ke.getKeyCode()==KeyEvent.VK_UP)
			{
				ku = new KU();
			}
		}

		public void keyReleased(KeyEvent arg0) 
		{
			
		}

		public void keyTyped(KeyEvent arg0) 
		{
			
		}
		
	}
	
	static class DOWNK implements KeyListener
	{

		public void keyPressed(KeyEvent ke) 
		{
			while(ke.getKeyCode()==KeyEvent.VK_DOWN)
			{
				kd = new KD();
			}
		}

		public void keyReleased(KeyEvent arg0) 
		{
			
		}

		public void keyTyped(KeyEvent arg0) 
		{
			
		}
		
	}
	
	static class LEFTK implements KeyListener
	{

		public void keyPressed(KeyEvent ke) 
		{
			while(ke.getKeyCode()==KeyEvent.VK_LEFT)
			{
				kl = new KL();
			}
		}

		public void keyReleased(KeyEvent arg0) 
		{
			
		}

		public void keyTyped(KeyEvent arg0) 
		{
			
		}
		
	}
	
	static class RIGHTK implements KeyListener
	{

		public void keyPressed(KeyEvent ke) 
		{
			while(ke.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				kr = new KR();
			}
		}

		public void keyReleased(KeyEvent arg0) 
		{
			
		}

		public void keyTyped(KeyEvent arg0) 
		{
			
		}
		
	}

	public void windowActivated(WindowEvent arg0)
	{
		System.err.println(" Window Activated");
		TestUI.this.setOpacity(1);
	}


	public void windowClosed(WindowEvent arg0) 
	{

	}


	public void windowClosing(WindowEvent arg0) 
	{
		System.err.println("Connection Terminated");
		System.exit(0);
	}


	public void windowDeactivated(WindowEvent arg0) 
	{
		System.err.println("Window Deactivated");
	}


	public void windowDeiconified(WindowEvent arg0) 
	{
		
	}


	public void windowIconified(WindowEvent arg0) 
	{
		
	}


	
	public void windowOpened(WindowEvent arg0) 
	{
		System.err.println(System.identityHashCode(c) + "\n" +
				System.identityHashCode(up) +  "\n" +
				System.identityHashCode(down) +  "\n" +
				System.identityHashCode(left) +  "\n" +
				System.identityHashCode(right) +  "\n" +
				System.identityHashCode(close) +  "\n" +
				System.identityHashCode(finchSpeed) +  "\n" +
				System.identityHashCode(spd) + "\n" + 
				System.identityHashCode(this) + " Window Activated");
	}
	
	public void exceptionThrown(Exception arg0) 
	{
		JOptionPane.showMessageDialog(null, "An unhandled exception occured", " ", JOptionPane.ERROR_MESSAGE);
	}
	
	static class CL implements CaretListener
	{

		public void caretUpdate(CaretEvent e)
		{
			
		}
		
	}
	
	static class CP implements ActionListener
	{

		public void actionPerformed(ActionEvent arg0) 
		{
			TestColorPicker tcp = new TestColorPicker();
			tcp.setVisible(true);
		}
		
	}

	@Override
	public void run() 
	{
		TestUI t = new TestUI();
		t.setVisible(true);
	}

}


