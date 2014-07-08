/*###################################################################
 * The following code is the Main class for Finch Application 		#
 * The FinchConnect class is initiated first for visual 			#
 * display of connection, and then the FinchApp class.				#
 * ##################################################################
 * The code is not fully complete, but all actions work.			#
 * Feel free to modify to make the code function better :D			#
 *#################################################################*/


package finchApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


/**@author Saurabh Rathod*/
public class FinchMain 
{

	/**Following is for the FinchApp**/
	static int wait = 2000;
	
	static ActionListener fa2 = new FA2();
	
	static Timer waitTimer = new Timer(wait, fa2);
	
	static FinchConnect fc = new FinchConnect();

	
	public static void main(String[] main)
	{

				fc.setVisible(true);

				waitTimer.start();
	}

		
		
		static class FA2 implements ActionListener
		{

			
			public void actionPerformed(ActionEvent e) 
			{

				waitTimer.stop();
				
				FinchApp f = new FinchApp();
				f.setVisible(true);

				/**For testing the UI*/
				/**Thread t = new Thread(new TestUI());
				t.setName("Test Finch UI");
				t.setDaemon(true);
				t.start();
				t.run();**/
				fc.dispose();
			}
		
		}
	
}
