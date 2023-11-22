/**
 * Copyright 2015 the original author or Linlan authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.linlan.commons.core;

import io.linlan.commons.core.abs.ThreadPool;

import java.util.concurrent.*;

/**
 * Thread class to provide utils for use
 * Filename:ThreadUtils.java
 * Desc:ThreadUtils is use concurrent mode to provide thread,
 * the methods include new, execute, sleep etc.
 *
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * Createtime 2017/7/11 16:38
 *
 * @version 1.0
 * @since 1.0
 *
 */
public final class ThreadUtils {
    /**
     * constructor of self
     */
    private ThreadUtils() {

    }

    /**
     * execute in the thread pool
     * 直接在公共线程池中执行线程
     *
     * @param task Runnable
     */
    public static void execute(Runnable task) {
        try {
            ThreadPool.execute(task);
        } catch (Exception e) {
            throw new CoreException("Exception when running task!", e);
        }
    }

    /**
     * new a single executor of thread pool
     *
     * @return ExecutorService
     */
    public static ExecutorService newSingleExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * new a executor of thread pool use default config
     *
     * @return ExecutorService
     */
    public static ExecutorService newExecutor() {
        return Executors.newCachedThreadPool();
    }

    /**
     * new a executor of thread pool, the pool size is input
     *
     * @param threadSize the concurrent thread size
     * @return ExecutorService
     */
    public static ExecutorService newExecutor(int threadSize) {
        return Executors.newFixedThreadPool(threadSize);
    }

    /**
     * 获得一个新的线程池<br>
     * 如果maximumPoolSize =》 corePoolSize，在没有新任务加入的情况下，多出的线程将最多保留60s
     *
     * @param corePoolSize 初始线程池大小
     * @param maximumPoolSize 最大线程池大小
     * @return {@link ThreadPoolExecutor}
     */
    public static ThreadPoolExecutor newExecutor(int corePoolSize, int maximumPoolSize) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, //
                60L, TimeUnit.SECONDS, //
                new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 获得一个新的线程池<br>
     * 传入阻塞系数，线程池的大小计算公式为：CPU可用核心数 / (1 - 阻塞因子)<br>
     * Blocking Coefficient(阻塞系数) = 阻塞时间／（阻塞时间+使用CPU的时间）<br>
     * 计算密集型任务的阻塞系数为0，而IO密集型任务的阻塞系数则接近于1。
     *
     * see: http://blog.csdn.net/partner4java/article/details/9417663
     *
     * @param blockingCoefficient 阻塞系数，阻塞因子介于0~1之间的数，阻塞因子越大，线程池中的线程数越多。
     * @return {@link ThreadPoolExecutor}
     * @since 3.0.6
     */
    public static ThreadPoolExecutor newExecutorByBlockingCoefficient(float blockingCoefficient) {
        if(blockingCoefficient > 1 || blockingCoefficient <=0){
            throw new IllegalArgumentException("[blockingCoefficient] must between 0 and 1, or equals 0.");
        }

        // 最佳的线程数 = CPU可用核心数 / (1 - 阻塞系数)
        int poolSize = (int)(Runtime.getRuntime().availableProcessors() / (1-blockingCoefficient));

        return new ThreadPoolExecutor(poolSize, poolSize, //
                0L, TimeUnit.MILLISECONDS, //
                new LinkedBlockingQueue<Runnable>());
    }

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task. The Future's {@code get} method will
     * return {@code null} upon <em>successful</em> completion.
     * 执行有返回值的异步方法<br>
     * Future代表一个异步执行的操作，通过get()方法可以获得操作的结果，如果异步操作还没有完成，则，get()会使当前线程阻塞
     *
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    public static Future<?> asynExecute(Runnable task) {
        return ThreadPool.submit(task);
    }

