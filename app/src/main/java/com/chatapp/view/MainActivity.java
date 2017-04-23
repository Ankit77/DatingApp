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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chatapp.R;
import com.chatapp.fragment.EditProfileFragment;
import com.chatapp.fragment.ProfileDetailFragment;
import com.chatapp.fragment.SuggestionFragment;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditProfileFragment suggestionFragment = new EditProfileFragment();
        getFragmentManager().beginTransaction().add(R.id.activity_home_container11, suggestionFragment, EditProfileFragment.class.getSimpleName()).commit();

        //fillWithTestData();
    }


}
