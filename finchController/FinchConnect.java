/*
 * The following code is for visual display of the
 * connection of FinchApp.
 * 
 * Once connected, this class will be disposed from FinchMain
 * 
 * The code is not fully complete, but all actions work.
 * Feel free to modify to make the code function better :D
 */

package finchApp;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**@author Saurabh Rathod*/
public class FinchConnect extends JFrame 
{
	

	private static final long serialVersionUID = 927L;

	JPanel main = new JPanel();
	
	JLabel status;
		
	public FinchConnect()
	{
		
	
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
		
		
	    	
	    	
		setIconImage(null); 
		
		setTitle("Finch Control Panel");
		setSize(270,110);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		status = new JLabel();
		
		getContentPane().add(main, BorderLayout.CENTER);
		main.add(status);
		status.setText("Connecting to Finch...");
		
		
	}
	
}
