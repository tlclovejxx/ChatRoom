package biz.tlc.biz.ChatRoom;


import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;

//�������벢�ҿ��Ծ۽�������Ϣ��TextPane��
public class MyTextPane extends JTextPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int positon  = 0;
	public MyTextPane(DefaultStyledDocument doc) {
		// TODO �Զ����ɵĹ��캯�����?
		super(doc);
	}
	public MyTextPane() {
		super();
	}
	public void savePositon(){
		positon = positon+2;
	}
	public void clear(){
		positon = 0;
	}
	 public void append(String str){
	       Document doc = getDocument();
	       if (doc != null)
	       {
	           try
	           {
	              doc.insertString(doc.getLength(), str, null);
	           }
	           catch (BadLocationException e)
	           {
	           }
	       }
	    }
}

