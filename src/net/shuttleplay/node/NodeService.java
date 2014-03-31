package net.shuttleplay.node;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.google.analytics.tracking.android.*;

public class NodeService extends Service implements NodeContext
{
    public static final String Android_Context = "Android_Context";
    public static final String Files_Dir = "Files_Dir";

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        android.util.Log.i("NodeService", "onDestroy");
        GoogleAnalytics ga = GoogleAnalytics.getInstance(this);
        Tracker t = ga.getTracker("UA-44940845-2");
        t.send(MapBuilder
                .createEvent(LAUNCH_NODE, "stop", "NodeService", null)
                .build());
        
        stopForeground(true);
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();

        mData = new HashMap<String, Object>();
        setData(Android_Context, this);
        setData(Files_Dir, this.getFilesDir().getAbsolutePath());

        GoogleAnalytics ga = GoogleAnalytics.getInstance(this);
        Tracker t = ga.getTracker("UA-44940845-2");
        t.send(MapBuilder
                .createEvent(LAUNCH_NODE, "start", "NodeService", null)
                .build());
        
        try
        {
            Class.forName("net.shuttleplay.node.NodeBroker");

            Properties props = new Properties();
            String configName = getFilesDir() + "/config.props";
            props.load(new FileInputStream(configName));

            String index = props.getProperty("index");
            int runId = getResources().getIdentifier("app_name", "string", getPackageName());
            String runlabel = getResources().getString(runId);
            String port = props.getProperty("port");
//            int labelId = getResources().getIdentifier("view", "string", getPackageName());
//            String littleLable = getResources().getString(labelId);
            props.setProperty("apk", SdUtil.getPackageFile(this));
            
            int resId = getResources().getIdentifier("icon", "drawable", getPackageName());
            Notification notification = new Notification(resId, runlabel,
                    System.currentTimeMillis());
            Intent notiIntent = new Intent(Intent.ACTION_VIEW);
            String ipAddress = NetUtil.getLocalIpAddress(this);
            notiIntent.setData(Uri.parse("http://" + ipAddress + ":" + port + index));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, 0);
//            notification.setLatestEventInfo(this, runlabel, littleLable, pendingIntent);
            notification.contentIntent = pendingIntent;
            
            RemoteViews rv = new RemoteViews(getPackageName(), getResources().getIdentifier("notify", "layout", getPackageName()));
            
            int exitId = getResources().getIdentifier("exit", "id", getPackageName());
            notiIntent = new Intent("net.shuttleplay.node.ShutDown");
            notiIntent.addCategory(Intent.CATEGORY_DEFAULT);
//            notiIntent = new Intent(Intent.ACTION_VIEW);
//            notiIntent.setData(Uri.parse("http://" + ipAddress + ":" + port + "/exit.html?exit=true"));
            pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, 0);
            rv.setOnClickPendingIntent(exitId, pendingIntent);
            
            int aboutId = getResources().getIdentifier("about", "id", getPackageName());
            notiIntent = new Intent(Intent.ACTION_VIEW);
            notiIntent.setData(Uri.parse("http://" + ipAddress + ":" + port + "/about.html"));
            pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, 0);
            rv.setOnClickPendingIntent(aboutId, pendingIntent);
            
            notification.contentView = rv;
            
            FileOutputStream fout = new FileOutputStream(configName);
            props.store(fout, null);
            fout.close();
            startForeground(1, notification);
        }
        catch(FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static final String LAUNCH_NODE = "net.shuttleplay.node.launch";

    @Override
    public IBinder onBind(Intent arg0)
    {
        return mBinder;
    }

    private final NodeJsService.Stub mBinder = new NodeJsService.Stub()
    {
        public void launchInstance(String file)
        {
            final String jsfile = file;
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    NodeBroker.runNodeJs(NodeService.this, jsfile);
                    NodeService.this.stopForeground(true);
                    NodeService.this.stopSelf();
                    android.util.Log.i("NodeService", "node.js stopped");
                }

            }).start();
        }

        public void debugInstance(String file)
        {
            final String jsfile = file;
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    // TODO Auto-generated method stub
                    NodeBroker.debugNodeJs(NodeService.this, jsfile);
                    NodeService.this.stopForeground(true);
                    NodeService.this.stopSelf();
                    android.util.Log.i("NodeService", "node.js debug stopped");
                }

            }).start();
        }
    };

    @Override
    public Object getData(String name)
    {
        return mData.get(name);
    }

    @Override
    public void setData(String name, Object value)
    {
        mData.put(name, value);
    }

    private HashMap<String, Object> mData;
    
}
