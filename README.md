#AndroidTracer

A gradle plugin to trace Android methods and gradle tasks execution.

This plugin will output the Android methods and gradle tasks execution time in logcat.Developers can figure out the reason why your apps are building and running so slow.



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





