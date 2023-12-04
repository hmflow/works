package dailyWorks.p202312;

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
	/**
	   
	 */
	
	/** 1 with
	 *  2 subquery
	 *  
	*/
	String qryStr = "    SELECT A.DATA1 AS TEST , B.DATA2 \n"
			+ ", B.DATA2 , B.DATA2 FROM TABLE_1 A\r\n"
			+ "                             INNER JOIN (SELECT C DATAINN AS C FROM TB_AAA A) B\r\n"
			+ "                         ON A.DATA = B.DATA \r\n"
			+ "                             LEFT OUTER JOIN (SELECT BBB AS C FROM TB_AAA A) C\r\n"
			+ "                         ON A.DATA = C.DATA \r\n"
			+ "                  WHERE      A.DATA = '202212'   AND A.DATA = '202212' OR A.DATA = '202212'"
			+ "      OVER BY A.DDD DESC";
	private List<String> spData = null;
	private List<String> jumpData = null;
	private int defaultBlnkDefault = 7;
	public W04() {
		spData = Arrays.asList ("INNER JOIN","LEFT OUTER JOIN","JOIN", ",","ON","WHERE","FROM", "AND", "OR","OVER BY");
		jumpData = Arrays.asList ("OVER BY");
	}
	
	public void queryChange() throws Exception{
		/*
		Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
		String query  = (String) cp.getData(DataFlavor.stringFlavor);
		System.err.println(query);
		*/
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
			System.err.println(nowStr);
			qry= qry.replace(key,"\n"+ changeQry(qryData.get(key), nowLength + defaultBlnkDefault ));
			
		}
		
		System.err.println(qry);
			
	}
	
	private String changeQry(String qry ,int defaultBlnk ) {

		while(qry.contains("  ")) {
			qry = qry.replaceAll("  ", " ");
		} 
		String selectStr = qry.split("FROM")[0];
		selectStr = selectStr.trim().replaceAll("^SELECT", "");
		String resultSelect = "";
		Set<String>  taNm = new HashSet<String>();  
		List<String> spData = Arrays.asList ("INNER JOIN","LEFT OUTER JOIN","JOIN", ",","ON","WHERE","FROM", "AND", "OR","OVER BY");
		
		for(String param : selectStr.split(",")) {
			taNm.add(param.contains(".")? String.format(" %s " , param.split("\\.")[0].trim()) : "&&&");
			resultSelect += (resultSelect.equals("") ? setBlank("SELECT",defaultBlnk): setBlank(",",defaultBlnk)) + param.trim() +"\n"  ;
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
			fromRsStr +=  ( isContain ? setBlank(fromEach,defaultBlnk ) : fromEach.trim() +"\n")   ;
		}
		
		
		
		return resultSelect+ fromRsStr;
	}
	
	private String setBlank(String str , int length) {
		String returnTemp = "";
		if(jumpData.contains(str)) {
			returnTemp = String.format("%"+length+"s", str) +" "; ;
		}else {
			String[] spDataBlnk = str.split(" ");
			returnTemp = String.format("%"+length+"s", spDataBlnk[0]) +" "; 
			for(int i = 1 ; i<spDataBlnk.length; i++) {
				returnTemp += spDataBlnk[i].trim() +" ";
			}
		}
		
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
