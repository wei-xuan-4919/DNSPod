package cn.dnspod.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author weixuan
 * @date 2023/4/4 9:53
 * @Description: 工具类
 */
public class Utils {

    /** int类型的NULL表示. */
    private static final int NULL_INT = -2147483648;

    /** float类型的NULL表示. */
    private static final float NULL_FLOAT = 1.4E-45F;

    /** double类型的NULL表示. */
    private static final double NULL_DOUBLE = 4.9E-324D;

    /** long类型的NULL表示. */
    private static final long NULL_LONG = -9999999999999998L;

    /** String类型的NULL标识 */
    private static final String NULL_STRING = "";

    /**
     * 判断对象数组是否为空
     *
     * @param objs
     * @return boolean
     */
    public static boolean isEmpty(Object[] objs) {
        return objs == null || objs.length == 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || NULL_STRING.equals(str) || "null".equals(str);
    }

    /**
     * 判断StringBuilder是否为空
     *
     * @param strBuilder
     * @return boolean
     */
    public static boolean isEmpty(StringBuilder strBuilder) {
        return strBuilder == null || strBuilder.length() == 0;
    }

    /**
     * 判断 StringBuffer 是否为空
     *
     * @param strBuffer
     * @return boolean
     */
    public static boolean isEmpty(StringBuffer strBuffer) {
        return strBuffer == null || strBuffer.length() == 0;
    }

    /**
     * 判断集合是否为空
     *
     * @param coll
     * @return boolean
     */
    public static boolean isEmpty(Collection coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * 判断int数组是否为空
     *
     * @param intArr
     * @return boolean
     */
    public static boolean isEmpty(byte[] intArr) {
        return intArr == null || intArr.length == 0;
    }

    /**
     * 判断int数组是否为空
     *
     * @param intArr
     * @return boolean
     */
    public static boolean isEmpty(int[] intArr) {
        return intArr == null || intArr.length == 0;
    }

    /**
     * 判断short数组是否为空
     *
     * @param shortArr
     * @return boolean
     */
    public static boolean isEmpty(short[] shortArr) {
        return shortArr == null || shortArr.length == 0;
    }

    /**
     * 判断long数组是否为空
     *
     * @param longArr
     * @return boolean
     */
    public static boolean isEmpty(long[] longArr) {
        return longArr == null || longArr.length == 0;
    }

    /**
     * 判断Map是否为空
     *
     * @param map
     * @return boolean
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断int值是否空值
     *
     * @param val
     *            int
     * @return boolean
     */
    public static boolean isEmpty(int val) {
        return (val == NULL_INT);
    }

    public static boolean isEmpty(Integer val) {
        return val == null || (val == NULL_INT);
    }

    /**
     * 判断long值是否空值
     *
     * @param val
     *            long
     * @return boolean
     */
    public static boolean isEmpty(long val) {
        return (val == NULL_LONG);
    }

    public static boolean isEmpty(Long val) {
        return val == null || (val == NULL_LONG);
    }

    /**
     * 判断float值是否空值
     *
     * @param val
     *            float
     * @return boolean
     */
    public static boolean isEmpty(float val) {
        return (val == NULL_FLOAT);
    }

    /**
     * 判断double值是否空值
     *
     * @param val
     *            double
     * @return boolean
     */
    public static boolean isEmpty(double val) {
        return (val == NULL_DOUBLE);
    }

}
