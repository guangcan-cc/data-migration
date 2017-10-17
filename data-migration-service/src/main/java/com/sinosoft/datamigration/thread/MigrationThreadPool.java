package com.sinosoft.datamigration.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Elvis on 2017/10/16.
 */
public class MigrationThreadPool {

    private static final int THREAD_NUMBER = 5;

    private static ExecutorService executorService = null;

    public static void execute(Runnable runnable){
        if(executorService == null){
            executorService = Executors.newFixedThreadPool(THREAD_NUMBER);
        }
        executorService.execute(runnable);
    }
}
