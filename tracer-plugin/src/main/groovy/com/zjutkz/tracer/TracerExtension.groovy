package com.zjutkz.tracer;

/**
 * Created by kangzhe on 16/8/29.
 */
public class TracerExtension {
    //插件开关
    public def enable = true

    //方法监测
    public def methodTraceEnable = true

    //Method time-consuming阀值
    public def varToTimeConsumingMethod = 100

    //是否只监测特定方法
    public def specialMethodOnly = false

    //不需要监测的方法列表
    public List<String> excludeMethods = new ArrayList<>()

    //task监测
    public def taskTraceEnable = true

    //是否只监测特定的task
    public def specialTaskOnly = false

    //特定的task列表
    public List<String> specialTasks = new ArrayList<>()

    //Task time-consuming阀值
    public def varToTimeConsumingTask = 50
}
