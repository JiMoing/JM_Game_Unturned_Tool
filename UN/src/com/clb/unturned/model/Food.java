package com.clb.unturned.model;

import com.clb.common.util.StringUtil;


/**
 * 
 * 食物
 * 
 * @author Cai
 *
 */
public class Food extends BaseItem{
	
	private static final String TIP_FOOD = "饱食度 +";
	private static final String TIP_ENERGY = "精力 +";
	private static final String TIP_WATER = "水分 +";
	private static final String TIP_DISINFECTANT = "健康 +";
	private static final String TIP_HEALTH = "血 +";
	private static final String TIP_VISION = "幻象 +";
	private static final String TIP_VIRUS = "病毒 +";
	private static final String TIP_DURABILITY = "耐用：";
	
	@Override
	protected void initCustomString() {
		int food = StringUtil.getInt(getField(FIELD_FOOD), 0);
		if (food > 0) {
			appendCustomDepict(TIP_FOOD + food);
		}
		int water = StringUtil.getInt(getField(FIELD_WATER), 0);
		if (water > 0) {
			appendCustomDepict(TIP_WATER + water);
		}
		int health = StringUtil.getInt(getField(FIELD_HEALTH), 0);
		if (health > 0) {
			appendCustomDepict(TIP_HEALTH + health);
		}
		int energy = StringUtil.getInt(getField(FIELD_ENERGY), 0);
		if (energy > 0) {
			appendCustomDepict(TIP_ENERGY + energy);
		}
		int disinfectant = StringUtil.getInt(getField(FIELD_DISINFECTANT), 0);
		if (disinfectant > 0) {
			appendCustomDepict(TIP_DISINFECTANT + disinfectant);
		}
		
//		int vision = StringUtil.getInt(getField(FIELD_VISION), 0);
//		if (vision > 0) {
//			appendCustomDepict(TIP_VISION + vision);
//		}
//		int virus = StringUtil.getInt(getField(FIELD_VIRUS), 0);
//		if (virus > 0) {
//			appendCustomDepict(TIP_VIRUS + virus);
//		}
//		int durability = StringUtil.getInt(getField(FIELD_DURABILITY), 0);
//		if (durability > 0) {
//			appendCustomDepict(TIP_DURABILITY + durability);
//		}
	}
}
