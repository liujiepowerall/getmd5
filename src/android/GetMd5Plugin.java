package com.powerall.plugin.md5;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import java.security.MessageDigest;

public class GetMd5Plugin extends CordovaPlugin {
	
	private static final String TAG = "MD5";

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	        android.util.Log.d(TAG,"action=>"+action);
	        PluginResult result = null;  
	        
			if (action.equals("getmd5")) {
				callbackContext.success();
				String md5 = getFileMD5(args.getString(0));
				android.util.Log.d(TAG,"md5=>"+md5);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("md5", md5);
				result = new PluginResult(PluginResult.Status.OK, jsonObj);
				result.setKeepCallback(true);
				callbackContext.sendPluginResult(result);				
				return true;
	    }
	        return false;
	}
	
	private File getFile(Uri contentUri){
		String res = null;
		File file = null;
		String[] proj = {MediaStore.Images.Media.DATA };
		Cursor cursor = this.cordova.getActivity().getContentResolver().query(contentUri, proj, null, null, null);
		if(cursor.moveToFirst()){;
		   int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		   res = cursor.getString(column_index);
		   Log.d(TAG,"res=>"+res);
		   file = new File(res);
		}
		cursor.close();
		return file;
	}
	
	
	 public String getFileMD5(String filePath) {
			Log.d(TAG,"filePath=>"+filePath);
			File file = getFile(Uri.parse(filePath));
		   if (!file.isFile()) {
				Log.d(TAG,"the file is not file");
				return null;
		   }
		   MessageDigest digest = null;
		   FileInputStream in = null;
		   byte buffer[] = new byte[1024];
		   int len;
		   try {
		    digest = MessageDigest.getInstance("MD5");
		    in = new FileInputStream(file);
		    while ((len = in.read(buffer, 0, 1024)) != -1) {
			 digest.update(buffer, 0, len);
		    }
		    in.close();
		   } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		   }
		   BigInteger bigInt = new BigInteger(1, digest.digest());
		   return bigInt.toString(16);
		  }
	}
