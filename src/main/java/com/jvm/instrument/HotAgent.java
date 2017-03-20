package com.jvm.instrument;

/**
 * @author chen
 * @description
 * @pachage com.jvm.instrument
 * @date 2016/05/22 22:06
 */
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Timer;

/**
 * 提供 Java 代理检测运行在 JVM 上的程序的服务。以及对方法的字节码进行修改
 * 代理被部署为 JAR 文件。JAR 文件清单中的属性指定将被加载以启动代理的代理类
 * 启动时可能需要的清单：
 * Premain-Class
    如果 JVM 启动时指定了代理，那么此属性指定代理类，即包含 premain 方法的类。如果 JVM 启动时指定了代理，那么此属性是必需的。如果该属性不存在，那么 JVM 将中止。注：此属性是类名，不是文件名或路径。
 Agent-Class
    如果实现支持 VM 启动之后某一时刻启动代理的机制，那么此属性指定代理类。 即包含 agentmain 方法的类。 此属性是必需的，如果不存在，代理将无法启动。 注：这是类名，而不是文件名或路径。
 Boot-Class-Path
    由引导类加载器搜索的路径列表。路径表示目录或库（在许多平台上通常作为 JAR 或 zip 库被引用）。
    查找类的特定于平台的机制失败后，引导类加载器会搜索这些路径。按列出的顺序搜索路径。列表中的路径由一个或多个空格分开。
    路径使用分层 URI 的路径组件语法。如果该路径以斜杠字符（“/”）开头，则为绝对路径，否则为相对路径。
    相对路径根据代理 JAR 文件的绝对路径解析。忽略格式不正确的路径和不存在的路径。如果代理是在 VM 启动之后某一时刻启动的，则忽略不表示 JAR 文件的路径。此属性是可选的。
 Can-Redefine-Classes
    布尔值（true 或 false，与大小写无关）。是否能重定义此代理所需的类。true 以外的值均被视为 false。此属性是可选的，默认值为 false。
 Can-Retransform-Classes
    布尔值（true 或 false，与大小写无关）。是否能重转换此代理所需的类。true 以外的值均被视为 false。此属性是可选的，默认值为 false。
 Can-Set-Native-Method-Prefix
    布尔值（true 或 false，与大小写无关）。是否能设置此代理所需的本机方法前缀。true 以外的值均被视为 false。此属性是可选的，默认值为 false。
 具体内容查看instrument doc
 */
public  class  HotAgent {
    /**
     * 跟随jvm启动而启动，方法类似main方法，启动顺序在jvm启动后，main执行之前
     * 需要在 MANIFEST.MF 文件指定Premain-Class属性，这样jvm会自动执行当前方法，此属性是类名，不是文件名或路径
     * 如何把参数打包到MANIFEST.MF 内，可以参考maven打包jar配置
     * 主要用于在JVM启动时开启代理，如果需要运行时开启代理则使用agentmain方法
     * 启动方式：java -javaagent:xxxx.jar [= 传入 premain 的参数 ] 注：如果有多个参数，则自己采用一种方式分解
     * @param agentArgs
     * @param inst
     * @throws Exception
     */
    public  static  void  premain(String  agentArgs, Instrumentation  inst)  throws Exception {
        run(agentArgs,inst);
    }

    /**
     * 跟premain方法类似，唯一的区别是premain是在jvm启动时进行代理，而agentmain是在运行时代理
     * 本方法不能使用命令行的形式开启，而是需要通过jvm tools.jar提供的VirtualMachine类的attach方法进行运行时绑定到指定的jvm实例
     * 因为attach需要指定某个jvm实例的pid，所以需要使用jps获取代理的pid，然后再执行main方法时显式的传入pid
     * 传入main方法参数为：pid javaagent.jar 具体查看com.jvm.instrument.test.MonitorAssign 实现方式
     * 需要在 MANIFEST.MF 文件指定Agent-Class属性，jvm在运行时会运行此方法，此属性是类名，不是文件名或路径
     * 在执行时，可能会出现no provided错误，这个是因为jre本身未提供tools.jar，并且需要依赖java其它的jar，
     * 所以显式传入classpath时指定tools.jar和jre提供的所有jar
     * 此方法只有是jvm启动后只运行一次
     * @param agentArgs
     * @param inst
     * @throws Exception
     */
    public  static  void  agentmain(String  agentArgs, Instrumentation  inst)  throws Exception {
        run(agentArgs,inst);
    }

    /**
     * 定时的监控java类是否被修改活着打印日志
     * 这里有两个很重要的配置 Can-Retransform-Classes Can-Redefine-Classes
     * Can-Redefine-Classes 用于开启是否可以修改已经在内存中存在的类，如可以监控某个类，每次都是获取最新的class字节码，
     * 保证只要类发生改变则可以立即生效，实现热部署。使用方法是：获取到类的字节码后，显式执行Instrumentation类的redefineClasses方法
     *
     * Can-Retransform-Classes 用于开启是否可以修改内存的字节码，如使用字节码技术动态在某个方法执行前和执行后插入代码
     * 使用方法是：实现ClassFileTransformer类，再将这个类加入到Instrumentation监控范围，调用addTransformer(transformer,true)
     * 一定要显式传入true，不然默认是false，否则达不到预期的效果
     *
     * 注意：因为redefineClasses方法是重新加载整个类，所以就算未开启Can-Retransform-Classes ，发现也可以实现动态插入代码
     *
     * 此方法只有执行代理时执行一次
     * @param agentArgs
     * @param inst
     */
    private static void run(String  agentArgs, Instrumentation  inst) {
        ClassFileTransformer  transformer = new ClassTransform(inst);
       // ClassFileTransformer  transformer = new ClassWriter(inst);
        inst.addTransformer(transformer,true);
        System.out.println("是否支持类的重定义："+inst.isRedefineClassesSupported());
        System.out.println("是否支持类转换："+inst.isRetransformClassesSupported());
        Timer  timer = new  Timer();
        timer.schedule(new ReloadTask(inst),10000,30000);
    }
}
