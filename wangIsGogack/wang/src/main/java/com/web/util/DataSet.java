package com.web.util;

import java.util.HashMap;

import com.google.common.base.CaseFormat;

/**
 * 2023-11-22 이상근 (OU14480) 
 * 
 *  데이터 베이스로 받은 컬럼값을 카멜형식으로 변환해서 반환할수 있도록 합니다
 */
public class DataSet extends HashMap{ 
	private static final long serialVersionUID = 1L; 
	public Object put(Object key, Object value) {
		return super.put(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (String) key), value); 
	}

}
