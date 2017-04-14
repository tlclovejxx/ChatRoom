package biz.tlc.biz.ChatRoom;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.BusinessBlueSteelSkin;

public class WindowChatMain {

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true); 
		JDialog.setDefaultLookAndFeelDecorated(true); 
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() { 
				try{ 
					SubstanceLookAndFeel.setSkin(new BusinessBlueSteelSkin()); 
		        	ChatWindow window = new ChatWindow();
		  			window.setVisib(); 
		         	}catch(Exception e){ 
		         		e.printStackTrace(); 
		        } 
		     } 
		 });  
	} 
}
