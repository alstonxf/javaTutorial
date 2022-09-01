# 15-企业权限管理-aop日志
## SysLog实体类

```
public class SysLog {<!-- -->
    private String id;
    private Date visitTime;
    private String visitTimeStr;
    private String username;
    private String ip;
    private String url;
    private Long executionTime;
    private String method;

    public String getId() {<!-- -->
        return id;
    }

    public void setId(String id) {<!-- -->
        this.id = id;
    }

    public Date getVisitTime() {<!-- -->
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {<!-- -->
        this.visitTime = visitTime;
    }

    public String getVisitTimeStr() {<!-- -->
        return visitTimeStr;
    }

    public void setVisitTimeStr(String visitTimeStr) {<!-- -->
        this.visitTimeStr = visitTimeStr;
    }

    public String getUsername() {<!-- -->
        return username;
    }

    public void setUsername(String username) {<!-- -->
        this.username = username;
    }

    public String getIp() {<!-- -->
        return ip;
    }

    public void setIp(String ip) {<!-- -->
        this.ip = ip;
    }

    public String getUrl() {<!-- -->
        return url;
    }

    public void setUrl(String url) {<!-- -->
        this.url = url;
    }

    public Long getExecutionTime() {<!-- -->
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {<!-- -->
        this.executionTime = executionTime;
    }

    public String getMethod() {<!-- -->
        return method;
    }

    public void setMethod(String method) {<!-- -->
        this.method = method;
    }
}


```

## 后置对象

<img src="https://img-blog.csdnimg.cn/2020042010045562.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## LogAop

```
@Component
@Aspect
public class LogAop {<!-- -->

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService sysLogService;

    private Date visitTime; //开始时间
    private Class clazz; //访问的类
    private Method method;//访问的方法

    //前置通知  主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {<!-- -->
        visitTime = new Date();//当前时间就是开始访问的时间
        clazz = jp.getTarget().getClass(); //具体要访问的类
        String methodName = jp.getSignature().getName(); //获取访问的方法的名称
        Object[] args = jp.getArgs();//获取访问的方法的参数

        //获取具体执行的方法的Method对象
        if (args == null || args.length == 0) {<!-- -->
            method = clazz.getMethod(methodName); //只能获取无参数的方法
        } else {<!-- -->
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {<!-- -->
                classArgs[i] = args[i].getClass();
            }
            clazz.getMethod(methodName, classArgs);
        }
    }

    //后置通知
    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {<!-- -->
        long time = new Date().getTime() - visitTime.getTime(); //获取访问的时长

        String url = "";
        //获取url
        if (clazz != null &amp;&amp; method != null &amp;&amp; clazz != LogAop.class) {<!-- -->
            //1.获取类上的@RequestMapping("/orders")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {<!-- -->
                String[] classValue = classAnnotation.value();
                //2.获取方法上的@RequestMapping(xxx)
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {<!-- -->
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];

                    //获取访问的ip
                    String ip = request.getRemoteAddr();

                    //获取当前操作的用户
                    SecurityContext context = SecurityContextHolder.getContext();//从上下文中获了当前登录的用户
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    //将日志相关信息封装到SysLog对象
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time); //执行时长
                    sysLog.setIp(ip);
                    sysLog.setMethod("[类名] " + clazz.getName() + "[方法名] " + method.getName());
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(visitTime);

                    //调用Service完成操作
                    sysLogService.save(sysLog);
                }
            }
        }

    }
}

```

<img src="https://img-blog.csdnimg.cn/20200420110705915.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMDYxMjkw,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"/>

## SysLogController

```
@Controller
@RequestMapping("/sysLog")
public class SysLogController {<!-- -->

    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {<!-- -->
        ModelAndView mv=new ModelAndView();
       List<SysLog> sysLogList= sysLogService.findAll();
       mv.addObject("sysLogs",sysLogList);
       mv.setViewName("syslog-list");
        return mv;
    }
}


```

## SysLogServiceImpl

```
@Service
@Transactional
public class SysLogServiceImpl implements ISysLogService {<!-- -->

    @Autowired
    private ISysLogDao sysLogDao;

    @Override
    public List<SysLog> findAll() throws Exception {<!-- -->
        return sysLogDao.findAll();
    }

    @Override
    public void save(SysLog sysLog) throws Exception {<!-- -->
        sysLogDao.save(sysLog);
    }
}


```

## ISysLogDao

```
public interface ISysLogDao {<!-- -->

    @Insert("insert into syslog(visitTime,username,ip,url,executionTime,method) values(#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method})")
    public void save(SysLog sysLog) throws Exception;

    @Select("select * from sysLog")
    List<SysLog> findAll() throws Exception;
}


```
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/105628219