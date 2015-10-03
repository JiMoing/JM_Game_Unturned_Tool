package com.clb.unturned.model;

/**
 * 
 * 工厂
 * 
 * @author Cai
 *
 */
public class ItemFactory {
	/**背包*/
	private static final String ITEM_BACKPACKS = "\\Items\\Backpacks";
	/**建筑*/
	private static final String ITEM_BARRICADES = "\\Items\\Barricades";
	/**裤子*/
	private static final String ITEM_PANTS = "\\Items\\Pants";
	/**衣服*/
	private static final String ITEM_SHIRTS = "\\Items\\Shirts";
	/**背心*/
	private static final String ITEM_VESTS = "\\Items\\Vests";
	/**枪*/
	private static final String ITEM_GUNS = "\\Items\\Guns";
	/**弹夹*/
	private static final String ITEM_MAGAZINES = "\\Items\\Magazines";
	
	/**食物*/
	private static final String ITEM_FOOD = "\\Items\\Food";
	/**水壶*/
	private static final String ITEM_REFILLS = "\\Items\\Refills";
	/**饮料*/
	private static final String ITEM_WATER = "\\Items\\Water";
	/**医疗*/
	private static final String ITEM_MEDICAL = "\\Items\\Medical";
	
	public static BaseItem create(String filePath) {
		if (filePath.startsWith(ITEM_BACKPACKS)) {
			return new Backpacks();
		} else if (filePath.startsWith(ITEM_BARRICADES)) {
			return new Barricades();
		} else if (filePath.startsWith(ITEM_PANTS)) {
			return new Pants();
		} else if (filePath.startsWith(ITEM_SHIRTS)) {
			return new Shirts();
		} else if (filePath.startsWith(ITEM_VESTS)) {
			return new Vests();
		} else if (filePath.startsWith(ITEM_GUNS)) {
			return new Guns();
		} else if (filePath.startsWith(ITEM_MAGAZINES)) {
			return new Magazines();
		} else if (filePath.startsWith(ITEM_FOOD)) {
			return new Food();
		}  else if (filePath.startsWith(ITEM_WATER)) {
			return new Water();
		}  else if (filePath.startsWith(ITEM_REFILLS)) {
			return new Refills();
		}  else if (filePath.startsWith(ITEM_MEDICAL)) {
			return new Medical();
		}  else {
			return new BaseItem();
		}
	}
}
