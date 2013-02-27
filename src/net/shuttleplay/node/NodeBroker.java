package net.shuttleplay.node;

public class NodeBroker
{

    public static native void runNodeJs(NodeContext nc, String nodefile);

    public static native void debugNodeJs(NodeContext nc, String nodefile);

    static
    {
        System.loadLibrary("nodejs");
        // Dummy code for debug
        System.out.println("Load lib success");
    }
}
