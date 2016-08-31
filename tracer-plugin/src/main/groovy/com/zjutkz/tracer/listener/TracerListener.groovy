package com.zjutkz.tracer.listener

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import org.gradle.util.Clock

/**
 * Created by kangzhe on 16/8/29.
 */
public class TracerListener implements BuildListener,TaskExecutionListener {
    
    private Clock mClock
    private Map<Long,String> mTime2TaskPath = new HashMap<>();
    private def var
    private def specialTaskOnly
    private List<String> specialTasks;

    public TracerListener(def var,def specialTaskOnly,List<String> specialTasks){
        this.var = var
        this.specialTaskOnly = specialTaskOnly
        this.specialTasks = specialTasks
    }

    @Override
    void beforeExecute(Task task) {
        mClock = new Clock()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        if(specialTaskOnly){
            if(specialTasks.contains(task.path)){
                def time = mClock.timeInMs
                mTime2TaskPath.put(time, task.path)
                println(task.path + " costs " + time + "ms")
            }
        }else {
            def time = mClock.timeInMs
            mTime2TaskPath.put(time, task.path)
            println(task.path + " costs " + time + "ms")
        }
    }

    @Override
    void buildFinished(BuildResult result) {
        println("build finished.....")
        println "Time-consuming Task:"
        //fixme cannot use each,use for loop instead
        for (time2TaskPath in mTime2TaskPath) {
            if(time2TaskPath.key >= var){
                println(time2TaskPath.value + " costs " + time2TaskPath.key + "ms")
            }
        }
    }

    @Override
    void buildStarted(Gradle gradle) {
        println("build started.....")
    }

    @Override
    void settingsEvaluated(Settings settings) {

    }

    @Override
    void projectsEvaluated(Gradle gradle) {}

    @Override
    void projectsLoaded(Gradle gradle) {}
}
