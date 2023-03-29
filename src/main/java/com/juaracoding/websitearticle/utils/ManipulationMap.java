package com.juaracoding.websitearticle.utils;/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author Vicki M a.k.a. Vicki Mantovani
Java Developer
Created on 10/03/2023 15:35
@Last Modified 10/03/2023 15:35
Version 1.1
*/

import java.util.Map;

public class ManipulationMap {
    public static String getKeyFromValue(Map<String,String> mapEntries, String value)
    {
        for (Map.Entry<String, String> entry : mapEntries.entrySet()) {
            if (entry.getValue().equals(value)) {
                System.out.println(entry.getKey());
            }
        }
        return "id";
    }
}
