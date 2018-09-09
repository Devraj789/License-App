package com.devraj.licenseapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;

public class LaUtils {
    public static boolean checkNetworkAvailability(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && (networkInfo.isConnected());

    }

    public static DecimalFormat getDecimalFormat() {
        String reg = "#0.00";
        DecimalFormat foramt = new DecimalFormat(reg);
        return foramt;
    }

    /**
     * method which returns blur bitmap
     *
     * @param
     * @return blur bitmap
     */
    public static Bitmap getBlurDrawable(Bitmap sentBitmap) {

        int radius = 25;

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return bitmap;
    }

    public static String[] getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String date = calendar.get(Calendar.YEAR)
                + "/"
                + (calendar.get(Calendar.MONTH) + 1)
                + "/"
                + calendar.get(Calendar.DAY_OF_MONTH);
//	            String date = calendar.getDisplayName(Calendar.DAY_OF_WEEK,
//				Calendar.LONG, Locale.US).toUpperCase(Locale.US)
//				+ " , "
//				+ calendar.getDisplayName(Calendar.MONTH, Calendar.LONG,
//						Locale.US).toUpperCase(Locale.US)
//				+ "  "
//				+ calendar.get(Calendar.DAY_OF_MONTH);
        String time = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))
                + ":"
                + String.format("%02d", calendar.get(Calendar.MINUTE));
//                + " "
//                + calendar.getDisplayName(Calendar.AM_PM, Calendar.LONG,
//                Locale.US).toUpperCase(Locale.US);

        return new String[]{time, date};
    }

    public static String getDeviceName() {
        return android.os.Build.MANUFACTURER;
    }

    public static File getVideoSaveLocation(Context context, String imageurl) {

        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + "/Android/data/"
                        + context.getPackageName() + "/Files/Video");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // return null;
            }
        }
        // Create a media file name

        String mImageName = Integer.toString(imageurl.hashCode());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + mImageName);
        return mediaFile;
    }

    public static File getImageSaveLocation(Context context, String imageurl) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + "/Android/data/"
                        + context.getPackageName() + "/Files/AdImage");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // return null;
            }
        }
        // Create a media file name

        String mImageName = Integer.toString(imageurl.hashCode());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + mImageName);
        return new File(mediaStorageDir.getPath());
    }

    public static File getImageLocation(Context context, String imageurl) {
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + "/Android/data/"
                        + context.getPackageName() + "/Files/AdImage");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // return null;
            }
        }
        // Creates a media file name

        String mImageName = Integer.toString(imageurl.hashCode());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + mImageName);
        return mediaFile;
    }

    public static File getThumbSaveLocation(Context context, String imageurl) {
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + "/Android/data/"
                        + context.getPackageName() + "/Files/Thumb");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // return null;
            }
        }


        return new File(mediaStorageDir.getPath());
    }

    public static File getThumbLocation(Context context, String imageurl) {
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + "/Android/data/"
                        + context.getPackageName() + "/Files/Thumb");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // return null;
            }
        }
        String mImageName = Integer.toString(imageurl.hashCode());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + mImageName);
        return mediaFile;
    }

    public static File getProfileLocation(Context context, String imageurl) {
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory() + "/Android/data/"
                        + context.getPackageName() + "/.profile");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // return null;
            }
        }
        String mImageName = Integer.toString(imageurl.hashCode());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + mImageName);
        return mediaFile;
    }

    public static boolean isMobileDataEabled(Context context) {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class<?> cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API
        }
        return mobileDataEnabled;
    }

    public boolean CheckInternet(Context ctx) {
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = connec
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Check if wifi or mobile network is available or not. If any of them
        // is
        // available or connected then it will return true, otherwise false;
        return mobile.isConnected();
    }

    public static int getRandomNum(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static int queryCallLog(Context mContext) {
        int missedCallNo = 0;
        final int MISSED_CALL_TYPE = android.provider.CallLog.Calls.MISSED_TYPE;
        final String[] projection = null;
        final String selection = null;
        final String[] selectionArgs = null;
        final String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(
                    Uri.parse("content://call_log/calls"), projection,
                    selection, selectionArgs, sortOrder);

            while (cursor.moveToNext()) {
                String callType = cursor.getString(cursor
                        .getColumnIndex(android.provider.CallLog.Calls.TYPE));
                String isCallNew = cursor.getString(cursor
                        .getColumnIndex(android.provider.CallLog.Calls.NEW));
                if (Integer.parseInt(callType) == MISSED_CALL_TYPE
                        && Integer.parseInt(isCallNew) > 0) {
                    missedCallNo = missedCallNo + 1;
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return missedCallNo;
    }


    public static boolean emptyFieldValidation(EditText view) {
        String value = view.getText().toString();
        if (value.length() > 0) {
            return true;
        } else {
            view.setError("Enter value");
            return false;
        }
    }

}
