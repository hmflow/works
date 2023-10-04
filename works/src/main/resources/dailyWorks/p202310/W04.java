package dailyWorks.p202310;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

public class W04 {

	public W04(){
	}
	// c:\\ 풀스켄
	public void findFileForder(String file){
		File[] files= new File(file).listFiles();
		Arrays.asList(true, false).forEach(eachBool->{
			try(Stream<File> tempStream = Arrays.stream(files)){
				tempStream.filter(fEach-> fEach.isFile() ==eachBool).forEach(fEach->{
					try {
						if(eachBool) {
							System.err.println(fEach.toString());
						}else {
							findFileForder(fEach.toString());
						}
					}catch(Exception e) {}
				});
			}
		});
	}
	
	
	public static void main(String[] args) {
		new W04().findFileForder("c://");
		
	}
}
