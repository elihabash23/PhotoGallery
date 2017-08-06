package com.bignerdranch.android.photogallery;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
        Log.i(TAG, "Received an intent: " + intent);
    }
}
