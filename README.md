# ScreenLockApp
* The mobile device gets locked after double tapping inside the app

* For device to be locked after double tapping on the screen, the device should get the admin permissions from the DevicePolicyManager

* This feature is usually seen in the Launcher application which allows the user to lock the screen in many different ways

* In our application we are going to do this, so at first, we get the one time permission allotted from the user through the DevicePolicyManager

* Later the double tap listener listens for the double taps which will lock the screen and destroys the activity, this makes sure that when the device is turned on again, it     starts from the home launcher.
