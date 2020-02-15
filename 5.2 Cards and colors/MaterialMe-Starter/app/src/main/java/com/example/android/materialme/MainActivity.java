/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.materialme;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

/***
 * Main Activity for the Material Me app, a mock sports news application with poor design choices
 */
public class MainActivity extends AppCompatActivity {
    public static final String STATE_SPORTS_TITLES = "com.example.android.materialme.SPORTS_TITLES";
    public static final String STATE_SPORTS_INFOS = "com.example.android.materialme.SPORTS_INFOS";
    public static final String STATE_SPORTS_IMAGE_RESOURCES = "com.example.android.materialme.SPORTS_IMAGE_RESOURCES";


    //Member variables
    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the RecyclerView
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        //Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the ArrayLIst that will contain the data
        mSportsData = new ArrayList<>();

        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        //Get the data
        String[] sportsList = null;
        String[] sportsInfo = null;
        int[] sportsImageResource = null;
        if (savedInstanceState != null) {
            Intent intent = getIntent();
            sportsList = intent.getStringArrayExtra(STATE_SPORTS_TITLES);
            sportsInfo = intent.getStringArrayExtra(STATE_SPORTS_INFOS);
            sportsImageResource = intent.getIntArrayExtra(STATE_SPORTS_IMAGE_RESOURCES);
        }

        initializeData(sportsList, sportsInfo, sportsImageResource);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mSportsData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mSportsData.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
        helper.attachToRecyclerView(mRecyclerView) ;
    }

    /**
     * Method for initializing the sports data from resources.
     */
    private void initializeData(@Nullable String[] sportsList, @Nullable String[] sportsInfo, @Nullable int[] sportsImageResources) {
        //Get the resources from the XML file
        if (sportsList == null) {
            sportsList = getResources().getStringArray(R.array.sports_titles);
        }
        if (sportsInfo == null) {
            sportsInfo = getResources().getStringArray(R.array.sports_info);
        }
        int length = sportsList.length;
        if (sportsImageResources == null) {
            sportsImageResources = new int[length];
            TypedArray sportsResources = getResources().obtainTypedArray(R.array.sports_images);
            for (int i = 0; i < length; i++) {
                sportsImageResources[i] = sportsResources.getResourceId(i, 0);
            }
            sportsResources.recycle();
        }

        //Clear the existing data (to avoid duplication)
        mSportsData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for (int i = 0; i < length; i++) {
            mSportsData.add(new Sport(sportsList[i], sportsInfo[i], sportsImageResources[i]));
        }

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int length = mSportsData.size();
        String[] sportsList = new String[length];
        String[] sportsInfo = new String[length];
        int[] sportsImageResources = new int[length];

        int i = 0;
        for (Sport sport: mSportsData) {
            sportsList[i] = sport.getTitle();
            sportsInfo[i] = sport.getInfo();
            sportsImageResources[i] = sport.getImageResource();
        }

        outState.putStringArray(STATE_SPORTS_TITLES, sportsList);
        outState.putStringArray(STATE_SPORTS_INFOS, sportsInfo);
        outState.putIntArray(STATE_SPORTS_IMAGE_RESOURCES, sportsImageResources);
    }

    public void resetSports(View view) {
        initializeData(null, null, null);
    }
}
