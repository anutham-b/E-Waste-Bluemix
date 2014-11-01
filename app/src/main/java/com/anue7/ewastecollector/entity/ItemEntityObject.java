package com.anue7.ewastecollector.entity;

import android.util.Log;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;


@IBMDataObjectSpecialization("ItemEntityObject")
public class ItemEntityObject extends IBMDataObject {
    public static final String CLASS_NAME = "ItemEntityObject";
    private static final String ITEM_QUANTITY = "itemQuantity";

    public static final int NO_OF_ITEM_TYPES = 7;

    public Integer[] getItemQuantity() {
        JSONArray itemsJson = (JSONArray)getObject(ITEM_QUANTITY);
        Integer[] integerArray = new Integer[NO_OF_ITEM_TYPES];
        if(itemsJson!=null) {
            for (int i = 0; i < NO_OF_ITEM_TYPES; i++) {
                try {
                    integerArray[i] = (itemsJson.getInt(i));
                } catch (JSONException e) {
                    Log.e(CLASS_NAME, "JSONException: \n" + e.getMessage());
                }
            }
        }
        return integerArray;
    }

    public void setItemQuantity(Integer[] itemQuantity) {
        setObject(ITEM_QUANTITY, itemQuantity);
    }

    /**
     * When calling toString() for an item, we'd really only want the name.
     *
     * @return String theItemName
     */
    public String toString() {
        Integer[] items = getItemQuantity();
        StringBuffer stringBuffer = new StringBuffer();
        for(Integer val: items)
        {
            stringBuffer.append(val);
        }
        return stringBuffer.toString();
    }
}
