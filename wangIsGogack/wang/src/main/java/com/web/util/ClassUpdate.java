package com.web.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
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

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 2023-11-21 JAVAWANG 
 * 
 * 수정된 클래스파일을 톰캣서버에 반영을 합니다.
 */

@Controller
public class ClassUpdate { 
	
	private static String fromFolder = "SpringTilesProject\\target\\classes\\";
	private static String toFolder = ".metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\SpringTilesProject\\WEB-INF\\classes\\";
	
	private List<Map<String,Path>> differentFileInfo = new ArrayList<Map<String,Path>>();
	
	private String LOG_TEMP= "fileName           : %s  \n"
			               + "server Update Time : %s \n"
			               + "modified time\t   : %s \n"
			               + "-------------------  -----------------------------------";
    /**
     * 2023-11-22 JAVAWANG 
     * 변경된 클레스를 찾아서 업데이트를 실행합니다.
     *  
     * @return  
     */
	@RequestMapping("/controllerUpdate") 
	public void classUpdate() { 
		System.err.println("test");
		//변경되어진 폴더 혹은 파일을 업데이트 할수 있도록 합니다.
		findChangedFiles(fromFolder); 
		//변경되어져서 업데이트할 파일명과 폴더명을 확인 합니다.

		System.err.println("test");
		differentFileInfo.forEach(changeEach->{ 
			try {
				// 
				FileTime fromtime = (FileTime) Files.getAttribute(changeEach.get("fromFilePath")  ,"lastModifiedTime");
				FileTime toTime   = (FileTime) Files.getAttribute(changeEach.get("toFilePath"), "lastModifiedTime");
				System.err.println(String.format(LOG_TEMP, changeEach.get("fromFilePath").toString().replace(fromFolder, "C:\\dev\\")
						                                 , fromtime.toString()
						                                 , toTime.toString()));
					
			}catch(Exception e) {e.printStackTrace();}
			
		});

		System.err.println("test");
		// 파일카피 로직을 실행합니다.
		fileCopy();

		System.err.println("test");
	}
    /**
     * 2023-11-22 JAVAWANG 
     * 변경된 클레스를 찾아서 업데이트를 실행합니다.
     * %% 전역변수를 두지않고 바뀐값만 리턴받을수 있게 수정
     * @return  
     */
	public void findChangedFiles(String path) {
		File[] files = new File(path).listFiles();
		try(Stream<File> fileStream = Arrays.stream(files)){
			Map<Object, List<File>> fileMap = fileStream.collect(Collectors.groupingBy(File::isDirectory));
			fileMap.keySet().forEach(keyBool->{
				List<File> fileList = fileMap.get(keyBool);
				fileList.forEach(file->{ 
					try {
						if((boolean) keyBool) {
							findChangedFiles(file.toString());
						}else{
							dataSaver(file);
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
				});				
			});
		}		
	}
	
	
    /**
     * 2023-11-22 JAVAWANG 
     * 
     * 
     * @return  
     */
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
    /**
     * 2023-11-22 JAVAWANG 
     * 파일카피를 시전합니다
     * %% 전역변수를 삭제하고 파라메터를 넘길수 있도록 합니다.
     * @param  Map sqlparam  - 파라메터를 받습니다.
     * @return ModelAndView - 페이지와 파라메터를 담아서 리턴합니다.
     * @throws ""
     */
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
}
