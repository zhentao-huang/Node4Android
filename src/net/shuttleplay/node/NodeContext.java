package net.shuttleplay.node;

public interface NodeContext
{
    Object getData(String name);

    void setData(String name, Object value);
}
