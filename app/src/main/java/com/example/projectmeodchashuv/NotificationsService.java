package com.example.projectmeodchashuv;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class
NotificationsService extends Service {
    DatabaseReference dbteruzRef;
    DatabaseReference dbuserRef;
    DatabaseReference dbrequstRef;

    public NotificationsService() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    static boolean isSame(Teruz m, Teruz z) {
        if (!m.getTluna().equals(z.getTluna()))
            return false;
        else if (!m.getReason().equals(z.getReason()))
            return false;
        else if (!m.getCreator().equals(z.getCreator()))
            return false;
        else if (m.getUpvotes() != z.getUpvotes())
            return false;
        return true;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("teruzim");

        dbteruzRef = database.getReference("teruzim");
        dbrequstRef = database.getReference("requests");
        if(!LoadingActivity.first) {
            /**
             * בודקת אם נוצר תירוץ חדש ואם הוא לא נוצר על הטלפון הזה היא שולחת התראה על זה
             */
            dbteruzRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<ArrayList<Teruz>> t = new GenericTypeIndicator<ArrayList<Teruz>>() {
                    };
                    ArrayList<Teruz> newTeruz = snapshot.getValue(t);
                    ArrayList<String> temp = new ArrayList<>();
                    for (int i = 0; i < MainActivity.teruzimOnThisDevice.size(); i++) {
                        temp.add(MainActivity.teruzimOnThisDevice.get(i).getTluna());
                    }
                    SharedPref.writeListInPref(getApplicationContext(), temp);

                    boolean flag = true;
                    for (int i = 0; i < MainActivity.teruzimOnThisDevice.size() && flag; i++) {
                        if (newTeruz.get(i).getTluna().equals(MainActivity.teruzimOnThisDevice.get(i).getTluna())) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        int NOTIFICATION_ID = 234;
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        String CHANNEL_ID = "Terutz";

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            CharSequence name = "Terutz";
                            String Description = "Terutzim channel";
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                            mChannel.setDescription(Description);
                            mChannel.enableLights(true);
                            mChannel.setLightColor(Color.RED);
                            mChannel.enableVibration(true);
                            mChannel.setShowBadge(true);
                            notificationManager.createNotificationChannel(mChannel);
                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Something happened, Come Check it out!")
                                .setContentText(newTeruz.get(newTeruz.size() - 1).getTluna());

                        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                        stackBuilder.addParentStack(MainActivity.class);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(resultPendingIntent);
                        notificationManager.notify(NOTIFICATION_ID, builder.build());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            /**
             * אותו דבר רק לבקשות
             */
            dbrequstRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<ArrayList<Request>> r = new GenericTypeIndicator<ArrayList<Request>>() {
                    };
                    ArrayList<Request> newRequest = snapshot.getValue(r);
                    boolean requestflag = true;
                    for (int i = 0; i < MainActivity.requestsOnThisDevice.size() && requestflag; i++) {
                        if (newRequest.get(i).getLog().equals(MainActivity.requestsOnThisDevice.get(i).getLog())) {
                            requestflag = false;
                        }
                    }
                    if (requestflag) {
                        int NOTIFICATION_ID = 234;
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        String CHANNEL_ID = "Terutz";

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            CharSequence name = "Terutz";
                            String Description = "Terutzim channel";
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                            mChannel.setDescription(Description);
                            mChannel.enableLights(true);
                            mChannel.setLightColor(Color.RED);
                            mChannel.enableVibration(true);
                            mChannel.setShowBadge(true);
                            notificationManager.createNotificationChannel(mChannel);
                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Something happened, Come Check it out!")
                                .setContentText(newRequest.get(newRequest.size() - 1).getLog());

                        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                        stackBuilder.addParentStack(MainActivity.class);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(resultPendingIntent);
                        notificationManager.notify(NOTIFICATION_ID, builder.build());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
            LoadingActivity.first = false;

        return super.onStartCommand(intent, flags, startId);
    }


}
