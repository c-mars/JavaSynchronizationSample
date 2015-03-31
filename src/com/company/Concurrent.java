package com.company;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.concurrent.*;

/**
 * Created by Constantine Mars on 3/31/15.
 */
public class Concurrent {
    public static void main(String[] args) {
        new Thread(new Consumer()).start();
    }

    private static class Producer implements Runnable {

        FutureResult result;

        public Producer(FutureResult result) {
            this.result = result;
        }

        @Override
        public void run() {
            System.out.println("p.running");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("p.done");
            synchronized (result) {
                result.setResult(new Result(5, "after done"));
                result.notifyAll();
                System.out.println("p.notifyAll");
            }
        }
    }

    private static class Consumer implements Runnable {

        @Override
        public void run() {
            System.out.println("c.running");
            FutureResult futureResult = new FutureResult();
            new Thread(new Producer(futureResult)).start();
            synchronized (futureResult) {
                try {
                    futureResult.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("c.result: " + futureResult.getResult().getCode() + ":" + futureResult.getResult().getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class FutureResult {
        public void setResult(Result result) {
            this.result = result;
        }

        Result result = new Result(0, "absent");

        public Result getResult() {
            return result;
        }
    }

    private static class Result {
        private int code;

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }

        private String msg;

        public Result(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
