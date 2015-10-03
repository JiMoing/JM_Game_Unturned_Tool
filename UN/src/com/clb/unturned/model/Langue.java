package com.clb.unturned.model;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 语言文字 资源
 * 
 * @author Cai
 *
 */
public class Langue extends BaseItem{

	/** 名字 */
	public String name;
	/** 描述 */
	public List<String> depict;
	
	public Langue(){
		depict = new ArrayList<String>();
	}
}
