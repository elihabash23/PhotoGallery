package com.bignerdranch.android.photogallery;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import java.util.List;

/**
 * Created by eliballislife11 on 8/5/17.
 */

public class PollService extends IntentService {
    private static final String TAG = "PollService";

    /**
     * newIntent(Context) should be used by any component that
     * wants to start this service
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public PollService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }

//        Log.i(TAG, "Received an intent: " + intent);
//        This code will use a service that will poll for new results
//         Step 1: Pull out the query and the last result ID from the default SharedPreferences
//         Step 2: Fetch the latest result with FlickrFetchr
//         Step 3: If there are results, grab the first one
//         Step 4: Check to see whether it is different from the last result ID
//         Step 5: Store the first result back in SharedPreferences
        String query = QueryPreferences.getStoredQuery(this);
        String lastResultId = QueryPreferences.getLastResultId(this);
        List<GalleryItem> items;

        if (query == null) {
            items = new FlickrFetchr().fetchRecentPhotos();
        } else {
            items = new FlickrFetchr().searchPhotos(query);
        }

        if (items.size() == 0) {
            return;
        }

        String resultId = items.get(0).getId();
        if (resultId.equals(lastResultId)) {
            Log.i(TAG, "Got an old result: " + resultId);
        } else {
            Log.i(TAG, "Got a new result: " + resultId);
        }

        QueryPreferences.setLastResultId(this, resultId);
    }


    /**
     * This method verifies with the ConnectivityManager that the network is
     * available since networking is being done in the background
     * @return
     */
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }
}
