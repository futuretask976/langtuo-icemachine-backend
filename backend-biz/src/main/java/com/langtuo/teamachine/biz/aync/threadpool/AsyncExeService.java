package com.langtuo.teamachine.biz.aync.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AsyncExeService {
    /**
     * 线程池常量
     */
    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 200;
    private static final long KEEP_ALIVE_TIME = 10l;
    private static final int AWAIT_TERMINATION_TIMEOUT = 5;

    /**
     * 线程池
     */
    private static ExecutorService executorService = null;

    public static ExecutorService getExecutorService() {
        if (executorService == null) {
            synchronized (AsyncExeService.class) {
                if (executorService == null) {
                    executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                            TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
                }
            }
        }
        return executorService;
    }

    public static void destroy() {
        log.debug("asyncExeService|onDestroy|entering");
        if (executorService != null) {
            try {
                log.debug("asyncExeService|onDestroy|beginning");
                // 启动关闭流程
                executorService.shutdown();
                if (!executorService.awaitTermination(AWAIT_TERMINATION_TIMEOUT, TimeUnit.SECONDS)) {
                    // 超时则强制关闭
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                log.error("awaitTermination|fatal|e=" + e.getMessage(), e);
                // 重新中断线程
                Thread.currentThread().interrupt();
            }
        }
        log.debug("onDestroy|exiting");
    }
}
