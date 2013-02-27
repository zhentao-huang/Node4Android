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

import android.util.Log;

public class NetUtil
{
    private static final int bufferSize = 64 * 1024;
    private static final String tag = "NetUtil";

    public static String getLocalIpAddress()
    {
        String localip = null;
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                Log.i(tag, intf.getDisplayName());
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress())
                    {
                        localip = inetAddress.getHostAddress().toString();
                        return localip;
                    }
                    Log.i(tag, "ip = " + inetAddress.getHostAddress().toString() + "; local = "
                            + inetAddress.isLoopbackAddress() + "; str = " + inetAddress);

                }
            }
        }
        catch(SocketException ex)
        {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return localip;
    }

    public static void copyIO(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[bufferSize];
        long len = 0;
        while (true)
        {
            len = in.read(buffer, 0, bufferSize);
            if (len < 0)
                break;
            out.write(buffer, 0, (int) len);
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
            writer.write(buffer, 0, (int) len);
        }
    }
}
