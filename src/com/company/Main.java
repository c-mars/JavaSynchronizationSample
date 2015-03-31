package com.company;

import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
//        System.out.println("start");
//        try {
//        Thread t = new Thread(new R());
//            t.start();
//        System.out.println("immediately");
//
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("joined");


        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("sleep");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("wake");
                return "result";
            }
        });
        System.out.println("submitted");
        executor.shutdown();
        System.out.println("shutdown");
//        try {
//            System.out.println("result: "+future.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//            System.out.println("exception");
//        }
        System.out.println("finished");
    }

    private static class R implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("sleep");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("wake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
