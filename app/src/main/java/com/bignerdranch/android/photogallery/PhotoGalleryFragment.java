package com.bignerdranch.android.photogallery;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eliballislife11 on 7/25/17.
 */

public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = "PhotoGalleryFragment";

    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        setRetainInstance(true);
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        setUpAdapter();

        return v;
    }

    /**
     * setUpAdapter() sets up the adapter for the RecyclerView
     */

    private void setUpAdapter() {
        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    /**
     * The PhotoHolder class will used to hold the views for the RecyclerView
     */

    private class PhotoHolder extends RecyclerView.ViewHolder {

//        private TextView mTitleTextView;
        private ImageView mItemImageView;

        public PhotoHolder(View itemView) {
            super(itemView);

//            mTitleTextView = (TextView) itemView;
            mItemImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }

//        public void bindGalleryItem(GalleryItem item) {
//            mTitleTextView.setText(item.toString());
//        }

        public void bindDrawable(Drawable drawable) {
            mItemImageView.setImageDrawable(drawable);
        }
    }

    /**
     * The PhotoAdapter class will be used for the RecyclerView Adapter
     */

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            TextView textView = new TextView(getActivity());
//            return new PhotoHolder(textView);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_gallery, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
//            photoHolder.bindGalleryItem(galleryItem);
            Drawable placeHolder = getResources().getDrawable(R.drawable.brian_up_close);
            photoHolder.bindDrawable(placeHolder);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    /**
     * FetchItemsTask will run a background thread to fetch
     * Flickr's most recent photos instead of running on
     * the main thread.
     */

    private class FetchItemsTask extends AsyncTask<Void, Void, List<GalleryItem>> {

        @Override
        protected List<GalleryItem> doInBackground(Void... params) {
//            try {
//                String result = new FlickrFetchr().getUrlString("https://www.bignerdranch.com");
//                Log.i(TAG, "Fetched contents of URL: " + result);
//            } catch (IOException ioe) {
//                Log.e(TAG, "Failed to fetch URL: ", ioe);
//            }
            return new FlickrFetchr().fetchItems();
            //return null;
        }

        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setUpAdapter();
        }
    }
}
