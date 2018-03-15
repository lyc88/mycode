package com.urun.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.zip.Adler32;

public class GsonUtil {
    private static  Gson gson= new GsonBuilder().create();
    /**
    *@Author: whyuan
    *@Description: 将Json串转化成实体对象
    *@Date: Created in 11:47 2017/12/21
    *@Modified By:
    */
    public static Object parseJsonToObject(String json, Class classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
    *@Author: whyuan
    *@Description: 将String->Json
    *@Date: Created in 16:50 2017/12/20
    *@Modified By:
    */
    public static <T> String parseObjectToJson(T object){
        return gson.toJson(object);

    }

    public static List parseJsonToList(String jsonData){
        Type type=new TypeToken<List>(){}.getType();
        List list =gson.fromJson(jsonData,type);

        return list;

    }
//=======================================工具类测试分割线=======================================
    public static void main(String[] args) {
        String jsonData1="[  \n" +
                "  {  \n" +
                "    \"is_online\": 1,  \n" +
                "    \"user_name\": \"guanghui.li\",  \n" +
                "    \"birthday\": \"2014-10-21\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 100,  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/da15e418-9e97-4480-9a15-29068e319c56\",  \n" +
                "    \"signature\": \"哈哈魔\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 1,  \n" +
                "    \"user_name\": \"111\",  \n" +
                "    \"birthday\": \"2015-09-07\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 111,  \n" +
                "    \"email\": \"5313321750@qq.com\",  \n" +
                "    \"nickname\": \"双方都\",  \n" +
                "    \"face_id\": 6,  \n" +
                "    \"face_url\": \"3c_home/face_img/2e9fb3b8-5eee-4a5f-9cb3-851fc411d50f\",  \n" +
                "    \"signature\": \"加油✌\\n635958464\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"115\",  \n" +
                "    \"birthday\": \"2020-09-22\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 115,  \n" +
                "    \"nickname\": \"阿斯蒂芬...\",  \n" +
                "    \"face_id\": 8,  \n" +
                "    \"face_url\": \"3c_home/8b4cec54-6c70-4487-a32b-b4432f2e8185\",  \n" +
                "    \"signature\": \"梦想还是要有的，万一实现了呢？\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"zhipeng.hui\",  \n" +
                "    \"birthday\": \"1990-05-16\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 129,  \n" +
                "    \"email\": \"zhipe2sng.hui@cloudsoar.com\",  \n" +
                "    \"nickname\": \"阿斯蒂芬\",  \n" +
                "    \"face_id\": 1,  \n" +
                "    \"face_url\": \"3c_home/face_img/ba2eca97-4577-427c-9a58-4a24126af824\",  \n" +
                "    \"signature\": \"务实才能创新，创新才能发展。\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"150135012275\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 136,  \n" +
                "    \"nickname\": \"大后天\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/91aa55ab-b60c-4b98-8939-345c0c34cd43\",  \n" +
                "    \"signature\": \"\",  \n" +
                "    \"is_test\": 0,  \n" +
                "    \"mobile\": \"150131502275\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"180381210025\",  \n" +
                "    \"birthday\": \"1899-12-30\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 138,  \n" +
                "    \"nickname\": \"棼棼\",  \n" +
                "    \"face_id\": 256,  \n" +
                "    \"face_url\": \"3c_home/face_img/85e54960-000e-4780-a440-ebb07f40be96\",  \n" +
                "    \"signature\": \"心若向阳，何惧伤悲！\",  \n" +
                "    \"is_test\": 0,  \n" +
                "    \"mobile\": \"186651927005\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"186887180051\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 139,  \n" +
                "    \"nickname\": \"James\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/ee701de0-1404-4d5f-b9bf-4da505284007\",  \n" +
                "    \"is_test\": 0,  \n" +
                "    \"mobile\": \"186887180051\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"138128868458\",  \n" +
                "    \"birthday\": \"2001-08-03\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 168,  \n" +
                "    \"nickname\": \"看看看韩国\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/c70e9cab-56a3-46e3-ab2d-61dfbca61f74\",  \n" +
                "    \"is_test\": 0,  \n" +
                "    \"mobile\": \"138288618458\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"huo2\",  \n" +
                "    \"birthday\": \"2010-02-26\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 201,  \n" +
                "    \"nickname\": \"不良人\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/5f9b4921-1abf-4a67-8810-b20024867911\",  \n" +
                "    \"signature\": \"彪悍的人生不需要解释\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"水电费\",  \n" +
                "    \"birthday\": \"2015-01-16\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 336,  \n" +
                "    \"nickname\": \"水电费\",  \n" +
                "    \"face_id\": 8,  \n" +
                "    \"face_url\": \"3c_home/face_img/7376c9c7-fbc1-4461-9444-0370f8e38ef0\",  \n" +
                "    \"signature\": \"学如逆水行舟，不进则退\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 1,  \n" +
                "    \"user_name\": \"186881889823\",  \n" +
                "    \"sex\": \"\",  \n" +
                "    \"id_user\": 526,  \n" +
                "    \"nickname\": \"randy\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"mobile\": \"186818889823\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"阿萨德\",  \n" +
                "    \"birthday\": \"1992-06-30\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 666,  \n" +
                "    \"nickname\": \"阿萨德\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/7b06eb1c-0da0-4eb7-972a-0311e1ec328f\",  \n" +
                "    \"signature\": \"\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"好\",  \n" +
                "    \"birthday\": \"1988-04-26\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 888,  \n" +
                "    \"email\": \"3801111720@qq.com\",  \n" +
                "    \"nickname\": \"规范\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/3ec8e894-f457-42b8-993f-ce010c4154b7\",  \n" +
                "    \"signature\": \"\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 1,  \n" +
                "    \"user_name\": \"阿斯蒂芬\",  \n" +
                "    \"birthday\": \"1900-01-27\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 889,  \n" +
                "    \"email\": \"xx@xx.com\",  \n" +
                "    \"nickname\": \"阿斯蒂芬\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/a43042f6-4b65-4ba9-91ee-716daa70a926\",  \n" +
                "    \"signature\": \"\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 1,  \n" +
                "    \"user_name\": \"ccccc\",  \n" +
                "    \"birthday\": \"0001-01-01\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 1122,  \n" +
                "    \"email\": \"5477171003@qq.com\",  \n" +
                "    \"nickname\": \"cccc\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/3667abde-d073-4a27-ab36-ec8a81a52d72\",  \n" +
                "    \"signature\": \"\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"159200016474\",  \n" +
                "    \"birthday\": \"1989-11-20\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 3480,  \n" +
                "    \"nickname\": \"Carol\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/60ff35e1-9d74-4150-8802-3e35a304f1c3\",  \n" +
                "    \"signature\": \"宠辱不惊，看庭前花开花落；去留无意，望天上云卷云舒。\",  \n" +
                "    \"mobile\": \"159200061474\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"131281758281\",  \n" +
                "    \"birthday\": \"2015-06-02\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 3773,  \n" +
                "    \"nickname\": \"sky\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/7cac4b97-b902-490b-b34d-057ed5682226\",  \n" +
                "    \"signature\": \"乐在勾通^_^\",  \n" +
                "    \"mobile\": \"131128758281\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 1,  \n" +
                "    \"user_name\": \"caoguigui\",  \n" +
                "    \"birthday\": \"2015-08-11\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 42052,  \n" +
                "    \"nickname\": \"暗红色的\",  \n" +
                "    \"face_id\": 8,  \n" +
                "    \"face_url\": \"3c_home/873a093d-c644-4ee1-b61a-237e191627c4\",  \n" +
                "    \"signature\": \"go go go!\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"136911887425\",  \n" +
                "    \"birthday\": \"1985-11-11\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 42188,  \n" +
                "    \"nickname\": \"按时到岗\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/85f4ddf2-6185-4ed2-9fa0-8f16b19e6847\",  \n" +
                "    \"signature\": \"加油。。。。\",  \n" +
                "    \"mobile\": \"136918187425\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"miaozi\",  \n" +
                "    \"birthday\": \"2015-09-07\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 42229,  \n" +
                "    \"nickname\": \"阿斯蒂芬\",  \n" +
                "    \"face_id\": 5,  \n" +
                "    \"face_url\": \"3c_home/7faec67c-52a5-40e3-9501-d88543850ab8\",  \n" +
                "    \"signature\": \"空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空军建军节空\",  \n" +
                "    \"mobile\": \"185031008635\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"Cgg\",  \n" +
                "    \"birthday\": \"2015-10-13\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 42262,  \n" +
                "    \"email\": \"cggc23zf@163.com\",  \n" +
                "    \"nickname\": \"阿什顿发\",  \n" +
                "    \"face_id\": 7,  \n" +
                "    \"face_url\": \"3c_home/face_img/ef7da1fc-8bd4-460f-a26c-cb1a46982471\",  \n" +
                "    \"signature\": \"⛪️\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"koukou\",  \n" +
                "    \"birthday\": \"1985-11-11\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 42670,  \n" +
                "    \"email\": \"79144102359@qq.com\",  \n" +
                "    \"nickname\": \"儿童\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/c0b70bdd-5242-40f6-bbc6-726aeb0f9c57\",  \n" +
                "    \"signature\": \"来咯哦哦五塔寺蘑菇街啦咯啦咯啦咯空军建军节空军建军节空军建军摸摸摸啊啊啊空军建军节空军建军节来的人的啦咯啦咯啦咯空军建军节空军建军节啦咯啦咯啦咯噢噢噢哦哦了考虑兔兔空军建军节啦咯啦咯啦咯空军建军节空军\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"186658711161\",  \n" +
                "    \"birthday\": \"1992-11-06\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 42953,  \n" +
                "    \"email\": \"zengxi212angjian.cn@gmail.com\",  \n" +
                "    \"nickname\": \"同意\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"signature\": \"\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"huo3\",  \n" +
                "    \"birthday\": \"2015-11-30\",  \n" +
                "    \"sex\": \"\",  \n" +
                "    \"id_user\": 42969,  \n" +
                "    \"nickname\": \"huo3\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/4fce24e3-ac84-4e62-987f-583005c75fca\",  \n" +
                "    \"signature\": \"..............\",  \n" +
                "    \"mobile\": \"1110\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"15019286469\",  \n" +
                "    \"birthday\": \"1990-05-05\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 42987,  \n" +
                "    \"nickname\": \"卷\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/e591f9dc-3740-4874-916b-a1b72a4d162f\",  \n" +
                "    \"signature\": \"时光海苔。\",  \n" +
                "    \"mobile\": \"150192861469\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"eric\",  \n" +
                "    \"birthday\": \"2015-06-29\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 43515,  \n" +
                "    \"nickname\": \"UI水电费\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/abf86610-5623-4971-85ba-32a17f9a21ae\",  \n" +
                "    \"signature\": \"     \",  \n" +
                "    \"mobile\": \"186030715850\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"sex\": \"\",  \n" +
                "    \"id_user\": 43664,  \n" +
                "    \"nickname\": \"加油冲刺\",  \n" +
                "    \"face_id\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"birthday\": \"2015-10-16\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 43666,  \n" +
                "    \"nickname\": \"感觉萌萌哒\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/bc7d9376-f323-4e83-8f82-b1e6ee3ad826\",  \n" +
                "    \"signature\": \"因为爱情  ……\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 1,  \n" +
                "    \"user_name\": \"wangyb\",  \n" +
                "    \"birthday\": \"1986-11-10\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 43905,  \n" +
                "    \"email\": \"3951044952@qq.com\",  \n" +
                "    \"nickname\": \"问问\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/9245335e-b8f1-43f5-b023-eb933b061bd2\",  \n" +
                "    \"signature\": \"\",  \n" +
                "    \"mobile\": \"186892010174\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"Mona\",  \n" +
                "    \"birthday\": \"1905-11-09\",  \n" +
                "    \"sex\": \"FEMALE\",  \n" +
                "    \"id_user\": 43980,  \n" +
                "    \"nickname\": \"Mona\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/094a7a2f-bfd3-491e-bb77-e3edd2c080bf\",  \n" +
                "    \"signature\": \"\",  \n" +
                "    \"mobile\": \"1867588171506\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"阿什顿发\",  \n" +
                "    \"birthday\": \"1991-01-12\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 666668,  \n" +
                "    \"email\": \"junbin.liu@cloudsoar.com\",  \n" +
                "    \"nickname\": \"地方噶\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/c792406c-90dd-4c2b-8628-4615901749e4\",  \n" +
                "    \"signature\": \"✌\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"cheng.li\",  \n" +
                "    \"birthday\": \"1988-04-26\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 888888,  \n" +
                "    \"email\": \"2607443042@qq.com\",  \n" +
                "    \"nickname\": \"阿什顿发\",  \n" +
                "    \"face_id\": 0,  \n" +
                "    \"face_url\": \"3c_home/face_img/68a3bea9-6349-4f4d-b7e3-d05a6af015aa\",  \n" +
                "    \"signature\": \"\",  \n" +
                "    \"is_test\": 0,  \n" +
                "    \"mobile\": \"cheng.li\"  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"wengcx88\",  \n" +
                "    \"birthday\": \"1985-11-11\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 1001019,  \n" +
                "    \"email\": \"wengcx520@sina.com\",  \n" +
                "    \"nickname\": \"酷狗\",  \n" +
                "    \"face_id\": 9,  \n" +
                "    \"face_url\": \"\",  \n" +
                "    \"signature\": \"寇可往，吾亦可往！\",  \n" +
                "    \"is_test\": 0  \n" +
                "  },  \n" +
                "  {  \n" +
                "    \"is_online\": 0,  \n" +
                "    \"user_name\": \"junsong.huang\",  \n" +
                "    \"birthday\": \"1915-12-28\",  \n" +
                "    \"sex\": \"MALE\",  \n" +
                "    \"id_user\": 1234658,  \n" +
                "    \"nickname\": \"驱蚊贴\",  \n" +
                "    \"face_id\": 2,  \n" +
                "    \"face_url\": \"3c_home/face_img/d2dd20ef-5b2f-458e-8000-efdcd6c269be\",  \n" +
                "    \"signature\": \"Tyrion Lannister\",  \n" +
                "    \"is_test\": 0  \n" +
                "  }  \n" +
                "]  ";
        System.out.println(parseJsonToList(jsonData1));
        String jsonData2="[{\n" +
                "\t\"name\":whyuan,\n" +
                "\t\"age\":18\n" +
                "}]";
        System.out.println(parseJsonToList(jsonData2));

    }
}
