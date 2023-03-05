package org.realcool.utils;

import java.util.UUID;

public class IdUtils {

    public static String genId(){
        return UUID.randomUUID().toString();
    }
}
