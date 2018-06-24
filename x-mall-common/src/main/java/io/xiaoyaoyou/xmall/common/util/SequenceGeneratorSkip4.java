/*
 * Copyright © 上海庆谷豆信息科技有限公司.
 */

package io.xiaoyaoyou.xmall.common.util;

/**
 * 生成整数序列，跳过任意一位值为4的整数
 */
public class SequenceGeneratorSkip4 {
    private static long[] SKIP_VALUES = new long[64];
    static {
        long v = 1;
        for (int i = 0; i < 64; i++) {
            SKIP_VALUES[i] = v;
            v *= 10;
        }
    }

    private long current;

    public SequenceGeneratorSkip4() {
        this(1);
    }

    public SequenceGeneratorSkip4(long start) {
        this.current = start;
    }

    public long next() {
        long nextVal = SequenceGeneratorSkip4.next(current);
        if (nextVal != current) {
            current = nextVal + 1;
        } else {
            current++;
        }
        return nextVal;
    }

    /**
     * 计算大于等于n的下一个不包含4的数字，例如：n=1返回1，n=4返回5，n=40返回50
     * @param n
     * @return
     */
    public static long next(long n) {
        //如果数字中包含4，则跳过
        int p = posOf4(n);
        if (p >= 0) {
            n += SKIP_VALUES[p];
        }
        return n;
    }

    /**
     * 查找4所在位置
     * @param value
     * @return
     */
    private static int posOf4(long value) {
        int pos = 0;
        while (value > 0) {
            if (value % 10 == 4) {
                return pos;
            }
            pos++;
            value /= 10;
        }
        return  -1;
    }
}
