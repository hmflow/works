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
 

public class W04 {  
	/** 1 with
	 *  2 subquery
	 *  
	*/
 
	private List<String> spData = null;
	private List<String> jumpData = null;
	private int defaultBlnkDefault = 10;
	public W04() {
		spData = Arrays.asList ("INNER JOIN","LEFT OUTER JOIN","JOIN", ",","ON","WHERE","FROM", "AND","ORDER BY","GROUP BY", "OR");
		jumpData = Arrays.asList ("ORDER BY","GROUP BY");
	}
	
	public void queryChange() throws Exception{
		
		Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
		String qryStr  = (String) cp.getData(DataFlavor.stringFlavor);
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
			qry= qry.replace(key, resultStr  ) ;
			
		}
		System.err.println(qry); 
		cp.setContents(new StringSelection(qry), null);
	}
	
	private String toCaseQryChange(String qry ,int  stpos) {  
		Pattern pattern = Pattern.compile("CASE(.*?)END(?!.*END)"); 
		Matcher matcher  = pattern.matcher(qry); 
		
		if(matcher.find()) {
			String result = matcher.group(1);  
			int scoposInn = stpos+4;
			
			String changeVal = toCaseQryChange(result, stpos);
			
			String splitBlnk =   String.format("%"+scoposInn+"s","")  ;
			for(String strEach : Arrays.asList( " WHEN ", " THEN ", " ELSE ", " END ")) {
				changeVal =   changeVal.replaceAll(strEach,splitBlnk + strEach );
			} 
			
			qry = qry.replace(result, changeVal);
			System.err.println(qry);
		}
		return qry;
	}
	
	private String changeQry(String qry ,int defaultBlnk  ) {

		while(qry.contains("  ")) {
			qry = qry.replaceAll("  ", " ");
		} 
		String selectStr = qry.split("FROM")[0];
		//selectStr = selectStr.trim().replaceAll("^SELECT", "");
		String qryHeader = selectStr.split("SELECT")[0] ;
		selectStr = selectStr.split("SELECT")[1];
		String resultSelect = "";
		Set<String>  taNm = new HashSet<String>();  
		
		for(String param : selectStr.split(",")) {
			taNm.add(param.contains(".")? String.format(" %s " , param.split("\\.")[0].trim()) : "&&&");
			boolean isVaInn = resultSelect.equals("");
			String varInn =  isVaInn ? "SELECT" : ",";
			
			String createRow =  justBlank(varInn, defaultBlnk) + param.trim() +"\n"  ;
			if(createRow.contains("CASE")) { 
				String rsCase = toCaseQryChange(createRow, 11).replaceAll("\\bCASE\\s+WHEN\\b", "CASE WHEN").replaceAll(" ", "ㅋ");
				
				for(int i = 100; i>=1; i--) {
					String replaceVal = "ㅋ".repeat(i);
					rsCase = rsCase.replaceAll(replaceVal, (i!=1 ? "\n": "")+replaceVal.replaceAll("ㅋ", " "));
					
				}
				System.err.println(rsCase);
				createRow = rsCase;
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
		
		for(String fromEach : fromStrLst.stream().filter(each->!"".equals(each)).toList()) { 
			boolean isContain = spData.stream().filter(each -> fromEach.indexOf(each) != -1).count() > 0;
			fromRsStr +=  ( isContain ? setBlank(fromEach,defaultBlnk )  : fromEach.trim() +"\n")   ;
		} 
		
		return justBlank(qryHeader, defaultBlnkDefault) +"\n"+ resultSelect+ fromRsStr;
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
		try {
			new W04().queryChange();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
