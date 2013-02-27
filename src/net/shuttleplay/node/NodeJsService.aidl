package net.shuttleplay.node;

interface NodeJsService
{
	void launchInstance(in String file);
	void debugInstance(in String file);
}