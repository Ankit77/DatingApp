package com.chatapp.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chatapp.DatingApp;
import com.chatapp.R;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    /**
     * Method is used for checking network availability.
     *
     * @param context context
     * @return isNetAvailable: boolean true for Internet availability, false otherwise
     */

    public static boolean isNetworkAvailable(Context context) {

        boolean isNetAvailable = false;
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    final Network[] allNetworks = mConnectivityManager.getAllNetworks();

                    for (Network network : allNetworks) {
                        final NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(network);
                        if (networkInfo != null && networkInfo.isConnected()) {
                            isNetAvailable = true;
                            break;
                        }
                    }

                } else {
                    boolean wifiNetworkConnected = false;
                    boolean mobileNetworkConnected = false;

                    final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                    if (mobileInfo != null) {
                        mobileNetworkConnected = mobileInfo.isConnected();
                    }
                    if (wifiInfo != null) {
                        wifiNetworkConnected = wifiInfo.isConnected();
                    }
                    isNetAvailable = (mobileNetworkConnected || wifiNetworkConnected);
                }
            }
        }
        return isNetAvailable;
    }

    /**
     * Display progress dialog with loading
     *
     * @param context context
     * @return dialog
     */
    public static ProgressDialog displayProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.str_loading));
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    /**
     * Dismiss current progress dialog
     *
     * @param dialog dialog
     */
    public static void dismissProgressDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    /**
     * Alert dialog to show common messages.
     *
     * @param title   title
     * @param message message
     * @param context context
     */
    public static void displayDialog(final Context context, final String title, final String message) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        if (title == null)
            alertDialog.setTitle(context.getString(R.string.app_name));
        else
            alertDialog.setTitle(title);
        alertDialog.setCancelable(false);

        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.str_ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        if (!((Activity) context).isFinishing()) {

            alertDialog.show();
        }
    }


    /**
     * Alert dialog to be displayed conditionally and getting the Current Fragment Popped back on it's "Ok" Button Click
     *
     * @param title   Title of the Dialog : Application's Name
     * @param message Message to be shown in the Dialog displayed
     * @param context Context of the Application, where the Dialog needs to be displayed
     */
    public static void displayDialogWithPopBackStack(final Context context, final String title, final String message) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);

        alertDialog.setMessage(message);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.str_ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                ((Activity) context).getFragmentManager().popBackStack();
            }
        });
        if (!((Activity) context).isFinishing()) {

            alertDialog.show();
        }
    }

    /**
     * Validate emailid with regular expression
     *
     * @param emailId email
     * @return true valid emailid, false invalid emailid
     */
    public static boolean isValidEmailId(final String emailId) {
        return !TextUtils.isEmpty(emailId) && android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }

    /**
     * Validate edit field for blank value
     *
     * @param editText edit field
     * @return true if contains empty value
     */
    public static boolean isEmptyField(final EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }

    /**
     * Validate edit field for blank value
     * show error in text input layout
     *
     * @param editText        edit field
     * @param textInputLayout textInputLayout
     * @param message         message field
     * @return true if contains empty value
     */

    /**
     * Hide the soft keyboard from screen for edit text only
     *
     * @param mContext Context
     * @param view     view
     */
    public static void hideSoftKeyBoard(Context mContext, View view) {

        try {

            final InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Hide keyboard
     *
     * @param activity activity
     */
    public static void hideSoftKeyBoard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }


    /**
     * Called to check permission(In Android M and above versions only)
     *
     * @param permission, which we need to pass
     * @return true, if permission is granted else false
     */
    public static boolean checkForPermission(final Context context, final String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        //If permission is granted then it returns 0 as result
        return result == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * Check if valid email address
     *
     * @param inputEmail inputEmail
     * @return valid flag
     */
    public static boolean isValidEmail(CharSequence inputEmail) {
        if (inputEmail == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
        }
    }

    /**
     * Displays alert dialog with given message and butttons
     *
     * @param context
     * @param title
     * @param msg
     * @param strPositiveText
     * @param strNegativeText
     * @param isNagativeBtn
     * @param isFinish
     */
    public static void displayDialog(final Activity context, final String title, final String msg, final String strPositiveText, final String strNegativeText,
                                     final boolean isNagativeBtn, final boolean isFinish) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setMessage(msg);
        dialog.setPositiveButton(strPositiveText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (isFinish) {
                    context.finish();
                }
            }
        });
        if (isNagativeBtn) {
            dialog.setNegativeButton(strNegativeText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }


    public static void displayDialogFragment(final Activity context, final String title, final String msg, final String strPositiveText, final String strNegativeText,
                                             final boolean isNagativeBtn, final boolean isFinish) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setMessage(msg);
        dialog.setPositiveButton(strPositiveText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (isFinish) {
                    context.getFragmentManager().popBackStack();
                }
            }
        });
        if (isNagativeBtn) {
            dialog.setNegativeButton(strNegativeText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }


    public static Date getDateFromString(String strDate, String srcFormat) {
        try {
            DateFormat formatter = new SimpleDateFormat(srcFormat);
            return formatter.parse(strDate);
        } catch (ParseException e) {

        }

        return null;
    }

    public static String changeDateTimeFormat(String strDate, String srcFormat, String destFormat) {
        Date date = new Date();
        String strFormatedDate = "";

        try {
            DateFormat formatter = new SimpleDateFormat(srcFormat, Locale.ENGLISH);
            date = formatter.parse(strDate);
            strFormatedDate = new SimpleDateFormat(destFormat, Locale.ENGLISH).format(date);
        } catch (ParseException e) {
        }

        return strFormatedDate;
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }


    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    /**
     * Common add fragment method
     *
     * @param containerid
     * @param mActivity
     * @param targetedFragment
     * @param shooterFragment
     * @param isDownToUp
     */

    public static void addNextFragment(int containerid, Activity mActivity, Fragment targetedFragment, Fragment shooterFragment, boolean isDownToUp) {

        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();


        if (isDownToUp) {
            transaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in,
                    R.animator.slide_fragment_vertical_right_out);


        } else {
            transaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in,
                    R.animator.slide_fragment_horizontal_right_out);

        }


        transaction.add(containerid, targetedFragment, targetedFragment.getClass().getSimpleName());
        //curFragment = targetedFragment;
        transaction.hide(shooterFragment);
        transaction.addToBackStack(shooterFragment.getClass().getSimpleName());
        transaction.commit();
    }

    public static void addNextFragmentTag(int containerid, Activity mActivity, Fragment targetedFragment, Fragment shooterFragment, boolean isDownToUp, String tag) {

        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();


        if (isDownToUp) {
            transaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in,
                    R.animator.slide_fragment_vertical_right_out);


        } else {
            transaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in,
                    R.animator.slide_fragment_horizontal_right_out);

        }


        transaction.add(containerid, targetedFragment, tag);
        //curFragment = targetedFragment;
        transaction.hide(shooterFragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }


    public static void addNextFragmentTagWithoutBackStack(int containerid, Activity mActivity, Fragment targetedFragment, Fragment shooterFragment, boolean isDownToUp, String tag) {

        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();


        if (isDownToUp) {
            transaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in,
                    R.animator.slide_fragment_vertical_right_out);


        } else {
            transaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in,
                    R.animator.slide_fragment_horizontal_right_out);

        }


        transaction.add(containerid, targetedFragment, tag);
        //curFragment = targetedFragment;
        transaction.hide(shooterFragment);
        transaction.commit();
    }

    /**
     * Common add fragment method
     *
     * @param containerid
     * @param mActivity
     * @param targetedFragment
     * @param shooterFragment
     */

    public static void addNextFragmentNoAnim(int containerid, Activity mActivity, Fragment targetedFragment, Fragment shooterFragment) {

        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();
        transaction.add(containerid, targetedFragment, targetedFragment.getClass().getSimpleName());
        //curFragment = targetedFragment;
        transaction.hide(shooterFragment);
        transaction.addToBackStack(targetedFragment.getClass().getSimpleName());
        transaction.commit();
    }

    public static void addNextChildFragment(int containerid, Activity mActivity, Fragment targetedFragment, Fragment shooterFragment, boolean isDownToUp, FragmentManager fragmentManager) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (isDownToUp) {
            transaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in,
                    R.animator.slide_fragment_vertical_right_out);


        } else {
            transaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in,
                    R.animator.slide_fragment_horizontal_right_out);

        }


        transaction.add(containerid, targetedFragment, targetedFragment.getClass().getSimpleName());
        //curFragment = targetedFragment;
        transaction.hide(shooterFragment);
        transaction.addToBackStack(targetedFragment.getClass().getSimpleName());
        transaction.commit();
    }

    /**
     * Common replace fragent method
     *
     * @param containerid
     * @param mActivity
     * @param targetedFragment
     */
    public static void replaceNextFragment(int containerid, Activity mActivity, Fragment targetedFragment) {

        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();
//        if (isDownToUp) {
//            transaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in,
//                    R.animator.slide_fragment_vertical_right_out);
//
//
//        } else {
//            transaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in,
//                    R.animator.slide_fragment_horizontal_right_out);
//
//        }
        transaction.replace(containerid, targetedFragment, targetedFragment.getClass().getSimpleName());
        //curFragment = targetedFragment;
        transaction.commit();
    }

    /**
     * Common replace method with backstack
     *
     * @param containerid
     * @param mActivity
     * @param targetedFragment
     * @param isDownToUp
     */
    public static void replaceNextFragmentBackStack(int containerid, Activity mActivity, Fragment targetedFragment, boolean isDownToUp) {
        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();

        if (isDownToUp) {
            transaction.setCustomAnimations(R.animator.slide_fragment_vertical_right_in, R.animator.slide_fragment_vertical_left_out, R.animator.slide_fragment_vertical_left_in,
                    R.animator.slide_fragment_vertical_right_out);
        } else {
            transaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in, R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in,
                    R.animator.slide_fragment_horizontal_right_out);
        }

        transaction.replace(containerid, targetedFragment, targetedFragment.getClass().getSimpleName());
        transaction.addToBackStack(targetedFragment.getClass().getSimpleName());
        //curFragment = targetedFragment;
        transaction.commit();
    }

    /**
     * Add fragment with lolipop trasncation
     *
     * @param containerid
     * @param mActivity
     * @param targetedFragment
     * @param shooterFragment
     */
    public static void addNextFragment(int containerid, Activity mActivity, Fragment targetedFragment, Fragment shooterFragment, String textTransitionName, TextView textView) {
        final FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            targetedFragment.setSharedElementEnterTransition(new ChangeImageTransform());
            targetedFragment.setEnterTransition(new Fade());
            targetedFragment.setExitTransition(new Fade());
            targetedFragment.setSharedElementReturnTransition(new ChangeImageTransform());
            transaction.addSharedElement(textView, textTransitionName);
            transaction.add(containerid, targetedFragment, targetedFragment.getClass().getSimpleName());
            transaction.hide(shooterFragment);
            transaction.addToBackStack(targetedFragment.getClass().getSimpleName());
            transaction.commit();
        }
    }

    /**
     * Get current font size index
     *
     * @param fontsize
     * @return
     */
    public static int getFontSizeIndex(int fontsize) {
        HashMap<Integer, Integer> fonthashmap = new HashMap<>();
        fonthashmap.put(Constants.FONT_SMALL, 0);
        fonthashmap.put(Constants.FONT_MEDIUM, 1);
        fonthashmap.put(Constants.FONT_LARGE, 2);
        return fonthashmap.get(fontsize);

    }

    /**
     * Get current language
     *
     * @param codeId
     * @return
     */
    public static String getLanguage(String codeId) {
        if (codeId.equalsIgnoreCase(Constants.LANGUAGE_AR_CODE))
            return Constants.LANGUAGE_AR;
        else if (codeId.equalsIgnoreCase(Constants.LANGUAGE_FR_CODE))
            return Constants.LANGUAGE_FR;
        else
            return Constants.LANGUAGE_EN;
    }

    /**
     * Get device name + UDID
     *
     * @param context
     * @return
     */
    public static String getDeviceName(Context context) {
        String name = "";
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            name = capitalize(model);
        } else {
            name = capitalize(manufacturer) + " " + model;
        }
        name += android.provider.Settings.System.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        return name;
    }

    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length(); ) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    /**
     * Capitalizes string
     *
     * @param s
     * @return
     */
    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static boolean isRTL() {
        return isRTL(Locale.getDefault());
    }

    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    /**
     * Emits a sample share {@link Intent}.
     */
    public static void share(Context context, String message) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(sharingIntent, "Ttile"));
    }

    public static Intent getFileChooserIntent(String contentType) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        final Intent openGalleryIntent;
        if (isKitKat) {
            openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            openGalleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
            openGalleryIntent.setType(contentType);
            openGalleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        } else {
            openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            openGalleryIntent.setType(contentType);
            openGalleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        }
        return openGalleryIntent;
    }

    public static void deleteFiles(File file) {
        if (file.isDirectory())
            for (File f : file.listFiles())
                deleteFiles(f);
        else
            file.delete();
    }

