package com.chatapp.fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chatapp.R;
import com.chatapp.common.ResideMenu;
import com.chatapp.common.ResideMenuItem;
import com.chatapp.view.MainActivity;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**

 */
public class HomeFragment extends BaseFragment implements SurfaceHolder.Callback {

    private static final int RESULT_LOAD_IMAGE = 101;
    private View parentView;
    private SurfaceView surfaceView;
    private ImageView imgCameraSwitch;
    private ImageView imgCameraFlash;
    private ImageView imgGallery;
    private ImageView imgCaptureVideo;
    private ImageView imgCaptureImage;
    private RecyclerView recyclerViewImages;
    private SurfaceHolder msurfaceHolder;
    private Camera mcamera;
    private boolean mpreviewing;
    private final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private final String GALLERY_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private LinearLayout llCapture;
    private RelativeLayout rrSurfaceView;
    private List<Camera.Size> mSupportedPreviewSizes;
    private List<Camera.Size> mSupportedPictureSize;
    private int currentCameraId;
    private int screenWidth, screenHeight;
    private boolean isBackCamera = true;
    private LinearLayout linearLayoutMain;
    public static ResideMenu resideMenu;
    private ResideMenuItem itemRateApp;
    private ResideMenuItem itemShareWithFriends;
    private ResideMenuItem itemContactUs;
    private ResideMenuItem itemFollowUs;
    private Button btnMenuIcon;
    private Boolean isFlashOn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_home, container, false);
        linearLayoutMain = (LinearLayout) parentView.findViewById(R.id.fragment_home_mainLayout);

        //This code will give reveal effect when fragemnt is load
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            parentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//                @Override
//                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                    v.removeOnLayoutChangeListener(this);
//                    int cx = (parentView.getLeft() + parentView.getRight()) / 2;
//                    int cy = (parentView.getTop() + parentView.getBottom()) / 2;
//                    // get the final radius for the clipping circle
//                    int finalRadius = Math.max(parentView.getWidth(), parentView.getHeight());
//                    // create the animator for this view (the start radius is zero)
//                    Animator anim = ViewAnimationUtils.createCircularReveal(parentView, cx, cy,
//                            0, finalRadius);
//                    anim.setDuration(1000);
//                    // make the view visible and start the animation
//                    parentView.setVisibility(View.VISIBLE);
//                    anim.start();
//
//                }
//            });
//        }
        setUpViews();
        return parentView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_home_ivCameraSwitch:
                changeCameraSwitch();
                break;
            case R.id.fragment_home_ivCameraFlash:
                setFlashOnOff();
                break;
            case R.id.fragment_home_ivGallery:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
        }


        /***
         * Reside menu item click
         */
        if (view == itemRateApp) {
            openPlaystoreForRating();
        } else if (view == itemShareWithFriends) {
            openSharingOptions();
        } else if (view == itemContactUs) {
            openGmailForContactUs();
        } else if (view == itemFollowUs) {
            resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
            resideMenu.closeMenu();
        }

    }

    /***
     * Methos will open application in play store for rating
     */
    private void openPlaystoreForRating() {
        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    /***
     * Method open gmail interface for contact us
     */
    private void openGmailForContactUs() {

    }


    /***
     * Method will open native sharing dialog
     */
    private void openSharingOptions() {
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        String shareBody = getString(R.string.str_google_url);
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.str_share));
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//        startActivity(Intent.createChooser(sharingIntent, getString(R.string.str_share_via)));
    }


    @Override
    protected void initView(View view) {
        isFlashOn = false;
        btnMenuIcon = (Button) view.findViewById(R.id.activity_main_navigationMenuIcon);
        surfaceView = (SurfaceView) view.findViewById(R.id.fragment_home_surfaceView);
        imgCameraSwitch = (ImageView) view.findViewById(R.id.fragment_home_ivCameraSwitch);
        imgCameraFlash = (ImageView) view.findViewById(R.id.fragment_home_ivCameraFlash);
        imgGallery = (ImageView) view.findViewById(R.id.fragment_home_ivGallery);
        imgCaptureVideo = (ImageView) view.findViewById(R.id.fragment_home_ivCaptureVideo);
        imgCaptureImage = (ImageView) view.findViewById(R.id.fragment_home_ivCaptureImage);
        recyclerViewImages = (RecyclerView) view.findViewById(R.id.fragment_home_recycleViewImages);

        msurfaceHolder = surfaceView.getHolder();
        msurfaceHolder.addCallback(this);
        msurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
//        final ArrayList<Integer> arrayListImages = new ArrayList<>();
//        arrayListImages.add(R.drawable.img1);
//        arrayListImages.add(R.drawable.img2);
//        arrayListImages.add(R.drawable.img3);
//        arrayListImages.add(R.drawable.img4);
//        arrayListImages.add(R.drawable.img5);
//        arrayListImages.add(R.drawable.img6);
//        final HomeImagesAdapter homeImagesAdapter = new HomeImagesAdapter(getActivity(), arrayListImages);
//        LinearLayoutManager horizontalLayoutmanager
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerViewImages.setLayoutManager(horizontalLayoutmanager);
//        recyclerViewImages.setAdapter(homeImagesAdapter);
//        recyclerViewImages.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return false;
//            }
//        });
//        recyclerViewImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
//                resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
        imgCameraSwitch.setOnClickListener(this);
        imgCameraFlash.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        llCapture = (LinearLayout) view.findViewById(R.id.fragment_home_llCapture);
        rrSurfaceView = (RelativeLayout) view.findViewById(R.id.fragment_home_rrSurfaceView);
        llCapture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                resideMenu.setSwipeDirectionEnable(ResideMenu.DIRECTION_LEFT);
                resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
                return true;
            }
        });
        rrSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                resideMenu.setSwipeDirectionEnable(ResideMenu.DIRECTION_LEFT);
                resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
                return true;
            }
        });
    }

    /***
     * This method  on and off camera flash light
     */
    private void setFlashOnOff() {
        Camera.Parameters p = mcamera.getParameters();
        if (isFlashOn) {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            isFlashOn = false;
        } else {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            isFlashOn = true;
        }
        mcamera.setParameters(p);
        mcamera.startPreview();
    }

    /***
     * This method switch camera between front and back
     */
    private void changeCameraSwitch() {
        if (mpreviewing) {
            mcamera.stopPreview();
        }
        //NB: if you don't release the current camera before switching, you app will crash
        mcamera.release();
        //swap the id of the camera to be used

        if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            isBackCamera = false;
        } else {
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            isBackCamera = true;
        }
        mcamera = Camera.open(currentCameraId);

        setCameraDisplayOrientation(getActivity(), currentCameraId, mcamera);
        try {

            mcamera.setPreviewDisplay(msurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mcamera.startPreview();
    }

    /***
     * Method is checking permission for Marshmallow
     *
     * @param context
     * @param permission
     * @return
     */
    private static boolean checkForPermission(final Context context, String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        //If permission is granted then it returns 0 as result
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * This is call when user allow or deny permissions in the Marshmallow
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openCamera(screenWidth, screenHeight);
                }
                break;
        }
    }

    /***
     * Method will open camera when surface is created.
     *
     * @param screenWidth
     * @param screenHeight
     */
    private void openCamera(int screenWidth, int screenHeight) {
        try {

            if (mpreviewing) {
                mcamera.stopPreview();
                mpreviewing = false;
            }
            //check orientation
            setCameraDisplayOrientation(getActivity(), Camera.CameraInfo.CAMERA_FACING_BACK, mcamera);
            mcamera = Camera.open();
            mcamera.setPreviewDisplay(msurfaceHolder);
            Camera.Parameters parameters = mcamera.getParameters();
            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                //parameters.setRotation(90);
                mcamera.setDisplayOrientation(90);
            } else {
                parameters.set("orientation", "landscape");
                mcamera.setDisplayOrientation(180);
            }
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            //get best previewsize and picture size
            mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
            mSupportedPictureSize = parameters.getSupportedPictureSizes();
            Camera.Size Prviewsize = getBestPreviewSize(screenWidth, screenHeight, parameters);
            Camera.Size Picturesize = getBestPictureSize(mcamera, mSupportedPictureSize, true, screenWidth, screenHeight);
            if (mSupportedPreviewSizes != null) {
                parameters.setPreviewSize(Prviewsize.width, Prviewsize.height);
            }
            if (mSupportedPictureSize != null) {
                parameters.setPictureSize(Picturesize.width, Picturesize.height);
            }
            mcamera.setParameters(parameters);
            mpreviewing = true;
        } catch (IOException exception) {
            exception.printStackTrace();
            mcamera.release();
        }
        mcamera.startPreview();
    }

    /***
     * This method is setting camera orientation
     *
     * @param activity
     * @param cameraId
     * @param camera
     */
    private static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        try {
            android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
            android.hardware.Camera.getCameraInfo(cameraId, info);
            int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360; // compensate the mirror
            } else { // back-facing
                result = (info.orientation - degrees + 360) % 360;
            }
            camera.setDisplayOrientation(result);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return (result);
    }

    private static Camera.Size getBestPictureSize(Camera camera, List<Camera.Size> supportedSizes, boolean preview, int displayWidth, int displayHeight) {

        final int PREVIEW_SIZE_WIDTH_EMULATOR = 176;
        final int PREVIEW_SIZE_HEIGHT_EMULATOR = 144;
        final int PICTURE_SIZE_WIDTH_EMULATOR = 213;
        final int PICTURE_SIZE_HEIGHT_EMULATOR = 350;

        double temporalDiff = 0;
        double diff = Integer.MAX_VALUE;

        Camera.Size size = null;
        Camera.Size supportedSize = null;

        if (supportedSizes == null) {
            if (isAndroidEmulator(android.os.Build.MODEL)) {
                if (preview) {
                    size = camera.new Size(
                            PREVIEW_SIZE_WIDTH_EMULATOR,
                            PREVIEW_SIZE_HEIGHT_EMULATOR);
                } else {
                    size = camera.new Size(
                            PICTURE_SIZE_WIDTH_EMULATOR,
                            PICTURE_SIZE_HEIGHT_EMULATOR);
                }
            }
        } else {
            Iterator<Camera.Size> iterator = supportedSizes.iterator();
            while (iterator.hasNext()) {
                supportedSize = iterator.next();
                temporalDiff = Math.sqrt(
                        Math.pow(supportedSize.width - displayWidth, 2) +
                                Math.pow(supportedSize.height - displayHeight, 2));

                if (temporalDiff < diff) {
                    diff = temporalDiff;
                    size = supportedSize;
                }
            }
        }
        return size;
    }

    private static boolean isAndroidEmulator(String model) {
        return (model.compareToIgnoreCase("sdk") == 0);
    }

    @Override
    protected void trackScreen() {
    }

    private void setUpViews() {
        MainActivity parentActivity = (MainActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int width, int height) {
        screenHeight = height;
        screenWidth = width;
        if (checkForPermission(getActivity(), CAMERA_PERMISSION) && checkForPermission(getActivity(), GALLERY_PERMISSION)) {
            openCamera(width, height);
        } else {
            requestPermissions(new String[]{CAMERA_PERMISSION, GALLERY_PERMISSION}, 1);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mcamera.stopPreview();
        mcamera.release();
        mcamera = null;
        mpreviewing = false;
    }
}
