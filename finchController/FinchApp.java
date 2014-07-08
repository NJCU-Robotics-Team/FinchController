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
import frames.PokerFrame;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

import org.jdom.Document;

import frames.PokerFrame;
import frames.PokerFrame.EXAC;
import frames.PokerFrame.EXAC2;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;

@SuppressWarnings("unused")
/**@author Saurabh Rathod*/
public class FinchApp extends JFrame implements ChangeListener, WindowListener, ExceptionListener
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
	
	static ActionListener ku = new KU();
	static ActionListener kd = new KD();
	static ActionListener kl = new KL();
	static ActionListener kr = new KR();
	ActionListener shut = new SHUT();
	//KeyListener rightk = new RIGHTK();
	//KeyListener upk = new UPK();
	
	private static final long serialVersionUID = 927L;
	
	static JSlider finchSpeed = new JSlider(JSlider.HORIZONTAL,
            SP_MIN, SP_MAX, SP_INIT);

	JTextArea spd = new JTextArea();
	JScrollPane jsp = new JScrollPane(spd);
	

	static Finch fh = new Finch();	
	public FinchApp()
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
		
		
		//setDefaultCloseOperation(EXIT_ON_CLOSE);


		setTitle("Finch Control Application");
		
		
	    setIconImage(null); 
		

	    spd = new JTextArea();
	    spd.setPreferredSize(new Dimension(50, 25));
		
	    getContentPane().add(n, BorderLayout.NORTH);
	    getContentPane().add(s, BorderLayout.SOUTH);
	    getContentPane().add(e, BorderLayout.EAST);
	    getContentPane().add(w, BorderLayout.WEST);
	    getContentPane().add(c, BorderLayout.CENTER);
            //getContentPane().add(jsp);
	    


	    
	    
	up = new JButton("Forward");
	up.setFocusable(false);
	up.setMnemonic(KeyEvent.VK_UP);
	//up.addActionListener(ku);
        //up.addKeyListener(upk);
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
		//spd.
	spd.setEditable(false);
        jsp.setAutoscrolls(true);
	spd.setLineWrap(true);
	jsp.setEnabled(true);
	//jsp.setViewportView(spd);
	jsp.setWheelScrollingEnabled(true);
		
	
	//Close Connection with Finch with this button
	close = new JButton("End Connection");

	close.setFocusable(false);
	close.addActionListener(shut);
	c.add(close, null, -1);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher( new UPK());
        
	}
	
	
	void callGUI()
	{
		setSize(400,200);
		setResizable(false);
		setLocationRelativeTo(null);
		
	}
	
	public static void goStraight(int left, int right)
	{
			fh.setLED(255, 0, 0);//Red
			fh.setWheelVelocities(left, right);

		
	}
	
	public static void goLeft(int left, int right)
	{
			fh.setLED(0, 255, 0);//Green
			fh.setWheelVelocities(-left, right);
	}
	
	public static void goRight(int left, int right)
	{
			fh.setLED(0, 0, 255);//Blue	
			fh.setWheelVelocities(left, -right);
	}
	
	public static void goReverse(int left, int right)
	{

			fh.setLED(255, 255, 0);//Yellow
			fh.setWheelVelocities(-left, -right);
	}
	
	public void Close()
	{
		dispose();
	}
	
	
	public void stateChanged(ChangeEvent arg0) 
	{
		if(fh.equals(null))
		{
			this.dispose();
			
		}
		else
		{

			String mark = "";
                        mark += mark + finchSpeed.getValue();
			//String speed_mark = mark + finchSpeed.getValue() + "\n";
			//spd.append(mark);
                        spd.setText(mark);
			spd.setAutoscrolls(true);
		}
		
	}

   

	static class KU implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
		{
                    goStraight(finchSpeed.getValue(), finchSpeed.getValue());
                }
		
	}
	
	static class KD implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
                {
                        goReverse(finchSpeed.getValue(), finchSpeed.getValue());
		}
		
	}
	
	static class KL implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
		{
			goLeft(finchSpeed.getValue(), finchSpeed.getValue());
			
		}
		
	}
	
	static class KR implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
		{
			goRight(finchSpeed.getValue(), finchSpeed.getValue());
			
		}
		
	}

	class SHUT implements ActionListener
	{

		public void actionPerformed(ActionEvent ae) 
		{
                    System.out.println("Connection has been terminated.");
                    System.exit(0);
			
		}
		
	}
	
	static class UPK implements KeyEventDispatcher 
	{
             @Override
        public boolean dispatchKeyEvent(KeyEvent e) 
        {
            int keyCode = e.getKeyCode();
        switch( keyCode ) 
        { 
            case KeyEvent.VK_UP:
                //handle up
                if (e.getID() == KeyEvent.KEY_PRESSED) 
                {
                    goStraight(finchSpeed.getValue(), finchSpeed.getValue());
                } 
                else if (e.getID() == KeyEvent.KEY_RELEASED) 
                {
                    fh.stopWheels();
                } 
                else if (e.getID() == KeyEvent.KEY_TYPED) 
                {
                    System.out.println("Does Nothing");
                }
                break;
            case KeyEvent.VK_DOWN:
                // handle down
                if (e.getID() == KeyEvent.KEY_PRESSED) 
                {
                    goReverse(finchSpeed.getValue(), finchSpeed.getValue());
                }
                else if (e.getID() == KeyEvent.KEY_RELEASED) 
                {
                    fh.stopWheels();
                }
                else if (e.getID() == KeyEvent.KEY_TYPED)
                {
                    System.out.println("Does Nothing");
                }
                break;
            case KeyEvent.VK_LEFT:
                // handle left
                if (e.getID() == KeyEvent.KEY_PRESSED) 
                {
                    goLeft(finchSpeed.getValue(), finchSpeed.getValue());
                }
                else if (e.getID() == KeyEvent.KEY_RELEASED) 
                {
                    fh.stopWheels();
                }
                else if (e.getID() == KeyEvent.KEY_TYPED) 
                {
                    System.out.println("Does Nothing");
                }
                break;
            case KeyEvent.VK_RIGHT :
                // handle right
                if (e.getID() == KeyEvent.KEY_PRESSED) 
                {
                    goRight(finchSpeed.getValue(), finchSpeed.getValue());
                }
                else if (e.getID() == KeyEvent.KEY_RELEASED) 
                {
                    fh.stopWheels();
                }
                else if (e.getID() == KeyEvent.KEY_TYPED) 
                {
                    System.out.println("Does Nothing");
                }
                break;
        }
            return false;
        }
	}
	
		
	

	public void windowActivated(WindowEvent arg0)
	{
            
	}


	public void windowClosed(WindowEvent arg0) 
	{
		System.out.println("Connection has been terminated.");
                System.exit(0);
	}


	public void windowClosing(WindowEvent arg0) 
	{   
                System.out.println("Connection has been terminated.");
		System.exit(0);
	}


	public void windowDeactivated(WindowEvent arg0) 
	{
		
	}


	public void windowDeiconified(WindowEvent arg0) 
	{
		
	}


	public void windowIconified(WindowEvent arg0) 
	{
		
	}


	
	public void windowOpened(WindowEvent arg0) 
	{
		
	}


	public void exceptionThrown(Exception arg0) 
	{
		JOptionPane.showMessageDialog(null, "An unhandled exception occured", " ", JOptionPane.ERROR_MESSAGE);
	}

}

