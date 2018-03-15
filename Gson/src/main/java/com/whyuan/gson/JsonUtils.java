package com.whyuan.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;

public class JsonUtils {
    public void parseJson01(String jsonData){
        try {
            //解析json对象首先要生产一个JsonReader对象
            JsonReader reader=new JsonReader(new StringReader(jsonData));
            reader.beginArray();
            while(reader.hasNext()){
                reader.beginObject();
                while(reader.hasNext()){
                    String tagName=reader.nextName();
                    if("name".equals(tagName)){
                        System.out.println("name--->"+reader.nextString());
                    }else if("age".equals(tagName)){
                        System.out.println("age--->"+reader.nextInt());
                    }
                }
                reader.endObject();
            }
            reader.endArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseJson02(String jsonData){
        Gson gson=new Gson();
        Person person=gson.fromJson(jsonData, Person.class);
        System.out.println("name--->" + person.getName());
        System.out.println("age---->" + person.getAge());
    }

    public void parseJson03(String jsonData){
        Type listType = new TypeToken<LinkedList<Person>>(){}.getType();
        Gson gson=new Gson();
        LinkedList<Person> persons=gson.fromJson(jsonData, listType);
        for(Person person:persons){
            System.out.println("name--->" + person.getName());
            System.out.println("age---->" + person.getAge());
        }
        System.out.println("==================");
        for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
            Person person = (Person) iterator.next();
            System.out.println("name--->" + person.getName());
            System.out.println("age---->" + person.getAge());
        }
    }
}
