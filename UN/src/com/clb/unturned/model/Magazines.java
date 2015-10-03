package com.clb.unturned.model;

import com.clb.common.util.StringUtil;

/**
 * 
 * 弹夹
 * 
 * @author Cai
 *
 */
public class Magazines extends BaseItem {

	@Override
	protected void initCustomString() {
		int amount = StringUtil.getInt(getField(FIELD_AMOUNT), 0);
		if(amount>1){
			setCustomNameSuffix(" (" + amount + ")");
		}
	}

}
