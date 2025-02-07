package com.saveslave.commons.util;

import java.math.BigInteger;
import java.util.Stack;

public class NumericConvertUtil
{
    /**
     * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号表示
     */
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final char[] digits26 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final char[] digits32 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W',
            'X', 'Y', 'Z'};

    private static final char[] digits34 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K','L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T','U', 'V', 'W',
            'X', 'Y', 'Z'};

    private static final char[] digits52 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'};

    private static final char[] digits58 = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final BigInteger INTEGER0 = new BigInteger("0");

    public static String toEncrypt(long number, int seed)
    {
        if (number < 0)
        {
            number = ((long)2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0)
        {
            buf[--charPos] = digits[(int)(number % seed)];
            number /= seed;
        }
        buf[--charPos] = digits[(int)(number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }

    public static long toDecrypt(String number, int seed)
    {
        char[] charBuf = number.toCharArray();
        if (seed == 10)
        {
            return Long.parseLong(number);
        }

        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i-- )
        {
            int index = 0;
            for (int j = 0, length = digits.length; j < length; j++ )
            {
                // 找到对应字符的下标，对应的下标才是具体的数值
                if (digits[j] == charBuf[i])
                {
                    index = j;
                }
            }
            result += index * base;
            base *= seed;
        }
        return result;
    }

    public static String toEncrypt26(long number, int seed)
    {
        if (number < 0)
        {
            number = ((long)2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0)
        {
            buf[--charPos] = digits26[(int)(number % seed)];
            number /= seed;
        }
        buf[--charPos] = digits26[(int)(number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }

    public static long toDecrypt26(String number, int seed)
    {
        char[] charBuf = number.toCharArray();
        if (seed == 10)
        {
            return Long.parseLong(number);
        }

        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i-- )
        {
            int index = 0;
            for (int j = 0, length = digits26.length; j < length; j++ )
            {
                // 找到对应字符的下标，对应的下标才是具体的数值
                if (digits26[j] == charBuf[i])
                {
                    index = j;
                }
            }
            result += index * base;
            base *= seed;
        }
        return result;
    }

    public static String toEncrypt52(long number, int seed)
    {
        if (number < 0)
        {
            number = ((long)2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0)
        {
            buf[--charPos] = digits52[(int)(number % seed)];
            number /= seed;
        }
        buf[--charPos] = digits52[(int)(number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }

    public static long toDecrypt52(String number, int seed)
    {
        char[] charBuf = number.toCharArray();
        if (seed == 10)
        {
            return Long.parseLong(number);
        }

        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i-- )
        {
            int index = 0;
            for (int j = 0, length = digits52.length; j < length; j++ )
            {
                // 找到对应字符的下标，对应的下标才是具体的数值
                if (digits52[j] == charBuf[i])
                {
                    index = j;
                }
            }
            result += index * base;
            base *= seed;
        }
        return result;
    }

    public static String toEncrypt58(long number, int seed)
    {
        if (number < 0)
        {
            number = ((long)2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0)
        {
            buf[--charPos] = digits58[(int)(number % seed)];
            number /= seed;
        }
        buf[--charPos] = digits58[(int)(number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }

    public static long toDecrypt58(String number, int seed)
    {
        char[] charBuf = number.toCharArray();
        if (seed == 10)
        {
            return Long.parseLong(number);
        }

        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i-- )
        {
            int index = 0;
            for (int j = 0, length = digits58.length; j < length; j++ )
            {
                // 找到对应字符的下标，对应的下标才是具体的数值
                if (digits58[j] == charBuf[i])
                {
                    index = j;
                }
            }
            result += index * base;
            base *= seed;
        }
        return result;
    }

    public static String toEncrypt32(long number, int seed)
    {
        if (number < 0)
        {
            number = ((long)2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0)
        {
            buf[--charPos] = digits32[(int)(number % seed)];
            number /= seed;
        }
        buf[--charPos] = digits32[(int)(number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }

    public static long toDecrypt32(String number, int seed)
    {
        char[] charBuf = number.toCharArray();
        if (seed == 10)
        {
            return Long.parseLong(number);
        }

        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i-- )
        {
            int index = 0;
            for (int j = 0, length = digits32.length; j < length; j++ )
            {
                // 找到对应字符的下标，对应的下标才是具体的数值
                if (digits32[j] == charBuf[i])
                {
                    index = j;
                }
            }
            result += index * base;
            base *= seed;
        }
        return result;
    }

    public static String toEncrypt34(long number, int seed)
    {
        if (number < 0)
        {
            number = ((long)2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[35];
        int charPos = 34;
        while ((number / seed) > 0)
        {
            buf[--charPos] = digits34[(int)(number % seed)];
            number /= seed;
        }
        buf[--charPos] = digits34[(int)(number % seed)];
        return new String(buf, charPos, (35 - charPos));
    }

    public static long toDecrypt34(String number, int seed)
    {
        char[] charBuf = number.toCharArray();
        if (seed == 10)
        {
            return Long.parseLong(number);
        }

        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i-- )
        {
            int index = 0;
            for (int j = 0, length = digits34.length; j < length; j++ )
            {
                // 找到对应字符的下标，对应的下标才是具体的数值
                if (digits34[j] == charBuf[i])
                {
                    index = j;
                }
            }
            result += index * base;
            base *= seed;
        }
        return result;
    }

    public static String bigEncrypt34(String number, int radix) {
        if(radix < 0 || radix > digits34.length){
            radix = digits34.length;
        }

        BigInteger bigNumber = new BigInteger(number);
        BigInteger bigRadix = new BigInteger(radix + "");

        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder(0);
        while (!bigNumber.equals(INTEGER0)) {
            stack.add(digits34[bigNumber.remainder(bigRadix).intValue()]);
            bigNumber = bigNumber.divide(bigRadix);
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.length() == 0 ? "0" : result.toString();
    }

    public static void main(String[] args)
    {
        //BigInteger time = new BigInteger("200000000000000000000000000000000000001");
        long time=1000000001111L;
        /*System.out.println("16进制结果" + Long.toUnsignedString(time, 16));
        System.out.println("32进制结果" + toEncrypt32(time, 32));
        System.out.println("36进制结果" + Long.toUnsignedString(time, 36));
        System.out.println("26进制结果" + toEncrypt26(time, 26));
        System.out.println("52进制结果" + toEncrypt52(time, 52));
        System.out.println("58进制结果" + toEncrypt58(time, 58));*/
        //System.out.println("34进制结果" + toEncrypt34(time, 34));
        String entryptStr=bigEncrypt34("210812788196553905786201996638150187696", 34);
        System.out.println("34进制结果" +entryptStr+":"+entryptStr.length() );

    }
}


