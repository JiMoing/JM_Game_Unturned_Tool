package com.clb.unturned.model;

import com.clb.common.util.StringUtil;

/**
 * 
 * 背包
 * 
 * @author Cai
 *
 */
public class Backpacks extends BaseItem {

	private static final String TIP_SPACE = "背包空间 -";

	@Override
	protected void initCustomString() {
		String width = getField(FIELD_WIDTH);
		String height = getField(FIELD_HEIGHT);
		if (!StringUtil.isEmpty(width) && !StringUtil.isEmpty(height)) {
			setCustomNameSuffix(" (" + width + "x" + height + ")");
		}

		appendCustomDepict(TIP_SPACE + width + "x" + height);
	}
}
