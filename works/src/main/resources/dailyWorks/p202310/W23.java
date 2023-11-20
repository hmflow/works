package dailyWorks.p202310;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class W23 {
	public W23(){
	}

	public void fileSearch(String fileName) {
		try(Stream<File> fileStram = Arrays.stream(new File(fileName).listFiles())){
			Map<Boolean, List<File>> fMap = fileStram.collect(Collectors.groupingBy(f->f.isFile()));
			fMap.get(true).stream().filter(eachF->eachF != null && eachF.toString().contains("proper")).forEach(System.out::println);
			fMap.get(false).forEach(f->{
				try {
					fileSearch(f.toString());
				}catch(Exception e) {}
				
			});
		}
	}

	public void useClipbard() {
		Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			String cpTxt = (String) cp.getData(DataFlavor.stringFlavor);
			System.err.println(cpTxt);
			String saveStr = String.format("copyed-- %S", cpTxt);
			cp.setContents(new StringSelection(saveStr), null);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void callFile() {
		String filePath = "file/c2022Data.csv";
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(fis,"euc-kr");
			BufferedReader br = new BufferedReader(isr);
			Stream<String> stream = br.lines();
			stream.map(line -> line.split(",")).forEach(line ->{
				System.err.println(line[0]);
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new W23().fileSearch("C:\\SOURCE_f\\busan"); 
	}
}
