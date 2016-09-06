package com.zjutkz.tracer

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.zjutkz.tracer.listener.TracerListener
import com.zjutkz.tracer.transform.AnnotationTransform
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
/**
 * Created by kangzhe on 16/8/29.
 */
public class Tracer implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        def variants

        //ensure variant
        if (project.plugins.withType(AppPlugin)) {
            variants = project.android.applicationVariants
        } else if(project.plugins.withType(LibraryPlugin)) {
            variants = project.android.libraryVariants
        } else {
            println("module must be an android application or library!")
            return
        }

        // add dependencies
        project.dependencies {
            debugCompile 'org.aspectj:aspectjrt:1.8.6'
            debugCompile 'com.zjutkz:tracer:1.0.0'
        }

        //get extension
        project.extensions.create('tracer', TracerExtension)

        variants.all { variant ->

            if ("debug".equals(variant.name)) {
                if (!project.tracer.enable) {
                    println("disabled,skip!")
                    return;
                }
                def methodTrace = project.tracer.methodTraceEnable
                def taskTrace = project.tracer.taskTraceEnable


                if (methodTrace) { //add aspectj config
                    configAspectJ(project, variant)
                } else {
                    println("method trace is disable!")
                }

                if (taskTrace) {
                    def var = project.tracer.varToTimeConsumingTask
                    def specialTaskOnly = project.tracer.specialTaskOnly
                    List<String> specialTasks = project.tracer.specialTasks
                    project.gradle.addListener(new TracerListener(var, specialTaskOnly, specialTasks))
                } else {
                    println("task trace is disable!")
                }

                //todo complete this function
                //fixme why cannot register transform in variants.all ?
                /*def specialMethodOnly = project.tracer.specialMethodOnly;
                if(specialMethodOnly){
                    //just tracing the defined methods
                    println("just tracing the given methods,skip!")
                }else {
                    //add annotation
                    addAnnotation(project)
                }*/
            }
        }
    }

    //add annotation
    synchronized void addAnnotation(Project project) {
        def isApp = project.plugins.hasPlugin(AppPlugin)

        if (isApp) {
            List<String> excludeMethods = project.tracer.excludeMethods;

            //register transform
            def android = project.extensions.getByType(AppExtension)
            def transform = new AnnotationTransform(project,excludeMethods)
            android.registerTransform(transform)
        }
    }

    //config aspectj
    synchronized void configAspectJ(Project project, def variant) {
        final def log = project.logger

        JavaCompile javaCompile = variant.javaCompile
        javaCompile.doLast {
            String[] args = [
                    "-showWeaveInfo",
                    "-1.5",
                    "-inpath", javaCompile.destinationDir.toString(),
                    "-aspectpath", javaCompile.classpath.asPath,
                    "-d", javaCompile.destinationDir.toString(),
                    "-classpath", javaCompile.classpath.asPath,
                    "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)
            ]

            MessageHandler handler = new MessageHandler(true);
            new Main().run(args, handler);
            for (IMessage message : handler.getMessages(null, true)) {
                switch (message.getKind()) {
                    case IMessage.ABORT:
                    case IMessage.ERROR:
                    case IMessage.FAIL:
                        println message.message, message.thrown
                        break;
                    case IMessage.WARNING:
                        log.warn message.message, message.thrown
                        break;
                    case IMessage.INFO:
                        log.info message.message, message.thrown
                        break;
                    case IMessage.DEBUG:
                        log.debug message.message, message.thrown
                        break;
                }
            }
        }
    }
}


