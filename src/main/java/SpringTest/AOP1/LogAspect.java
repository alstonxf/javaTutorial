package SpringTest.AOP1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 创建日志切面类
 */
@Aspect // @Aspect注解标记这个类是一个切面类
@Component // @Component注解标记这个被扫描包扫描到时需要加入IOC容器
public class LogAspect {  //定义一个日志切面类

    // @Before注解将当前方法标记为前置通知
    // value属性：配置当前通知的切入点表达式，通俗来说就是这个通知往谁身上套
    @Before(value = "execution(public void SpringTest.AOP1.UserServiceImpl.addUser(String,Integer))")
    public void doBefore(JoinPoint joinPoint) { // 在通知方法中，声明JoinPoint类型的形参，就可以在Spring调用当前方法时把这个类型的对象传入

        // 1.通过JoinPoint对象获取目标方法的签名
        // 所谓方法的签名就是指方法声明时指定的相关信息，包括方法名、方法所在类等等
        Signature signature = joinPoint.getSignature();

        // 2.通过方法签名对象可以获取方法名
        String methodName = signature.getName();

        // 3.通过JoinPoint对象获取目标方法被调用时传入的参数
        Object[] args = joinPoint.getArgs();

        // 4.为了方便展示参数数据，把参数从数组类型转换为List集合
        List<Object> argList = Arrays.asList(args);

        System.out.println("[前置通知]"+ methodName +"方法开始执行，参数列表是：" + argList);
    }
}