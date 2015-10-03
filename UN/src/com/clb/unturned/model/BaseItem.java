package com.clb.unturned.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.clb.common.util.StringUtil;
import com.clb.unturned.Constant;

/**
 * 
 * 道具
 * 
 * @author Cai
 *
 */
public class BaseItem {
	/** 类型 */
	public static final String FIELD_TYPE = "Type";
	public static final String FIELD_FORMAT_TYPE = "^Type (.*)?$";
	/** id */
	public static final String FIELD_ID = "ID";
	public static final String FIELD_FORMAT_ID = "^ID (\\d*)?$";
	/** 占用X */
	public static final String FIELD_X = "Size_X";
	public static final String FIELD_FORMAT_X = "^Size_X (\\d*)?$";
	/** 占用Y */
	public static final String FIELD_Y = "Size_Y";
	public static final String FIELD_FORMAT_Y = "^Size_Y (\\d*)?$";
	/** 扩展背包宽度 */
	public static final String FIELD_WIDTH = "Width";
	public static final String FIELD_FORMAT_WIDTH = "^Width (\\d*)?$";
	/** 扩展背包高度 */
	public static final String FIELD_HEIGHT = "Height";
	public static final String FIELD_FORMAT_HEIGHT = "^Height (\\d*)?$";
	/** 外部存储高度 */
	public static final String FIELD_STORAGE_X = "Storage_X";
	public static final String FIELD_FORMAT_STORAGE_X = "^Storage_X (\\d*)?$";
	/** 外部存储高度 */
	public static final String FIELD_STORAGE_Y = "Storage_Y";
	public static final String FIELD_FORMAT_STORAGE_Y = "^Storage_Y (\\d*)?$";
	/** 弹夹装弹 */
	public static final String FIELD_AMOUNT = "Amount";
	public static final String FIELD_FORMAT_AMOUNT_Y = "^Amount (\\d*)?$";
	/** 武器有效范围 */
	public static final String FIELD_RANGE = "Range";
	public static final String FIELD_FORMAT_RANGE_Y = "^Range (\\d*)?$";
	/** 武器攻击速度 */
	public static final String FIELD_FIRERATE = "Firerate";
	public static final String FIELD_FORMAT_FIRERATE_Y = "^Firerate (\\d*)?$";

	/** 补充-饱食度 */
	public static final String FIELD_FOOD = "Food";
	public static final String FIELD_FORMAT_FOOD = "^Food (\\d*)?$";
	/** 补充-能源 */
	public static final String FIELD_ENERGY = "Energy";
	public static final String FIELD_FORMAT_ENERGY = "^Energy (\\d*)?$";
	/** 补充-水分 */
	public static final String FIELD_WATER = "Water";
	public static final String FIELD_FORMAT_WATER = "^Water (\\d*)?$";
	/** 减少-辐射 */
	public static final String FIELD_DISINFECTANT = "Disinfectant";
	public static final String FIELD_FORMAT_DISINFECTANT = "^Disinfectant (\\d*)?$";
	/** 使用-补血 */
	public static final String FIELD_HEALTH = "Health";
	public static final String FIELD_FORMAT_HEALTH = "^Health (\\d*)?$";
	/** 使用-幻象 */
	public static final String FIELD_VISION = "Vision";
	public static final String FIELD_FORMAT_VISION = "^Vision (\\d*)?$";
	/** 使用-病毒 */
	public static final String FIELD_VIRUS = "Virus";
	public static final String FIELD_FORMAT_VIRUS = "^Virus (\\d*)?$";
	/** ？？耐用 */
	public static final String FIELD_DURABILITY = "Durability";
	public static final String FIELD_FORMAT_DURABILITY = "^Durability (\\d*)?$";

	/** 治疗流血 */
	public static final String FIELD_BLEEDING = "Bleeding";
	public static final String FIELD_FORMAT_BLEEDING = "^Bleeding$";
	/** 治疗骨折 */
	public static final String FIELD_BROKEN = "Broken";
	public static final String FIELD_FORMAT_BROKEN = "^Broken$";

	/** 文件 路径 */
	public String filePath;
	/** 语言 资源 */
	public Map<String, Langue> strMap;
	/** 属性 字段 */
	private JSONObject fields;
	/** 自定义 详情 */
	private StringBuilder customDepictStr;
	/** 自定义 名字后缀 */
	private String customNameStr;
	/** 是否 一初始化 自定义 字符串 */
	private Boolean isInitCustomString = false;

	public BaseItem() {
		strMap = new LinkedHashMap<String, Langue>();
		fields = new JSONObject();
	}

	/**
	 * 增加 名字
	 * 
	 * @param key
	 * @param name
	 */
	public void addName(String key, String name) {
		Langue langue = getLangue(key);
		langue.name = cleanName(name);
		strMap.put(key, langue);
	}

