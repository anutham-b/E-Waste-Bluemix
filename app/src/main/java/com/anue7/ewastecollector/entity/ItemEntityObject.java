package com.anue7.ewastecollector.entity;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

@IBMDataObjectSpecialization("ItemEntityObject")
public class ItemEntityObject extends IBMDataObject {
	public static final String CLASS_NAME = "ItemEntityObject";
	private static final String ITEM_TYPE = "itemType";
	private static final String ITEM_QUANTITY = "itemQuantity";

	public String getItemType() {
		return (String) getObject(ITEM_TYPE);
	}
	public void setItemType(String itemType) {
		setObject(ITEM_TYPE, (itemType != null) ? itemType : "");
	}
	public String getItemQuantity() {
		return (String) getObject(ITEM_QUANTITY);
	}
	public void setItemQuantity(String itemQuantity) {
		setObject(ITEM_QUANTITY, (itemQuantity != null) ? itemQuantity : "");
	}

	/**
	 * When calling toString() for an item, we'd really only want the name.
	 * 
	 * @return String theItemName
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getItemType()).append(",").append(getItemQuantity()).append(" nos.");
		return buffer.toString();
	}
}
