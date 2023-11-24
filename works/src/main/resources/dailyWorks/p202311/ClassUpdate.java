package dailyWorks.p202311;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassUpdate {
	private static String fromFolder = "D:\\SOURCE\\busan\\workspace\\SpringTilesProject\\target\\classes\\";
	private static String toFolder = "D:\\SOURCE\\busan\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\SpringTilesProject\\WEB-INF\\classes\\";
	
	private List<Map<String,Path>> differentFileInfo = new ArrayList<Map<String,Path>>();
	
	private String LOG_TEMP= "fileName           : %s  \n"
			               + "server Update Time : %s \n"
			               + "modified time\t   : %s \n"
			               + "------------------------------------------------------";
	
	public ClassUpdate() {
		findChangedFiles(fromFolder);
		differentFileInfo.forEach(changeEach->{ 
			try {
				FileTime fromtime = (FileTime) Files.getAttribute(changeEach.get("fromFilePath")  ,"lastModifiedTime");
				FileTime toTime   = (FileTime) Files.getAttribute(changeEach.get("toFilePath"), "lastModifiedTime");
				System.err.println(String.format(LOG_TEMP, changeEach.get("fromFilePath").toString().replace(fromFolder, "")
						                                 , fromtime.toString()
						                                 , toTime.toString()));
					
			}catch(Exception e) {e.printStackTrace();}
			
		});
		fileCopy();
	}
	
	public void findChangedFiles(String path) {
		File[] files = new File(path).listFiles();
		try(Stream<File> fileStream = Arrays.stream(files)){
			Map<Object, List<File>> fileMap = fileStream.collect(Collectors.groupingBy(file->file.isDirectory()));
			fileMap.keySet().forEach(keyBool->{
				List<File> fileList = fileMap.get(keyBool);
				fileList.forEach(file->{ 
					try {
						if((boolean) keyBool) {
							findChangedFiles(file.toString());
						}else{
							dataSaver(file);
						}
					}catch(Exception e) { }
				});				
			});
		}		
	}
	
	private void dataSaver(File file) throws Exception {
		Path fromFilePath = file.toPath();  
		String dtlFileInfo = toFolder+ fromFilePath.toString().replace(fromFolder,"");
		Path toFilePath = Paths.get(dtlFileInfo) ;
		
		 
        if (Files.mismatch(fromFilePath, toFilePath) != -1) { 
			Map<String, Path> difFileInfo = new HashMap<String, Path>();
			difFileInfo.put("fromFilePath", fromFilePath);
			difFileInfo.put("toFilePath", toFilePath);
			differentFileInfo.add(difFileInfo);
		}
	}
	
	private void fileCopy() {
		differentFileInfo.forEach(fileInfoMap->{
			try {
				Path fromFilePath = fileInfoMap.get("fromFilePath");
				Path toFilePath = fileInfoMap.get("toFilePath");
				Files.copy(fromFilePath,toFilePath, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("ok");
			}catch(Exception e) {e.printStackTrace();}
		});
	}
	
	public static void main(String[] args) {
		new ClassUpdate();
		
	}
	
	
}
