package com.whyuan.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.io.Reader;

public class JsonToJava {
    public static void main(String[] args) throws Exception{
        Reader reader=new InputStreamReader(
                JsonToJava.class.getResourceAsStream("/server1.json"),"UTF-8"
        );
        Gson gson =new GsonBuilder().create();
        Person person=gson.fromJson(reader,Person.class);
        System.out.println(person);
        System.out.println("姓名："+person.getName()+"，年龄："+person.getAge()+"，学科："+person.getExam().getsubject()+"，分数："+person.getExam().getGrade());
        reader.close();
    }
}
