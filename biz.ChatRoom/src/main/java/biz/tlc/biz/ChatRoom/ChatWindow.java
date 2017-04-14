package biz.tlc.biz.ChatRoom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class ChatWindow{
	private User user;
	private String userName;
	MyTextPane newsText = new MyTextPane();
	public ChatWindow(){
		userName = JOptionPane.showInputDialog(null,"Input you nickName first .^_^. ��\n",
				"Welcome to Rabbit ChatRoom .^_^.��\n",JOptionPane.PLAIN_MESSAGE); 
		if(userName == null){System.exit(0);}
		user = new User(userName, "127.0.0.1");
		try {
			user.addRoom("biz.Exchange");
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public void setVisib(){
		JFrame frame = new JFrame("Rabbit RoomChat,this user is " + userName);
		JPanel upPanel = new JPanel();
		JPanel underPanel = new JPanel();
		final JTextArea sendText = new JTextArea(8, 38);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try {
					user.sendMessage(sendText.getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
				sendText.setText("");
				sendText.requestFocus();
			}
		});
		
		newsText.setFont(new Font("Default",Font.PLAIN,18));
		newsText.setPreferredSize(new Dimension(500,260));
		newsText.setEditable(false);
		JScrollPane scrollpane1 = new JScrollPane(newsText);
		scrollpane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		sendText.setEditable(true);
		sendText.setWrapStyleWord(true);
		sendText.setLineWrap(true);
		sendText.setCaretPosition(sendText.getDocument().getLength());
		JScrollPane scrollpanel2 = new JScrollPane(sendText);
		scrollpanel2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpanel2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		upPanel.add(scrollpane1);
		underPanel.add(scrollpanel2);
		underPanel.add(sendButton);
		
		frame.getContentPane().add(BorderLayout.NORTH, upPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, underPanel);
		frame.setSize(550, 460);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO �Զ����ɵķ������?
				super.windowClosing(e);
				try {
					user.userClose();
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				} catch (TimeoutException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				System.exit(0);
			}		
		});
		
		try {
			user.setListener(new MessageListener() {
				
				public void getMessage(String message) {
					String name = message.substring(0,message.indexOf("said")-1);
					System.out.println(name);
					setDocs(name+"\n", new Color(50,190,70), 15,StyleConstants.ALIGN_LEFT);
					setDocs(message.substring(message.indexOf(":")+1)+"\n",new Color(50,150,140),18,StyleConstants.ALIGN_LEFT);
				}
			});
		} catch (ShutdownSignalException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		} catch (ConsumerCancelledException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		try {
			user.listenMessage();
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		frame.setResizable(false);
		//writer("��Է���������?");
		frame.setVisible(true);
	}
	public void setDocs(String message, Color color,int size,int style){
		int weizhi =newsText.getCaretPosition();
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, color);
		StyleConstants.setFontSize(attrSet, size);
		StyleConstants.setAlignment(attrSet, style);
		StyledDocument doc = newsText.getStyledDocument();
		doc.setParagraphAttributes(weizhi, weizhi+message.length(), attrSet, false);
		try {
			doc.insertString(doc.getLength(),message,attrSet);
		} catch (BadLocationException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
