package com.yarenchoi.tenderness.utils;

import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

/**
 * Created by YarenChoi on 2016/10/10.
 *
 * 新消息提醒class
 */
public class Notifier {
    private final static String TAG = "notify";
    Ringtone ringtone = null;

    protected final static String msg_eng = "%1 contacts sent %2 messages";
    protected final static String msg_ch = "%1个联系人发来%2条消息";

    protected static int notifyID = 0525; // start notification id
    protected static int foregroundNotifyID = 0555;//前台的通知会自动取消

    protected NotificationManager notificationManager = null;

    protected int notificationNum = 0;

    protected Context appContext;
    protected String packageName;
    protected String msgs;
    protected long lastNotifiyTime;
    protected AudioManager audioManager;
    protected Vibrator vibrator;//振动
    protected NotificationInfoProvider notificationInfoProvider;

    public Notifier() {
    }

    /**
     * 开发者可以重载此函数
     * this function can be override
     * @param context
     * @return
     */
    public Notifier init(Context context) {
        appContext = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        packageName = appContext.getApplicationInfo().packageName;

        if (Locale.getDefault().getLanguage().equals("zh")) {
            msgs = msg_ch;
        } else {
            msgs = msg_eng;
        }

        audioManager = (AudioManager) appContext.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) appContext.getSystemService(Context.VIBRATOR_SERVICE);

        return this;
    }

    /**
     * 开发者可以重载此函数
     * this function can be override
     */
    public void reset() {
        resetNotificationCount();
        cancelNotificaton();
    }

    void resetNotificationCount() {
        notificationNum = 0;
    }

    void cancelNotificaton() {
        if (notificationManager != null)
            notificationManager.cancel(notifyID);
    }

    /**
     * 处理新收到的消息，然后发送通知
     *
     * 开发者可以重载此函数
     * this function can be override
     *
     * @param message
     */
//    public synchronized void onNewMsg(EMMessage message) {
//        if (EMClient.getInstance().chatManager().isSlientMessage(message)) {
//            return;
//        }
//        EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
//        if (!settingsProvider.isMsgNotifyAllowed(message)) {
//            return;
//        }
//
//        // 判断app是否在后台
//        if (!EasyUtils.isAppRunningForeground(appContext)) {
//            EMLog.d(TAG, "app is running in backgroud");
//            sendNotification(message, false);
//        } else {
//            sendNotification(message, true);
//
//        }
//
//        viberateAndPlayTone(message);
//    }

//    public synchronized void onNewMesg(List<EMMessage> messages) {
//        if (EMClient.getInstance().chatManager().isSlientMessage(messages.get(messages.size() - 1))) {
//            return;
//        }
//        EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
//        if (!settingsProvider.isMsgNotifyAllowed(null)) {
//            return;
//        }
//        // 判断app是否在后台
//        if (!EasyUtils.isAppRunningForeground(appContext)) {
//            EMLog.d(TAG, "app is running in backgroud");
//            sendNotification(messages, false);
//        } else {
//            sendNotification(messages, true);
//        }
//        viberateAndPlayTone(messages.get(messages.size() - 1));
//    }

    /**
     * 发送通知栏提示
     * This can be override by subclass to provide customer implementation
     * @param messages
     * @param isForeground
     */
