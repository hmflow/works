package dailyWorks.p202310;

import java.io.File;
import java.util.Arrays;
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
				}catch(Exception e) {};
				
			});
		}
	}
	
	
	public static void main(String[] args) {
		new W05().findFileForder("c://");
		
	}
}
