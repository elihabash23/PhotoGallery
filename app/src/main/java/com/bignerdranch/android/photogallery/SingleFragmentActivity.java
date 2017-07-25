package com.bignerdranch.android.photogallery;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by eliballislife11 on 6/7/17.
 */

//public abstract class SingleFragmentActivity extends FragmentActivity {
public abstract class SingleFragmentActivity extends AppCompatActivity {    // For the use of a toolbar
    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {        // Making SingleFragmentActivity flexible
        return R.layout.activity_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Called when the activity is first created
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fragment);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // The FragmentTransaction class uses a fluent interface - methods that
        // configure FragmentTransaction return a Fragment Transaction instead
        // of void, which allows you to chain them together. The code says
        // "Create a new fragment transaction, include on add operation in it,
        // and then commit it."

        if(fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