//    public static Menu newMenuInstance(Context context) {
//        try {
//            Class<?> menuBuilderClass = Class.forName("com.android.internal.view.menu.MenuBuilder");
//            Constructor<?> constructor = menuBuilderClass.getDeclaredConstructor(Context.class);
//            return (Menu) constructor.newInstance(context);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public static void openFile(Context context, File url) {
        // Create URI
        File file = url;
        Uri uri = Uri.fromFile(file);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // Check what kind of file you are trying to open, by comparing the url with extensions.
            // When the if condition is matched, plugin sets the correct intent (mime) type,
            // so Android knew what application to use to open the file
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.error_unsupportedfile, Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isNotNullOrEmpty(String data) {
        return !TextUtils.isEmpty(data) && !data.equalsIgnoreCase("null");
    }

    public static String isCheckNullOrEmpty(JSONObject contentObject, String arg) {
        return contentObject.isNull(arg) ? "" : contentObject.optString(arg);
    }


    @NonNull
    public static String getBasePath(Context context) {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/" + context.getString(R.string.app_name));
        return dir.getPath();
    }

    /**
     * Common interface to implement form validation
     */
    public interface FormValidator {
        void onValidate();

        void onValidationSuccess();

        void onValidationFailure(String errorMessage);
    }

    public static String getHashKey(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
                return something;
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        return null;
    }

    public static ArrayList<String> getFacebookPermision() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("public_profile");
        arrayList.add("user_friends");
        arrayList.add("email");
        arrayList.add("user_about_me");
        arrayList.add("user_birthday");
        arrayList.add("user_education_history");
        arrayList.add("user_events");
        arrayList.add("user_games_activity");
        arrayList.add("user_hometown");
        arrayList.add("user_likes");
        arrayList.add("user_location");
        arrayList.add("user_photos");
        arrayList.add("user_posts");
        arrayList.add("user_relationships");
        arrayList.add("user_relationship_details");
        arrayList.add("user_religion_politics");
        arrayList.add("user_tagged_places");
        arrayList.add("user_work_history");
        arrayList.add("read_custom_friendlists");
        arrayList.add("pages_show_list");
        return arrayList;
    }

    public static int convertKMToMile(int km) {
        int mile = (int) (0.621 * km);
        return mile;
    }

    public static int convertMileToKM(int Mile) {
        int km = (int) (Mile / 0.621);
        return km;
    }

    public static long getFileSizeInKB(String path) {
        // Get file from file name
        File file = new File(path);

// Get length of file in bytes
        long fileSizeInBytes = file.length();
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        return fileSizeInKB;
    }

    public static String getLocationFromLatLong(Location location, Context context) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            return city + "," + state;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getAge(String dateofbirth) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.API_DATEFORMAT, Locale.ENGLISH);

            cal.setTime(sdf.parse(dateofbirth));

            Calendar dob = Calendar.getInstance();
            Calendar today = Calendar.getInstance();

            dob.setTime(sdf.parse(dateofbirth));

            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            Integer ageInt = new Integer(age);
            String ageS = ageInt.toString();

            return ageS;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String changeFbDateFormat(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);

            cal.setTime(sdf.parse(date));

            SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.API_DATEFORMAT, Locale.US);
            return sdf1.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDistanceinString(double src_lat, double src_long, double dest_lat, double dest_long) {
        try {
            Location locationA = new Location("point A");

            locationA.setLatitude(src_lat);
            locationA.setLongitude(src_long);

            Location locationB = new Location("point B");

            locationB.setLatitude(dest_lat);
            locationB.setLongitude(dest_long);

            float distance = locationA.distanceTo(locationB);
            if (DatingApp.getsInstance().getSharedPreferences().getBoolean(PREF.PREF_DISTANCE_UNIT_KM, true)) {
                return "" + distance / 1000 + " km";
            } else {
                return "" + (float) ((distance / 1000) * 0.000621371192) + " mi";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
