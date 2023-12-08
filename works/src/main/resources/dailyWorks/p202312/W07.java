package dailyWorks.p202312;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class W07 {  
	/** 1 with
	 *  2 subquery
	 *  
	*/ 
	private String qqqq = " /** dadadad \n aaaaaa   */  WITH EXAAA AS ( SELECT CASE WHEN TEAA =1 THEN 0 ELSE 1 END AS DATA FROM DUAL ), EADDD AS (/*ㅁㄴㅇㅁㄴㅇ*/ SELECT DATA FROM UDATA ) \n"
			+ " /** dadadad */ SELECT "
			+ "  CASE WHEN A.VALaa = 1111 THEN 		CASE WHEN A.VAL_R = 21 THEN A.VAL_2 ELSE 			CASE WHEN A.VAL_R = 223 THEN 				CASE WHEN A.VAL_R = 223 THEN A.VAL_2 ELSE (/*ㅁㄴㅇㅁㄴㅇ*/SELECT * FROM DUAL) 				END ELSE A.VVV 			END ELSE B.TEST 		END 	ELSE '' END AS CASE_DATA  "
  
			+ "   ,A.DATA1 AS TEST         , B.DATA2         , B.DATA2         , B.DATA2    , CASE WHEN A.VAL = 1                THEN A.VAL_2                ELSE C.DATA END AS VVALUE      FROM TABLE_1 A     INNER JOIN (                             SELECT C DATAINN AS C                    FROM TB_AAA A                ) B        ON A.DATA = B.DATA      LEFT OUTER JOIN (                                   SELECT BBB AS C                          FROM TB_AAA A                          LEFT OUTER JOIN TB_JAVAWANG B                            ON A.VAL = B.VAL                      ) C        ON A.DATA = C.DATA     WHERE A.DATA = '202212'       AND A.DATA = '202212'        OR A.DATA = '202212'  ORDER BY A.DDD DESC  GROUP BY A.AA         , B.AAAER DESC  ";
	private List<String> spData = null;
	private List<String> jumpData = null;
	private int defaultBlnkDefault = 10;
	private String widthData = ""; 
	public void  qryClean111aa(){
		widthData = "";
		spData = Arrays.asList ("INNER JOIN","LEFT OUTER JOIN","JOIN", ",","ON","WHERE","FROM", "AND","ORDER BY","GROUP BY", "OR" , "WITH");
		jumpData = Arrays.asList ("ORDER BY","GROUP BY");
		Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
		String qryStr = "";
		try {
			qryStr=  (String) cp.getData(DataFlavor.stringFlavor);
		}catch(Exception e) {}
		 
		System.err.println(qryStr);
		
		Map<String, String> qryData = new HashMap<String,String>();
		String qry = qryStr.toUpperCase().replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\t"," "); 
		Pattern pattern = Pattern.compile("\\(([^)]*SELECT[^)]*)\\)");
	    Matcher matcher = pattern.matcher(qry);
 
	    int sql = 0;
		while (matcher.find()) {
            String match = matcher.group(1);   
            String key = String.format("[qry%d]" , sql);
            qryData.put(key, match);
            qry = qry.replace(match,key); 
            sql ++;
        }
		
		qry = changeQry(qry, defaultBlnkDefault);
		for(String key : qryData.keySet()) {
			List<String> strList = Arrays.stream(qry.split("\n")).filter(f-> f.indexOf(key) != -1).collect(Collectors.toList());
			String nowStr = strList.get(0);
			int nowLength = nowStr.split("\\(")[0].length(); 
			String[] splitStr = changeQry(qryData.get(key) , 7 ).split("\n");
			String resultStr = "";
			for(int i = 0 ; i < splitStr.length; i++) {
				resultStr += (i > 0 ?   String.format("%"+(nowLength+1)+"s", "") : "")+splitStr[i] +"\n";
			}
			resultStr += String.format("%"+(nowLength)+"s", "");
			qry= qry.replace(key, resultStr); 
		}   
		
		if(qry.indexOf("WITH") != -1) {
			String withStr ="";
			String qryMain = "";
			int open = 0;
			for(String strEach : qry.split("\n")) { 
				
				if(open != 99 && (strEach.indexOf("(") != -1 || strEach.indexOf(")") != -1) ){
					for(String cha : Arrays.asList("\\(","\\)")) {
						int now = strEach.split(cha).length -1; 
						now =  now * (cha.equals("\\(") ? 1 : -1);
						open += now; 
					}
				}

				if((strEach.indexOf("SELECT") != -1 && open == 0) || open == 99){
					open = 99;
					qryMain += strEach +"\n"; 
				}else {
					withStr += strEach +"\n"; 
				} 
			}  
			  
			qry = toSetBeforeSelect(withStr) +"\n" +qryMain;
		} 
		
		System.err.println(qry); 
		cp.setContents(new StringSelection(qry), null); 
	}
	
	
	private String toCaseQryChange(String qry ,int  stpos) {   
		Pattern pattern = Pattern.compile("CASE(.*?)END(?!.*END)"); 
		Matcher matcher  = pattern.matcher(qry);   
		if(matcher.find()) {
			String result = matcher.group(1);   
			int scoposInn = stpos+   5; 
			String changeVal = toCaseQryChange(result, stpos);  
			String splitBlnk =   String.format("%"+scoposInn+"s","")  ; 
			for(String strEach : Arrays.asList( " WHEN ", " THEN ", " ELSE ", " END ")) { 
				changeVal =   changeVal.replaceAll(strEach,splitBlnk + strEach );  
			}  
			qry = qry.replace(result, changeVal);  
 
		}
		return qry;
	}
	
	private String toSetBeforeSelect(String withStr) { 
		Map<String, String> qryData2 = new HashMap<String,String>();
		String qry2 = withStr.toUpperCase().replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\t"," "); 
		Pattern pattern2 = Pattern.compile("\\(([^)]*SELECT[^)]*)\\)");
	    Matcher matcher2 = pattern2.matcher(qry2);
	    
	    int sql2 = 0;
		while (matcher2.find()) {
            String match = matcher2.group(1);   
            String key = String.format("[qry%d]" , sql2);
            qryData2.put(key, match);
            qry2 = qry2.replace(match,key); 
            sql2 ++;
        }
		qry2 = qry2.replaceAll("\\(", "\\(").replaceAll("\\)", "\n\\)");
		for(String key : qryData2.keySet()) {
			 
			String[] splitStr = changeQry(qryData2.get(key) , defaultBlnkDefault ).split("\n");
			String resultStr = "";
			for(int i = 0 ; i < splitStr.length; i++) {
				resultStr +=  splitStr[i] +"\n";
			} 
			qry2= qry2.replace(key, resultStr); 
		}   

		qry2 =qry2.replaceAll("\n", "\n    ")
		          .replaceAll("\\(\\s+\n" , "(\n")
		          .replaceAll("^","    ")
		          .replaceAll("\\)\\s\\/\\*",")\n    /*")
		          .replaceAll("\n\\s+\\)", "\n    )")
		          .replaceAll("\\(\\/\\*", "\n\s\s\s\s\s\s\s\s/*")
		          .replaceAll("WITH ", "\n    WITH ");
 
		 
		
		return qry2;
	}
	
	private String changeQry(String qry ,int defaultBlnk  ) {  
		while(qry.contains("  ")) {
			qry = qry.replaceAll("  ", " ");
		} 
		String selectStr = qry.split("FROM")[0]; 
		String qryHeader = selectStr.split("SELECT")[0]  ;
		 
		selectStr = selectStr.split("SELECT")[1];
		String resultSelect = "";
		Set<String>  taNm = new HashSet<String>();   
		
		for(String param : selectStr.split(",")) {
			taNm.add(param.contains(".")? String.format(" %s " , param.split("\\.")[0].trim()) : "&&&");
			boolean isVaInn = resultSelect.equals("");
			String varInn =  isVaInn ? "SELECT" : ",";
			
			String createRow =  justBlank(varInn, defaultBlnk) + param.trim() +"\n"  ;
			if(createRow.contains("CASE")) { 
				createRow =createRow.trim();
				createRow = createRow.replaceFirst(",", "") ; 
				
				String rsCase = toCaseQryChange(createRow,  0).replaceAll(" ", "ㅋ");
				
				for(int i = 100; i>=1; i--) {
					String replaceVal = "ㅋ".repeat(i);
					rsCase = rsCase.replaceAll(replaceVal, (i!=1 ? "\n": "")+replaceVal.replaceAll("ㅋ", " "));
				} 
				rsCase = "," +rsCase;
				String rsChange = "";
				for(String eachRow  : rsCase.split("\n")) {
					int sppend = defaultBlnkDefault;
					if(eachRow.replaceAll(" ", "").equals("END")) {
						sppend += 5;
					}else if(eachRow.replaceAll(" ", "").equals(",CASE")) {
						sppend -= 1;
					} 
					rsChange += String.format("%"+sppend+"s" ,"") + eachRow +"\n";
				} 
				createRow = rsChange.replaceFirst("\\bCASE\\s+WHEN\\b", "CASE WHEN"); 
			}
			resultSelect += createRow;
		}
 
		String fromStr = "FROM" + qry.split("FROM")[1]; 
		 
		String regex = String.join("|", spData.stream().map(Pattern::quote).toArray(String[]::new));
		Matcher matcher = Pattern.compile(regex).matcher(fromStr);

		int lastEnd = 0;
		List<String> fromStrLst = new ArrayList<String>();
		while(matcher.find()) {
			int start =  matcher.start();
			int end = matcher.end();
			fromStrLst.add(fromStr.substring(lastEnd,start).trim());
			fromStrLst.add(fromStr.substring(start,end).trim());
			lastEnd = end;
		}
		if (lastEnd < fromStr.length()) {
			fromStrLst.add(fromStr.substring(lastEnd));
        }
		String fromRsStr =  "";  
		
		for(String fromEach : fromStrLst.stream().filter(each->!"".equals(each)).collect(Collectors.toList())) { 
			boolean isContain = spData.stream().filter(each -> fromEach.indexOf(each) != -1).count() > 0;
			fromRsStr +=  ( isContain ? setBlank(fromEach,defaultBlnk )  : fromEach.trim() +"\n")   ;
		} 
		return (qryHeader  +"\n"+ resultSelect+ fromRsStr).replaceAll("      ,SELECT CASE", "SELECT CASE");
	}
	
	private String setBlank(String str , int length) {
		String returnTemp = "";
		if(jumpData.contains(str)) {
			returnTemp =justBlank(str , length);
		}else {
			String[] spDataBlnk = str.split(" ");
			returnTemp = justBlank(spDataBlnk[0] , length) ;  
			for(int i = 1 ; i<spDataBlnk.length; i++) {
				returnTemp += spDataBlnk[i].trim() +" ";
			}
		} 
		return returnTemp;
	}
	
	private String justBlank(String str , int length) { 
		String returnTemp = "";
		returnTemp = String.format("%"+length+"s", str ) +" ";   
		return returnTemp;
	}
 
	public static void main(String[] args) {
		new W07().qryClean111aa();
	}
}
