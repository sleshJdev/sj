package by.slesh.sj.view.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arseniy on 27/06/15.
 */
public class Share {
    public static void share(String subject, String message, String url, Activity context){
        final Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

        if (notEmpty(url)) {
            if (notEmpty(message)) {
                message += " " + url;
            } else {
                message = url;
            }
        }
        if (notEmpty(message)) {
            sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            sendIntent.putExtra("sms_body", message); // sometimes required when the user picks share via sms
        }

        String blacklist[] = {""};
        final String[] packOfAppsToShareWith = new String[] { "com.viber.voip", "com.vkontakte.android", "org.telegram.messenger", "com.whatsapp", "com.facebook.katana", "com.twitter.android", "com.skype.raider", "ru.ok.android", "com.instagram.android" };
        String titleShare = "Поделиться";
        context.startActivityForResult(generateCustomChooserIntent(sendIntent, blacklist, packOfAppsToShareWith, titleShare, context), 1);
    }

    private static Intent generateCustomChooserIntent(Intent prototype, String[] forbiddenChoices, String[] AppsToShare, String shareTitle, Activity context)
    {
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        List<HashMap<String, String>> intentMetaInfo = new ArrayList<HashMap<String, String>>();
        Intent chooserIntent;
        PackageManager pm = context.getPackageManager();
        Intent dummy = new Intent(prototype.getAction());
        dummy.setType(prototype.getType());
        List<ResolveInfo> resInfo = pm.queryIntentActivities(dummy,0);

        // Sms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context); //Need to change the build to API 19
            Intent dummySms = new Intent(Intent.ACTION_SEND);
            dummySms.setType("text/plain");
            dummySms.putExtra(Intent.EXTRA_TEXT, "");
            if (defaultSmsPackageName != null)//Can be null in case that there is no default, then the user would be able to choose any app that support this intent.
            {
                dummySms.setPackage(defaultSmsPackageName);
                List<ResolveInfo> resInfoSms = pm.queryIntentActivities(dummySms,0);
                if (!resInfoSms.isEmpty()){
                    for (ResolveInfo resolveInfo : resInfoSms)
                    {
                        String appName = resolveInfo.activityInfo.packageName;
                        HashMap<String, String> info = new HashMap<String, String>();
                        info.put("packageName", resolveInfo.activityInfo.packageName);
                        info.put("className", resolveInfo.activityInfo.name);
                        info.put("simpleName", appName);
                        intentMetaInfo.add(info);
                        break;
                    }
                }
            }
        } else {
            Intent dummySms = new Intent(Intent.ACTION_VIEW);
            dummySms.setType("vnd.android-dir/mms-sms");
            List<ResolveInfo> resInfoSms = pm.queryIntentActivities(dummySms,0);
            if (!resInfoSms.isEmpty()){
                for (ResolveInfo resolveInfo : resInfoSms)
                {
                    String appName = resolveInfo.activityInfo.packageName;
                    HashMap<String, String> info = new HashMap<String, String>();
                    info.put("packageName", resolveInfo.activityInfo.packageName);
                    info.put("className", resolveInfo.activityInfo.name);
                    info.put("simpleName", appName);
                    intentMetaInfo.add(info);
                    break;
                }
            }
        }

        if (!resInfo.isEmpty())
        {
            ArrayList<String> selected = new ArrayList<>();
            for (ResolveInfo resolveInfo : resInfo)
            {
                if (resolveInfo.activityInfo == null
                        || Arrays.asList(forbiddenChoices).contains(resolveInfo.activityInfo.packageName)
                        || selected.contains(resolveInfo.activityInfo.packageName))
                    continue;
                selected.add(resolveInfo.activityInfo.packageName);
                //Get all the posible sharers
                HashMap<String, String> info = new HashMap<String, String>();
                info.put("packageName", resolveInfo.activityInfo.packageName);
                info.put("className", resolveInfo.activityInfo.name);
                // for app packege name
                String appName = resolveInfo.activityInfo.packageName;
                info.put("simpleName", appName);
                if (Arrays.asList(AppsToShare).contains(appName.toLowerCase()))
                {
                    intentMetaInfo.add(info);
                }
            }
            if (!intentMetaInfo.isEmpty())
            {
                // sorting for nice readability
                Collections.sort(intentMetaInfo,
                        new Comparator<HashMap<String, String>>() {
                            @Override
                            public int compare(
                                    HashMap<String, String> map,
                                    HashMap<String, String> map2) {
                                return map.get("simpleName").compareTo(
                                        map2.get("simpleName"));
                            }
                        });
                // create the custom intent list
                for (HashMap<String, String> metaInfo : intentMetaInfo)
                {
                    Intent targetedShareIntent = (Intent) prototype.clone();
                    targetedShareIntent.setPackage(metaInfo.get("packageName"));
                    targetedShareIntent.setClassName(
                            metaInfo.get("packageName"),
                            metaInfo.get("className"));
                    targetedShareIntents.add(targetedShareIntent);
                }
                shareTitle = shareTitle.substring(0, 1).toUpperCase()
                        + shareTitle.substring(1);
                chooserIntent = Intent.createChooser(targetedShareIntents
                        .remove(targetedShareIntents.size() - 1), shareTitle);
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[]{}));
                return chooserIntent;
            }
        }
        return Intent.createChooser(prototype, shareTitle);
    }

    private static boolean notEmpty(String what) {
        return what != null &&
                !"".equals(what) &&
                !"null".equalsIgnoreCase(what);
    }
}
