package dailyWorks.p202310;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class W05 {
	public W05(){
	}
	// 풀스캔
	public void findFileForder(String file){
		try(Stream<File> stream = Arrays.stream(new File(file).listFiles())){
			stream.forEach(fEach->{
				try {
					if(fEach.isDirectory()) {
						findFileForder(fEach.toString());
					}else{
						System.err.println(fEach.toString());
					}
				}catch(Exception e) {}
			});
		}
	}

	// 파일을 스트림으로 읽어오기
	public void dataSearch() {
		try (Stream<String> readData = new BufferedReader(new InputStreamReader(new FileInputStream("file/c2022Data.csv"),"euc-kr")).lines()){
			Map<Object, List<String[]>> mapData = readData.map(lineStr->lineStr.split(",")).collect(Collectors.groupingBy(linArr->linArr[3]));
			mapData.keySet().forEach(System.err::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//new W05().findFileForder("c://");
		new W05().dataSearch();
	}
}
