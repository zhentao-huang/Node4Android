package net.shuttleplay.node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public/* abstract */class LaunchActivity extends Activity implements ServiceConnection
{

    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        //unbindService(this);
        super.onDestroy();
    }

    public static final String LaunchIcon = "LaunchIcon";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        
        Log.i("LaunchActivity", "Intent = " + intent.getAction());
        
        if (intent.getAction().equals(Intent.ACTION_MAIN))
        {
            deploy();

            launchService();
        }
        else
        {
//            if (mNodejs == null || mConnection == null)
//            {
//                Log.e("LaunchActivity", "mNodejs is " + mNodejs + "; mConnection is " + mConnection);
//                return;
//            }
//            else
            {
                Properties props = new Properties();
                FileInputStream in;
                try
                {
                    in = new FileInputStream(getFilesDir() + "/config.props");
                    props.load(in);
                    in.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }

                String port = props.getProperty("port");
            
                String ipAddress = NetUtil.getLocalIpAddress(this);
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://" + ipAddress + ":" + port + "/exit"));
                startActivity(intent);
                stopService(new Intent(NodeService.LAUNCH_NODE));
//                unbindService(mConnection);
            }
        }
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
        mConnection = this;
        mNodejs = NodeJsService.Stub.asInterface(service);
        // mProgress.dismiss();
        // mProgress = null;

        Properties props = new Properties();
        try
        {
            String configName = getFilesDir() + "/config.props";
            FileInputStream fin = new FileInputStream(configName);
            props.load(fin);
            fin.close();

            final String index = props.getProperty("index");
            String mainfile = props.getProperty("main");
            final String port = props.getProperty("port");
            final String folderName = props.getProperty("sdfolder");
            boolean debugable = Boolean.parseBoolean(props.getProperty("debug", "false"));
            File externalSD = SdUtil.getSdCard();
            Log.i("node", "externalSd = " + externalSD.getAbsolutePath());
//            Log.i("node", "externalCache = " + this.getExternalCacheDir().getAbsolutePath());
//            Log.i("node", "externalFileDir = " + this.getExternalFilesDir("cchess").getAbsolutePath());
//            if (externalSD == null)
//            {
//                Toast.makeText(this, "No external SD card found", Toast.LENGTH_LONG).show();
//                finish();
//                return;
//            }
            File folder = new File(externalSD, folderName);
            if (!folder.exists() && !folder.mkdirs() && !folder.isDirectory())
            {
                Toast.makeText(this, "Folder " + folder.getAbsolutePath() + " can't be created", Toast.LENGTH_LONG).show();
                Log.i("node", "folder can't be created");
                finish();
                return;
            }
            
            props.setProperty("apk", SdUtil.getPackageFile(this));
            props.setProperty("sdcard", folder.getAbsolutePath());
            FileOutputStream fout = new FileOutputStream(configName);
            props.store(fout, null);
            fout.close();
            runNodeJs(mainfile, debugable);
            
            mTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    Intent notiIntent = new Intent(Intent.ACTION_VIEW);
                    String ipAddress = NetUtil.getLocalIpAddress(LaunchActivity.this);
                    notiIntent.setData(Uri.parse("http://" + ipAddress + ":" + port + index));
                    startActivity(notiIntent);
                }
            };

            mTimer.schedule(mTask, 3000);
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

    protected void runNodeJs(String mainfile, boolean debug)
    {
        File js = new File(getFilesDir(), mainfile);
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

    private static NodeJsService mNodejs = null;
    private static ServiceConnection mConnection = null;
    private final Timer mTimer = new Timer();
    private TimerTask mTask;
    private Handler mHandler;
}
