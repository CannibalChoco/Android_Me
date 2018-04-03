/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;

import java.util.List;

public class BodyPartFragment extends Fragment {

    private static final String LOG_TAG = BodyPartFragment.class.getSimpleName();

    // DONE (1) Create a setter method and class variable to set and store of a list of image resources
    private List<Integer> imageResources;

    // DONE (2) Create another setter method and variable to track and set the index of the list item to display
        // ex. index = 0 is the first image id in the given list , index 1 is the second, and so on
    private int resourceIndex;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public BodyPartFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        // Get a reference to the ImageView in the fragment layout
        ImageView imageView = rootView.findViewById(R.id.body_part_image_view);

        // DONE (3) If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        if (imageResources != null){
            imageView.setImageResource(imageResources.get(resourceIndex));
        } else {
            Log.v(LOG_TAG, "image resource list is null");
        }

        // Return the rootView
        return rootView;
    }

    public void setImageResources(List<Integer> imageresources){
        this.imageResources = imageresources;
    }

    public void setResourceIndex(int index){
        this.resourceIndex = index;
    }

}
