package com.taixin.android.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
/**
 * 
 * <dl>
 * <dt>ShortcutUtil.java</dt>
 * <dd>Description: ������ݷ�ʽ</dd>
 * <dd>Copyright: Copyright (C) 2014</dd>
 * <dd>Company: ɽ��̩�ŵ��ӹɷ����޹�˾</dd>
 * <dd>CreateDate: 2014-02-18</dd>
 * </dl>
 * @author Zhouye
 *
 */
public class ShortcutUtil {
	  
	    public static void createShortCut(Activity act, int iconResId,  
	            int appnameResId) {  
	    	if(hasShortcut(act,appnameResId))
	    		delShortcut(act,appnameResId);
	        // com.android.launcher.permission.INSTALL_SHORTCUT  
	    	Intent intent = new Intent();
	    	intent.setClass(act, act.getClass());
	    	intent.setAction("android.intent.action.MAIN");
	    	intent.addCategory("android.intent.category.LAUNCHER");
	        Intent shortcutintent = new Intent(  
	                "com.android.launcher.action.INSTALL_SHORTCUT"); 
	        // �������ظ�����  
	        shortcutintent.putExtra("duplicate", false);  
	        // ��Ҫ��ʵ�����  
	        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME,  
	                act.getString(appnameResId));  
	        // ���ͼƬ  
	        Parcelable icon = Intent.ShortcutIconResource.fromContext(  
	                act.getApplicationContext(), iconResId);  
	        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);  
	        // ������ͼƬ�����еĳ��������  
	        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,  
	                intent);  
	        // ���͹㲥  
	        act.sendBroadcast(shortcutintent);  
	    }  
	    private static void delShortcut(Activity act,int appnameid){  
	        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");  
	              
	        //��ݷ�ʽ�����  
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, act.getString(appnameid));  
	              
	        //ָ����ǰ��ActivityΪ��ݷ�ʽ�����Ķ���: �� com.everest.video.VideoPlayer  
	        //ע��: ComponentName�ĵڶ������������������������+���������޷�ɾ���ݷ�ʽ  
	        Intent intent = new Intent();
	        intent.setClass(act, act.getClass());
	        intent.setAction("android.intent.action.Main");
	        intent.addCategory("android.intent.category.LAUNCHER");
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
	        act.sendBroadcast(shortcut);  
	    }
	    private static boolean hasShortcut(Activity act,int appnameid)
	    {
	           
	    	String url = "";
	    	int systemversion = Integer.parseInt(android.os.Build.VERSION.SDK);
	           /*����8��ʱ����com.android.launcher2.settings ���ѯ��δ���ԣ�*/
	    	if(systemversion < 8){
	    		url = "content://com.android.launcher.settings/favorites?notify=true";
	    		}
	    	else{
	    		url = "content://com.android.launcher2.settings/favorites?notify=true";
	    	} 
	    	ContentResolver resolver = act.getContentResolver();
	    	Cursor cursor = resolver.query(Uri.parse(url), null, "title=?",new String[] {act.getString(appnameid)}, null);
	    	if (cursor != null && cursor.moveToFirst()) {
	    		cursor.close();              
	    		return true; 
         } 
	           return false; 
	           
	    }

}
