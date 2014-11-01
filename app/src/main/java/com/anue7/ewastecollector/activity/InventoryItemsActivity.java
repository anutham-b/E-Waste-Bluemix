package com.anue7.ewastecollector.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.anue7.ewastecollector.R;
import com.anue7.ewastecollector.entity.ItemEntityObject;
import com.ibm.mobile.services.data.IBMDataObject;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bolts.Continuation;
import bolts.Task;


public class InventoryItemsActivity extends Activity {

    public static final String CLASS_NAME = "InventoryItemsActivity";

    public static final String TAG = "InventoryItemsActivity";

    public static final int noOfItemTypes = 7;

    public static Map<String, Integer> qtyToIdMap = new HashMap<String, Integer>() {
        {
            put("qty1", R.id.qty1);
            put("qty2", R.id.qty2);
            put("qty3", R.id.qty3);
            put("qty4", R.id.qty4);
            put("qty5", R.id.qty5);
            put("qty6", R.id.qty6);
            put("qty7", R.id.qty7);

        }
    };

    public static Map<String, Integer> cbToIdMap = new HashMap<String, Integer>() {
        {
            put("checkBox1", R.id.checkBox1);
            put("checkBox2", R.id.checkBox2);
            put("checkBox3", R.id.checkBox3);
            put("checkBox4", R.id.checkBox4);
            put("checkBox5", R.id.checkBox5);
            put("checkBox6", R.id.checkBox6);
            put("checkBox7", R.id.checkBox7);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_items);

        /*Initially set all quantity fields disabled */

        setAllQtyDisabled();
    }

    private void setAllQtyDisabled() {
        for (int qty : qtyToIdMap.values()) {
            TextView qtyText = (TextView) findViewById(qty);
            qtyText.setEnabled(false);
        }
    }

    private ArrayList<Integer> getItemQtyInfo() {
        List<Integer> qtyColln = new ArrayList<Integer>(qtyToIdMap.values());
        Collections.sort( qtyColln, new Comparator<Integer>() {
            @Override
            public int compare(Integer o, Integer o2) {
                return (o.compareTo(o2));
            }
        });
        List<Integer> cbColln = new ArrayList<Integer>(cbToIdMap.values());
        Collections.sort( cbColln, new Comparator<Integer>() {
            @Override
            public int compare(Integer o, Integer o2) {
                return (o.compareTo(o2));
            }
        });
        Iterator<Integer> qtyIterator = qtyColln.iterator();
        Iterator<Integer>  cbIterator = cbColln.iterator();
        ArrayList<Integer> itemQuantities = new ArrayList<Integer>();
        while (qtyIterator.hasNext() && cbIterator.hasNext()) {
            CheckBox itemChecked = (CheckBox) findViewById(cbIterator.next().intValue());
            EditText qtyValue = (EditText) findViewById(qtyIterator.next().intValue());
            if (itemChecked.isChecked()) {

                itemQuantities.add(Integer.valueOf(qtyValue.getText().toString()));
            } else {itemQuantities.add(0);}
        }
        return itemQuantities;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inventory_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClick(View v) {
        // TODO Auto-generated method stub
        //ID value retrieved here is a numerical
//        Log.v(TAG, "ID: " + ((CheckBox) v).getId());
        String qtyIdTag = (String) ((CheckBox) v).getTag();
        boolean isChecked = ((CheckBox) v).isChecked();
//        Log.v(TAG,"isChecked: "+isChecked);
//        Log.v(TAG, "Tag: " + qtyIdTag);
        TextView qtyText = (TextView) findViewById(qtyToIdMap.get(qtyIdTag));
        if (!isChecked) {
            //Disable the Qty field if unchecked
            qtyText.setText("");
            qtyText.setEnabled(false);
        } else {
            qtyText.setEnabled(true);
        }
    }

    /**
     * Will be called upon click of 'Done' button in the form
     */
    public void doneUpdatingItems(View v) {
        ArrayList<Integer> items= getItemQtyInfo();
        ItemEntityObject item = new ItemEntityObject();
        item.setItemQuantity(items);
        item.save().continueWith(new Continuation<IBMDataObject, Void>() {

            @Override
            public Void then(Task<IBMDataObject> task) throws Exception {
                if (task.isCancelled()) {
                    Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                }
                // Log error message, if the save task fails.
                else if (task.isFaulted()) {
                    Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                }
                // If the result succeeds, LOG it for now
                else {
                    Log.v(TAG, "Save successful!");
                }
                return null;
            }
        });
    }
}
