package com.Mining.app.Money;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.sql.SQLException;
import java.util.TimerTask;

/**
 * Created by Carlos Roldan on 26/07/2017.
 */

public class MyTimerTask extends TimerTask {
    private DataBase database;
    Context context;
    String email;


    public MyTimerTask(DataBase dataBase, Context context, String email) {
        this.database = dataBase;
        this.context = context;
        this.email = email;
    }

    @Override
    public void run() {
        try {
            database.update();
            database.updateCommon();
            database.load();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        winner_NOTIFICATION();
    }


    private void winner_NOTIFICATION(){
       if (database.isAnyWinner()){
           Intent notifyIntent = new Intent(context, MainActivity.class);

           // notifyIntent.putExtra("id", database.getId()); vincular para poner set up de Loteria
           receiveNotification(notifyIntent);
       }
    }

    private void receiveNotification(Intent notifyIntent) {
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Lottery Ended!")
                    .setContentText("Find out if you won the Lottery!")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        database.setAnyWinner(false);
        try {
            database.updateCommon();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
