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

import java.util.ArrayList;

public class
NotificationsService extends Service {
    public NotificationsService() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    static boolean isSame(Teruzim m, Teruzim z) {
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


        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Teruzim newTeruz = snapshot.getValue(Teruzim.class);
                Request newRequest = snapshot.getValue(Request.class);
                if (LoadingActivity.first) {
                    if (previousChildName != null) {
                        if (Integer.parseInt(previousChildName) == (DataModel.teruzims.size() - 2)) {
                            LoadingActivity.first = false;
                            return;
                        }
                    }

                } else {
                    ArrayList<String> temp = new ArrayList<>();
                    for (int i = 0; i < MainActivity.beenOnThisDevice.size(); i++) {
                        temp.add(MainActivity.beenOnThisDevice.get(i).getTluna().toString());
                    }
                    SharedPref.writeListInPref(getApplicationContext(), temp);
                    if (MainActivity.getBackTeruzim != null)
                        for (int i = 0; i < MainActivity.getBackTeruzim.size(); i++) {
                            if (isSame(newTeruz, MainActivity.getBackTeruzim.get(i)))
                                return;
                        }
                    boolean requestflag = true;
                    if(MainActivity.getBackRequests != null){
                        for (int i = 0; i < MainActivity.getBackRequests.size(); i++){
                            if(MainActivity.getBackRequests.get(i).getLog().equals(newRequest.getLog())){
                                requestflag = false;
                            }
                        }
                    }
                    boolean flag = true;

                    for (int i = 0; i < MainActivity.beenOnThisDevice.size() && flag; i++) {
                        if (newTeruz.getTluna().equals(MainActivity.beenOnThisDevice.get(i).getTluna())) {
                            flag = false;
                        }

                    }
                    if (flag || requestflag) {
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
                                .setContentText(newTeruz.getTluna());

                        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                        stackBuilder.addParentStack(MainActivity.class);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(resultPendingIntent);
                        notificationManager.notify(NOTIFICATION_ID, builder.build());

                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Teruzim changedTeruz = snapshot.getValue(Teruzim.class);
                if (LoadingActivity.first) {
                    LoadingActivity.first = false;
                    return;
                } else {
                    boolean flag = true;
                    for (int i = 0; i < MainActivity.beenOnThisDevice.size(); i++) {
                        if (flag && changedTeruz == MainActivity.beenOnThisDevice.get(i))
                            return;
                        flag = false;
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
                                .setContentTitle("Terutz has been changed")
                                .setContentText(changedTeruz.getTluna());

                        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                        stackBuilder.addParentStack(MainActivity.class);
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(resultPendingIntent);
                        notificationManager.notify(NOTIFICATION_ID, builder.build());
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }


}
