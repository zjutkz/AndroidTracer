#AndroidTracer

A gradle plugin to trace Android methods and gradle tasks execution.

This plugin will output the Android methods and gradle tasks execution time in logcat.Developers can figure out the reason why your apps are building and running so slow.



#Download


```groovy
buildscript {
  dependencies {
    repositories {
      mavenCentral()

      // NOTE: This is only needed when developing the plugin!
      mavenLocal()
    }

    classpath 'com.zjutkz.tracer-plugin:1.0.2'
  }
}
```



#Usage

####enabled

This plugin will just working in debug build type.

You can use enable property to close this plugin.

```groovy
tracer {
    enable = false
}
```

####Method tracing

using Java annotation @MethodTracer to tag the method you want to know the execution time.

```java
@MethodTracer
private void recursion() {
    if(recursionCount > 10){
        return;
    }

    Log.d(TAG, "execute recursion method: " + ++recursionCount);
    recursion();
}

@MethodTracer
private void delay() {
    try {
        Thread.sleep(100);
        Log.d(TAG, "delay in thread!");
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

The output you can see in the logcat.

```
08-31 16:45:53.973 28235-28235/zjutkz.com.sample D/MainActivity: delay in thread!
08-31 16:45:53.973 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> delay --> [100ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 1
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 2
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 3
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 4
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 5
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 6
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 7
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 8
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 9
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 10
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: execute recursion method: 11
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [0ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [0ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [0ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [1ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [1ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [1ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [2ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [2ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [3ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [4ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [4ms]
08-31 16:45:54.833 28235-28235/zjutkz.com.sample D/MainActivity: MethodTracer --> recursion --> [5ms]
```

####Task tracing

You can config it in your build.gradle file.

```groovy
tracer {
    taskTraceEnable boolean//enable task tracing
    specialTaskOnly boolean //just tracing the particular tasks
    specialTasks    List<String> //The particular tasks set
    varToTimeConsumingTask long //time consuming tasks threshold
}
```

output in Gradle Console:

```
    Incremental java compilation is an incubating feature.
    Configuration(s) specified but the install task does not exist in project :tracer.
    :sample:preBuild UP-TO-DATE
    :sample:preBuild costs 1ms
    :sample:preDebugBuild UP-TO-DATE
    :sample:preDebugBuild costs 0ms
    :sample:checkDebugManifest
    :sample:checkDebugManifest costs 3ms
    :sample:preReleaseBuild UP-TO-DATE
    :sample:preReleaseBuild costs 0ms
    :tracer:preBuild UP-TO-DATE
    :tracer:preBuild costs 1ms
    :tracer:preReleaseBuild UP-TO-DATE
    :tracer:preReleaseBuild costs 0ms
    :tracer:compileReleaseNdk UP-TO-DATE
    :tracer:compileReleaseNdk costs 1ms
    :tracer:compileLint
    :tracer:compileLint costs 1ms
    :tracer:copyReleaseLint UP-TO-DATE
    :tracer:copyReleaseLint costs 1ms
    :tracer:checkReleaseManifest
    :tracer:checkReleaseManifest costs 1ms
    :tracer:preDebugAndroidTestBuild UP-TO-DATE
    :tracer:preDebugAndroidTestBuild costs 1ms
    :tracer:preDebugBuild UP-TO-DATE
    :tracer:preDebugBuild costs 0ms
    :tracer:preDebugUnitTestBuild UP-TO-DATE
    :tracer:preDebugUnitTestBuild costs 0ms
    :tracer:preReleaseUnitTestBuild UP-TO-DATE
    :tracer:preReleaseUnitTestBuild costs 0ms
    :tracer:prepareComAndroidSupportAnimatedVectorDrawable2321Library UP-TO-DATE
    :tracer:prepareComAndroidSupportAnimatedVectorDrawable2321Library costs 3ms
    :tracer:prepareComAndroidSupportAppcompatV72321Library UP-TO-DATE
    :tracer:prepareComAndroidSupportAppcompatV72321Library costs 16ms
    :tracer:prepareComAndroidSupportSupportV42321Library UP-TO-DATE
    :tracer:prepareComAndroidSupportSupportV42321Library costs 2ms
    :tracer:prepareComAndroidSupportSupportVectorDrawable2321Library UP-TO-DATE
    :tracer:prepareComAndroidSupportSupportVectorDrawable2321Library costs 3ms
    :tracer:prepareReleaseDependencies
    :tracer:prepareReleaseDependencies costs 1ms
    :tracer:compileReleaseAidl UP-TO-DATE
    :tracer:compileReleaseAidl costs 6ms
    :tracer:compileReleaseRenderscript UP-TO-DATE
    :tracer:compileReleaseRenderscript costs 2ms
    :tracer:generateReleaseBuildConfig UP-TO-DATE
    :tracer:generateReleaseBuildConfig costs 1ms
    :tracer:mergeReleaseShaders UP-TO-DATE
    :tracer:mergeReleaseShaders costs 1ms
    :tracer:compileReleaseShaders UP-TO-DATE
    :tracer:compileReleaseShaders costs 1ms
    :tracer:generateReleaseAssets UP-TO-DATE
    :tracer:generateReleaseAssets costs 0ms
    :tracer:mergeReleaseAssets UP-TO-DATE
    :tracer:mergeReleaseAssets costs 3ms
    :tracer:generateReleaseResValues UP-TO-DATE
    :tracer:generateReleaseResValues costs 2ms
    :tracer:generateReleaseResources UP-TO-DATE
    :tracer:generateReleaseResources costs 0ms
    :tracer:mergeReleaseResources UP-TO-DATE
    :tracer:mergeReleaseResources costs 39ms
    :tracer:processReleaseManifest UP-TO-DATE
    :tracer:processReleaseManifest costs 7ms
    :tracer:processReleaseResources UP-TO-DATE
    :tracer:processReleaseResources costs 7ms
    :tracer:generateReleaseSources UP-TO-DATE
    :tracer:generateReleaseSources costs 0ms
    :tracer:incrementalReleaseJavaCompilationSafeguard UP-TO-DATE
    :tracer:incrementalReleaseJavaCompilationSafeguard costs 2ms
    :tracer:compileReleaseJavaWithJavac UP-TO-DATE
    :tracer:compileReleaseJavaWithJavac costs 2ms
    :tracer:extractReleaseAnnotations UP-TO-DATE
    :tracer:extractReleaseAnnotations costs 1ms
    :tracer:mergeReleaseProguardFiles UP-TO-DATE
    :tracer:mergeReleaseProguardFiles costs 1ms
    :tracer:packageReleaseRenderscript UP-TO-DATE
    :tracer:packageReleaseRenderscript costs 1ms
    :tracer:packageReleaseResources UP-TO-DATE
    :tracer:packageReleaseResources costs 5ms
    :tracer:processReleaseJavaRes UP-TO-DATE
    :tracer:processReleaseJavaRes costs 0ms
    :tracer:transformResourcesWithMergeJavaResForRelease UP-TO-DATE
    :tracer:transformResourcesWithMergeJavaResForRelease costs 7ms
    :tracer:transformClassesAndResourcesWithSyncLibJarsForRelease UP-TO-DATE
    :tracer:transformClassesAndResourcesWithSyncLibJarsForRelease costs 3ms
    :tracer:mergeReleaseJniLibFolders UP-TO-DATE
    :tracer:mergeReleaseJniLibFolders costs 1ms
    :tracer:transformNative_libsWithMergeJniLibsForRelease UP-TO-DATE
    :tracer:transformNative_libsWithMergeJniLibsForRelease costs 1ms
    :tracer:transformNative_libsWithSyncJniLibsForRelease UP-TO-DATE
    :tracer:transformNative_libsWithSyncJniLibsForRelease costs 1ms
    :tracer:bundleRelease UP-TO-DATE
    :tracer:bundleRelease costs 1ms
    :sample:prepareComAndroidSupportAnimatedVectorDrawable2321Library UP-TO-DATE
    :sample:prepareComAndroidSupportAnimatedVectorDrawable2321Library costs 1ms
    :sample:prepareComAndroidSupportAppcompatV72321Library UP-TO-DATE
    :sample:prepareComAndroidSupportAppcompatV72321Library costs 7ms
    :sample:prepareComAndroidSupportSupportV42321Library UP-TO-DATE
    :sample:prepareComAndroidSupportSupportV42321Library costs 1ms
    :sample:prepareComAndroidSupportSupportVectorDrawable2321Library UP-TO-DATE
    :sample:prepareComAndroidSupportSupportVectorDrawable2321Library costs 1ms
    :sample:prepareZjutkzTracer100Library UP-TO-DATE
    :sample:prepareZjutkzTracer100Library costs 2ms
    :sample:prepareDebugDependencies
    :sample:prepareDebugDependencies costs 1ms
    :sample:compileDebugAidl UP-TO-DATE
    :sample:compileDebugAidl costs 5ms
    :sample:compileDebugRenderscript UP-TO-DATE
    :sample:compileDebugRenderscript costs 4ms
    :sample:generateDebugBuildConfig UP-TO-DATE
    :sample:generateDebugBuildConfig costs 50ms
    :sample:mergeDebugShaders UP-TO-DATE
    :sample:mergeDebugShaders costs 4ms
    :sample:compileDebugShaders UP-TO-DATE
    :sample:compileDebugShaders costs 3ms
    :sample:generateDebugAssets UP-TO-DATE
    :sample:generateDebugAssets costs 0ms
    :sample:mergeDebugAssets UP-TO-DATE
    :sample:mergeDebugAssets costs 5ms
    :sample:generateDebugResValues UP-TO-DATE
    :sample:generateDebugResValues costs 2ms
    :sample:generateDebugResources UP-TO-DATE
    :sample:generateDebugResources costs 0ms
    :sample:mergeDebugResources UP-TO-DATE
    :sample:mergeDebugResources costs 43ms
    :sample:processDebugManifest UP-TO-DATE
    :sample:processDebugManifest costs 2ms
    :sample:processDebugResources UP-TO-DATE
    :sample:processDebugResources costs 26ms
    :sample:generateDebugSources UP-TO-DATE
    :sample:generateDebugSources costs 1ms
    :sample:preDebugAndroidTestBuild UP-TO-DATE
    :sample:preDebugAndroidTestBuild costs 0ms
    :sample:prepareDebugAndroidTestDependencies
    :sample:prepareDebugAndroidTestDependencies costs 0ms
    :sample:compileDebugAndroidTestAidl UP-TO-DATE
    :sample:compileDebugAndroidTestAidl costs 3ms
    :sample:processDebugAndroidTestManifest UP-TO-DATE
    :sample:processDebugAndroidTestManifest costs 1ms
    :sample:compileDebugAndroidTestRenderscript UP-TO-DATE
    :sample:compileDebugAndroidTestRenderscript costs 1ms
    :sample:generateDebugAndroidTestBuildConfig UP-TO-DATE
    :sample:generateDebugAndroidTestBuildConfig costs 1ms
    :sample:mergeDebugAndroidTestShaders UP-TO-DATE
    :sample:mergeDebugAndroidTestShaders costs 2ms
    :sample:compileDebugAndroidTestShaders UP-TO-DATE
    :sample:compileDebugAndroidTestShaders costs 0ms
    :sample:generateDebugAndroidTestAssets UP-TO-DATE
    :sample:generateDebugAndroidTestAssets costs 0ms
    :sample:mergeDebugAndroidTestAssets UP-TO-DATE
    :sample:mergeDebugAndroidTestAssets costs 1ms
    :sample:generateDebugAndroidTestResValues UP-TO-DATE
    :sample:generateDebugAndroidTestResValues costs 1ms
    :sample:generateDebugAndroidTestResources UP-TO-DATE
    :sample:generateDebugAndroidTestResources costs 0ms
    :sample:mergeDebugAndroidTestResources UP-TO-DATE
    :sample:mergeDebugAndroidTestResources costs 8ms
    :sample:processDebugAndroidTestResources UP-TO-DATE
    :sample:processDebugAndroidTestResources costs 2ms
    :sample:generateDebugAndroidTestSources UP-TO-DATE
    :sample:generateDebugAndroidTestSources costs 0ms
    :sample:mockableAndroidJar UP-TO-DATE
    :sample:mockableAndroidJar costs 3ms
    :sample:preDebugUnitTestBuild UP-TO-DATE
    :sample:preDebugUnitTestBuild costs 2ms
    :sample:prepareDebugUnitTestDependencies
    :sample:prepareDebugUnitTestDependencies costs 1ms
    :tracer:checkDebugManifest
    :tracer:checkDebugManifest costs 2ms
    :tracer:prepareDebugDependencies
    :tracer:prepareDebugDependencies costs 2ms
    :tracer:compileDebugAidl UP-TO-DATE
    :tracer:compileDebugAidl costs 5ms
    :tracer:compileDebugRenderscript UP-TO-DATE
    :tracer:compileDebugRenderscript costs 3ms
    :tracer:generateDebugBuildConfig UP-TO-DATE
    :tracer:generateDebugBuildConfig costs 4ms
    :tracer:mergeDebugShaders UP-TO-DATE
    :tracer:mergeDebugShaders costs 3ms
    :tracer:compileDebugShaders UP-TO-DATE
    :tracer:compileDebugShaders costs 2ms
    :tracer:generateDebugAssets UP-TO-DATE
    :tracer:generateDebugAssets costs 0ms
    :tracer:mergeDebugAssets UP-TO-DATE
    :tracer:mergeDebugAssets costs 1ms
    :tracer:generateDebugResValues UP-TO-DATE
    :tracer:generateDebugResValues costs 1ms
    :tracer:generateDebugResources UP-TO-DATE
    :tracer:generateDebugResources costs 0ms
    :tracer:mergeDebugResources UP-TO-DATE
    :tracer:mergeDebugResources costs 53ms
    :tracer:processDebugManifest UP-TO-DATE
    :tracer:processDebugManifest costs 3ms
    :tracer:processDebugResources UP-TO-DATE
    :tracer:processDebugResources costs 20ms
    :tracer:generateDebugSources UP-TO-DATE
    :tracer:generateDebugSources costs 0ms
    :tracer:mockableAndroidJar UP-TO-DATE
    :tracer:mockableAndroidJar costs 1ms
    :tracer:prepareDebugUnitTestDependencies
    :tracer:prepareDebugUnitTestDependencies costs 0ms
    :tracer:prepareDebugAndroidTestDependencies
    :tracer:prepareDebugAndroidTestDependencies costs 0ms
    :tracer:compileDebugAndroidTestAidl UP-TO-DATE
    :tracer:compileDebugAndroidTestAidl costs 3ms
    :tracer:compileDebugNdk UP-TO-DATE
    :tracer:compileDebugNdk costs 1ms
    :tracer:copyDebugLint UP-TO-DATE
    :tracer:copyDebugLint costs 0ms
    :tracer:incrementalDebugJavaCompilationSafeguard UP-TO-DATE
    :tracer:incrementalDebugJavaCompilationSafeguard costs 5ms
    :tracer:compileDebugJavaWithJavac UP-TO-DATE
    :tracer:compileDebugJavaWithJavac costs 7ms
    :tracer:extractDebugAnnotations UP-TO-DATE
    :tracer:extractDebugAnnotations costs 8ms
    :tracer:mergeDebugProguardFiles UP-TO-DATE
    :tracer:mergeDebugProguardFiles costs 1ms
    :tracer:packageDebugRenderscript UP-TO-DATE
    :tracer:packageDebugRenderscript costs 1ms
    :tracer:packageDebugResources UP-TO-DATE
    :tracer:packageDebugResources costs 1ms
    :tracer:processDebugJavaRes UP-TO-DATE
    :tracer:processDebugJavaRes costs 0ms
    :tracer:transformResourcesWithMergeJavaResForDebug UP-TO-DATE
    :tracer:transformResourcesWithMergeJavaResForDebug costs 0ms
    :tracer:transformClassesAndResourcesWithSyncLibJarsForDebug UP-TO-DATE
    :tracer:transformClassesAndResourcesWithSyncLibJarsForDebug costs 2ms
    :tracer:mergeDebugJniLibFolders UP-TO-DATE
    :tracer:mergeDebugJniLibFolders costs 1ms
    :tracer:transformNative_libsWithMergeJniLibsForDebug UP-TO-DATE
    :tracer:transformNative_libsWithMergeJniLibsForDebug costs 1ms
    :tracer:transformNative_libsWithSyncJniLibsForDebug UP-TO-DATE
    :tracer:transformNative_libsWithSyncJniLibsForDebug costs 1ms
    :tracer:bundleDebug UP-TO-DATE
    :tracer:bundleDebug costs 2ms
    :tracer:compileDebugSources UP-TO-DATE
    :tracer:compileDebugSources costs 0ms
    :tracer:assembleDebug UP-TO-DATE
    :tracer:assembleDebug costs 0ms
    :tracer:processDebugAndroidTestManifest UP-TO-DATE
    :tracer:processDebugAndroidTestManifest costs 2ms
    :tracer:compileDebugAndroidTestRenderscript UP-TO-DATE
    :tracer:compileDebugAndroidTestRenderscript costs 5ms
    :tracer:generateDebugAndroidTestBuildConfig UP-TO-DATE
    :tracer:generateDebugAndroidTestBuildConfig costs 3ms
    :tracer:mergeDebugAndroidTestShaders UP-TO-DATE
    :tracer:mergeDebugAndroidTestShaders costs 3ms
    :tracer:compileDebugAndroidTestShaders UP-TO-DATE
    :tracer:compileDebugAndroidTestShaders costs 6ms
    :tracer:generateDebugAndroidTestAssets UP-TO-DATE
    :tracer:generateDebugAndroidTestAssets costs 0ms
    :tracer:mergeDebugAndroidTestAssets UP-TO-DATE
    :tracer:mergeDebugAndroidTestAssets costs 3ms
    :tracer:generateDebugAndroidTestResValues UP-TO-DATE
    :tracer:generateDebugAndroidTestResValues costs 0ms
    :tracer:generateDebugAndroidTestResources UP-TO-DATE
    :tracer:generateDebugAndroidTestResources costs 0ms
    :tracer:mergeDebugAndroidTestResources UP-TO-DATE
    :tracer:mergeDebugAndroidTestResources costs 66ms
    :tracer:processDebugAndroidTestResources UP-TO-DATE
    :tracer:processDebugAndroidTestResources costs 38ms
    :tracer:generateDebugAndroidTestSources UP-TO-DATE
    :tracer:generateDebugAndroidTestSources costs 0ms

    BUILD SUCCESSFUL

    Total time: 1.549 secs
    build finished.....
    Time-consuming Task:
    :tracer:mergeDebugAndroidTestResources costs 66ms
    :sample:generateDebugBuildConfig costs 50ms
    :tracer:mergeDebugResources costs 53ms
```



#License

```
Copyright 2016 zjutkz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

