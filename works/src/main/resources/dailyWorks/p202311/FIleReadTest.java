package dailyWorks.p202311;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FIleReadTest {
	
	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("D:\\SOURCE\\busan\\workspace\\SpringTilesProject\\target\\classes\\com\\web\\controller\\ViewController.class");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