	/**
	 * 增加 详情
	 * 
	 * @param key
	 * @param name
	 */
	public void addDepict(String key, String depict) {
		Langue langue = getLangue(key);
		langue.depict.add(cleanDepict(depict));
		strMap.put(key, langue);
	}

	/**
	 * 清洗名字
	 * 
	 * @param str
	 * @return
	 */
	private String cleanName(String str) {
		return str.replaceAll(Constant.REPLACE_CUSTOM_NAME_SUFFIX, "").trim();
	}

	/**
	 * 清洗详情
	 * 
	 * @param str
	 * @return
	 */
	private String cleanDepict(String str) {
		if (!StringUtil.isEmpty(str)) {
			return str.replaceAll(Constant.REPLACE_CUSTOM_DEPICT_SUFFIX, "");
		}
		return "";
	}

	/**
	 * 得到语言 类型
	 * 
	 * @param key
	 * @return
	 */
	private Langue getLangue(String key) {
		Langue langue = strMap.get(key);
		if (null == langue) {
			langue = new Langue();
		}
		return langue;
	}

	/**
	 * 获取名字
	 * 
	 * @param key
	 * @return
	 */
	public String getName(String key) {
		Langue langue = getLangue(key);
		if (null == langue) {
			return "";
		}
		return langue.name;
	}

	/**
	 * 获取详情
	 * 
	 * @param key
	 * @return
	 */
	public String getDepict(String key) {
		Langue langue = getLangue(key);
		if (null == langue || langue.depict.size() == 0) {
			return "";
		}
		StringBuilder temp = new StringBuilder();
		for (String str : langue.depict) {
			temp.append(str).append(Constant.SPLIT);
		}
		return temp.toString().trim();
	}

	/**
	 * 获取 名字 带有附加字段
	 * 
	 * @param key
	 * @return
	 */
	public String getPrintName(String key) {
		if(!isInitCustomString){
			isInitCustomString = true;
			initCustomString();
		}
		String value = getName(key);
		return value + getCustomNameSuffix();
	}

	/**
	 * 获取 详情 带有附加字段
	 * 
	 * @param key
	 * @return
	 */
	public String getPrintDepict(String key) {
		if(!isInitCustomString){
			isInitCustomString = true;
			initCustomString();
		}
		return (getDepict(key) + getCustomDepictSuffix()).replaceAll("[。]{2,}", "。");
	}

	/**
	 * 获取自定义 名字 后缀 附件字段
	 * 
	 * @param strMap
	 * @return
	 */
	private String getCustomNameSuffix() {
		if (StringUtil.isEmpty(customNameStr)) {
			return "";
		}
		return customNameStr;
	}

	/**
	 * 修改 自定义 名字 后缀
	 * 
	 * @param suffix
	 */
	protected void setCustomNameSuffix(String suffix) {
		customNameStr = suffix;
	}

	/**
	 * 获取自定义 详情 后缀 附件字段
	 * 
	 * @param strMap
	 * @return
	 */
	private String getCustomDepictSuffix() {
		if (StringUtil.isEmpty(customDepictStr)) {
			return "";
		}
		return customDepictStr.substring(0, customDepictStr.length() - 2);
	}

	/**
	 * 添加 详情 后缀
	 * 
	 * @param strMap
	 * @return
	 */
	protected void appendCustomDepict(String str) {
		if (null == customDepictStr) {
			customDepictStr = new StringBuilder();
			customDepictStr.append(Constant.CUSTOM_DEPICT_TIP);
		}
		customDepictStr.append(str).append(", ");
	}

	/**
	 * 增加 附加字段
	 * 
	 * @param type
	 * @param value
	 */
	public void addField(String name, String value) {
		try {
			fields.put(name, value);
		} catch (JSONException e) {
			System.out.println("fields add error !!! " + filePath + " : " + name + "/" + value);
			e.printStackTrace();
		}
	}

	/**
	 * 得到附加字段
	 * 
	 * @return
	 */
	public String getAllFields() {
		return fields.toString();
	}

	/**
	 * 设置 附加字段
	 * 
	 * @param str
	 */
	public void setFieldsByString(String str) {
		try {
			fields = new JSONObject(str);
		} catch (JSONException e) {
			System.out.println("fields set error !!! str : " + str);
			e.printStackTrace();
		}
	}

	/**
	 * 得到 附加字段
	 * 
	 * @param name
	 * @return
	 */
	public String getField(String name) {
		String value = fields.optString(name);
		return null == value ? "" : value;
	}

	/**
	 * 得到 ID
	 * 
	 * @param name
	 * @return
	 */
	public String getID() {
		return getField(FIELD_ID);
	}

	/**
	 * 初始化 自定义 内容
	 * 
	 * 修改自定义名字后缀 setCustomNameSuffix 添加自定义详情后缀 appendCustomDepict
	 */
	protected void initCustomString() {
	}
}
