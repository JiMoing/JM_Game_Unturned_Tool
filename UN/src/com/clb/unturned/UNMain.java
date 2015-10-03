package com.clb.unturned;

import com.clb.unturned.util.ItemUtil;

public class UNMain {
	//UN安装目录
	private static final String DIR_APP_UN = "E:\\Games\\Steam\\steamapps\\common\\Unturned\\Bundles";
	
	//UN 英文资源文件
	private static final String DIR_EN = "E:\\Code\\Temp\\Bundles-en";
	//UN 中文 资源文件
	private static final String DIR_CN = "E:\\Code\\Temp\\Bundles-cn";
	//整理后的 资源文件
	private static final String DIR_OK = "E:\\Code\\Temp\\Bundles";
	//翻译文件
	private static final String EXCEL_M1 = "E:\\Code\\Temp\\String-m1.xls";
	private static final String KEY_OK = "Bundles-cn";
	
	public static void main(String[] args) {
		/** 1.copy UN 里面 资源文件 */
//		ItemUtil.copyFileBat(DIR_APP_UN, DIR_EN);
		/** 2.读取，打印到excel */
//		ItemUtil.mergeResToFile(EXCEL_M1, DIR_EN, EXCEL_M1);//注意删除 excel 同标题 翻译
		/** 3.读取翻译 生成资源文件  */
		ItemUtil.createTranslatorFiles(DIR_OK, EXCEL_M1, KEY_OK);
	}

}
