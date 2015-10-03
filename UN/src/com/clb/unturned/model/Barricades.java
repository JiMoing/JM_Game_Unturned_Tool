package com.clb.unturned.model;

import com.clb.common.util.StringUtil;

/**
 * 
 * 建筑
 * 
 * @author Cai
 *
 */
public class Barricades extends BaseItem{
	
	private static final String TIP_STORAGE = "储物空间 -";

	@Override
	protected void initCustomString() {
		String storageX = getField(FIELD_STORAGE_X);
		String storageY = getField(FIELD_STORAGE_Y);
		if (!StringUtil.isEmpty(storageX) && !StringUtil.isEmpty(storageY)) {
			setCustomNameSuffix(" (" + storageX + "x" + storageY + ")");
		}

		appendCustomDepict(TIP_STORAGE + storageX + "x" + storageY);
	}
}
