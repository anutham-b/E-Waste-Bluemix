E-Waste-Bluemix
===============
Features needed (In order of priority):

To have multiple users
* LOGIN - Google+ connect for app - SOCIAL LAYER
	* For login
	* For sharing the e-waste donation once handed over in the kiosk. This spreads the word
	* For sharing the e-waste pledge (<Name> has pledged to donate 4 useless electronic items for scientific disposal and recycling)

* GEO location of the device
	* To be set in preferences and immediately updated to Mobile Backend

* Maintaining Inventory - OPERATIONAL
	* [DONE] User adds/modifies items to the waste inventory.
	* After the collection, the received inventories are marked as accepted.

* Push notifications for collection points
	* Run a daily cron-type job to run an Algorithm to find proximal nodes and show results.
	* If acceptable schedule collection points and send to closest (and nearby) nodes.
	* Mark the items as received. The user can share the story later.


* Educative menu item to describe the purpose of the initiative.


2 Nov Updates
=============
* Links:
https://hub.jazz.net/user/mobilecloud
http://www.ibm.com/developerworks/library/mo-android-googleauth-app/#N1016C
http://en.wikipedia.org/wiki/DBSCAN

* Debugging: Step inside till you see an exception class (F7)

* From IBM's point of evaluation, I guess if we use 3 Bluemix services (MCloud, Push, Auth) it will be good. Max use of their infra is always better as per their app evaluation criteria.