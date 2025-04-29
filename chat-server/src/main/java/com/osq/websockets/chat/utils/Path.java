package com.osq.websockets.chat.utils;

public class Path {
    public static String resolvePath(String path, String anotherPath) {
        String separator = "";

        if (path.endsWith("/")) {
            if (anotherPath.startsWith("/"))
                anotherPath = anotherPath.substring(1);
        } else {    
            if (!anotherPath.startsWith("/"))
                separator = "/";
        }

        return path + separator + anotherPath;
    }
}
