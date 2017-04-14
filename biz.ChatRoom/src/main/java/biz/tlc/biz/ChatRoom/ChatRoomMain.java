package biz.tlc.biz.ChatRoom;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class ChatRoomMain {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Rabbit ChatRoom .^_^.");
		System.out.println("Type q to exit...");
		System.out.println("Input you nickName first .^_^.");
		String name = input.nextLine();
		final User user = new User(name, "127.0.0.1");
		try {
			user.addRoom("biz.Exchange");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (TimeoutException e1) {
			e1.printStackTrace();
		}
		user.setListener(new MessageListener() {
			
			@Override
			public void getMessage(String message) {
				System.out.println(message);
			}
		});
		try {
			user.listenMessage();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Hello " + name +", you can chat form now, enjoy it");
		
		String val = null; 
		do{
            val = input.next();      
            try {
				user.sendMessage(val);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }while(!val.equals("q"));  
        System.out.println("Goodbye .^_^.");
        input.close(); 
        try {
			user.userClose();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
	
}
