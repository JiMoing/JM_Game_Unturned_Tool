package com.clb.unturned.model;

import com.clb.common.util.StringUtil;

/**
 * 
 * 枪
 * 
 * @author Cai
 *
 */
public class Guns extends BaseItem {

	private static final String TIP_RANGE = "射程：";
	private static final String TIP_FIRERATE = "冷却：";

	@Override
	protected void initCustomString() {
		int range = StringUtil.getInt(getField(FIELD_RANGE), 0);
		int firerate = StringUtil.getInt(getField(FIELD_FIRERATE), 0);
		if (range > 0) {
			appendCustomDepict(TIP_RANGE + range);
		}
		if (firerate > 0) {
			appendCustomDepict(TIP_FIRERATE + firerate);
		}
	}
}
