package dailyWorks.p202310;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

public class W12 {
	public W12(){
	}

	public void clipboardWork() throws Exception {
		Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
		String strData = (String) cp.getData(DataFlavor.stringFlavor);
		System.err.println(strData);
		cp.setContents(new StringSelection(strData + " __ TEST"), null);
	}

	public void clipboardWork2() throws Exception {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard cp = toolkit.getSystemClipboard();
		String cpdStr = copyContent(cp);
		System.err.println(cpdStr);

		cp.setContents(new StringSelection(cpdStr + " ___ copied"), null);
		System.err.println(copyContent(cp));
	}

	private String copyContent(Clipboard cp) throws Exception{
		return (String) cp.getData(DataFlavor.stringFlavor);
	}

	public static void main(String[] args) {
		try {
			new W12().clipboardWork2() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
