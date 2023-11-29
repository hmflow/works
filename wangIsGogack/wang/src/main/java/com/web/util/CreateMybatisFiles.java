package com.web.util;
 
import java.io.File; 
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections; 
import java.util.List;
import java.util.Map; 
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RestController;

import com.web.service.DbConnecterService;
 

/**
 * 2023-11-22 JAVAWANG 
 * 
 *데이터베이스에 저장되어진 엑셀파일을 마이바티스XML과 Mapper을 생성합니다.
 */
@RestController
public class CreateMybatisFiles {
    
	@Autowired
	private DbConnecterService dbConnService;
	
	/**
     * 2023-11-20 JAVAWANG 
     * 생성되어진 매퍼클래스파일과 매퍼가 저장되어질 경로를 지정합니다.
     */
	//생성되어질 클래스파일
	private String ABSTRACT_MAPPER_CODE = 
			        "package com.web.mapperImpls.mappers;\n"+
					"import org.apache.ibatis.annotations.Mapper;\n"+
					"import com.web.mapper.MybatisMapper;\n"+
					"// CreateMybatisFile 클래스로 부터 생성되어진 파일입니다.\n"+
					"@Mapper\n"+
					"public abstract class %sMapper implements MybatisMapper {}";
	//생성되어질 매퍼의 경로
	private String MAPPERS_JAVA_PACKAGE_PATH = "D:\\SOURCE\\busan\\workspace\\SpringTilesProject\\src\\main\\java\\com\\web\\mapperImpls\\mappers";
	//생성되어질 쿼리XML파일의 경로
	private String MAPPERS_XML_PACKAGE_PATH = "D:\\SOURCE\\busan\\workspace\\SpringTilesProject\\src\\main\\resources\\mapper\\mappers";
	// 마이바티스 XML 파일의 몸체부분코드 설정합니다.
	private String XML_QRY_MAIN =  "<?xml version='1.0' encoding='UTF-8'?> \n"+
								   "<!DOCTYPE mapper PUBLIC '-//mybatis.org//DTD Mapper 3.0//EN'  '../../../dtd/mybatis-3-mapper.dtd'>\n"+
								   "<!--CreateMybatisFile 클래스로 부터 생성되어진 파일입니다. -->\n"+
								   "<mapper namespace='com.web.mapperImplss.mappers.%s'>\n"+
							  	   "%s\n" +
								   "</mapper> \n";
	
	// 마이바티스 XML 파일의 코드부분을 설정합니다.
	private String XML_QRY_BODY ="\t<%s id='%s' parameterType='%s' %s> \n"
								+"%s"
							    +"\t</%s>\n";
	/**
     * 2023-11-22 JAVAWANG 
     *  
     * (Controller) 데이터 베이스에 적용되어진 쿼리정보를 기준으로 바이바티스 소스파일을 생성합니다.
     * @param  Map - 파라메터를 받습니다.
     * @return  
     */
	@PostMapping("/mybatisFileMake")
	public void mybatisFileMake(@RequestBody  Map<String,Object> sqlparam) { 
		// mappers/util/ADMIN01 의 findMapper쿼리를 호출합니다
		List<Map> qryData = (List<Map>)dbConnService.selectDataList("com.web.mapperImplss.util.UtilMapper.findMapper", sqlparam);
		// create 함수에서 폴더생성과 mybatis 의xml파일생성을 합니다.
		createMapperAndXml(qryData);
	}
	
