package com.rishav.quizearn;

 class MyFirebaseMessagingService {
  /**
   * Called if FCM registration token is updated. This may occur if the security of
   * the previous token had been compromised. Note that this is called when the
   * FCM registration token is initially generated so this is where you would retrieve
   * the token.
   */
 // @Override
  public void onNewToken(String token) {
   //Log.d(TAG, "Refreshed token: " + token);

   // If you want to send messages to this application instance or
   // manage this apps subscriptions on the server side, send the
   // FCM registration token to your app server.
   //sendRegistrationToServer(token);
  }



 }
