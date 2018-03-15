package com.whyuan.gson;

public class TestJsonUtils {
    public static void main(String[] args) {
         String jsonData01 = "[{\"name\":\"Michael\",\"age\":20},{\"name\":\"Mike\",\"age\":21}]";
         String jsonData02 = "{\"name\":\"Michael\",\"age\":20}";

         JsonUtils jsonUtils=new JsonUtils();
         jsonUtils.parseJson01(jsonData01);
         System.out.println("-------------------------------");
         jsonUtils.parseJson02(jsonData02);
         System.out.println("-------------------------------");
         jsonUtils.parseJson03(jsonData01);


    }
}
