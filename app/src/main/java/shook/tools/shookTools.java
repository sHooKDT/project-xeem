package shook.tools;

// My own library with some useful tools

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import shook.xeem.R;

public class shookTools {

    public static void showNotification (AppCompatActivity context, String title, String text) {

        NotificationManager notifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(title)
                .setContentText(text);

        notifyMgr.notify(001, nBuilder.build());

    }

}