    /**
     * 2023-11-22 JAVAWANG 
     *  
     *    카테고리2개와 페이지명을 받아 반환합니다.
     * @param  List<Map> category1 - 패키지 내의 소스파일을생성합니다. 
     * @return  
     */
	private void createMapperAndXml(List<Map> qryData) {
		// 생성전에 경로를 모두 삭제 합니다.
		removeAllMapperAndXml();
		// 파라메터로 받은 List<Map> 의 Map 의 mapper 별로 분류합니다.
		// mapper은 MAPPERS_PACKAGE_PATH 아래의 패키지명이 되어집니다.
		Map<Object, List<Map>> mapStream = qryData.stream().collect(Collectors.groupingBy(m->m.get("mapper")));
		// 위에서 분류한 mapper별로 분류한 Xml파일을 생성합니다.
		mapStream.keySet().forEach(eachSeg ->{ 
			String mapperFolderName = (String) eachSeg;
			String mapperClassName = (mapperFolderName.charAt(0)+"").toUpperCase() + mapperFolderName.substring(1) ;
			/**  마이바티스 쿼리파일을 파일을 생성합니다. */
			makeFile(MAPPERS_JAVA_PACKAGE_PATH,mapperClassName +"Mapper.java",String.format(ABSTRACT_MAPPER_CODE,  mapperClassName ));
			 
			/** 마이바티스 쿼리파일을 파일을 생성합니다. */
			// xml 파일생성 을위해 폴더 생성
			final String xmlFilePath = MAPPERS_XML_PACKAGE_PATH + File.separatorChar + mapperFolderName;
			// 폴더를 생성합니다.
			File folderInfo= new File(xmlFilePath);
			if(!folderInfo.exists()) {
				folderInfo.mkdir(); 
			} 
			// 현재 매퍼의 버젼별로 분류 합니다.
			Map<Object, List<Map>> grupByVerMap = mapStream.get(eachSeg).stream().collect(Collectors.groupingBy(eachInfo-> eachInfo.get("sqlVer")));
			// 마이바티스 XML 파일을 생성합니다.
			// 버젼을 기준으로 파일을생성 할 수 있도록 합니다.
			grupByVerMap.keySet().forEach(keyVer->{
				// 쿼리 내용을 저장 할 파라메터
				StringBuffer fileEachStr = new StringBuffer();
				// 각각의 쿼리를 생성합니다.
				grupByVerMap.get(keyVer).forEach(eachList->{ 
					// 쿼리 문자열을 두칸 탭을 한다음 칸넘김단위로 수정합니다.
					String[] sqlString = ((String)eachList.get("sql")).split("\n");
					// 쿼리 결과 값이 저장되어지는 변수
					StringBuffer sqlStrBuffer = new StringBuffer(); 
					Arrays.asList(sqlString).forEach(strRow->{
						sqlStrBuffer.append("\t\t" + strRow +"\n");	
					});
					// sqltype 이 select 이면 resultType를입력합니다.
					String resultTypeXml = "select".equals(eachList.get("sqlType")) ? " resultType='DataSet'" : ""; 
					// XML_QRY_BODY 문자열 순번대로 문자열을변경합니다 . [ 시작태그, 아이디,쿼리, 종료태그] 
					String nowQueryInfo = String.format(XML_QRY_BODY, eachList.get("sqlType")
							                                        , eachList.get("sqlId")
							                                        , eachList.get("paramType")
							                                        , resultTypeXml
							                                        , sqlStrBuffer.toString()
							                                        , eachList.get("sqlType"));
					// 쿼리를 임시 저장합니다.
					fileEachStr.append(nowQueryInfo);
				}); 
				// 파일명칭을 설정합니다. 
				String fileName = mapperFolderName.toUpperCase()+ String.format("%2s", String.valueOf(keyVer)).replaceAll(" ", "0")  + ".xml"; 
				// 파일생성 로직을 실행합니다
				makeFile(xmlFilePath , fileName, String.format(XML_QRY_MAIN, mapperClassName+"Mapper" , fileEachStr.toString())); 
			});  
		});
		
	}
	
    /**
     * 2023-11-23 JAVAWANG 
     *  
     * 파일을생성합니다
     * @param  filePath filePath - 생성할 파일경로
     * @param  filePath fileNm   - 파일명
     * @param  filePath content - 소스코드
     * 
     * @return  
     */
	private void makeFile(String filePath, String fileNm, String content) {
 		File folderInfo= new File(filePath + File.separatorChar + fileNm  );
		// 매퍼 파일생성
		try (FileWriter writer = new FileWriter(folderInfo, true)) {
			// 매퍼 파일을 생성합니다
			writer.append(content);
			writer.flush();
		} catch (IOException e) { 
			e.printStackTrace();
		}   
	}
	
    /**
     * 2023-11-23 JAVAWANG 
     *  
     * 매퍼가 생성되어지기 전에 파일을 정리합니다.
     * @return  
     */
	private void removeAllMapperAndXml() {
		// 삭제할 폴더와 데이터의 경로를 호출합니다.
		List<File> delFileDataLst = new ArrayList<File>();
		//  매퍼 JAVA 파일과 쿼리 XML 파일의 경로를 각각 데이터를 불러옵니다.
		for(String path  : Arrays.asList(MAPPERS_JAVA_PACKAGE_PATH, MAPPERS_XML_PACKAGE_PATH)) {
			// 가져온 파일을 저장합니다.
			delFileDataLst.addAll(getRemoveFilesInfo(path,new ArrayList<File>()));
		};
		// 삭제할 파일을 역순으로 변환하여 삭제를 실행합니다.
		delFileDataLst.stream().sorted(Collections.reverseOrder()).forEach(file->{
			file.delete();
		});
	}
    /**
     * 2023-11-23 JAVAWANG 
     *  
     * 매퍼와 XML파일을 삭제하기위해 경로와 파일을 가져옵니다.
     * @return  
     */
	private List<File> getRemoveFilesInfo(String path,List<File> stack) {
		// 리턴할 데이터
		List<File> resultStack = new ArrayList<File>();
		// 매퍼 JAVA 파일과 쿼리 XML 파일의 경로와 위를 가져오기위해 설정되어진 경로 가져옵니다.
	    try (Stream<File> filesStream = Arrays.stream(new File(path).listFiles())) {
	    	// 위에 스트림으로 가져온 경로를 폴더와 파일로 구분합니다. File.isDirectory 함수를 이용하여 true, false 로 구분합니다.
	        Map<Boolean, List<File>> fileAndFoldMap = filesStream.collect(Collectors.groupingBy(File::isDirectory));
	        // 파일과 디렉토리를 각각 호출합니다
	        List<File> dirs = fileAndFoldMap.getOrDefault(true, Collections.emptyList());
	        List<File> files = fileAndFoldMap.getOrDefault(false, Collections.emptyList());
	        // 현재 디렉토리의 파일을 모두 추가합니다.
	        resultStack.addAll(files);	
	        //폴더아래의 데이터를 스캔합니다.
	        for(File dir : dirs) {
	        	// 폴더를 추가하고 하위폴더를 호출합니다.
	            resultStack.add(dir);
	            resultStack.addAll(getRemoveFilesInfo(dir.toString(), resultStack));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return resultStack;
	}
	 
}
