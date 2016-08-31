package zjutkz.com.tracer;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by kangzhe on 16/8/29.
 */
@Aspect
public class TracerAspectj {

    private static final String METHOD =
            "execution(@zjutkz.com.tracer.MethodTracer * *(..))";

    private static final String CONSTRUCTOR =
            "execution(@zjutkz.com.tracer.MethodTracer *.new(..))";

    @Pointcut(METHOD)
    public void method() {}

    @Pointcut(CONSTRUCTOR)
    public void constructor() {}

    @Around("method() || constructor()")
    public Object proceedPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final Counter counter = new Counter();
        counter.start();
        Object result = joinPoint.proceed();
        counter.stop();

        Log.d(className, buildLog(methodName, counter.getTotalTimeMillis()));

        return result;
    }


    private static String buildLog(String methodName, long methodDuration) {
        StringBuilder log = new StringBuilder();
        log.append("MethodTracer --> ");
        log.append(methodName);
        log.append(" --> ");
        log.append("[");
        log.append(methodDuration);
        log.append("ms");
        log.append("]");

        return log.toString();
    }
}
