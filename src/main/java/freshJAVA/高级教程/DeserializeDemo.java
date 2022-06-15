package freshJAVA.高级教程;

import java.io.*;

public class DeserializeDemo
{
    public static void main(String [] args)
    {
        SerializeDemo.Employee e = null;
        try
        {
            //反序列化对象
            FileInputStream fileIn = new FileInputStream("output\\employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (SerializeDemo.Employee) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
        System.out.println("Deserialized Employee...");
        System.out.println("Name: " + e.name);
        System.out.println("Address: " + e.address);
        System.out.println("SSN: " + e.SSN);
        System.out.println("Number: " + e.number);
    }
}