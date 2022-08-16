### log4j-logger .isDebugEnabled和logger.isInfoEnabled的作用

![image-20220814145601527](/Users/lixiaofeng/Library/Application Support/typora-user-images/image-20220814145601527.png)

直接使用logger.info(...)来输出log，在进入log.info函数之产有，括号内的[表达式](https://so.csdn.net/so/search?q=表达式&spm=1001.2101.3001.7020)已经通过运算拼接成了一个字符串，而如果事先使用if(logger.isInfoEnabled())进行断断，那么当log级别为debug及以上时，就省去上述的字符串操作，在高并发和复杂的log信息拼接的情况下，使用这种标准的方法输出log能够省去不小的开销。

ERROR级的log信息是一定会输出的，所以没有Logger.isErrorEnabled方法。

### BeanUtils.copyProperties的用法

![image-20220814154619391](/Users/lixiaofeng/Library/Application Support/typora-user-images/image-20220814154619391.png)
BeanUtils.copyProperties（source,target)有两个参数，用于将事件源的数据拷贝到目标源中。
但是BeanUtils有两个，一个是spring的，一个是apache的commons下的。用法一样，但是两者的区别在于：一个是将前者拷贝给后者，另一个是将后者拷贝给前面的。

```java
public class Test{
    public static void main(String[] args) {
            Student studentOne  = new StudentOne("张三");
            Student studentTwo  = new StudentOne();
            //BeanUtils.copyProperties是将studentOne的数据拷贝给studentTwo
            BeanUtils.copyProperties(studentOne, studentTwo);
    }
}
```

如果两个类不一样时，只会拷贝属性一样的内容。

```java
@Data
public class User {
    private Integer id;
    private String idCard;
    private String pwd;
    private String workUnit;
    private String name;
    private String sex;
}
@Data
public class Salary {
    private Integer id;
    private Integer userId;
		private String idCard;
}
@Data
public class UserSalaryDto{
    private Integer id;
    private String idCard;
    private String pwd;
    private String workUnit;
    private String name;
    private String sex;
    private Integer userId;
    private String idCard;
}
public class Test{
    public void test(UserSalaryDto userSalaryDto) {
            //BeanUtils.copyProperties是将studentOne的数据拷贝给studentTwo
            User user = new User();
            Salary salary = new Salary();
            BeanUtils.copyProperties(userSalaryDto, user);
            BeanUtils.copyProperties(userSalaryDto, salary);
    }
}
```


这时需要注意的是id属性应该为User的属性，salary的id获取的为User的id，会出现id与userId一致的情况，因此在使用阶段需要注意相同属性名的问题。
————————————————
版权声明：本文为CSDN博主「黑白交織℡」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_50704668/article/details/119824500