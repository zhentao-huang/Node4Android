package net.shuttleplay.node;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetUtil 
{
	private static final int bufferSize = 64*1024;
	private static final String tag = "NetUtil";
	
	public static String getLocalIpAddress(Context context) {
		String localip = null;
                Log.i(tag, "Enter getLocalIpAddress()");
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				Log.i(tag, intf.getDisplayName());
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
						localip = inetAddress.getHostAddress().toString();
						return localip;
					}
					Log.i(tag, "ip = " + inetAddress.getHostAddress().toString() + "; local = " + inetAddress.isLoopbackAddress() + "; str = " + inetAddress);
					
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
                if (localip == null)
                {
                    WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo info = manager.getConnectionInfo();
                    if (info != null)
                    {
                        int address = info.getIpAddress();
                        localip = String.format("%d.%d.%d.%d", (address & 0xff), (address & 0xff00) >>>8, (address & 0xff0000) >>>16, address >>> 24);  
                    }
                }
		return localip;
	}

    public static String getWifiSSID(Context context)
    {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        if (info != null)
        {
            return info.getSSID();
        }
        return null;
    }

	public static void copyIO(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[bufferSize];
		long len = 0;
	    while (true)
	    {
	        len=in.read(buffer,0,bufferSize);
	        if (len<0 )
	            break;
			out.write(buffer,0, (int) len);
	    }
	}
	
	public static void copyText(Reader reader, Writer writer) throws IOException
	{
		char[] buffer = new char[bufferSize];
		long len = 0;
		while (true)
		{
			len = reader.read(buffer, 0, bufferSize);
			if (len < 0)
			{
				break;
			}
			writer.write(buffer,  0, (int) len);
		}
	}
	
}
