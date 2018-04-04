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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

// This activity is responsible for displaying the master list of all images
// Implement the MasterListFragment callback, OnImageClickListener
public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    // Variables to store the values for the list index of the selected images
    // The default value will be index = 0
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    // DONE (3) Create a variable to track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean isTwoPane;

    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.next_button);

        // DONE (4) If you are making a two-pane display, add new BodyPartFragments to create an initial Android-Me image
        // Also, for the two-pane display, get rid of the "Next" button in the master list fragment
        if (findViewById(R.id.android_me_linear_layout) != null) {
            isTwoPane = true;

            nextButton.setVisibility(View.GONE);

            BodyPartFragment headFragment = new BodyPartFragment();
            headFragment.setImageIds(AndroidImageAssets.getHeads());
            headFragment.setListIndex(7);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment)
                    .commit();

            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setImageIds(AndroidImageAssets.getBodies());
            bodyFragment.setListIndex(2);

            fragmentManager.beginTransaction()
                    .add(R.id.body_container, bodyFragment)
                    .commit();

            BodyPartFragment legFragment = new BodyPartFragment();
            legFragment.setImageIds(AndroidImageAssets.getLegs());
            legFragment.setListIndex(3);

            fragmentManager.beginTransaction()
                    .add(R.id.leg_container, legFragment)
                    .commit();
        } else {
            isTwoPane = false;
        }


    }

    // Define the behavior for onImageSelected
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        int bodyPartNumber = position / 12;

        int listIndex = position - 12 * bodyPartNumber;

        // Set the currently displayed item for the correct body part fragment
        switch (bodyPartNumber) {
            case 0:
                headIndex = listIndex;
                break;
            case 1:
                bodyIndex = listIndex;
                break;
            case 2:
                legIndex = listIndex;
                break;
            default:
                break;
        }

        // DONE (5) Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        // The two-pane case will not need a Bundle or Intent since a new activity will not be started;
        // This is all happening in this MainActivity and one fragment will be replaced at a time

        if (isTwoPane) {

            BodyPartFragment bodyPartFragment = new BodyPartFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            switch (bodyPartNumber) {
                case 0:
                    bodyPartFragment.setImageIds(AndroidImageAssets.getHeads());
                    bodyPartFragment.setListIndex(headIndex);
                    fragmentManager.beginTransaction()
                            .replace(R.id.head_container, bodyPartFragment)
                            .commit();
                    break;
                case 1:
                    bodyPartFragment.setImageIds(AndroidImageAssets.getBodies());
                    bodyPartFragment.setListIndex(bodyIndex);
                    fragmentManager.beginTransaction()
                            .replace(R.id.body_container, bodyPartFragment)
                            .commit();
                    break;
                case 2:
                    bodyPartFragment.setImageIds(AndroidImageAssets.getLegs());
                    bodyPartFragment.setListIndex(legIndex);
                    fragmentManager.beginTransaction()
                            .replace(R.id.leg_container, bodyPartFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        } else {
            // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);

            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(b);

            // The "Next" button launches a new AndroidMeActivity
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }


    }

}