    /**
     * 执行异步方法
     *
     * @param runnable 需要执行的方法体
     * @param isDamon 是否守护线程。守护线程会在主线程结束后自动结束
     * @return 执行的方法体
     */
    public static Runnable asynExecute(final Runnable runnable, boolean isDamon) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                runnable.run();
            }
        };
        thread.setDaemon(isDamon);
        thread.start();

        return runnable;
    }

    /**
     * 执行有返回值的异步方法<br>
     * Future代表一个异步执行的操作，通过get()方法可以获得操作的结果，如果异步操作还没有完成，则，get()会使当前线程阻塞
     *
     * @param <T> 回调对象类型
     * @param task {@link Callable}
     * @return Future
     */
    public static <T> Future<T> asynExecute(Callable<T> task) {
        return ThreadPool.submit(task);
    }

    /**
     * sleep the current thread in milli seconds
     *
     * @param millis sleep millis seconds
     * @return if interrupt false, else true
     */
    public static boolean sleep(Number millis) {
        if (millis == null) {
            return true;
        }

        try {
            Thread.sleep(millis.longValue());
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    /**
     * 挂起当前线程
     *
     * @param timeout 挂起的时长
     * @param timeUnit 时长单位
     * @return 被中断返回false，否则true
     */
    public static boolean sleep(Number timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout.longValue());
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    /**
     * 考虑{@link Thread#sleep(long)}方法有可能时间不足给定毫秒数，此方法保证sleep时间不小于给定的毫秒数
     * @see ThreadUtils#sleep(Number)
     * @param millis 给定的sleep时间
     * @return 被中断返回false，否则true
     */
    public static boolean safeSleep(Number millis){
        long millisLong = millis.longValue();
        long done = 0;
        while(done < millisLong){
            long before = System.currentTimeMillis();
            if(false == sleep(millisLong - done)){
                return false;
            }
            long after = System.currentTimeMillis();
            done += (after - before);
        }
        return true;
    }


    /**
     * new a thread local if is inheritable
     *
     * @param <T> the T hold object
     * @param isInheritable if heritable from parent
     * @return ThreadLocal
     */
    public static <T> ThreadLocal<T> newThreadLocal(boolean isInheritable){
        if(isInheritable){
            return new InheritableThreadLocal<>();
        }else{
            return new ThreadLocal<>();
        }
    }

    /**
     * interrupt the thread, if join then wait to die, else interrupt
     * if the thread is killed, {@link InterruptedException}
     * @param thread thread
     * @param isWaiting if waiting to end
     */
    public static void interrupt(Thread thread, boolean isWaiting){
        if(null != thread && false == thread.isInterrupted()){
            thread.interrupt();
            if(isWaiting){
                waitForDie(thread);
            }
        }
    }

    /**
     * wait the thread to end, {@link Thread#join()}, ignore {@link InterruptedException}
     *
     * @param thread thread
     */
    public static void waitForDie(Thread thread) {
        boolean dead = false;
        do {
            try {
                thread.join();
                dead = true;
            } catch (InterruptedException e) {

            }
        } while (!dead);
    }

    /**
     * get the main thread of JVM threads, return the first thread<br>
     *
     * @return Thread
     */
    public static Thread mainThread(){
        for(Thread thread: getThreads()){
            if(thread.getId() == 1){
                return thread;
            }
        }
        return null;
    }

    /**
     * get the JVM all threads in the same thread group<br>
     * 获取JVM中与当前线程同组的所有线程<br>
     * @return Thread[]     线程对象数组
     */
    public static Thread[] getThreads(){
        return getThreads(Thread.currentThread().getThreadGroup().getParent());
    }

    /**
     * get the JVM all threads in the input thread group<br>
     * use arraycopy to avoid exception<br>
     * 获取JVM中与当前线程同组的所有线程<br>
     * 使用数组二次拷贝方式，防止在线程列表获取过程中线程终止<br>
     *
     * @param group ThreadGroup
     * @return Thread[]
     */
    public static Thread[] getThreads(ThreadGroup group){
        final Thread[] slackList = new Thread[group.activeCount() * 2];
        final int actualSize = group.enumerate(slackList);
        final Thread[] result = new Thread[actualSize];
        System.arraycopy(slackList, 0, result, 0, actualSize);
        return result;
    }

    /**
     * 新建一个CompletionService，调用其submit方法可以异步执行多个任务，最后调用take方法按照完成的顺序获得其结果。<br>
     * 若未完成，则会阻塞
     *
     * @param <T> 回调对象类型
     * @return CompletionService
     */
    public static <T> CompletionService<T> newCompletionService() {
        return new ExecutorCompletionService<>(ThreadPool.getExecutor());
    }

    /**
     * 新建一个CompletionService，调用其submit方法可以异步执行多个任务，最后调用take方法按照完成的顺序获得其结果。<br>
     * 若未完成，则会阻塞
     *
     * @param <T> 回调对象类型
     * @param executor 执行器 {@link ExecutorService}
     * @return CompletionService
     */
    public static <T> CompletionService<T> newCompletionService(ExecutorService executor) {
        return new ExecutorCompletionService<T>(executor);
    }

    /**
     * 新建一个CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
     *
     * @param threadCount 线程数量
     * @return CountDownLatch
     */
    public static CountDownLatch newCountDownLatch(int threadCount) {
        return new CountDownLatch(threadCount);
    }



    /**
     * @return 获得堆栈列表
     */
    public static StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    /**
     * 获得堆栈项
     *
     * @param i 第几个堆栈项
     * @return 堆栈项
     */
    public static StackTraceElement getStackTraceElement(int i) {
        StackTraceElement[] stackTrace = getStackTrace();
        if (i < 0) {
            i += stackTrace.length;
        }
        return stackTrace[i];
    }

    /**
     * 创建本地线程对象
     *
     * @param <T> 持有对象类型
     * @param isInheritable 是否为子线程提供从父线程那里继承的值
     * @return 本地线程
     */
    public static <T> ThreadLocal<T> createThreadLocal(boolean isInheritable){
        if(isInheritable){
            return new InheritableThreadLocal<>();
        }else{
            return new ThreadLocal<>();
        }
    }

    /**
     * 结束线程，调用此方法后，线程将抛出 {@link InterruptedException}异常
     * @param thread 线程
     * @param isJoin 是否等待结束
     */
    public static void interupt(Thread thread, boolean isJoin){
        if(null != thread && false == thread.isInterrupted()){
            thread.interrupt();
            if(isJoin){
                waitForDie(thread);
            }
        }
    }


}
