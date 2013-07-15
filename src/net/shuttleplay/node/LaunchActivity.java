package net.shuttleplay.node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

public/* abstract */class LaunchActivity extends Activity implements ServiceConnection
{

    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        unbindService(this);
        super.onDestroy();
    }

    public static final String LaunchIcon = "LaunchIcon";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        deploy();

        launchService();
    }

    private void deploy()
    {
        File filedir = getFilesDir();
        File config = new File(filedir, "config.props");
        if (config.exists())
        {
            return;
        }

        int resId = getResources().getIdentifier("web", "raw", getPackageName());
        InputStream webStream = getResources().openRawResource(resId);
        if (webStream != null)
        {
            try
            {
                JarInputStream jin = new JarInputStream(webStream);
                JarEntry entry;
                while ((entry = jin.getNextJarEntry()) != null)
                {
                    String entryName = entry.getName();
                    File file = new File(filedir, entryName);
                    if (entry.isDirectory())
                    {
                        if (!file.exists())
                        {
                            file.mkdirs();
                        }
                    }
                    else
                    {
                        File dir = new File(file.getParent());
                        if (!dir.exists())
                        {
                            dir.mkdirs();
                        }
                        FileOutputStream fout = null;
                        try
                        {
                            fout = new FileOutputStream(file);
                            NetUtil.copyIO(jin, fout);
                        }
                        finally
                        {
                            fout.close();
                        }
                    }
                }
            }
            catch(Exception e)
            {

            }
        }
    }

    private void launchService()
    {
        Intent intent = new Intent(NodeService.LAUNCH_NODE);
        // intent.putExtra(LaunchIcon, this.getResourceId(LaunchIcon));
        startService(intent);
        boolean ret = bindService(intent, this, Context.BIND_AUTO_CREATE);
        if (!ret)
        {
            finish();
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {

        mNodejs = NodeJsService.Stub.asInterface(service);
        // mProgress.dismiss();
        // mProgress = null;

        Properties props = new Properties();
        try
        {
            props.load(new FileInputStream(getFilesDir() + "/config.props"));

            String index = props.getProperty("index");
            boolean debugable = Boolean.parseBoolean(props.getProperty("debug", "false"));
            runNodeJs(debugable);

            Intent notiIntent = new Intent(Intent.ACTION_VIEW);
            String ipAddress = NetUtil.getLocalIpAddress(this);
            notiIntent.setData(Uri.parse("http://" + ipAddress + ":8001" + index));
            startActivity(notiIntent);
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
        finally
        {
            props = null;
        }
        finish();
    }

    @Override
    public void onServiceDisconnected(ComponentName name)
    {

    }

    protected void runNodeJs(boolean debug)
    {
        File js = new File(getFilesDir(), "nodeweb/nodeweb.js");
        if (js.exists() && mNodejs != null)
        {
            try
            {
                if (!debug)
                {
                    mNodejs.launchInstance(js.getAbsolutePath());
                }
                else
                {
                    mNodejs.debugInstance(js.getAbsolutePath());
                }
            }
            catch(RemoteException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            // Toast.makeText(this, R.string.nofilefound,
            // Toast.LENGTH_LONG).show();
        }
    }

    // protected abstract int getResourceId(String resourceName);

    private NodeJsService mNodejs = null;
}
