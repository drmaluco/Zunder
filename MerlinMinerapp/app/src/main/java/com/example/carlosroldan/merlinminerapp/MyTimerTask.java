package com.example.carlosroldan.merlinminerapp;

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
    private boolean readed;

    public MyTimerTask(DataBase dataBase, Context context) {
        this.database = dataBase;
        this.context = context;
    }

    @Override
    public void run() {
        check_notify_PAY();
        // check_notify_MESSAGE();
        check_notify_ONLINE();
        check_notify_BUGS();
        check_notify_MERLIN();
    }

    private void check_notify_PAY() {
        try {
            database.doLoad("readyPayment", "true");
            if (database.isReadyPayment() == true) {
                Intent notifyIntent = new Intent(context, notification.class);

                notifyIntent.putExtra("id", database.getId());
                notifyIntent.putExtra("paymentType", database.getPaymentType());
                notifyIntent.putExtra("name", database.getUser());
                notifyIntent.putExtra("address", database.getAddress());
                notifyIntent.putExtra("email", database.getUserEmail());
                receiveNotification(notifyIntent);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void check_notify_MESSAGE() {
        try {
            database.messageReceived();
            if (database.isAnyMessage() == false && database.isReaded() == false) {
                Intent notifyIntent = new Intent(context, notification.class);
                notifyIntent.putExtra("id", database.getId());
                notifyIntent.putExtra("name", database.getUser());
                notifyIntent.putExtra("address", database.getAddress());
                notifyIntent.putExtra("email", database.getUserEmail());
                notificationForReading(notifyIntent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void check_notify_ONLINE() {
        try {
            database.doLoad("online", "true");
            if (database.isOnline()) {
                onlineNotification();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void check_notify_BUGS() {
        try {
            database.doLoad("anyBug", "true");
            if (database.isAnyBug()) {
                Intent notifyIntent = new Intent(context, Bug.class);
                notifyIntent.putExtra("id", database.getId());
                notifyIntent.putExtra("paymentType", database.getPaymentType());
                notifyIntent.putExtra("name", database.getUser());
                notifyIntent.putExtra("address", database.getAddress());
                notifyIntent.putExtra("email", database.getUserEmail());
                notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                bugNotification(notifyIntent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void check_notify_MERLIN() {
        database.checkMerlinNOTAvailability();
        if (!database.isMerlinAvailable()) {
            Intent notifyIntent = new Intent(context, firstActivity.class);
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            bugNotification(notifyIntent);
        }
    }

    private void bugNotification(Intent notifyIntent) {
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(database.getUser() + " ha reportado un BUG")
                .setContentText("Toca para leer el problema")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        database.setAnyBug(false);
        try {
            database.updateAnyBug();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void availabilityNotification(Intent notifyIntent) {
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Merlin Miner está desactivado")
                .setContentText("Ves a ajustes para activar Merlin Miner")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private void onlineNotification() {
        Intent notifyIntent = new Intent(context, firstActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_menu_rotate)
                .setContentTitle(database.getUser() + " se ha conectado!")
                .setContentText(database.getUser() + " acaba de entrar a Merlin Miner")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private void notificationForReading(Intent notifyIntent) {

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        String nombre = database.getUser();
        if (database.getUser() == null) {
            nombre = "Sin Nombre";
        }
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle(nombre + " ha leído tu mensaje")
                .setContentText("Tu mensaje ha sido leído correctamente")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        database.setReaded(true);
        try {
            database.updateReaded();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void receiveNotification(Intent notifyIntent) {
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Nueva orden de Pago (" + database.getPaymentType() + ")")
                .setContentText(database.getUser() + " ha solicitado una pago de "
                        + database.getMoney() + database.getCurrency())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        database.setReadyPayment(false);
        try {
            database.updateReadyPayment(String.valueOf(database.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
