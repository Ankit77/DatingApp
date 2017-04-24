package com.chatapp.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chatapp.DatingApp;
import com.chatapp.R;
import com.chatapp.chipedittext.EditTag;
import com.chatapp.util.Constants;
import com.chatapp.util.FileUtils;
import com.chatapp.util.GetFilePath;
import com.chatapp.util.PREF;
import com.chatapp.util.Utils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ANKIT on 4/23/2017.
 */

public class EditProfileFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, EditTag.TagAddCallback, EditTag.TagDeletedCallback {

    private ImageView img1, img2, img3, img4, img5;
    private ImageView imgdelete1, imgdelete2, imgdelete3, imgdelete4, imgdelete5;
    private ImageView imgedit1, imgedit2, imgedit3, imgedit4, imgedit5;
    private View view;
    private EditText etAboutUs;
    private EditText etCurrentJob;
    private RadioGroup rgGender;
    private SwitchCompat swtShowAge;
    private SwitchCompat swtLocation;
    private final int REQUEST_CAPTURE_IMAGE_1 = 111;
    private final int REQUEST_CAPTURE_IMAGE_2 = 112;
    private final int REQUEST_CAPTURE_IMAGE_3 = 113;
    private final int REQUEST_CAPTURE_IMAGE_4 = 114;
    private final int REQUEST_CAPTURE_IMAGE_5 = 115;

    private final int REQUEST_PICK_IMAGE_1 = 211;
    private final int REQUEST_PICK_IMAGE_2 = 212;
    private final int REQUEST_PICK_IMAGE_3 = 213;
    private final int REQUEST_PICK_IMAGE_4 = 214;
    private final int REQUEST_PICK_IMAGE_5 = 215;


    private String imageurl1, imageurl2, imageurl3, imageurl4, imageurl5;
    private String cameraFilePath;
    int PERMISSION_ALL = 1;
    private int position = -1;
    private EditTag editTagView;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private ArrayList<String> interestList = new ArrayList<>();
    private CropImageFragment cropImageFragment;
//    private HomeActivity homeActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        homeActivity = (HomeActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_editprofile, null);
//        homeActivity.setActionBarTitle(getString(R.string.screen_editprofile));
//        homeActivity.isBackEnable(true);
        editTagView = (EditTag) view.findViewById(R.id.fragment_editprofile_et_interest);
        img1 = (ImageView) view.findViewById(R.id.fragment_editprofile_img1);
        img2 = (ImageView) view.findViewById(R.id.fragment_editprofile_img2);
        img3 = (ImageView) view.findViewById(R.id.fragment_editprofile_img3);
        img4 = (ImageView) view.findViewById(R.id.fragment_editprofile_img4);
        img5 = (ImageView) view.findViewById(R.id.fragment_editprofile_img5);
        imgdelete1 = (ImageView) view.findViewById(R.id.fragment_editprofile_imgdelete1);
        imgdelete2 = (ImageView) view.findViewById(R.id.fragment_editprofile_imgdelete2);
        imgdelete3 = (ImageView) view.findViewById(R.id.fragment_editprofile_imgdelete3);
        imgdelete4 = (ImageView) view.findViewById(R.id.fragment_editprofile_imgdelete4);
        imgdelete5 = (ImageView) view.findViewById(R.id.fragment_editprofile_imgdelete5);
        etAboutUs = (EditText) view.findViewById(R.id.fragment_editprofile_et_aboutme);
        etCurrentJob = (EditText) view.findViewById(R.id.fragment_editprofile_et_currentjob);
        rgGender = (RadioGroup) view.findViewById(R.id.fragment_editprofile_rg_gender);
        swtShowAge = (SwitchCompat) view.findViewById(R.id.fragment_editprofile_sc_age);
        swtLocation = (SwitchCompat) view.findViewById(R.id.fragment_editprofile_sc_distance);
        swtLocation.setOnCheckedChangeListener(this);
        imgdelete1.setOnClickListener(this);
        imgdelete2.setOnClickListener(this);
        imgdelete3.setOnClickListener(this);
        imgdelete4.setOnClickListener(this);
        imgdelete5.setOnClickListener(this);

