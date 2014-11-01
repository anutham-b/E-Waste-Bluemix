package com.anue7.ewastecollector.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.anue7.ewastecollector.R;
import com.anue7.ewastecollector.entity.ItemEntityObject;
import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMQuery;

import java.util.List;

import bolts.Continuation;
import bolts.Task;


/**
 * The class corresponding to the Home activity(screen)
 * 
 */
public class HomeActivity extends Activity {

	public static final String CLASS_NAME = "HomeActivity";

	private static final String TAG = "HomeActivity";

	EWasteApplication eWasteApplication;

	// Currently the Home screen items are Hardcoded here.
	static final String[] LIST_ITEMS = new String[]{"Pledge your E-waste", "Upcoming collection schedules", "Why join the initiative?",};

	ArrayAdapter<com.anue7.ewastecollector.activity.HomeItem> lvArrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		/* Use application class to maintain global state. */
		eWasteApplication = (EWasteApplication) getApplication();

		HomeItem[] menuItems = {new HomeItem("Pledge your e-waste"), new HomeItem("Locate nearest collection schedules"),
				new HomeItem("Why recycle E-waste")};

        fetchItemsFromCloud();

		/* Set up the array adapter for menu items list view. */
		ListView menuItemsLV = (ListView) findViewById(R.id.listView1);
		lvArrayAdapter = new ArrayAdapter<HomeItem>(this, R.layout.menu_list, menuItems);
		menuItemsLV.setAdapter(lvArrayAdapter);

		/* Listener for click on the Menu item in the Home screen */
		menuItemsLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Log.v(TAG, "Click sensed from the listItem num: " + arg2);
				switch (arg2) {
					case 0 :
						startActivity(new Intent(getApplicationContext(), InventoryItemsActivity.class));
						break;
					case 1 :
						startActivity(new Intent(getApplicationContext(), CollectionSchedulesActivity.class));
						break;
					case 2 :
						startActivity(new Intent(getApplicationContext(), AboutInitiativeActivity.class));
						break;
				}
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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


     /**
     * Fetches the saved data from IBM Cloud APIs
     *
     * An IBMQuery is used to find all the list items.
     */
    public void fetchItemsFromCloud() {
        try {
            IBMQuery<ItemEntityObject> query = IBMQuery.queryForClass(ItemEntityObject.class);
            // Query all the Item objects from the server.
            query.find().continueWith(new Continuation<List<ItemEntityObject>, Void>() {

                @Override
                public Void then(Task<List<ItemEntityObject>> task) throws Exception {
                    final List<ItemEntityObject> objects = task.getResult();
                    // Log if the find was cancelled.
                    if (task.isCancelled()){
                        Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                    }
                    // Log error message, if the find task fails.
                    else if (task.isFaulted()) {
                        Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                    }


                    // If the result succeeds, load the list.
                    else {
                        // Clear local itemList.
                        // We'll be reordering and repopulating from DataService.
                        eWasteApplication.setItemEntityObject(objects.get(0));
                    }
                    return null;
                }
            },Task.UI_THREAD_EXECUTOR);

        }  catch (IBMDataException error) {
            Log.e(CLASS_NAME, "Exception : " + error.getMessage());
        }
    }
}
