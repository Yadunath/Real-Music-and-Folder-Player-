package com.trialvynscloudup.fragments;


import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.trialvynscloudup.R;
import com.trialvynscloudup.recycler.GridCursorAdapter;
import com.trialvynscloudup.utilities.CommonUtility;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {

    private static String LOG_TAG=AlertDialog.class.getName();
    Cursor cursor;
    FastScrollRecyclerView gridView;
    private GridLayoutManager mLayoutManager;

    private int index = -1;
    private int top = -1;

    
    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        
        super.onViewCreated(view, savedInstanceState);
        gridView=(FastScrollRecyclerView)view.findViewById(R.id.recyclerViewGrid);
        gridView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        gridView.setLayoutManager(mLayoutManager);
        gridView.addItemDecoration(new SpacesItemDecoration(20));
      
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        
        super.onActivityCreated(savedInstanceState);
        cursor=getActivity().managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Audio.Media.ALBUM);
        String[] from=new String[] {MediaStore.Audio.Media.ALBUM};
        int[] to =new int []{android.R.id.text1};

        GridCursorAdapter adapter=new GridCursorAdapter(getActivity(),cursor);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        
        super.onResume();
        CommonUtility commonUtility=new CommonUtility();
        commonUtility.setCurrentFragmentId(1);
        if(index != -1)
        {
            mLayoutManager.scrollToPositionWithOffset( index, top);
        }


    }

    @Override
    public void onPause() {
        super.onPause();

        index = mLayoutManager.findFirstVisibleItemPosition();
        View v = gridView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - gridView.getPaddingTop());
        
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
        index=-1;
        top=-1;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        
    }

    /*              Space divider between recyclerview grid items       */
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;


            // Add top margin only for the first item to avoid double space between items
            if(parent.getChildLayoutPosition(view) == 0)
                outRect.top = space;
        }
    }
}
