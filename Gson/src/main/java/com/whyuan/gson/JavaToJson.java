package com.whyuan.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class JavaToJson {
    public static void main(String[] args) throws Exception{
        Writer writer=new OutputStreamWriter(
                new FileOutputStream("D:\\Gson\\src\\main\\resources\\output.json"),"UTF-8"
        );
        Gson gson =new GsonBuilder().create();
        gson.toJson("擎天柱",writer);
        gson.toJson(18,writer);
        /*writer.flush();
        writer.close();*/


        Person person=new Person("whyuan",18);
        Gson gson1=new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        String obj=gson1.toJson(person);//将对象的属性转换成指定的Json名字：{"Name":"whyuan","Age":18}
        System.out.println(obj);
        String obj1=gson.toJson(person);//将java对象转换成json
        System.out.println(obj1);
        Person person1=new Person("whyuan",18,new Exam("英语",78.9));
        String obj2=gson.toJson(person1);
        System.out.println(obj2);
        gson.toJson(person1,writer);
        writer.flush();
        writer.close();
        System.out.println("成功将Java对象转变为Json!");
        String jsonData01 = "[{\"name\":\"Michael\",\"age\":20},{\"name\":\"Mike\",\"age\":21}]";
        System.out.println("啊啊啊啊啊啊啊啊啊："+gson.toJson(jsonData01));

    }
}