        cropImageFragment = new CropImageFragment();


        //Code for ChipView
        for (int i = 0; i < 10; i++) {
            interestList.add("Test" + i + 1);
        }
        editTagView.setEditable(true);
        editTagView.setTagAddCallBack(this);
        editTagView.setTagList(interestList);
        //end
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_location, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_done) {
            int radioid = rgGender.getCheckedRadioButtonId();

            if (radioid == R.id.fragment_editprofile_rb_male) {
                DatingApp.getsInstance().getSharedPreferences().edit().putInt(PREF.PREF_GENDER, Constants.GENDER_MALE).commit();
            } else {
                DatingApp.getsInstance().getSharedPreferences().edit().putInt(PREF.PREF_GENDER, Constants.GENDER_FEMALE).commit();
            }

            getFragmentManager().popBackStack();
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton == swtLocation) {
            DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_SHOW_DISTANCE, b).commit();
        } else if (compoundButton == swtShowAge) {
            DatingApp.getsInstance().getSharedPreferences().edit().putBoolean(PREF.PREF_SHOW_AGE, b).commit();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == imgdelete1) {
            position = 1;
            if (hasPermissions(getActivity(), PERMISSIONS)) {
                showDialog(1);
            }
        } else if (view == imgdelete2) {
            position = 2;
            if (hasPermissions(getActivity(), PERMISSIONS)) {
                showDialog(2);
            }

        } else if (view == imgdelete3) {
            position = 3;
            if (hasPermissions(getActivity(), PERMISSIONS)) {
                showDialog(3);
            }
        } else if (view == imgdelete4) {
            position = 4;
            if (hasPermissions(getActivity(), PERMISSIONS)) {
                showDialog(4);
            }
        } else if (view == imgdelete5) {
            position = 5;
            if (hasPermissions(getActivity(), PERMISSIONS)) {
                showDialog(5);
            }
        }
    }

    private void showDialog(final int position) {
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.dialog_attachimage);
        Button btnCaptureImage = (Button) dialog.findViewById(R.id.dialog_attacimage_captureimage);
        Button btnPickImage = (Button) dialog.findViewById(R.id.dialog_attacimage_btn_pickimage);
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 1) {
                    captureImage(REQUEST_CAPTURE_IMAGE_1);
                } else if (position == 2) {
                    captureImage(REQUEST_CAPTURE_IMAGE_2);
                } else if (position == 3) {
                    captureImage(REQUEST_CAPTURE_IMAGE_3);
                } else if (position == 4) {
                    captureImage(REQUEST_CAPTURE_IMAGE_4);
                } else if (position == 5) {
                    captureImage(REQUEST_CAPTURE_IMAGE_5);
                }
                dialog.dismiss();
            }
        });

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 1) {
                    chooseImageFileFromStorage(REQUEST_PICK_IMAGE_1);
                } else if (position == 2) {
                    chooseImageFileFromStorage(REQUEST_PICK_IMAGE_2);
                } else if (position == 3) {
                    chooseImageFileFromStorage(REQUEST_PICK_IMAGE_3);
                } else if (position == 4) {
                    chooseImageFileFromStorage(REQUEST_PICK_IMAGE_4);
                } else if (position == 5) {
                    chooseImageFileFromStorage(REQUEST_PICK_IMAGE_5);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private Uri getPostImageUri(boolean canCleanup) {
        final File file = new File(getActivity().getExternalCacheDir() + File.separator + "Image_" + System.currentTimeMillis() + Constants.IMAGE_EXTENSION);
        if (canCleanup) {
            if (file.exists()) {
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        cameraFilePath = file.getAbsolutePath();
        return Uri.fromFile(file);
    }

    private void chooseImageFileFromStorage(int requestcode) {
        Intent intent = Utils.getFileChooserIntent("image/*");
        if (intent != null) {
            startActivityForResult(intent, requestcode);
        }

    }

    /**
     * Opens camera activity
     */

    private void captureImage(int requestcode) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {

            Uri outputFileUri = getPostImageUri(true);
            intent1.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            intent1.putExtra("return-data", true);
            startActivityForResult(intent1, requestcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        String filepath;
        try {
            if (resultCode == RESULT_OK) {
                // Callback from Gallery
                Uri uri = null;
                if (requestCode == REQUEST_CAPTURE_IMAGE_1) {
                    uri = Uri.fromFile(new File(cameraFilePath));
                    CropImage.activity(uri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(getActivity());
                } else if (requestCode == REQUEST_PICK_IMAGE_1) {
                    uri = data.getData();
                    CropImage.activity(uri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(getActivity());
//                    CropImage.activity(uri)
//                            .start(getActivity(), this);
                    //cropImageFragment.setImageUri(uri);
//                    try {
//                        imageurl1 = GetFilePath.getPath(getActivity(), selectedImageUri);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    Bitmap bmp = BitmapFactory.decodeFile(imageurl1);
//                    if (bmp != null)
//                        img1.setImageBitmap(bmp);
                } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        Uri resultUri = result.getUri();
                        if (position == 1) {
                            try {
                                imageurl1 = GetFilePath.getPath(getActivity(), resultUri);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Bitmap bmp = BitmapFactory.decodeFile(imageurl1);
                            if (bmp != null)
                                img1.setImageBitmap(bmp);
                        } else if (position == 2) {
                            try {
                                imageurl2 = GetFilePath.getPath(getActivity(), resultUri);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Bitmap bmp = BitmapFactory.decodeFile(imageurl2);
                            if (bmp != null)
                                img1.setImageBitmap(bmp);
                        } else if (position == 3) {
                            try {
                                imageurl3 = GetFilePath.getPath(getActivity(), resultUri);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Bitmap bmp = BitmapFactory.decodeFile(imageurl3);
                            if (bmp != null)
                                img1.setImageBitmap(bmp);
                        } else if (position == 4) {
                            try {
                                imageurl4 = GetFilePath.getPath(getActivity(), resultUri);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Bitmap bmp = BitmapFactory.decodeFile(imageurl4);
                            if (bmp != null)
                                img1.setImageBitmap(bmp);
                        } else if (position == 5) {
                            try {
                                imageurl5 = GetFilePath.getPath(getActivity(), resultUri);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Bitmap bmp = BitmapFactory.decodeFile(imageurl5);
                            if (bmp != null)
                                img1.setImageBitmap(bmp);
                        }

                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCompressImage(String imagepath) {
        File compressedImage = null;
        //compress image
        if (Utils.getFileSizeInKB(imagepath) > 500) {
            compressedImage = new Compressor.Builder(getActivity())
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(FileUtils.createFolderInExternalStorageDirectory(getString(R.string.app_name) + "/" + Constants.IMAGE_FOLDER))
                    .build()
                    .compressToFile(new File(imagepath));
            //Delete file
            File file = new File(imagepath);
            file.delete();
        } else {
            compressedImage = new File(imagepath);
        }
        return compressedImage.toString();
    }

//Permission check


    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Requests the write permission.
     * If the permission has been denied previously, a alert will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestPermission() {
        // permission has not been granted yet. Request it directly.
        FragmentCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_ALL) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for write permission.
            // Check if the only required permission has been granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // write permission has been granted
                showDialog(position);
            } else {
                Toast.makeText(getActivity(), R.string.alret_permision_need, Toast.LENGTH_LONG).show();
            }
            // END_INCLUDE(permission_result)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onTagAdd(String tagValue) {
        {
            return true;
        }
    }

    @Override
    public void onTagDelete(String deletedTagValue) {
        Toast.makeText(getActivity(), deletedTagValue, Toast.LENGTH_SHORT).show();
    }

}
