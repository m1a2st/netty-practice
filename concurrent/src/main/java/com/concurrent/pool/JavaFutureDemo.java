package com.concurrent.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class JavaFutureDemo {
    public static final int SLEEP_GAP = 500;

    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    static class HotWaterJob implements Callable<Boolean> {
        @Override
        public Boolean call() {

            try {
                log.info("洗好水壶");
                log.info("灌上凉水");
                log.info("放在火上");

                //线程睡眠一段时间，代表烧水中
                Thread.sleep(SLEEP_GAP);
                log.info("水开了");

            } catch (InterruptedException e) {
                log.info(" 发生异常被中断.");
                return false;
            }
            log.info(" 运行结束.");
            return true;
        }
    }

    static class WashJob implements Callable<Boolean> {

        @Override
        public Boolean call() {
            try {
                log.info("洗茶壶");
                log.info("洗茶杯");
                log.info("拿茶叶");
                //线程睡眠一段时间，代表清洗中
                Thread.sleep(SLEEP_GAP);
                log.info("洗完了");

            } catch (InterruptedException e) {
                log.info(" 清洗工作 发生异常被中断.");
                return false;
            }
            log.info(" 清洗工作  运行结束.");
            return true;
        }

    }


    public static void main(String[] args) {

        // 異步邏輯
        Callable<Boolean> hJob = new HotWaterJob();
        Callable<Boolean> wJob = new WashJob();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Future<Boolean> hTask = pool.submit(hJob);
        Future<Boolean> wTask = pool.submit(wJob);

        try {
            boolean waterOk = hTask.get();
            boolean cupOk = wTask.get();

            Thread.currentThread().setName("主线程");
            if (waterOk && cupOk) {
                log.info("泡茶喝");
            } else if (!waterOk) {
                log.info("烧水失败，没有茶喝了");
            } else if (!cupOk) {
                log.info("杯子洗不了，没有茶喝了");
            }

        } catch (InterruptedException e) {
            log.info(getCurThreadName() + "发生异常被中断.");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        log.info(getCurThreadName() + " 运行结束.");

        pool.shutdown();
    }
}
