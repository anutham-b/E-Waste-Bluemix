package com.anue7.ewastecollector.entity;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

import java.util.ArrayList;

@IBMDataObjectSpecialization("ItemEntityObject")
public class ItemEntityObject extends IBMDataObject {
    public static final String CLASS_NAME = "ItemEntityObject";
    private static final String ITEM_QUANTITY = "itemQuantity";

    public ArrayList<Integer> getItemQuantity() {
        return (ArrayList<Integer>) getObject(ITEM_QUANTITY);
    }

    public void setItemQuantity(ArrayList<Integer> itemQuantity) {
        setObject(ITEM_QUANTITY, (itemQuantity));
    }

    /**
     * When calling toString() for an item, we'd really only want the name.
     *
     * @return String theItemName
     */
    public String toString() {
        return getItemQuantity().toString();
    }
}
