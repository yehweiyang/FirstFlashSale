package com.weiyang.util;

/**
 * 描述: Twitter的分散式自增ID雪花演算法snowflake (Java版)
 **/
public class SnowFlake {
    /**
     * 起始的時間戳記
     */
    private final static long START_STAMP = 1480166465631L;
    /**
     * 每一部分佔用的位數
     */
    private final static long SEQUENCE_BIT = 12; //序號佔用的位數
    private final static long MACHINE_BIT = 5; //機器標識佔用的位元數
    private final static long DATACENTER_BIT = 5;//資料中心佔用的位數
    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    private final long datacenterId; //資料中心

    private final long machineId; //機器標識
    private long sequence = 0L; //序號
    private long lastStamp = -1L;//上一次時間戳記

    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 產生下一個ID
     *
     * @return long id
     */
    public synchronized long nextId() {
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        if (currStamp == lastStamp) {
            //相同毫秒內，序號自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列數已經達到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒內，序號置為0
            sequence = 0L;
        }
        lastStamp = currStamp;
        return (currStamp - START_STAMP) << TIMESTAMP_LEFT //時間戳記部分
                | datacenterId << DATACENTER_LEFT //資料中心部分
                | machineId << MACHINE_LEFT //機器標識部分
                | sequence; //序號部分
    }

    private long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private long getNewStamp() {

        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 1);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(snowFlake.nextId());
        }
        System.out.println("總耗時：" + (System.currentTimeMillis() - start));
    }
}

