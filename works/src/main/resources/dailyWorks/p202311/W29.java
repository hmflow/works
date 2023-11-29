package dailyWorks.p202311;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class W29 { 
	public List<File> fileFind(String path) {
		List<File> listFile = new ArrayList<File>();
		File[] files = new File(path).listFiles();
		try(Stream<File> fileStream = Arrays.stream(files)){
			Map<Boolean, List<File>> filsMap = fileStream.collect(Collectors.groupingBy(File::isDirectory));
			listFile.addAll(filsMap.getOrDefault(false, Collections.emptyList()));
			List<File> dirList = filsMap.getOrDefault(true, Collections.emptyList());
			for(File fileEach : dirList) {
				listFile.addAll(fileFind(fileEach.toString()));
			}
		}
		return listFile;
	}
	public static void main(String[] args) {
		W29 w29 = new W29();
		List<File> fileList = w29.fileFind("D:\\");
		fileList.forEach(System.out::println);
	}

}
