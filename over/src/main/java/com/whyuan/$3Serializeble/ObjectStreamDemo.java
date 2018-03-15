package com.whyuan.$3Serializeble;

import java.io.*;

/**
 * @author Administrator
 *对象的序列化的输入与输出，解析规则由对象输出流ObjectOutputStream控制
 *ObjectOutputStream 和 ObjectInputStream
    writeObject(obj) 和 readObject()
 */
public class ObjectStreamDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream out=new ByteArrayOutputStream();//目标节点流：内存中的一个字节数组
        ObjectOutputStream oos=new ObjectOutputStream(out);//序列化流：对象输出流
        Foo foo=new Foo(1,"ABC");
        oos.writeObject(foo);//序列化对象到流中
        oos.close();//完成关闭流（自动清理缓冲区）
        byte[] ary=out.toByteArray();//获得序列化结果
        //foo对象-》ary（流）
        System.out.println(IOUtils.toHexString(ary));


        ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(ary));//反序列化
        Object o=ois.readObject();
        ois.close();
        if(o instanceof Foo){
            Foo f=(Foo)o;
            System.out.println(f.getId()+f.getName());
            System.out.println(f==foo);//结果为false,说明并不是一个对象，是复制的。
        }
    }
}


/**
 * Object -> n个byte流
 * 对象序列化：类必须实现序列化接口，此接口内没有抽象方法，由java虚拟机自动实现序列化与反序列化。
 * @author Administrator
 * java规定所有的类都实现序列化接口
 *
 */
class Foo implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    public Foo(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
