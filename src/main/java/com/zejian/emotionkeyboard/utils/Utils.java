//package com.zejian.emotionkeyboard.utils;
//
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.app.ActivityManager.RunningTaskInfo;
//import android.app.Dialog;
//import android.app.KeyguardManager;
//import android.content.ActivityNotFoundException;
//import android.content.ContentProviderOperation;
//import android.content.ContentResolver;
//import android.content.ContentUris;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.CompressFormat;
//import android.graphics.Bitmap.Config;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.os.Parcelable;
//import android.os.PowerManager;
//import android.provider.ContactsContract;
//import android.provider.ContactsContract.Data;
//import android.provider.ContactsContract.PhoneLookup;
//import android.provider.MediaStore;
//import android.provider.Settings;
//import android.support.v4.content.FileProvider;
//import android.text.TextPaint;
//import android.util.Log;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.acucom.R;
//import com.acucom.activities.WebViewActivity;
//import com.acucom.data.Contact;
//import com.acucom.data.Contact.Node;
//import com.acucom.data.Country;
//import com.acucom.data.SimContact;
//import com.acucom.data.SimpleContact;
//import com.acucom.data.SimpleContact.Item;
//import com.acucom.data.msgs.ShareLocationTopic;
//import com.acucom.data.msgs.TextTopic;
//import com.acucom.data.msgs.Topic;
//import com.acucom.data.msgs.TopicEntity;
//import com.acucom.data.msgs.TopicEntity.Permission;
//import com.acucom.data.msgs.VoipTopic;
//import com.acucom.runtime.Runtime;
//import com.acucom.server.NotificationManagerCtl;
//import com.acucom.storage.SharePreferenceStorage;
//import com.acucom.ui.photoviewer.ImagePagerActivity;
//import com.acucom.ui.photoviewer.ImagePagerActivityEx;
//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.util.SortedMap;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.sql.Date;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Random;
//import java.util.Set;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class Utils {
//    private static final String TAG = "Utils";
//    private static final long ADAY = 24 * 60 * 60 * 1000;
//
//    public static String calculateTime(Long time) {
//        if (time == null)
//            return "N/A";
//        SimpleDateFormat sdf = new SimpleDateFormat();
//        Date date = new Date(time);
//        if (time > System.currentTimeMillis() - ADAY) {
//            sdf = new SimpleDateFormat("H:m");
//        } else {
//            sdf = new SimpleDateFormat("d/M");
//        }
//        return sdf.format(date);
//    }
//
//    /**
//     * @return CN .etc
//     */
//    public static String getCurVersionCode() {
//        try {
//            Context context = (Context) Runtime.getInstance().getAnyContext();
//            PackageManager packageManager = context.getPackageManager();
//            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
//            String vName = packInfo.versionName;
//            return vName;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static String getCurVersionCode(Context cx) {
//        try {
//            PackageManager packageManager = cx.getPackageManager();
//            PackageInfo packInfo = packageManager.getPackageInfo(cx.getPackageName(), 0);
//            String vName = packInfo.versionCode+"";
//            return vName;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static String getCurVersionName(Context cx) {
//        try {
//            PackageManager packageManager = cx.getPackageManager();
//            PackageInfo packInfo = packageManager.getPackageInfo(cx.getPackageName(), 0);
//            String vName = packInfo.versionName;
//            return vName;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static String getProductName() {
//        Context context = (Context) Runtime.getInstance().getAnyContext();
//        return context.getString(R.string.product_name);
//    }
//    /**
//     * 为了比避免空指针，在sd卡卸载或其他原因不存在时返回空字符串
//     * @param context
//     * @return
//     */
////	public static File getSDPath(Context context) {
////		File sdDir = null;
////		boolean sdCardExist = Environment.getExternalStorageState().equals(
////				android.os.Environment.MEDIA_MOUNTED);
////		if (sdCardExist) {
////			sdDir = Environment.getExternalStorageDirectory();
////		} else {
////			Toast.makeText(context, context.getString(R.string.session_sdcard_disabled), 1)
////					.show();
////		}
////		return sdDir;
////	}
//
//    /**
//     * 为了比避免空指针，在sd卡卸载或其他原因不存在时返回空字符串
//     *
//     * @return
//     */
//    public static String getSDAbsPath() {
//        String sdAbsPath = null;
//        boolean sdCardExist = Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED);
//        if (sdCardExist) {
//            sdAbsPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//            AppBuffer.isSDCardExist = true;
//        } else {
//            AppBuffer.isSDCardExist = false;
////			Toast.makeText(context, context.getString(R.string.session_sdcard_disabled), 1)
////			.show();
//        }
//        return sdAbsPath;
//    }
//
//    /**
//     * 根据标识判断sdcard是否已加载
//     *
//     * @return
//     */
//    public static boolean isSDCardExist() {
//        return AppBuffer.isSDCardExist;
//    }
//
//    /**
//     * 根据标识判断sdcard是否存在，不存在的话弹出toast
//     *
//     * @param cx
//     * @return
//     */
//    public static boolean isSDCardExist(Context cx) {
//        if (AppBuffer.isSDCardExist) {
//            return true;
//        } else {
//            Toast.makeText(cx, cx.getString(R.string.sd_card_not_available), AcuConstant.TOAST_LENGTH_MAX).show();
//            return false;
//        }
//    }
//
//    public static byte[] readStream(InputStream inStream) throws IOException {
//        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len = -1;
//        while ((len = inStream.read(buffer)) != -1) {
//            outSteam.write(buffer, 0, len);
//        }
//        outSteam.close();
//        inStream.close();
//        return outSteam.toByteArray();
//    }
//
//    /**
//     * 基于内存的图片压缩
//     *
//     * @param fromFile
//     * @param toFile
//     * @param width
//     * @param height
//     * @param quality
//     * @param rotateValue
//     * @throws IOException
//     */
//    public static void transImage(String fromFile, String toFile, int width,
//                                  int height, int quality, int rotateValue) throws IOException
//
//    {
//        //默认目标宽高 width height 都是1000 的
//        Bitmap bitmap = BitmapFactory.decodeFile(fromFile);
//        if (rotateValue > 0) {
//            bitmap = ImageUtil.rotateBitmap(bitmap, rotateValue); // 旋转图片
//        }
//        int bitmapWidth = bitmap.getWidth();
//        int bitmapHeight = bitmap.getHeight();
//
//        // 缩放图片的尺寸
//        float targetWidth = 0;
//        float targetHeight = 0;
//
//        if (bitmapHeight > bitmapWidth && bitmapHeight > height) {
//            targetHeight = height;
//            targetWidth = (float) (bitmapWidth * height) / (float) bitmapHeight;
//        } else if (bitmapWidth > bitmapHeight && bitmapWidth > height) {
//            targetWidth = height;
//            targetHeight = (float) (bitmapHeight * height) / (float) bitmapWidth;
//        } else if (bitmapHeight == bitmapWidth && bitmapHeight > height) {
//            targetWidth = height;
//            targetHeight = height;
//        } else {  // 原始图片都在1000 以下 
//            targetWidth = bitmapWidth;
//            targetHeight = bitmapHeight;
//        }
//
//        float scaleWidth = targetWidth / (float) bitmapWidth;
//        float scaleHeight = targetHeight / (float) bitmapHeight;
//
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
//        // 产生缩放后的Bitmap对象
//        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth,
//                bitmapHeight, matrix, false);
//        // save file
//        File myCaptureFile = new File(toFile);
//        FileOutputStream out = new FileOutputStream(myCaptureFile);
//        if (resizeBitmap.compress(CompressFormat.PNG, quality, out)) {
//            out.flush();
//            out.close();
//        }
//        if (!bitmap.isRecycled()) {
//            bitmap.recycle();// 记得释放资源，否则会内存溢出
//        }
//        if (!resizeBitmap.isRecycled()) {
//            resizeBitmap.recycle();
//        }
//
//    }
//
//    /**
//     * 根据传入的图片文件路径得到缩略图并保存到指定目录
//     *
//     * @param fromPath       源文件路径
//     * @param tartgetAblPath 复制到的文件绝对路径
//     * @param type           图片需要压缩的宽高比，如 AcuConstant.TYPE_1000X1000
//     * @param rotateValue    AcuConstant.TYPE_1000X1000
//     */
//    public static int[] compressAndSaveImage(String fromPath, String tartgetAblPath, int type, int rotateValue) {
//        Context cx = (Context) Runtime.getInstance().getContext();
//        int quality = 90;
////		CompressFormat formatType = Bitmap.CompressFormat.PNG;
//        CompressFormat formatType = CompressFormat.JPEG;
//        Bitmap sourceBmp = ReadBitmapByPath(cx, fromPath, type);
//        if (rotateValue > 0) {
//            sourceBmp = ImageUtil.rotateBitmap(sourceBmp, rotateValue);  //旋转图片
//        }
//        int[] s = new int[2];
//        if (sourceBmp != null) {
//            s[0] = sourceBmp.getWidth();
//            s[1] = sourceBmp.getHeight();
//        }
//        saveBitmap2File(sourceBmp, tartgetAblPath, quality, formatType);
//
//        return s;
//    }
//
//    /**
//     * 保存bitmap到指定路径的文件
//     *
//     * @param bmp
//     * @param tartgetAblPath 保存的文件的绝对路径
//     * @param quality        图片保存的quality
//     * @param formatType     图片保存的格式
//     */
//    public static void saveBitmap2File(Bitmap bmp, String tartgetAblPath, int quality, CompressFormat formatType) {
//        File targetFile = new File(tartgetAblPath);
//        if (!targetFile.exists())
//            FileUtil.isFileExist(tartgetAblPath);
//        FileOutputStream fOut = null;
//        try {
//            fOut = new FileOutputStream(targetFile);
//            if (fOut != null) {
//                bmp.compress(formatType, quality, fOut);
//            }
//            bmp.recycle();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fOut != null)
//                    fOut.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (fOut != null)
//                    fOut.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 将图片写入到磁盘
//     *
//     * @param img         图片数据流
//     * @param absFileName 文件保存时的绝对路径
//     */
//    public static void writeImageByteToDisk(byte[] img, String absFileName) {
//        try {
//            File file = new File(absFileName);
//            if (file != null) {
//                FileOutputStream fops = new FileOutputStream(file);
//                fops.write(img);
//                fops.flush();
//                fops.close();
//            } else {
//                throw new IOException("file not exist!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 根据图片的绝对路径获取Bitmap
//     *
//     * @param cx
//     * @param filePath 文件的绝对路径
//     * @param type     图片需要压缩的宽高比，如 AcuConstant.TYPE_1000X1000， 值为-1时，获取的是原图的Bitmap
//     * @return
//     */
//    public static Bitmap ReadBitmapByPath(Context cx, String filePath, int type) {
//        Uri uri = Uri.fromFile(new File(filePath));
//        InputStream is = null;
//        try {
//            is = cx.getContentResolver().openInputStream(uri);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Config.RGB_565;
//            options.inInputShareable = true;
//            options.inPurgeable = true;
//            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
//            //
//            int srcWidth = bitmap.getWidth();
//            int srcHeight = bitmap.getHeight();
//            int targetWidth = srcWidth;
//            int targetHeight = srcHeight;
//            int[] targetArray = null;
//            switch (type) {
//                case AcuConstant.TYPE_2000X2000:
//                    targetArray = getTargetWHByMaxHeight(2000, srcWidth, srcHeight, targetWidth, targetHeight);
//                    break;
//                case AcuConstant.TYPE_1000X1000:
//                    targetArray = getTargetWHByMaxHeight(1000, srcWidth, srcHeight, targetWidth, targetHeight);
//                    break;
//                case AcuConstant.TYPE_640X640:
//                    targetArray = getTargetWHByMaxHeight(640, srcWidth, srcHeight, targetWidth, targetHeight);
//                    break;
//                case AcuConstant.TYPE_400X400:
//                    targetArray = getTargetWHByMaxHeight(400, srcWidth, srcHeight, targetWidth, targetHeight);
//                    break;
//                case AcuConstant.TYPE_300X300:
//                    targetArray = getTargetWHByMaxHeight(300, srcWidth, srcHeight, targetWidth, targetHeight);
//                    break;
//                case AcuConstant.TYPE_200X200:
//                    targetArray = getTargetWHByMaxHeight(200, srcWidth, srcHeight, targetWidth, targetHeight);
//                    break;
//                case AcuConstant.TYPE_100X100:
//                    targetArray = getTargetWHByMaxHeight(100, srcWidth, srcHeight, targetWidth, targetHeight);
//                    break;
//                case AcuConstant.TYPE_60X60:
//                    targetArray = getTargetWHByMaxHeight(60, srcWidth, srcHeight, targetWidth, targetHeight);
//                    break;
//                case AcuConstant.TYPE_30X30:
//                    targetArray = getTargetWHByMaxHeight(30, srcWidth, srcHeight, targetWidth, targetHeight);
//                    break;
//            }
//            if (targetArray != null && targetArray.length != 0) {
//                targetWidth = targetArray[0];
//                targetHeight = targetArray[1];
//            }
//            //
//            return BitmapUtil.getBitmap(bitmap, targetWidth, targetHeight);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (is != null)
//                    is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public static int[] getTargetSpecalWByMaxWidth(int maxWdith,
//                                                   int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
//
//        targetWidth = maxWdith;
//        targetHeight = (int) ((float) (srcHeight * targetWidth) / (float) srcWidth);
//        int[] targetArray = {targetWidth, targetHeight};
//        return targetArray;
//    }
//
//    /**
//     * 返回的数组第一个是targetWidth 第二个是targetHeight
//     *
//     * @param maxHeight
//     * @param srcWidth
//     * @param srcHeight
//     * @param targetWidth
//     * @param targetHeight
//     * @return
//     */
//    public static int[] getTargetWHByMaxHeight(int maxHeight,
//                                               int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
//        if (maxHeight == AcuConstant.TYPE_1000X1000) maxHeight = 1000;
//        else if (maxHeight == AcuConstant.TYPE_2000X2000) maxHeight = 2000;
//        if (srcHeight > srcWidth && srcHeight > maxHeight) {
//            targetHeight = maxHeight;
//            targetWidth = (int) ((float) (srcWidth * maxHeight) / (float) srcHeight);
//        } else if (srcWidth > srcHeight && srcWidth > maxHeight) {
//            targetWidth = maxHeight;
//            targetHeight = (int) ((float) (srcHeight * maxHeight) / (float) srcWidth);
//        } else if (srcHeight == srcWidth && srcHeight > maxHeight) {
//            targetWidth = maxHeight;
//            targetHeight = maxHeight;
//        } else {  // 原始图片都在maxHeight 以下 
//            targetWidth = srcWidth;
//            targetHeight = srcHeight;
//        }
//        int[] targetArray = {targetWidth, targetHeight};
//        return targetArray;
//    }
//
//    /**
//     * 传入的srcWidth 小于 minWidth
//     * 返回的数组第一个是targetWidth 第二个是targetHeight
//     *
//     * @param minWidth
//     * @param srcWidth
//     * @param srcHeight
//     * @param targetWidth
//     * @param targetHeight
//     * @return
//     */
//    public static int[] getTargetWHByMinWidth(int minWidth, int srcWidth,
//                                              int srcHeight, int targetWidth, int targetHeight) {
//        if (srcWidth != srcHeight) {
//            targetWidth = minWidth;
//            targetHeight = (int) ((float) (srcHeight * minWidth) / (float) srcWidth);
//        } else if (srcWidth == srcHeight) {
//            targetWidth = minWidth;
//            targetHeight = minWidth;
//        }
//        int[] targetArray = {targetWidth, targetHeight};
//        return targetArray;
//    }
//
//    /**
//     * 根据传入的文件完整路径将文件删除
//     *
//     * @param tempPath
//     */
//    public static void cleanTempPhoto(String tempPath) {
////		if(tempPath == null) {
////			return;
////		}
////
////		try {
////			File tempFile = new File(tempPath);
////			if(tempFile.exists()) {
////				tempFile.delete();
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//        Utils.deleteFile(tempPath);
//    }
//
//    /**复制指定路径的图片文件到指定目录*/
//    /**
//     * 赋值文件到指定路径
//     *
//     * @param fromPath     文件源路径
//     * @param toPath       复制后新文件路径
//     * @param newFilenName 复制后的新文件名
//     */
//    public static boolean copyFile(String fromPath, String toPath, String newFilenName) {
//
//        if (fromPath == null || toPath == null || newFilenName == null) {
//            return false;
//        }
//
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//        try {
//            File toFile = new File(toPath);
//            if (!toFile.exists()) {
//                toFile.mkdirs();
//            }
//            File fromFile = new File(fromPath);
//
//            bis = new BufferedInputStream(new FileInputStream(fromFile));
//            bos = new BufferedOutputStream(new FileOutputStream(toFile + "/" + newFilenName));
//            byte[] buf = new byte[1024];
//            int readLen = 0;
//
//            while ((readLen = bis.read(buf)) != -1) {
//                bos.write(buf, 0, readLen);
//                bos.flush();
//            }
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bis != null && bos != null) {
//                    bis.close();
//                    bos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//
//    }
//
//    /**
//     * 赋值文件到指定路径
//     *
//     * @param sourceAbsPath 文件源绝对路径
//     * @param targetAbsPath 复制后新文件绝对路径
//     */
//    public static boolean copyFile(String sourceAbsPath, String targetAbsPath) {
//
//        if (targetAbsPath == null || targetAbsPath == null) {
//            return false;
//        }
//
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//        try {
//            File toFile = new File(targetAbsPath);
//            if (!toFile.exists()) {
//                FileUtil.isFileExist(targetAbsPath);
//            }
//            File fromFile = new File(sourceAbsPath);
//
//            bis = new BufferedInputStream(new FileInputStream(fromFile));
//            bos = new BufferedOutputStream(new FileOutputStream(targetAbsPath));
//            byte[] buf = new byte[1024];
//            int readLen = 0;
//
//            while ((readLen = bis.read(buf)) != -1) {
//                bos.write(buf, 0, readLen);
//                bos.flush();
//            }
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bis != null && bos != null) {
//                    bis.close();
//                    bos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//
//    }
//
//    /**
//     * 文件流的拷贝
//     * @param input
//     * @param output
//     * @return
//     * @throws IOException
//     */
//    public static int copy(InputStream input, OutputStream output) throws IOException {
//        long count = copyLarge(input, output);
//        return count > 2147483647L?-1:(int)count;
//    }
//
//    private static long copyLarge(InputStream input, OutputStream output)
//            throws IOException {
//        byte[] buffer = new byte[4096];
//        long count = 0L;
//
//        int n1;
//        for(boolean n = false; -1 != (n1 = input.read(buffer)); count += (long)n1) {
//            output.write(buffer, 0, n1);
//        }
//
//        return count;
//    }
//
//    /**
//     * 读取文件里的Json数据并封装到SortedMap返回
//     *
//     * @param context
//     * @return
//     */
//    public static SortedMap<String, Country> getCountryList(Context context) {
//        InputStream stream = null;
//        SortedMap<String, Country> countryMap;
//
//        try {
////			AssetManager am = context.getAssets();
////			stream = am.open("countrys");
//
//            stream = context.getResources().openRawResource(R.raw.countrys);
//            int size = stream.available();
//            byte[] buffer = new byte[size];
//            stream.read(buffer);
//            stream.close();
//            String text = new String(buffer, "UTF-8");
//
//            JSONObject data = new JSONObject(text);
//            JSONObject json = data.getJSONObject("dict");
//            JSONArray keyArray = json.optJSONArray("key");
//
//            JSONArray allValueArray = json.optJSONArray("array");
//            JSONArray valueArray;
//            JSONObject valueJson;
//
//            if (keyArray != null && keyArray.length() > 0) {
//                countryMap = new SortedMap<String, Country>();
//                int keySize = keyArray.length();
//
//                for (int i = 0; i < keySize; i++) {
//                    valueJson = allValueArray.optJSONObject(i);
//                    valueArray = valueJson.optJSONArray("string");
//                    if (valueArray != null) {
//                        Country country = new Country();
//                        country.key = keyArray.optString(i);
//                        for (int j = 0; j < valueArray.length(); j++) {
//                            country.name = valueArray.optString(0);
//                            country.code = valueArray.optString(1);
//                        }
//                        countryMap.put(country.key, country);
//                    }
//
//                }
//                return countryMap;
//            }
//
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } finally {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return null;
//
//    }
//
//    /**
//     * 判断传入的Activity是否在栈定
//     *
//     * @param context
//     * @param comActivityName
//     * @return
//     */
//    public static boolean isTopActivity(Context context, String comActivityName) {
//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
//
//        String topActivityName = null;
//        if (runningTaskInfos != null) {
//            topActivityName = runningTaskInfos.get(0).topActivity.toString();
//            return topActivityName.equals(comActivityName);
//        } else return false;
//
//    }
//
//    /**
//     * 判断指定路径的文件是否存在
//     *
//     * @param filePath
//     * @return
//     */
//    public static boolean isFileIsExist(String filePath) {
//        if (filePath == null || "".equals(filePath)) {
//            return false;
//        }
//        File file = new File(filePath);
//        return file.exists();
//    }
//
//    /**
//     * 判断指定路径的文件是否存在，只有当两个路径对应的文件都不存在时才返回false
//     *
//     * @param localPath 从Msg里获取的路径
//     * @param msgIdPath 根据rid组拼的路径
//     * @return
//     */
//    public static boolean isFileIsExist(String localPath, String msgIdPath) {
//        boolean flag = false;
//        if (localPath != null && !"".equals(localPath)) {
//            File file1 = new File(localPath);
//            if (file1 != null && file1.exists()) {
//                flag = true;
//            } else if (msgIdPath != null && !"".equals(msgIdPath)) {
//                File file2 = new File(msgIdPath);
//                if (file2 != null && file2.exists()) {
//                    flag = true;
//                }
//            }
//        }
//
//
//        return flag;
//    }
//
//    /**
//     * 调用浏览器打开url
//     *
//     * @param urlStr
//     */
//    public static void callForBrowser(Context context, String urlStr) {
//        if (urlStr == null || "".equals(urlStr)) {
//            return;
//        }
//
//        String tempUrlStr = urlStr.toLowerCase();
//        if (!tempUrlStr.startsWith("http://") && !tempUrlStr.startsWith("https://")) {
//            urlStr = "http://" + urlStr;
//        }
//        if (urlStr.length() > 5) {
//            String preStr = urlStr.substring(0, 5).toLowerCase();
//            String suffixStr = urlStr.substring(5, urlStr.length());
//            urlStr = preStr + suffixStr;
//        }
//        try {
//            Uri content_url = Uri.parse(urlStr);
//            Intent intent = new Intent(Intent.ACTION_VIEW, content_url);
//            context.startActivity(intent);
//        } catch (Exception e) {
//            Toast.makeText(context, context.getString(R.string.no_app_available), AcuConstant.TOAST_LENGTH_MAX).show();
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 调用WebviewActivity 打开本地文件
//     *
//     * @param cx
//     * @param filePath
//     * @param withStyle 是否需要加载本地样式
//     */
//    public static void call4WebView2OpenFile(Context cx, String filePath, String withStyle) {
//        Intent i = new Intent();
//        i.putExtra("modelType", WebViewActivity.MODEL_LOCALFILE);
//        i.putExtra("withStyle", WebViewActivity.STYLE_CENTER);
//        i.putExtra("filePath", filePath);
//        i.setClass(cx, WebViewActivity.class);
//        cx.startActivity(i);
//    }
//
//    /**
//     * 调用系统发邮件
//     *
//     * @param context
//     * @param targetAddress 收件人地址
//     * @param emailSubject  邮件主题
//     */
//    public static void call4SysEmail(Context context, String targetAddress, String emailSubject) {
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL,
//                new String[]{targetAddress});
//        if(emailSubject != null)
//            i.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
//        i.putExtra(Intent.EXTRA_TEXT, "");
//        try {
//            context.startActivity(Intent.createChooser(i, "Send mail..."));
//        } catch (ActivityNotFoundException ex) {
//            Toast.makeText(context,
//                    "Email can't be sent due to device problem",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * 调用系统发邮件
//     *
//     * @param context
//     * @param filePath
//     * @param subtitle
//     * @param attachList
//     */
//    public static void call4SysEmail(Context context, String filePath, String subtitle, List<String> attachList) {
//        //need to "send multiple" to get more than one attachment
//        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
//        emailIntent.setType("text/plain");
//        emailIntent.putExtra("subject", subtitle);
////	    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
////	        new String[]{emailTo});
////	    emailIntent.putExtra(android.content.Intent.EXTRA_CC,
////	        new String[]{emailCC});
//        //has to be an ArrayList
//        ArrayList<Uri> uris = new ArrayList<Uri>();
//        //convert from paths to Android friendly Parcelable Uri's
//        for (String file : attachList) {
//            File fileIn = new File(file);
//            Uri u = Uri.fromFile(fileIn);
//            uris.add(u);
//        }
//        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
////	    context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//        context.startActivity(emailIntent);
//    }
//
//    /**
//     * 调用系统添加/编辑联系人页面，并传入响应数据
//     *
//     * @param s
//     */
//    public static void callForSysContactAdd(Context context, SimpleContact s) {
//        if (s == null) {
//            return;
//        }
//
//        Intent intent = new Intent(Intent.ACTION_INSERT);
//        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
//
//
//        intent.putExtra(ContactsContract.Intents.Insert.NAME, s.getName()); //名字
//        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, s.getOrgn()); //公司
//        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, s.getJobt()); //职称
//        //phones
//        Item[] phones = s.getPhones();
//        if (phones != null && phones.length > 0) {
//            intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, phones[0].getValue()); //手机
////    		intent.putExtra(ContactsContract.Intents.Insert.PHONE, "111"); //住宅
////    		intent.putExtra(ContactsContract.Intents.Insert.TERTIARY_PHONE, "333");//单位
//        }
//        //emails
//        Item[] emails = s.getEmails();
//        if (emails != null && emails.length > 0) {
//            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emails[0].getValue()); //
////    		intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_EMAIL, emails[0].getValue()); //
////    		intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_EMAIL, emails[0].getValue()); //
//        }
//        //addres
//        Item[] addres = s.getAdds();
//        if (addres != null && addres.length > 0) {
//            intent.putExtra(ContactsContract.Intents.Insert.POSTAL, emails[0].getValue()); //邮政地址
//        }
//
//        context.startActivity(intent);
//    }
//
//    /**
//     * 添加联系人到数据库中
//     *
//     * @param context
//     * @param contact
//     */
//    //TODO?
//    public static void insertContacts(Context context, Contact contact) {
////		String DisplayName = "XYZ";
////		String MobileNumber = "123456";
////		String HomeNumber = "1111";
////		String WorkNumber = "2222";
////		String emailID = "email@nomail.com";
////		String company = "bad";
////		String jobTitle = "abcd";
//        String DisplayName = null;
//        String MobileNumber = null;
//        String HomeNumber = null;
//        String WorkNumber = null;
//        String MobileEmail = null;
//        String HomeEmail = null;
//        String WorkEmail = null;
//        String company = null;
//        String jobTitle = null;
//
//        DisplayName = contact.getDisplayName();
//        Node[] nodes = contact.getMsisdns();
//        if (nodes != null && nodes.length > 0) {
//            for (int i = 0; i < nodes.length; i++) {
//                switch (i) {
//                    case 0:
//                        MobileNumber = nodes[i].getValue();
//                        break;
//                    case 1:
//                        HomeNumber = nodes[i].getValue();
//                        break;
//                    case 2:
//                        WorkNumber = nodes[i].getValue();
//                        break;
//                }
//            }
//        }
//        Node[] emails = contact.getEmails();
//        if (emails != null && emails.length > 0) {
//            for (int i = 0; i < emails.length; i++) {
//                switch (i) {
//                    case 0:
//                        MobileEmail = emails[i].getValue();
//                        break;
//                    case 1:
//                        HomeEmail = emails[i].getValue();
//                        break;
//                    case 2:
//                        WorkEmail = emails[i].getValue();
//                        break;
//                }
//            }
//        }
////		if(emails != null && emails.length > 0) {
////			emailID = emails[0].getValue();
////		}
//        company = contact.getCompany();
//        jobTitle = contact.getJobTitle();
//
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//
//        ops.add(ContentProviderOperation
//                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
//                .build());
//
//        // ------------------------------------------------------ Names
//        if (DisplayName != null) {
//            ops.add(ContentProviderOperation
//                    .newInsert(Data.CONTENT_URI)
//                    .withValueBackReference(
//                            Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
//                            DisplayName).build());
//        }
//
//        // ------------------------------------------------------ Mobile Number
//        if (MobileNumber != null) {
//            ops.add(ContentProviderOperation
//                    .newInsert(Data.CONTENT_URI)
//                    .withValueBackReference(
//                            Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
//                            MobileNumber)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
//                    .build());
//        }
//
//        // ------------------------------------------------------ Home Numbers
//        if (HomeNumber != null) {
//            ops.add(ContentProviderOperation
//                    .newInsert(Data.CONTENT_URI)
//                    .withValueBackReference(
//                            Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
//                            HomeNumber)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//                            ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
//                    .build());
//        }
//
//        // ------------------------------------------------------ Work Numbers
//        if (WorkNumber != null) {
//            ops.add(ContentProviderOperation
//                    .newInsert(Data.CONTENT_URI)
//                    .withValueBackReference(
//                            Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
//                            WorkNumber)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
//                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
//                    .build());
//        }
//
//        // ------------------------------------------------------ Email
//        if (MobileEmail != null) {
//            ops.add(ContentProviderOperation
//                    .newInsert(Data.CONTENT_URI)
//                    .withValueBackReference(
//                            Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Email.DATA,
//                            MobileEmail)
//                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
//                            ContactsContract.CommonDataKinds.Email.TYPE_MOBILE)
//                    .build());
//        }
//        if (HomeEmail != null) {
//            ops.add(ContentProviderOperation
//                    .newInsert(Data.CONTENT_URI)
//                    .withValueBackReference(
//                            Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Email.DATA,
//                            HomeEmail)
//                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
//                            ContactsContract.CommonDataKinds.Email.TYPE_HOME)
//                    .build());
//        }
//        if (WorkEmail != null) {
//            ops.add(ContentProviderOperation
//                    .newInsert(Data.CONTENT_URI)
//                    .withValueBackReference(
//                            Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Email.DATA,
//                            WorkEmail)
//                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
//                            ContactsContract.CommonDataKinds.Email.TYPE_WORK)
//                    .build());
//        }
//
//        // ------------------------------------------------------ Organization
//        if (company != null && jobTitle != null && !"".equals(company) && !"".equals(jobTitle)) {
//            ops.add(ContentProviderOperation
//                    .newInsert(Data.CONTENT_URI)
//                    .withValueBackReference(
//                            Data.RAW_CONTACT_ID, 0)
//                    .withValue(
//                            Data.MIMETYPE,
//                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.Organization.COMPANY,
//                            company)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.Organization.TYPE,
//                            ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.Organization.TITLE,
//                            jobTitle)
//                    .withValue(
//                            ContactsContract.CommonDataKinds.Organization.TYPE,
//                            ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
//                    .build());
//        }
//
//        // Asking the Contact provider to create a new contact
//        try {
//            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void call4SysOpenVideo(Context cx, String videoFileAbsPath) {
//        LogEx.d(TAG, "call4SysOpenVideo()  videoFileAbsPath= " + videoFileAbsPath);
//        if (DataUtil.checkFloatViewLogical(cx)) {
//            return;
//        }
//        if (videoFileAbsPath == null || "".equals(videoFileAbsPath)) {
//            return;
//        }
//
//        String type = "*/*";
//        if (videoFileAbsPath.endsWith(AcuConstant.SUFFIX_VEDIO_TEMP)) { // 强制设置为MP4
//            for (int i = 0; i < MIME_MapTable.length; i++) {
//                if (AcuConstant.SUFFIX_VEDIO.equals(MIME_MapTable[i][0]))
//                    type = MIME_MapTable[i][1];
//            }
//        } else {
//            type = getMIMEType(new File(videoFileAbsPath));
//        }
//
//        try {
//            File file = new File(videoFileAbsPath);
//            if (file != null) {
//                Intent intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                //设置intent的Action属性
//                intent.setAction(Intent.ACTION_VIEW);
//                //设置intent的data和Type属性。
//                intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
//                //跳转
//                cx.startActivity(intent);
//            }
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(cx, cx.getString(R.string.no_app_available), AcuConstant.TOAST_LENGTH_MAX).show();
//        } catch (Throwable t) {
//            LogEx.e("openFile", "Open file failed, " + t.getMessage());
//        }
//    }
//
//    /**
//     * 点击打开 msgtype 为 file 类型的消息
//     * @param cx
//     * @param filePath
//     */
//    public static void openFileMsg(Context cx, String filePath, String rid) {
//        if(Runtime.getInstance().isFilePreEnable()) {
//            Set<String> filePreSuffixs =  Runtime.getInstance().getFilePreSuffixs();
//            String fileSuffix = filePath.substring(filePath.lastIndexOf("."));
//            fileSuffix = fileSuffix.replace(".", "");
//            if(fileSuffix != null && fileSuffix.length() > 0 && filePreSuffixs.contains(fileSuffix)) {
//                DataUtil.openFileMsgPreviewLogical(cx, rid);
//            }
//        }
//        else {
//            call4SysOpenFile(cx, filePath);
//        }
//    }
//
//    /**
//     * 调用系统应用打开文件
//     *
//     * @param context
//     * @param filePath 文件的本地绝对路径
//     */
//    public static void call4SysOpenFile(Context context, String filePath) {
//        LogEx.d(TAG, "call4SysOpenFile()  filePath= " + filePath);
//        if (filePath == null || "".equals(filePath)) {
//            return;
//        } else if (filePath != null && filePath.endsWith(".vcf") && Utils.isFileIsExist(filePath)) {
//            Intent intent = new Intent();
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //设置intent的Action属性
//            intent.setAction(Intent.ACTION_VIEW);
//            //设置intent的data和Type属性。
//            intent.setDataAndType(/*uri*/Uri.fromFile(new File(filePath)), "text/x-vcard");
//            //跳转
//            context.startActivity(intent);
//        } else {
//            try {
//                File file = new File(filePath);
//                if (file != null && file.exists()) {
//                    Intent intent = new Intent();
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setAction(Intent.ACTION_VIEW);
//                    String type = getMIMEType(file);
//
//                    Uri data;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        data = FileProvider.getUriForFile(context, context.getApplicationInfo().packageName + ".provider", file);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    } else {
//                        data = Uri.fromFile(file);
//                    }
//
//                    intent.setDataAndType(/*uri*/data, type);
//                    context.startActivity(intent);
//                }
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(context, context.getString(R.string.no_app_available), AcuConstant.TOAST_LENGTH_MAX).show();
//            } catch (Throwable t) {
//                Log.e("openFile", "Open file failed, " + t.getMessage());
//            }
//        }
//    }
//
//    /**
//     * 调用系统发送短信功能并设置了接收对象和信息体
//     *
//     * @param context
//     * @param smsToPhone
//     * @param smsBody
//     */
//    public static void call4SysSendSmsMessage(Context context, String smsToPhone, String smsBody) {
//        Uri smsToUri = Uri.parse("smsto:" + smsToPhone);
//        Intent intent = new Intent(Intent.ACTION_VIEW,
//                smsToUri);
//        intent.putExtra("sms_body", smsBody);
//        intent.setType("vnd.android-dir/mms-sms");
//        context.startActivity(intent);
//    }
//
////	public static void callTel(Context context, String phoneNumber) {
////		Uri uri = Uri.parse("tel:"+phoneNumber);
////		Intent it = new Intent(Intent.ACTION_CALL, uri);
////		context.startActivity(it);
////	}
//
//    /**
//     * 调用系统安装的地图应用打开一个位置点
//     *
//     * @param cx
//     * @param latitude
//     * @param longitude
//     * @param locationName 标注该位置点名称
//     */
//    public static void call4SysLocation(Context cx, double latitude, double longitude, String locationName) {
//        Uri uri = null;
//        if (locationName != null && !"".equals(locationName))
//            uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + locationName);// + "&" + "z=16"
//        else
//            uri = Uri.parse("geo:" + latitude + "," + longitude + "?q=(" + latitude + "," + longitude + ")");//+ "&" + "z=16"
//        Intent it = new Intent(Intent.ACTION_VIEW, uri);
//        try {
//            cx.startActivity(it);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(cx, cx.getString(R.string.no_app_available), AcuConstant.TOAST_LENGTH_MAX).show();
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//    }
//
//    /**
//     * 根据文件后缀名获得对应的MIME类型。
//     *
//     * @param file
//     */
//    public static String getMIMEType(File file) {
//
//        String type = "*/*";
//        String fName = file.getName();
//        //获取后缀名前的分隔符"."在fName中的位置。
//        int dotIndex = fName.lastIndexOf(".");
//        if (dotIndex < 0) {
//            return type;
//        }
//        /* 获取文件的后缀名*/
//        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
//        if (end == "") return type;
//        //在MIME和文件类型的匹配表中找到对应的MIME类型。
//        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
//            if (end.equals(MIME_MapTable[i][0]))
//                type = MIME_MapTable[i][1];
//        }
//        return type;
//    }
//
//    public static final String[][] MIME_MapTable = {
//            //{后缀名，MIME类型}
//            {".3gp", "video/3gpp"},
//            {".apk", "application/vnd.android.package-archive"},
//            {".asf", "video/x-ms-asf"},
//            {".avi", "video/x-msvideo"},
//            {".bin", "application/octet-stream"},
//            {".bmp", "image/bmp"},
//            {".c", "text/plain"},
//            {".class", "application/octet-stream"},
//            {".conf", "text/plain"},
//            {".cpp", "text/plain"},
//            {".doc", "application/msword"},
//            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
//            {".xls", "application/vnd.ms-excel"},
//            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
//            {".exe", "application/octet-stream"},
//            {".gif", "image/gif"},
//            {".gtar", "application/x-gtar"},
//            {".gz", "application/x-gzip"},
//            {".h", "text/plain"},
//            {".htm", "text/html"},
//            {".html", "text/html"},
//            {".jar", "application/java-archive"},
//            {".java", "text/plain"},
//            {".jpeg", "image/jpeg"},
//            {".jpg", "image/jpeg"},
//            {".js", "application/x-javascript"},
//            {".log", "text/plain"},
//            {".m3u", "audio/x-mpegurl"},
//            {".m4a", "audio/mp4a-latm"},
//            {".m4b", "audio/mp4a-latm"},
//            {".m4p", "audio/mp4a-latm"},
//            {".m4u", "video/vnd.mpegurl"},
//            {".m4v", "video/x-m4v"},
//            {".mov", "video/quicktime"},
//            {".mp2", "audio/x-mpeg"},
//            {".mp3", "audio/x-mpeg"},
//            {".mp4", "video/mp4"},
//            {".mpc", "application/vnd.mpohun.certificate"},
//            {".mpe", "video/mpeg"},
//            {".mpeg", "video/mpeg"},
//            {".mpg", "video/mpeg"},
//            {".mpg4", "video/mp4"},
//            {".mpga", "audio/mpeg"},
//            {".msg", "application/vnd.ms-outlook"},
//            {".ogg", "audio/ogg"},
//            {".pdf", "application/pdf"},
//            {".png", "image/png"},
//            {".pps", "application/vnd.ms-powerpoint"},
//            {".ppt", "application/vnd.ms-powerpoint"},
//            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
//            {".prop", "text/plain"},
//            {".rc", "text/plain"},
//            {".rmvb", "audio/x-pn-realaudio"},
//            {".rtf", "application/rtf"},
//            {".sh", "text/plain"},
//            {".tar", "application/x-tar"},
//            {".tgz", "application/x-compressed"},
//            {".txt", "text/plain"},
//            {".wav", "audio/x-wav"},
//            {".wma", "audio/x-ms-wma"},
//            {".wmv", "audio/x-ms-wmv"},
//            {".wps", "application/vnd.ms-works"},
//            {".xml", "text/plain"},
//            {".z", "application/x-compress"},
//            {".zip", "application/x-zip-compressed"},
//            {"", "*/*"}
//    };
//
//
//    /**
//     * 对电话号码进行格式化
//     *
//     * @param swissNumberStr 传入的电话号码
//     * @param courntyCode    国家代号 中国 CN
//     * @return
//     */
//    public static String formatPhoneNum(String swissNumberStr, String courntyCode) {
//        if (swissNumberStr == null || "".equals(swissNumberStr)) {
//            return null;
//        }
//
//        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
//
//        try {
//            PhoneNumber swissNumberProto = phoneUtil.parse(swissNumberStr, courntyCode);
//            swissNumberProto.getNationalNumber();
//            return phoneUtil.format(swissNumberProto, PhoneNumberFormat.E164);
//        } catch (NumberParseException e) {
//            return swissNumberStr;
//        }
//
//    }
//
//    public static String getNationalNum(String phoneNum, String countryCode) {
//        if (phoneNum == null || "".equals(phoneNum)) {
//            return null;
//        }
//        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
//        try {
//            PhoneNumber swissNumberProto = phoneUtil.parse(phoneNum, countryCode);
//            swissNumberProto.getNationalNumber();
//            return swissNumberProto.getNationalNumber() + "";
//        } catch (NumberParseException e) {
//            return phoneNum;
//        }
//    }
//
//    /**
//     * 根据手机号获取contactId和displayName
//     *
//     * @param context
//     * @param number
//     * @return
//     */
//    public static SimContact getSimContactInfoByPhone(Context context, String number) {
//        SimContact contact = new SimContact();
//        String name = null;
//        String contactId = null;
//
//        // define the columns I want the query to return
//        String[] projection = new String[]{
//                PhoneLookup.DISPLAY_NAME,
//                PhoneLookup._ID};
//
//        // encode the phone number and build the filter URI
//        Uri contactUri = Uri.withAppendedPath(
//                PhoneLookup.CONTENT_FILTER_URI,
//                Uri.encode(number));
//
//        // query time
//        Cursor cursor = context.getContentResolver().query(contactUri,
//                projection, null, null, null);
//        try {
//            if (cursor.moveToFirst()) {
//                // Get values from contacts database:
//                contactId = cursor.getString(cursor
//                        .getColumnIndex(PhoneLookup._ID));
//                name = cursor.getString(cursor
//                        .getColumnIndex(PhoneLookup.DISPLAY_NAME));
//
//                contact.displayName = name;
//                contact.contactId = contactId;
//            }
//            return contact;
//        } finally {
//            cursor.close();
//        }
//    }
//
//    public static String getShortWordFromTopic(Topic m, Context context) {
//        String newestMessage = "";
//        if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_AUDIO)) {
//            newestMessage = context.getString(R.string.message_audio);
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_CONTACT)) {
//            newestMessage = context.getString(R.string.message_contact);
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_FILE)) {
//            newestMessage = context.getString(R.string.message_file);
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_IMAGE)) {
//            newestMessage = context.getString(R.string.message_picture);
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_LOCATION)) {
//            newestMessage = context.getString(R.string.message_location);
//        } else if(m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_LINK)) {
//            newestMessage = context.getString(R.string.message_link);
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_VIDEO)) {
//            newestMessage = context.getString(R.string.message_video);
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_STICKER)) {
//            newestMessage = context.getString(R.string.message_sticker);
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_TEXT)) {
//            newestMessage = ((TextTopic) m).getText();
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_SYSTEM)) {
//            newestMessage = ((TextTopic) m).getText();
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_VIDEOCALL)
//                || m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_AUDIOCALL)
//                || m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_VOICECALL)
//                || m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_PTTCALL)
//                || m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_VIDEOCHAT)) {
//            newestMessage = ((VoipTopic) m).getText();
//        } else if (m.getContentType().equalsIgnoreCase(Topic.CONTENTTYPE_SHARELOCATION)) {
//            newestMessage = ((ShareLocationTopic) m).getText();
//        }
//        return newestMessage;
//    }
//
//    public static Bitmap getContactPhoto(Context cnotext, String phoneNumber) {
//
//        Uri phoneUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
//        ContentResolver cr = cnotext.getContentResolver();
//        Bitmap contactPhoto = null;
//        int photoID = 0;
//        Cursor cur = cr.query(phoneUri, new String[]{PhoneLookup.PHOTO_ID, PhoneLookup.NUMBER}, null, null, null);
//
//        try {
//            if (cur.moveToFirst() == true) {
//                photoID = cur.getInt(cur.getColumnIndex(PhoneLookup.PHOTO_ID));
//                cr = cnotext.getContentResolver();
//                Uri photoUri = Data.CONTENT_URI;
//                cur = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO, Data.PHOTO_ID}, Data.PHOTO_ID + " = " + photoID, null, null);
//
//                if (cur.moveToFirst() == true) {
//                    try {
//                        ByteArrayInputStream rawPhotoStream = new ByteArrayInputStream(cur.getBlob(cur.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO)));
//                        contactPhoto = BitmapFactory.decodeStream(rawPhotoStream);
//                        return contactPhoto;
//                    } catch (NullPointerException ex) {
//
//                    }
//
//                }
//            }
////            Bitmap defaultPhoto = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_report_image);
//            return null;
//        } finally {
//            cur.close();
//        }
//    }
//
//    /**
//     * 根据手机号码获取本地通讯录里的联系人头像
//     *
//     * @param cnotext
//     * @param phoneNumber
//     * @return
//     */
//    public static Bitmap getFacebookPhoto(Context cnotext, String phoneNumber) {
//        Uri phoneUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
//        Uri photoUri = null;
//        ContentResolver cr = cnotext.getContentResolver();
//        Cursor cursor = cr.query(phoneUri,
//                new String[]{ContactsContract.Contacts._ID}, null, null, null);
//        try {
//            if (cursor.moveToFirst()) {
//                long userId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                photoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, userId);
//
//            } else {
//                return null;
//            }
//        } finally {
//            cursor.close();
//        }
//
//        if (photoUri != null) {
//            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(
//                    cr, photoUri);
//            if (input != null) {
//                return BitmapFactory.decodeStream(input);
//            }
//        } else {
//            return null;
//        }
//        return null;
//    }
//
//    public static Map<String, String> getNotificationMessage(String teId,
//                                                             String sessionTitle, String sessionType, String authorName,
//                                                             String msgContent, int sidNum, int totalUnRead) {
//        String title = sessionTitle;
//        Map<String, String> map = new HashMap<String, String>();
//        String tickerText = null;
//        String text = null;
//        if (sidNum == 1 && totalUnRead == 1) {
//            if (TopicEntity.ENTITYTYPE_SINGLECHAT.equals(sessionType)) {
//                text = msgContent;
//            } else {
//                text = authorName + ": " + msgContent;
//            }
//        }
//
//        if (sidNum == 1 && totalUnRead > 1) {
//            text = totalUnRead + " new messages";
//        }
//
//        if (sidNum > 1) {
//            text = totalUnRead + " new messages from " + sidNum + " contacts";
//            sessionTitle = ((Context) Runtime.getInstance().getContext())
//                    .getString(R.string.product_name);
//        }
//
//        if (TopicEntity.ENTITYTYPE_SINGLECHAT.equals(sessionType)) {
//            tickerText = authorName + ": " + msgContent;
//        } else {
//            tickerText = authorName + "@" + title + ": " + msgContent;
//        }
//        // 处理destrct逻辑及系统广播逻辑
//        if (teId != null && Runtime.getInstance().getAllTopicEntity().containsKey(teId)) {
//            TopicEntity te = Runtime.getInstance().getAllTopicEntity().get(teId);
//            Permission per = te.getPerm();
//            if (per != null && per.getDestructMessage() == Permission.DESTRUCTMESSAGE_ALLOW) {
//                tickerText = "New message from " + authorName;
//                text = "New message from " + authorName;
//            }
//            if (TopicEntity.ENTITYTYPE_SYSTEMCHAT.equals(te.getType())) {
//                title = AcuConstant.SYSTEM_NAME;
//                text = AcuConstant.SYSTEM_NAME + ": " + msgContent;
//                tickerText = AcuConstant.SYSTEM_NAME + ": " + msgContent;
//            }
//        }
//        LogEx.d("jjj", "sidNum =" + sidNum + " text = " + text
//                + " tickerText = " + tickerText);
//        map.put(NotificationManagerCtl.NOTIFI_CONTENT_TEXT, text);
//        map.put(NotificationManagerCtl.NOTIFI_CONTENT_TICKERTEXT, tickerText);
//        map.put(NotificationManagerCtl.NOTIFI_CONTENT_TITLE, sessionTitle);
//        return map;
//    }
//
//    /**
//     * 根据文件后缀名判断对应的图标rid，当 iv != null 时，将对应的rid设置到iv
//     *
//     * @param iv
//     * @param extension
//     * @return
//     */
//    public static int setImageViewByFileExtension(ImageView iv, String extension) {
//        List<String> mediaExLists = getMediaExlists();
//        List<String> wordList = getWordList();
//        List<String> pptList = getPptList();
//        List<String> excelList = getExcels();
//
//        int exRid = R.drawable.timeline_icon_others;
//        if (extension.equalsIgnoreCase("html") || extension.equalsIgnoreCase("htm")) {
//            exRid = R.drawable.timeline_icon_html;
//        } else if (extension.equalsIgnoreCase("chm")) {
//            exRid = R.drawable.file_icon_chm;
//        } else if (wordList.contains(extension)) {
//            exRid = R.drawable.timeline_icon_doc;
//        } else if (excelList.contains(extension)) {
//            exRid = R.drawable.timeline_icon_xls;
//        } else if (pptList.contains(extension)) {
//            exRid = R.drawable.timeline_icon_ppt;
//        } else if (extension.equalsIgnoreCase("rar") || extension.equalsIgnoreCase("zip") || extension.equalsIgnoreCase("tar") || extension.equalsIgnoreCase("gz")) {
//            exRid = R.drawable.timeline_icon_rar;
//        } else if (extension.equalsIgnoreCase("txt")) {
//            exRid = R.drawable.timeline_icon_txt;
//        } else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("tif") || extension.equalsIgnoreCase("bmp")) {
//            exRid = R.drawable.file_icon_pic;
//        } else if (mediaExLists.contains(extension)) {
//            exRid = R.drawable.timeline_icon_mp4;
//        } else if (extension.equalsIgnoreCase("pdf")) {
//            exRid = R.drawable.timeline_icon_pdf;
//        } else if (extension.equals("vcf")) {
//            exRid = R.drawable.file_icon_vcf;
//        }else if(extension.equals("apk")){
//            exRid = R.drawable.share_icon_apk;
//        } else {
//            exRid = R.drawable.timeline_icon_others;
//        }
//
//        if (iv != null) {
//            iv.setImageResource(exRid);
//        }
//        return exRid;
//    }
//
//    /**
//     * 根据文件名和rid获取组合后的新的文件名
//     *
//     * @param fileName
//     * @param resourceId
//     * @return
//     */
//    public static String getFileNameByParame(String fileName, String resourceId) {
//        String prefix = null;
//        String suffix = null;
//        if (fileName == null) {
//            return "_" + resourceId;
//        }
//        int index = fileName.lastIndexOf(".");
//        if (index > 0) {
//            prefix = fileName.substring(0, index);
//            suffix = fileName.substring(index, fileName.length());
//            return prefix + "_" + resourceId + suffix;
//        } else {
//            return fileName + "_" + resourceId;
//        }
//    }
//
//    /**
//     * 根据文件绝对路径或文件名获取后缀名
//     *
//     * @param fileName
//     * @return
//     */
//    public static String getSuffixByNameStr(String fileName) {
//        if (fileName != null && fileName.contains(".")) {
//            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
//        }
//        return null;
//    }
//
//    /**
//     * 为当前应用添加桌面快捷方式
//     *
//     * @param cx
//     */
//    public static void addShortcut(Context cx) {
//        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//
//        Intent shortcutIntent = cx.getPackageManager()
//                .getLaunchIntentForPackage(cx.getPackageName());
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
//        // 获取当前应用名称
//        String title = null;
//        try {
//            final PackageManager pm = cx.getPackageManager();
//            title = pm.getApplicationLabel(
//                    pm.getApplicationInfo(cx.getPackageName(),
//                            PackageManager.GET_META_DATA)).toString();
//        } catch (Exception e) {
//        }
//        // 快捷方式名称
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
//        // 不允许重复创建（不一定有效）
//        shortcut.putExtra("duplicate", false);
//        // 快捷方式的图标
//        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(cx,
//                R.drawable.ic_launcher);
//        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
//
//        cx.sendBroadcast(shortcut);
//    }
//
//    /**
//     * 判断桌面是否已添加快捷方式
//     *
//     * @param cx
//     * @return
//     */
//    public static boolean hasShortcut(Context cx) {
//        boolean result = false;
//        // 获取当前应用名称
//        String title = null;
//        try {
//            final PackageManager pm = cx.getPackageManager();
//            title = pm.getApplicationLabel(
//                    pm.getApplicationInfo(cx.getPackageName(),
//                            PackageManager.GET_META_DATA)).toString();
//        } catch (Exception e) {
//        }
//
//        final String uriStr;
//        if (Build.VERSION.SDK_INT < 8) {
//            uriStr = "content://com.android.launcher.settings/favorites?notify=true";
//        } else {
//            uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
//        }
//        final Uri CONTENT_URI = Uri.parse(uriStr);
//        final Cursor c = cx.getContentResolver().query(CONTENT_URI, null,
//                "title=?", new String[]{title}, null);
//        if (c != null && c.getCount() > 0) {
//            result = true;
//        }
//        return result;
//    }
//
//    public static boolean onceHadShortcut(Context cx) {
//        SharedPreferences sp = cx.getSharedPreferences(AcuConstant.ACUCOM_APP_SP, Context.MODE_PRIVATE);
//        return sp.getBoolean(AcuConstant.KEY_ISSHORTCUT, false);
//    }
//
//    public static void setonceHadShortcut(Context cx) {
//        Editor editor = cx.getSharedPreferences(AcuConstant.ACUCOM_APP_SP, Context.MODE_PRIVATE).edit();
//        editor.putBoolean(AcuConstant.KEY_ISSHORTCUT, true);
//        editor.commit();
//    }
//
//    /**
//     * 判断是否选择过host地址，没有的话随机选择一个并保存在sharepreferences中
//     *
//     * @param cx
//     */
//    public static String getPushHostPicked(Context cx) {
//        SharedPreferences sp = cx.getSharedPreferences(SharePreferenceStorage.SHAREDPREFERENCES_NAME_USER, Context.MODE_PRIVATE);
//        String defaultPushHost = sp.getString(AcuConstant.KEY_ACUCOM_PUSH_HOST, null);
//        if (defaultPushHost == null) {
//            Properties pro = new Properties();
//            try {
//                pro.load(Conf.class.getResourceAsStream(("/conf.properties")));
//                ArrayList<String> hostList = new ArrayList<String>();
//                for (int i = 0; i < 10; i++) {
//                    String hailStormHost = pro.getProperty("acucom_push_host_" + i);
//                    if (hailStormHost != null && !"".equals(hailStormHost)) {
//                        hostList.add(hailStormHost);
//                    } else {
//                        break;
//                    }
//                }
//
//                Random r = new Random();
//                int index = r.nextInt(3);
//                if (index < hostList.size()) {
//                    defaultPushHost = hostList.get(index);
//                } else {
//                    defaultPushHost = hostList.get(0);
//                }
//
//                Editor editor = cx.getSharedPreferences(SharePreferenceStorage.SHAREDPREFERENCES_NAME_USER, Context.MODE_PRIVATE).edit();
//                editor.putString(AcuConstant.KEY_ACUCOM_PUSH_HOST, defaultPushHost);
//                editor.commit();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return defaultPushHost;
//    }
//
//    /**
//     * 扫描指定文件以使其显示在gallery中
//     *
//     * @param cx
//     * @param filePath
//     */
//    public static void refreshOnefileInGallery(Context cx, String filePath) {
////		MediaScannerConnection.scanFile(cx, new String[] { filePath },
////				null, new MediaScannerConnection.OnScanCompletedListener() {
////					public void onScanCompleted(String path, Uri uri) {
////						Log.i("ExternalStorage", "Scanned " + path + ":");
////						Log.i("ExternalStorage", "-> uri=" + uri);
////					}
////				});
//        if (cx == null) {
//            cx = (Context) Runtime.getInstance().getContext();
//        }
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(new File(filePath));
//        intent.setData(uri);
//        cx.sendBroadcast(intent);
//    }
//
//    /**
//     * 去掉手机号前面带着的0
//     *
//     * @param phoneNum
//     * @return
//     */
//    public static String formaatPhoneNum(String phoneNum) {
//        if (phoneNum == null || "".equals(phoneNum)) {
//            return null;
//        }
//
//        while (phoneNum.charAt(0) == '0') {
//            phoneNum = phoneNum.substring(1);
//        }
//        return phoneNum;
//    }
//
//    /**
//     * 判断当前设备是否支持使用google map相关api
//     *
//     * @return true 支持； false 不支持
//     */
//    public static boolean canUserGoogleApi() {
//        try {
//            Class.forName("com.google.android.maps.MapActivity");
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * 设置TextView的最大显示宽度
//     *
//     * @param tv
//     * @param maxLength
//     */
//    public static void setMaxTvLength(TextView tv, int maxLength) {
//        if (maxLength != 0) {
//            tv.setMaxWidth(maxLength);
//        }
//    }
//
//    /**
//     * 获取录音时麦克风的得到的声音分贝值
//     *
//     * @param maxAmplitude
//     * @return
//     */
//    public static double getdB(int maxAmplitude) {
//        double p = maxAmplitude / 51805.5336;
//        double p0 = 0.0002;
//        double dB = 20 * Math.log10(p / p0);
//        return dB;
//    }
//
//    /**
//     * 隐藏软键盘
//     *
//     * @param instance
//     */
//    public static void hideSoftKeyboard(Activity instance) {
//        if (instance == null) return;
//        if (instance.getSystemService(Context.INPUT_METHOD_SERVICE) != null && instance.getCurrentFocus() != null) {
//            InputMethodManager m = (InputMethodManager) instance.getSystemService(Context.INPUT_METHOD_SERVICE);
//            if(m.isActive())
//                m.hideSoftInputFromWindow(instance.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }
//
//    /**
//     * 通过延时达到在oncreate方法调用显示软键盘
//     *
//     * @param instance
//     */
//    public static void showSoftKeyboardRightNow(Activity instance) {
//        InputMethodManager m = (InputMethodManager) instance.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if(!m.isActive())
//            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//    }
//
//    /**
//     * 通过延时达到在oncreate方法调用显示软键盘
//     *
//     * @param instance
//     */
//    public static void showSoftKeyboard(final Activity instance) {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                InputMethodManager m = (InputMethodManager) instance.getSystemService(Context.INPUT_METHOD_SERVICE);
//                if(!m.isActive())
//                    m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }, 300);
//    }
//
//    /**
//     * 使View获取焦点，常跟软键盘的弹出收起一起使用
//     *
//     * @param v
//     */
//    public static void callViewFocusable(EditText v) {
//        v.setFocusable(true);
//        v.setFocusableInTouchMode(true);
//        v.requestFocus();
//    }
//
//    /**
//     * 使View失去焦点， 常跟软键盘的弹出收起一起使用
//     *
//     * @param v
//     */
//    public static void removeViewFoucsable(EditText v) {
//        v.setFocusable(false);
//    }
//
//    /**
//     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
//     */
//    public static int dip2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    /**
//     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
//     */
//    public static int px2dip(Context context, float pxValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }
//
//    /**
//     * 将px值转换为sp值，保证文字大小不变
//     *
//     * @param pxValue
//     * @param pxValue
//     *            （DisplayMetrics类中属性scaledDensity）
//     * @return
//     */
//    public static int px2sp(Context context, float pxValue) {
//        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//        return (int) (pxValue / fontScale + 0.5f);
//    }
//
//    /**
//     * 将sp值转换为px值，保证文字大小不变
//     *
//     * @param spValue
//     * @param spValue
//     *            （DisplayMetrics类中属性scaledDensity）
//     * @return
//     */
//    public static int sp2px(Context context, float spValue) {
//        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * fontScale + 0.5f);
//    }
//
//    public static float getTextDrawnSize(TextView tv, String text) {
//
//        TextPaint paint = tv.getPaint();
//        float len = paint.measureText(text);
//        return len;
//    }
//
//    /**
//     * 根据文件绝对路径删除文件
//     *
//     * @param filePath
//     */
//    public static void deleteFile(String filePath) {
//        if (filePath == null || "".equals(filePath)) return;
//        try {
//            File f = new File(filePath);
//            if (f != null && f.exists())
//                f.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 删除指定文件夹及其下的所有文件
//     *
//     * @param folderPath
//     */
//    public static void delFolder(String folderPath) {
//        try {
//            delAllFile(folderPath); //删除完里面所有内容
//            String filePath = folderPath;
//            filePath = filePath.toString();
//            File myFilePath = new File(filePath);
//            myFilePath.delete(); //删除空文件夹
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 删除指定文件夹下的所有文件
//     *
//     * @param path
//     * @return
//     */
//    public static boolean delAllFile(String path) {
//        boolean flag = false;
//        File file = new File(path);
//        if (!file.exists()) {
//            return flag;
//        }
//        if (!file.isDirectory()) {
//            return flag;
//        }
//        String[] tempList = file.list();
//        File temp = null;
//        for (int i = 0; i < tempList.length; i++) {
//            if (path.endsWith(File.separator)) {
//                temp = new File(path + tempList[i]);
//            } else {
//                temp = new File(path + File.separator + tempList[i]);
//            }
//            if (temp.isFile()) {
//                temp.delete();
//            }
//            if (temp.isDirectory()) {
//                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
//                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
//                flag = true;
//            }
//        }
//        return flag;
//    }
//
//    /**
//     * 获取设备宽度
//     *
//     * @param context
//     * @return
//     */
//    public static int getDeviceWidth(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();//屏幕宽度
//        return width;
//    }
//
//    /**
//     * 获取设备高度
//     *
//     * @param context
//     * @return
//     */
//    public static int getDeviceHeight(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        return height;
//    }
//
//    /**
//     * 调用图片查看器
//     *
//     * @param cx
//     * @param position
//     * @param urlsList
//     */
//    public static void call4ImageBrower(Context cx, int position, ArrayList<String> urlsList) {
//        // TODO? @@@ 临时措施，将 url 中以 https 开头的替换成 http
////        ArrayList<String> newUrlList = new ArrayList<>();
////        if(urlsList != null && urlsList.size() > 0) {
////            for(String url : urlsList) {
////                String newUrl = DataUtil.formatUrlFromHttps2Http(url);
////                newUrlList.add(newUrl);
////            }
////        }
//        //
//        Intent intent = new Intent(cx, ImagePagerActivity.class);
//        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urlsList);
//        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
//        cx.startActivity(intent);
//    }
//
//    /**
//     * 在Topic聊天组里调用图片查看器；图片插件包含了左右自动加载数据逻辑
//     *
//     * @param cx
//     * @param t
//     * @param curReadedSeq
//     */
//    public static void call4ImageBrower(Activity cx, Topic t, boolean isDestruct, Long curReadedSeq) {
//        Intent intent = new Intent(cx, ImagePagerActivityEx.class);
//        intent.putExtra(ImagePagerActivityEx.EXTRA_IMAGE_TOPIC, t);
//        intent.putExtra(ImagePagerActivityEx.EXTRA_ISDESTRUCT, isDestruct);
//        intent.putExtra(ImagePagerActivityEx.EXTRA_CURREADEDSEQ, curReadedSeq);
//        intent.putExtra(ImagePagerActivityEx.EXTRA_GETDATE_FROMSERVER, true);
//        intent.putExtra(ImagePagerActivityEx.EXTRA_ISNEEDSHOW_GALLERYICON, true);
//        cx.startActivity(intent);
//        cx.overridePendingTransition(0,0);
//    }
//
//    /**
//     * 唤醒并解锁屏幕
//     *
//     * @param context
//     */
//    public static void wakeUpAndUnlockScreen(Context context) {
//        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
//        //解锁
//        kl.disableKeyguard();
//        //获取电源管理器对象
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
//        //点亮屏幕
//        wl.acquire();
//        //释放，即关闭屏幕
////        wl.release();
//    }
//
//    /**
//     * 关闭自定义的dialog；在activity销毁前；
//     *
//     * @param dialog
//     */
//    public static void dimissCustomDialog(Dialog dialog) {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
//    }
//
//    /**
//     * 获取文件时长
//     *
//     * @param mediaFile
//     * @return
//     */
//    public static int getDurationFromMediaFile(String mediaFile) {
//        FileInputStream fis = null;
//        MediaPlayer tempPlayer = new MediaPlayer();
//        try {
//            File file = new File(mediaFile);
//            if (!file.exists())
//                return 0;
//            fis = new FileInputStream(file);
//            tempPlayer.reset();
//            tempPlayer.setDataSource(fis.getFD());
//            tempPlayer.prepare();
//            return tempPlayer.getDuration();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            tempPlayer.reset();
//            tempPlayer.release();
////			tempPlayer.stop();
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return 0;
//    }
//
//    /**
//     * 判断是否有网络
//     */
//    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivity = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity == null) {
//            return false;
//        } else {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 检测应用permission逻辑，权限可用返回true，不可用返回false同时弹出对应的toast提示
//     *
//     * @param permissinStr
//     * @return
//     */
//    public static boolean checkPerssionLogic(String permissinStr) {
////		Context cx = (Context) Runtime.getInstance().getContext();
////		boolean flag = checkAppPointPermission(permissinStr);
////		if(!flag) {
////			String errorStr =  cx.getString(R.string.no_permission);
//////			if(AcuConstant.PERMISSION_CAMERA.equals(permissinStr))
//////				errorStr = cx.getString(R.string.no_permission);
//////			else if(AcuConstant.PERMISSION_READ_EXTERNAL_STORAGE.equals(permissinStr))
//////				errorStr = cx.getString(R.string.no_permission);
////
////			Toast.makeText(cx, errorStr, Toast.LENGTH_SHORT).show();
////		}
////		return flag;
//        return checkPerssionLogic(permissinStr, null);
//    }
//
//    public static boolean checkPerssionLogic(String permissinStr, Activity activity) {
//        Context cx = (Context) Runtime.getInstance().getContext();
//        boolean flag = checkAppPointPermission(permissinStr);
//        if (!flag) {
//            String errorStr = cx.getString(R.string.no_permission);
////			if(AcuConstant.PERMISSION_CAMERA.equals(permissinStr))
////				errorStr = cx.getString(R.string.no_permission);
////			else if(AcuConstant.PERMISSION_READ_EXTERNAL_STORAGE.equals(permissinStr))
////				errorStr = cx.getString(R.string.no_permission);
//            if (activity != null) {
//                getAppDetailSettingIntent(activity);
//            }
//
//            Toast.makeText(cx, errorStr, Toast.LENGTH_SHORT).show();
//        }
//        return flag;
//    }
//
//    public static boolean checkPerssionWitoutToast(String permissinStr) {
//        Context cx = (Context) Runtime.getInstance().getContext();
//        boolean flag = checkAppPointPermission(permissinStr);
//        if (!flag) {
//            String errorStr = cx.getString(R.string.no_permission);
////			if(AcuConstant.PERMISSION_CAMERA.equals(permissinStr))
////				errorStr = cx.getString(R.string.no_permission);
////			else if(AcuConstant.PERMISSION_READ_EXTERNAL_STORAGE.equals(permissinStr))
////				errorStr = cx.getString(R.string.no_permission);
//        }
//        return flag;
//    }
//
//    /**
//     * 检查应用某一指定的permission是否可用
//     *
//     * @param permissinStr
//     * @return
//     */
//    public static boolean checkAppPointPermission(String permissinStr) {
//        Context cx = (Context) Runtime.getInstance().getContext();
//        if (cx == null || permissinStr == null || permissinStr.length() == 0)
//            return false;
//        PackageManager pm = cx.getPackageManager();
//        boolean permission = (PackageManager.PERMISSION_GRANTED ==
//                pm.checkPermission(permissinStr, cx.getPackageName()));
//        return permission;
//    }
//
//
//    //以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
//    public static void getAppDetailSettingIntent(Activity activity) {
//        Intent localIntent = new Intent();
//        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (Build.VERSION.SDK_INT >= 9) {
//            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
//        } else if (Build.VERSION.SDK_INT <= 8) {
//            localIntent.setAction(Intent.ACTION_VIEW);
//            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
//            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
//        }
//        activity.startActivity(localIntent);
//    }
//
//    public static void getSystemAppsSettingIntent(Activity activity) {
//        Intent intent =  new Intent(Settings.ACTION_APPLICATION_SETTINGS);
//        activity.startActivity(intent);
//    }
//
//    /**
//     * 检测当前是否有有线耳机或蓝牙耳机
//     * @return
//     */
//    public static boolean isHaveHeadsetOn() {
//        Context cx = (Context) Runtime.getInstance().getContext();
//        AudioManager localAudioManager = (AudioManager) cx.getSystemService(Context.AUDIO_SERVICE);
//        boolean isWiredHeadsetOn = localAudioManager.isWiredHeadsetOn();
//        boolean isBluetoothA2dpOn = localAudioManager.isBluetoothA2dpOn(); //isBluetoothON();//
//        LogEx.d(TAG, "isHaveHeadsetOn: isWiredHeadsetOn == " + isWiredHeadsetOn + " isBluetoothA2dpOn == " + isBluetoothA2dpOn);
//        return isWiredHeadsetOn || isBluetoothA2dpOn;
//    }
//
//    /**
//     * 返回当前蓝牙工作状态，true: 工作中
//     * @return
//     */
//    public static boolean isBluetoothON() {
//        LogEx.d(TAG, "isBluetoothON: ");
//        Context cx = (Context) Runtime.getInstance().getContext();
//        AudioManager localAudioManager = (AudioManager) cx.getSystemService(Context.AUDIO_SERVICE);
//        boolean isBluetoothA2dpOn = localAudioManager.isBluetoothA2dpOn();
//        Log.d(TAG, "isBluetoothON: isBluetoothA2dpOn == " + isBluetoothA2dpOn);
//        return isBluetoothA2dpOn;
////        boolean isBluetoothEnable = false;
////        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
////        if(ba == null) {
////            isBluetoothEnable = false;
////        }
////        else if(ba.enable()) {
////            int a2dp = ba.getProfileConnectionState(BluetoothProfile.A2DP);              //可操控蓝牙设备，如带播放暂停功能的蓝牙耳机
//////            int headset = ba.getProfileConnectionState(BluetoothProfile.HEADSET);        //蓝牙头戴式耳机，支持语音输入输出
//////            int health = ba.getProfileConnectionState(BluetoothProfile.HEALTH);          //蓝牙穿戴式设备
////            if (a2dp == BluetoothProfile.STATE_CONNECTED) {
////                LogEx.d(TAG, "isBluetoothON: a2dp CONNECTED");
////                isBluetoothEnable = true;
////            }
//////            else if (headset == BluetoothProfile.STATE_CONNECTED) {
//////                Log.d(TAG, "isBluetoothON: headset CONNECTED");
//////            } else if (health == BluetoothProfile.STATE_CONNECTED) {
//////                Log.d(TAG, "isBluetoothON: health CONNECTED");
//////            }
////        }
////        LogEx.d(TAG, "isBluetoothON: isBluetoothEnable == " + isBluetoothEnable);
////        return isBluetoothEnable;
//    }
//
//    public static boolean isBluetoothA2dpOn() {
//        Context cx = (Context) Runtime.getInstance().getContext();
//        AudioManager localAudioManager = (AudioManager) cx.getSystemService(Context.AUDIO_SERVICE);
//        boolean isBluetoothA2dpOn = localAudioManager.isBluetoothA2dpOn();
//        Log.d(TAG, "isBluetoothA2dpOn: isBluetoothA2dpOn == " + isBluetoothA2dpOn);
//        return isBluetoothA2dpOn;
//    }
//
//    public static String getResourceKBLength(Long sourceLengh) {
//        if (sourceLengh == null || sourceLengh == 0l)
//            return "0k";
//        else if (sourceLengh < 1024)
//            return "<1k";
//        else
//            return sourceLengh != null ? (sourceLengh / 1024 + "k") : "?k";
//    }
//
//    /**
//     * 通过Uri获取文件在本地存储的真实路径
//     * @param contentUri
//     * @return
//     */
//    public static String getRealPathFromURI(Activity activity, Uri contentUri) {
//
//// can post image
//        String[] proj = { MediaStore.Images.Media.DATA };
//        Cursor cursor = activity.managedQuery(contentUri, proj, // Which columns to return
//                null, // WHERE clause; which rows to return (all rows)
//                null, // WHERE clause selection arguments (none)
//                null); // Order-by clause (ascending by name)
//        if (cursor==null) {
//            String path = contentUri.getPath();
//            return path;
//        }
//
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//
//    }
//
//    /**
//     * 获取Image类型的List，此List不可变
//     * @return
//     */
//    public static List<String> getImageList(){
//        return Arrays.asList(new String[]{"jpg","png" ,"gif","jpeg","bmp"});
//    }
//
//    /**
//     * 获取Audio类型的List，此List不可变
//     * @return
//     */
//    public static List<String> getAudioList(){
//        return Arrays.asList(new String[]{"mp3","aac","wav","wma","cda","flac","m4a","mid","mka","mp2","mpa","mpc","ape","ofr","ogg","ra","wv","tta","ac3","dts"});
//    }
//    /**
//     * 获取Media类型List，此List不可变
//     * @return
//     */
//    public static List<String> getMediaExlists(){
//        return Arrays.asList( new String[]{"asf", "avi", "wm", "wmp", "wmv", "ram", "rm", "rmvb", "rp", "rpm", "rt", "smi", "smil", "m1v", "m2v", "m2p", "m2t", "m2ts", "mp2v", "mpe", "mpeg", "mpg", "mpv2", "pss", "pva", "tp", "tpr", "ts", "m4b", "m4p", "m4v", "mp4", "mpeg4", "3g2", "3gp", "3gp2", "3gpp", "mov", "qt", "f4v", "flv", "hlv", "swf", "ifo", "vob", "amv", "bik",
//                "csf", "divx", "evo", "ivm", "mkv", "mod", "mts", "ogm", "pmp", "scm", "tod", "vp6", "webm", "xlmv", "aac", "ac3", "amr", "ape", "cda", "dts", "flac", "m1a", "m2a", "m4a", "mid", "midi", "mka", "mp2", "mp3", "mpa", "ogg", "ra", "tak", "tta", "wav", "wma", "wv", "asx", "cue", "kpl", "m3u", "pls", "qpl", "smpl", "ass", "srt", "ssa", "dat"});
//    }
//
//    /**
//     * 获取Word类型List，此List不可变
//     * @return
//     */
//    public static List<String> getWordList(){
//        return  Arrays.asList(new String[]{"doc", "docx", "docm", "dotx", "dotm"});
//    }
//
//    /**
//     * 获取Ppt类型List，此List不可变
//     * @return
//     */
//    public static List<String> getPptList(){
//        return Arrays.asList( new String[]{"ppt", "pptx", "pptm", "ppsx", "ppsm", "potx", "potm", "ppam"});
//    }
//
//    /**
//     * 获取Excel类型List，此List不可变
//     * @return
//     */
//    public static List<String> getExcels(){
//        return Arrays.asList(new String[]{"xls", "xlsx", "xlsm", "xltx", "xltm", "xlsb", "xlam"});
//    }
//
//    /**
//     * 获取当前应用程序的包名
//     * @param context 上下文对象
//     * @return 返回包名
//     */
//    public static String getAppProcessName(Context context) {
//        //当前应用pid
//        int pid = android.os.Process.myPid();
//        //任务管理类
//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        //遍历所有应用
//        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
//        for (ActivityManager.RunningAppProcessInfo info : infos) {
//            if (info.pid == pid)//得到当前应用
//                return info.processName;//返回包名
//        }
//        return "";
//    }
//
//    /**
//     * 判断图片是否已损坏
//     * @param path
//     * @return
//     */
//    public static boolean isDesturbed(String path){
//        BitmapFactory.Options options = null;
//        if (options == null) options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//
//        BitmapFactory.decodeFile(path, options); //filePath代表图片路径
//        if (options.mCancel || options.outWidth == -1
//                || options.outHeight == -1) {
//            //表示图片已损毁
//            return false;
//        }else {
//            return true;
//        }
//    }
//}
