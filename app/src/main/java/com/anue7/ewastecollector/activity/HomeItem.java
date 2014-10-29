package com.anue7.ewastecollector.activity;

/**
 * This class is used by {@link android.widget.ArrayAdapter} to display the menu items in a
 * ListView component
 */
public class HomeItem {
	public String menuItem;

	public HomeItem(String itemName) {
		menuItem = itemName;
	}

	/**
	 * Gets the name of the HomeItem.
	 * 
	 * @return String menuItem
	 */
	public String getName() {
		return menuItem;
	}

	/**
	 * Sets the name of a menu item, as well as calls setCreationTime().
	 * 
	 */
	public void setName(String lMenuItem) {
		menuItem = lMenuItem;
	}

	/**
	 * When calling toString() for an menu item, we'd really only want the name.
	 * 
	 * @return String theItemName
	 */
	public String toString() {
		String theItemName = "";
		theItemName = getName();
		return theItemName;
	}
}
