package ci.inphb.ihm_bd;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Application {

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
				MainFrame mainframe = new MainFrame("projet Java");
				mainframe.setLocationRelativeTo(null);
				mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				mainframe.pack();
				mainframe.setVisible(true);
			}
		});
	}
}
