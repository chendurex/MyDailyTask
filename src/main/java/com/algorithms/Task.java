package com.algorithms;

/**
 * @author chen
 * @description
 * @pachage com.algorithms
 * @date 2016/11/23 9:12
 */
public class Task {

    public static void main(String[] args) {
        Integer number = 31230000;
        System.out.println(obtainOneNumberCountOfBinary(number));
        System.out.println(Integer.toBinaryString(number));
       // System.out.println(7 & 4);
    }

    /**
     * 求出一个整数的二进制位有多少个奇数
     * 实现方案：将整数里面的每个二进制位都抽取出来比较，是奇数就+1，偶数就忽略
     * 2的N次方数，只有首位是1，其它位是0，利用这个特性进行每个二进制位求异或，
     * 如果原始位是0，那么异或出来就是1，当前数就会变大。如果原始位是1，那么异或出来就是0，当前数就会变小
     * 边界条件就是如果2的N次方大于当前数就停止计算
     */
    public static int sumOddNumberOfBinary(int number) {
        int sum = 0;
        if (number < 1) {
            return sum;
        }
        for (int i = 1; i <= number;) {
            sum += (number ^ i) > number ? 0 : 1;
            i = i << 1;
        }
        return sum;
    }

    /**
     * 求出某个数是2的N次方
     * 实现方案：2的N次方有个特性就是首位是1，其它位是0
     * 2的N次方-1的特性就是所有位都是1
     * 那么将2的N次方与2的N次方-1进行位与运算得到的所有位都是0
     * @param number
     * @return
     */
    public static boolean is2NPowerForNumber(int number) {
        return (number & (number -1)) == 0;
    }

    /**
     * 获取某个数的二进制位中1的个数
     * 实现方案：将每个数的尾数与1求与运算
     * @param number
     * @return
     */
    public static int obtainOneNumberCountOfBinary(int number) {
        int count = 0;
        int curNumber = number;
        do {
            if ((curNumber & 1) == 1) {
                count ++;
            }
            curNumber >>= 1;
        } while (curNumber != 0);
        return count;
    }
}
