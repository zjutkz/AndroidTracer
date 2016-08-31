package com.zjutkz.tracer.utils

import javassist.ByteArrayClassPath
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.bytecode.AnnotationsAttribute
import javassist.bytecode.ClassFile
import javassist.bytecode.ConstPool
import javassist.bytecode.annotation.Annotation

/**
 * Created by kangzhe on 16/8/30.
 */
public class TracerUtils {

    //check clz name
    public static boolean checkClz(List<String> excludeMethods,def javaFile) {
        (!javaFile.absolutePath.contains('R.class') && !javaFile.absolutePath.contains('R$')
                && !javaFile.absolutePath.contains('$AjcClosure') && !excludeMethods.contains(javaFile.absolutePath)
                && !javaFile.absolutePath.contains("BuildConfig.class"))
    }

    //inject code
    public static void processClz(File javaFile){
        if(javaFile.name.endsWith(".class")){

            ClassPool pool = ClassPool.getDefault()

            // create the class
            FileInputStream inputStream = new FileInputStream(javaFile)
            CtClass injectedClz = pool.makeClass(inputStream)

            CtClass annotationClz = pool.makeClass("zjutkz.com.tracerlog.MethodTracer")
            byte[] annotationByte = annotationClz.toBytecode()
            annotationClz.defrost()
            pool.insertClassPath(new ByteArrayClassPath("zjutkz.com.tracerlog.MethodTracer",annotationByte))
            ClassFile annotationFile = annotationClz.getClassFile()
            ConstPool constPool = annotationFile.getConstPool()

            // create the annotation
            AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag)
            Annotation annotation = new Annotation("zjutkz.com.tracerlog.MethodTracer",constPool)
            attr.addAnnotation(annotation)


            CtMethod[] methods = injectedClz.getMethods()

            methods.each { ctMethod ->
                if(!ctMethod.name.contains("aroundBody")){
                    println(ctMethod.name)
                    ctMethod.getMethodInfo().addAttribute(attr)
                }
            }
        }
    }
}
