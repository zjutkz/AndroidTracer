package com.zjutkz.tracer.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * Created by kangzhe on 16/8/30.
 */
public class AnnotationTransform extends Transform{

    private static final NAME = "Annotation"

    Project project

    List<String> excludeMethods

    public AnnotationTransform(Project project, List<String> excludeMethods) {
        this.project = project
        this.excludeMethods = excludeMethods
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        transformInvocation.inputs.each { transformInput ->
            transformInput.directoryInputs.each {directoryInput ->
                directoryInput.file.listFiles().each { file ->
                    if(!file.absolutePath.contains("/android")){
                        traverseFileToAddAnnotation(file)
                    }

                    File dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY);
                    FileUtils.copyDirectory(directoryInput.file, dest);
                }
            }
            transformInput.jarInputs.each { jarInput ->
                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath);
                String destName = jarInput.name;
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4);
                }

                File dest = transformInvocation.getOutputProvider().getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR);

                FileUtils.copyFile(jarInput.file, dest);
            }
        }
    }

    //traverse files to add annotation without exclude methods
    synchronized void traverseFileToAddAnnotation(File file) {
        file.listFiles().each { javaFile ->
            if(javaFile.isDirectory()){
                traverseFileToAddAnnotation(javaFile)
            }else if(TracerUtils.checkClz(excludeMethods,javaFile)){
                TracerUtils.processClz(javaFile)
            }
        }
    }


}