//    protected void sendNotification(List<EMMessage> messages, boolean isForeground) {
//        for (EMMessage message : messages) {
//            if (!isForeground) {
//                notificationNum++;
//                fromUsers.add(message.getFrom());
//            }
//        }
//        sendNotification(messages.get(messages.size() - 1), isForeground, false);
//    }
//
//    protected void sendNotification(EMMessage message, boolean isForeground) {
//        sendNotification(message, isForeground, true);
//    }

    /**
     * 发送通知栏提示
     *
     */
    protected void sendNotification(boolean isForeground, boolean numIncrease) {
        try {
            String notifyText = "";

            PackageManager packageManager = appContext.getPackageManager();
            String appName = (String) packageManager.getApplicationLabel(appContext.getApplicationInfo());
            // notification title 默认为appName
            String contentTitle = appName;

            if (notificationInfoProvider != null) {
                String customNotifyText = notificationInfoProvider.getDisplayedText();
                String customCotentTitle = notificationInfoProvider.getTitle();
                if (customNotifyText != null) {
                    // 设置自定义的状态栏提示内容
                    notifyText = customNotifyText;
                }

                if (customCotentTitle != null) {
                    // 设置自定义的通知栏标题
                    contentTitle = customCotentTitle;
                }
            }



            // create and send notificaiton
            /**
             * 旧版本的方法是先创建Notification对象，再调用setLatestEventInfo方法
             * 该方法已被抛弃
             * Notification notification = new Notification(R.drawable.ic_launcher, "This is ticker text", System.currentTimeMillis());
             * notification.setLatestEventInfo(this, "This is content title", "This is content text", pendingIntent);
             *
             * 设置led灯
             * .setLights(Color.GREEN, 1000, 1000) 后两个参数为亮起时长、熄灭时长
             */
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(appContext)
                    .setSmallIcon(appContext.getApplicationInfo().icon)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);

            Intent msgIntent = appContext.getPackageManager().getLaunchIntentForPackage(packageName);
            if (notificationInfoProvider != null) {
                // 设置自定义的notification点击跳转intent
                msgIntent = notificationInfoProvider.getLaunchIntent();
            }

            /**
             * PendingIntent更倾向于在某个合适的时机去执行某个动作
             *
             * PendingIntent 提供三种静态方法可获得PendingIntent对象
             * getActivity()
             * getBroadcast()
             * getService()
             *
             * 第四个参数用于确定PendingIntent的行为，有以下四种值可选
             * FLAG_ONE_SHOT
             * FLAG_NO_CREATE
             * FLAG_CANCEL_CURRENT
             * FLAG_UPDATE_CURRENT
             */
            PendingIntent pendingIntent = PendingIntent.getActivity(appContext, notifyID, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            if (numIncrease) {
                // prepare latest event info section
                if (!isForeground) {
                    notificationNum++;
                }
            }

            // TODO: 2016/10/13 计算当前为第几条信息
//            int fromUsersNum = fromUsers.size();
//            String summaryBody = msgs[6].replaceFirst("%1", Integer.toString(fromUsersNum)).replaceFirst("%2", Integer.toString(notificationNum));

            if (notificationInfoProvider != null) {
                // lastest text
                String customSummaryBody = notificationInfoProvider.getLatestText();
                if (customSummaryBody != null) {
//                    summaryBody = customSummaryBody;
                }

                // small icon
                int smallIcon = notificationInfoProvider.getSmallIcon();
                if (smallIcon != 0) {
                    mBuilder.setSmallIcon(smallIcon);
                }
            }

            mBuilder.setContentTitle(contentTitle);
            mBuilder.setTicker(notifyText);
//            mBuilder.setContentText(summaryBody);
            mBuilder.setContentIntent(pendingIntent);
            // mBuilder.setNumber(notificationNum);
            Notification notification = mBuilder.build();

            if (isForeground) {
                notificationManager.notify(foregroundNotifyID, notification);
                notificationManager.cancel(foregroundNotifyID);
            } else {
                notificationManager.notify(notifyID, notification);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 手机震动和声音提示
     */
    public void viberateAndPlayTone() {
        if (System.currentTimeMillis() - lastNotifiyTime < 1000) {
            // received new messages within 2 seconds, skip play ringtone
            return;
        }

        try {
            lastNotifiyTime = System.currentTimeMillis();

            // 判断是否处于静音模式
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
//                EMLog.e(TAG, "in slient mode now");
                return;
            }

            //默认的震动频率
            long[] pattern = new long[]{0, 180, 80, 120};
            vibrator.vibrate(pattern, -1);

            if (ringtone == null) {
                Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                ringtone = RingtoneManager.getRingtone(appContext, notificationUri);
                if (ringtone == null) {
//                        EMLog.d(TAG, "cant find ringtone at:" + notificationUri.getPath());
                    return;
                }
            }

            if (!ringtone.isPlaying()) {
                String vendor = Build.MANUFACTURER;

                ringtone.play();
                // for samsung S3, we meet a bug that the phone will
                // continue ringtone without stop
                // so add below special handler to stop it after 3s if
                // needed
                if (vendor != null && vendor.toLowerCase().contains("samsung")) {
                    Thread ctlThread = new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(3000);
                                if (ringtone.isPlaying()) {
                                    ringtone.stop();
                                }
                            } catch (Exception e) {
                            }
                        }
                    };
                    ctlThread.run();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置通知栏消息Provider
     *
     * @param provider 接口对象
     */
    public void setNotificationInfoProvider(NotificationInfoProvider provider) {
        notificationInfoProvider = provider;
    }

    public interface NotificationInfoProvider {
        /**
         * 设置发送notification时状态栏提示新消息的内容(比如Xxx发来了一条图片消息)
         *
         * @return null为使用默认
         */
        String getDisplayedText();

        /**
         * 设置notification持续显示的新消息提示
         *
         * @return null为使用默认
         */
        String getLatestText();

        /**
         * 设置notification标题
         *
         * @return null为使用默认
         */
        String getTitle();

        /**
         * 设置小图标
         *
         * @return 0使用默认图标
         */
        int getSmallIcon();

        /**
         * 设置notification点击时的跳转intent
         *
         * @return null为使用默认
         */
        Intent getLaunchIntent();
    }
}
