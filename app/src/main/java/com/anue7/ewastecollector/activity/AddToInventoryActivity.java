package com.anue7.ewastecollector.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import bolts.Continuation;
import bolts.Task;
import com.anue7.ewastecollector.R;
import com.anue7.ewastecollector.entity.ItemEntityObject;
import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;

public class AddToInventoryActivity extends Activity {

	public static final String CLASS_NAME = "AddToInventoryActivity";

	private static final String TAG = "AddToInventoryActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_to_inventory);

		/* Fetching the items upon screen load */
		listItems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_to_inventory, menu);
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
	 * Will be called upon click of 'Done' button in the form
	 *
	 */
	public void doneAddingItems(View v) {
		ItemEntityObject item = new ItemEntityObject();
		EditText itemTypeText = (EditText) findViewById(R.id.editTextItemType);
		String itemType = itemTypeText.getText().toString();
		item.setItemType(itemType);
		EditText itemQuantityText = (EditText) findViewById(R.id.editTextItemQuantity);
		String itemQuantity = itemQuantityText.getText().toString();
		item.setItemQuantity(itemQuantity);
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
					listItems();
				}
				return null;
			}

		});
	}

	/**
	 * Fetches the saved data from IBM Cloud APIs
	 * 
	 * An IBMQuery is used to find all the list items.
	 */
	public void listItems() {
		try {
			IBMQuery<ItemEntityObject> query = IBMQuery.queryForClass(ItemEntityObject.class);
			// Query all the Item objects from the server.
			query.find().continueWith(new Continuation<List<ItemEntityObject>, Void>() {

				@Override
				public Void then(Task<List<ItemEntityObject>> task) throws Exception {
					final List<ItemEntityObject> objects = task.getResult();
					// Log if the find was cancelled.
					if (task.isCancelled()) {
						Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
					}
					// Log error message, if the find task fails.
					else if (task.isFaulted()) {
						Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
					}

					// If the result succeeds, load the list.
					else {
						TextView textView = (TextView) findViewById(R.id.pledgedItemsText);
						textView.setText("");
						StringBuffer textBuffer = new StringBuffer();
						// We'll be populating from DataService
						for (IBMDataObject item : objects) {
							textBuffer.append((ItemEntityObject) item).append(System.getProperty("line.separator"));
						}
						textView.setText(textBuffer.toString());
					}
					return null;
				}
			}, Task.UI_THREAD_EXECUTOR);

		} catch (IBMDataException error) {
			Log.e(CLASS_NAME, "Exception : " + error.getMessage());
		}
	}
}
