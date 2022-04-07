package ro.pub.cs.systems.eim.colocviu1_1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Colocviu1_1Service extends Service {
    public Colocviu1_1Service() {
    }

    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String steps = intent.getStringExtra("StepUntilNow");
        processingThread = new ProcessingThread(this, steps);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}