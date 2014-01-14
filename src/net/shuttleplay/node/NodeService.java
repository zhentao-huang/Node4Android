package net.shuttleplay.node;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class NodeService extends Service implements NodeContext
{
    public static final String Android_Context = "Android_Context";
    public static final String Files_Dir = "Files_Dir";

    @Override
    public void onStart(Intent intent, int startId)
    {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        mData = new HashMap<String, Object>();
        setData(Android_Context, this);
        setData(Files_Dir, this.getFilesDir().getAbsolutePath());

        try
        {
            Class.forName("net.shuttleplay.node.NodeBroker");

            Properties props = new Properties();
            props.load(new FileInputStream(getFilesDir() + "/config.props"));

            String index = props.getProperty("index");
            String runlabel = props.getProperty("runlabel");
            String port = props.getProperty("port");
            String littleLable = props.getProperty("littlelabel");

            int resId = getResources().getIdentifier("icon", "drawable", getPackageName());
            Notification notification = new Notification(resId, runlabel,
                    System.currentTimeMillis());
            Intent notiIntent = new Intent(Intent.ACTION_VIEW);
            String ipAddress = NetUtil.getLocalIpAddress(this);
            notiIntent.setData(Uri.parse("http://" + ipAddress + ":" + port + index));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notiIntent, 0);
            notification.setLatestEventInfo(this, runlabel, littleLable, pendingIntent);
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
