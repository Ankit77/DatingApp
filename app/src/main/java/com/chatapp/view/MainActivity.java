/*
 * Copyright (C) 2016 Frederik Schweiger
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

package com.chatapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chatapp.R;
import com.chatapp.fragment.EditProfileFragment;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditProfileFragment suggestionFragment = new EditProfileFragment();
        getFragmentManager().beginTransaction().add(R.id.activity_home_container11, suggestionFragment, EditProfileFragment.class.getSimpleName()).commit();

        //fillWithTestData();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        EditProfileFragment fragment = (EditProfileFragment) getFragmentManager().findFragmentByTag(EditProfileFragment.class.getSimpleName());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
    }
}
