package org.example.university.generator;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Generator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Random RANDOM = new Random();
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    public static class Data {
        private int pk;
        private String varchar1;
        private String varchar2;
        private String varchar3;
        private int int1;
        private int int2;
        private Timestamp timestamp;
        private boolean bool;

        public Data() {
        }

        public int getPk() {
            return pk;
        }

        public void setPk(int pk) {
            this.pk = pk;
        }

        public String getVarchar1() {
            return varchar1;
        }

        public void setVarchar1(String varchar1) {
            this.varchar1 = varchar1;
        }

        public String getVarchar2() {
            return varchar2;
        }

        public void setVarchar2(String varchar2) {
            this.varchar2 = varchar2;
        }

        public String getVarchar3() {
            return varchar3;
        }

        public void setVarchar3(String varchar3) {
            this.varchar3 = varchar3;
        }

        public int getInt1() {
            return int1;
        }

        public void setInt1(int int1) {
            this.int1 = int1;
        }

        public int getInt2() {
            return int2;
        }

        public void setInt2(int int2) {
            this.int2 = int2;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isBool() {
            return bool;
        }

        public void setBool(boolean bool) {
            this.bool = bool;
        }
    }

    public ConcurrentLinkedQueue<Data> generateData(int rows) {
        ConcurrentLinkedQueue<Data> dataList = new ConcurrentLinkedQueue<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < rows; i++) {
            executor.submit(() -> {
                Data data = new Data();
                data.setPk(ID_GENERATOR.getAndIncrement());
                data.setVarchar1(generateRandomString(10));
                data.setVarchar2(generateRandomString(10));
                data.setVarchar3(generateRandomString(10));
                data.setInt1(RANDOM.nextInt());
                data.setInt2(RANDOM.nextInt());
                data.setTimestamp(new Timestamp(System.currentTimeMillis()));
                data.setBool(RANDOM.nextBoolean());
                dataList.add(data);
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return dataList;
    }

    private String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return builder.toString();
    }
}