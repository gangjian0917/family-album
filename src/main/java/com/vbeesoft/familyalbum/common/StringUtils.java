package com.vbeesoft.familyalbum.common;


import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此类中封装一些常用的字符串操作。 所有方法都是静态方法，不需要生成此类的实例， 为避免生成此类的实例，构造方法被申明为private类型的。
 *
 * @since 0.1
 */
public final class StringUtils {
    /**
     * 产生一个随机的字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    /**
     * 产生一个随机的只含数字的字符串
     *
     * @param length
     * @return
     * @author tankaichao
     */
    public static String getRandomNumString(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(10);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }


    /**
     * 正则解析内容
     *
     * @param regex
     * @param content
     * @param indexs
     * @return
     */
    public static List<String[]> analyticalContent(String regex, String content, int[] indexs) {
        List<String[]> fieldList = new ArrayList<String[]>();
        Pattern pattern = Pattern.compile(regex);
        Matcher mat = pattern.matcher(content);
        while (mat.find()) {
            String[] fieldStr = new String[indexs.length];
            for (int index : indexs) {
                fieldStr[index] = mat.group(index + 1);
            }
            fieldList.add(fieldStr);
        }
        return fieldList;
    }


    public static String makeSpecialChar(String oldString) {
        int contentLen = oldString.length();
        char c;
        StringBuffer tmpValue = new StringBuffer();
        for (int j = 0; j < contentLen; j++) {
            c = oldString.charAt(j);
            if (c == '\\') {
                tmpValue.append("\\\\");
            } else if (c == '(') {
                tmpValue.append("\\(");
            } else if (c == ')') {
                tmpValue.append("\\)");
            } else if (c == '+') {
                tmpValue.append("\\+");
            } else if (c == '-') {
                tmpValue.append("\\-");
            } else if (c == '_') {
                tmpValue.append("\\_");
            } else if (c == '|') {
                tmpValue.append("\\|");
            } else if (c == ']') {
                tmpValue.append("\\]");
            } else if (c == '[') {
                tmpValue.append("\\[");
            } else if (c == '{') {
                tmpValue.append("\\{");
            } else if (c == '}') {
                tmpValue.append("\\}");
            } else if (c == '*') {
                tmpValue.append("\\*");
            } else if (c == '&') {
                tmpValue.append("\\&");
            } else if (c == '^') {
                tmpValue.append("\\^");
            } else if (c == '%') {
                tmpValue.append("\\%");
            } else if (c == '$') {
                tmpValue.append("\\$");
            } else if (c == '#') {
                tmpValue.append("\\#");
            } else if (c == '@') {
                tmpValue.append("\\@");
            } else if (c == '!') {
                tmpValue.append("\\!");
            } else if (c == '~') {
                tmpValue.append("\\~");
            } else if (c == '`') {
                tmpValue.append("\\`");
            } else if (c == ':') {
                tmpValue.append("\\:");
            } else if (c == '\"') {
                tmpValue.append("\\\"");
            } else if (c == '\'') {
                tmpValue.append("\\'");
            } else if (c == '?') {
                tmpValue.append("\\?");
            } else if (c == '/') {
                tmpValue.append("\\/");
            } else if (c == '>') {
                tmpValue.append("\\>");
            } else if (c == '<') {
                tmpValue.append("\\<");
            } else if (c == ',') {
                tmpValue.append("\\,");
            } else if (c == '.') {
                tmpValue.append("\\.");
            } else if (c == ';') {
                tmpValue.append("\\;");
            } else
                tmpValue.append(c);
        }
        return tmpValue.toString();
    }

    /**
     * @param j
     * @param BeR
     * @return
     */
    public static String AsiicZH(int j, char BeR) {
        String upp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (!java.lang.Character.isDigit(BeR)) {
            upp = upp.substring(j - 1, j);
        } else {
            upp = String.valueOf(j);
        }
        return upp;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + "";
            }
        }
        return hs;
    }

    /**
     * 将字符串加密。
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     * @since 0.6
     */
    public static String calcMD5(String str) {
        try {
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update(str.getBytes());
            byte[] digesta = alga.digest();
            return byte2hex(digesta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 整理String，包括对NULL和“NULL”的判断
     *
     * @param originalString 原始String
     * @return 整理后的String
     */
    public static String clearString(String originalString) {
        if (originalString == null) {
            return "";
        } else if (originalString.equalsIgnoreCase("null")) {
            return "";
        }
        return originalString.trim();
    }

    /**
     * 将字符串数组使用指定的分隔符合并成一个字符串。
     *
     * @param array 字符串数组
     * @param delim 分隔符，为null的时候使用""作为分隔符（即没有分隔符）
     * @return 合并后的字符串
     * @since 0.4
     */
    public static String combineStringArray(String[] array, String delim) {
        delim = delim == null ? "" : delim;
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < array.length - 1; i++) {
            result.append(array[i]);
            result.append(delim);
        }
        result.append(array[array.length - 1]);
        return result.toString();
    }

    /**
     * 字符串数组中是否包含指定的字符串。大小写敏感。
     *
     * @param strings 字符串数组
     * @param string  字符串
     * @return 包含时返回true，否则返回false
     * @since 0.4
     */
    public static boolean contains(String[] strings, String string) {
        return contains(strings, string, true);
    }

    /**
     * 字符串数组中是否包含指定的字符串。
     *
     * @param strings       字符串数组
     * @param string        字符串
     * @param caseSensitive 是否大小写敏感
     * @return 包含时返回true，否则返回false
     * @since 0.4
     */
    public static boolean contains(String[] strings, String string, boolean caseSensitive) {
        for (int i = 0; i < strings.length; i++) {
            if (caseSensitive == true) {
                if (strings[i].equals(string)) {
                    return true;
                }
            } else {
                if (strings[i].equalsIgnoreCase(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 不区分大小写判定字符串数组中是否包含指定的字符串。
     *
     * @param strings 字符串数组
     * @param string  字符串
     * @return 包含时返回true，否则返回false
     * @since 0.4
     */
    public static boolean containsIgnoreCase(String[] strings, String string) {
        return contains(strings, string, false);
    }

    public static String convertChar2XMLEntity(String xmlSource) {
        xmlSource = StringUtils.clearString(xmlSource);
        xmlSource = xmlSource.replace("&", "&amp;");
        xmlSource = xmlSource.replace("<", "&lt;");
        xmlSource = xmlSource.replace(">", "&gt;");
        xmlSource = xmlSource.replace("'", "\\'");
        return xmlSource;
    }

    public static String convertXMLEntity2Char(String xmlSource) {
        xmlSource = StringUtils.clearString(xmlSource);
        xmlSource = xmlSource.replace("&lt;", "<");
        xmlSource = xmlSource.replace("&gt;", ">");
        xmlSource = xmlSource.replace("&amp;", "&");
        return xmlSource;
    }

    /**
     * 创建一个32位的令牌
     *
     * @return 32位的字符串
     */
    public static String createToken() {
        Calendar cal = Calendar.getInstance();
        String timestamp = String.valueOf(cal.getTimeInMillis());
        return calcMD5(timestamp);
    }

    /**
     * 根据转义列表对字符串进行转义。
     *
     * @param source        待转义的字符串
     * @param escapeCharMap 转义列表
     * @return 转义后的字符串
     * @since 0.6
     */
    @SuppressWarnings("rawtypes")
    public static String escapeCharacter(String source, HashMap escapeCharMap) {
        if (source == null || source.length() == 0)
            return source;
        if (escapeCharMap.size() == 0)
            return source;
        StringBuffer sb = new StringBuffer();
        StringCharacterIterator sci = new StringCharacterIterator(source);
        for (char c = sci.first(); c != StringCharacterIterator.DONE; c = sci.next()) {
            String character = String.valueOf(c);
            if (escapeCharMap.containsKey(character))
                character = (String) escapeCharMap.get(character);
            sb.append(character);
        }
        return sb.toString();
    }

    /**
     * 以指定的字符和长度生成一个该字符的指定长度的字符串。
     *
     * @param c      指定的字符
     * @param length 指定的长度
     * @return 最终生成的字符串
     * @since 0.6
     */
    public static String fillString(char c, int length) {
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += c;
        }
        return ret;
    }

    /**
     * 把字符串格式化为SQL语句中需要的字符串格式
     *
     * @param originalString 原始字符串，可以是逗号分隔的
     * @return 格式化之后的字符串
     */
    public static String format2SQLDialect(String originalString) {
        StringBuffer sqlStringBuffer = new StringBuffer();
        if (originalString.indexOf(",") > 0) {
            if (!originalString.startsWith("'")) {
                sqlStringBuffer.append("'");
                originalString = originalString.replace(",", "','");
                sqlStringBuffer.append(originalString + "'");
            } else {
                sqlStringBuffer.append(originalString);
            }
        } else {
            sqlStringBuffer.append(originalString.startsWith("'") ? "" : "'");
            sqlStringBuffer.append(originalString);
            sqlStringBuffer.append(originalString.endsWith("'") ? "" : "'");
        }
        return sqlStringBuffer.toString();
    }

    /**
     * 拚装右模糊查询条件字符串
     *
     * @param fieldName       字段名
     * @param conditionString 条件值，多个用逗号分隔
     * @return
     */
    public static String format2SQLRLikeDialect(String fieldName, String conditionString) {
        StringBuffer sqlStringBuffer = new StringBuffer();
        conditionString = conditionString.replace("'", "");
        String[] conditions = conditionString.split(",");
        for (int i = 0; i < conditions.length; i++) {
            sqlStringBuffer.append(fieldName + " like '" + conditions[i] + "%'");
            if (i < conditions.length - 1) {
                sqlStringBuffer.append(" or ");
            }
        }
        return sqlStringBuffer.toString();
    }

    /**
     * 返回整形数的指定长度，指定填充因子的字符串
     *
     * @param number     ,指定整形数
     * @param destLength 指定长度
     * @param paddedChar 指定填充因子
     * @return 如果该整形数长度大于指定长度。截到一部分，如果小于指定长度，左填充指定填充因子
     */
    public static String formatNumber(int number, int destLength, char paddedChar) {
        String oldString = String.valueOf(number);
        StringBuffer newString = new StringBuffer("");
        int oldLength = oldString.length();
        if (oldLength > destLength) {
            newString.append(oldString.substring(oldLength - destLength));
        } else if (oldLength == destLength) {
            newString.append(oldString);
        } else {
            for (int i = 0; i < destLength - oldLength; i++) {
                newString.append(paddedChar);
            }
            newString.append(oldString);
        }
        return newString.toString();
    }

    /**
     * 得到字符串的字节长度
     *
     * @param source 字符串
     * @return 字符串的字节长度
     * @since 0.6
     */
    public static int getByteLength(String source) {
        int len = 0;
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            int highByte = c >>> 8;
            len += highByte == 0 ? 1 : 2;
        }
        return len;
    }

    /**
     * 根据WBS码格式化字符串
     *
     * @param wbsCode   WBS码
     * @param separator 分隔符
     * @param name      待格式化的字符串
     * @param pad       填充字符
     * @return 格式化之后的带层次关系的字符串
     */
    public static String getCascadeName(String wbsCode, String separator, String name, String pad) {
        String[] strings = split(wbsCode, separator);
        StringBuffer buffer = new StringBuffer("");
        for (int i = 0; i < strings.length - 2; i++) {
            buffer.append(pad);
        }
        buffer.append(name);
        return buffer.toString();
    }

    /**
     * 验正日期字符串是否正确
     *
     * @param condition 待验正的日期字符串
     * @return 如果待验正的字符串不为空，且去能格式化成日期类形，返回真
     */
    @SuppressWarnings("unused")
    public static boolean isValidDateCondition(String condition) {
        boolean isValid = false;
        try {
            if (condition != null) {
                String separator = condition.indexOf('/') > 0 ? "/" : "-";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd");
                Date date = simpleDateFormat.parse(condition);
                isValid = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return isValid;
    }

    /**
     * 判断是否合法字符串，“合法”指不是null、不等于‘null’、不全是空格
     *
     * @param targetString 待判断的字符串
     * @return 是否合法
     */
    public static boolean isValidString(String targetString) {
        boolean isValid = false;
        if (targetString != null) {
            targetString = targetString.trim();
            if (!targetString.equalsIgnoreCase("null") && targetString.length() > 0) {
                isValid = true;
            }
        }
        return isValid;
    }

    /**
     * 验正去左右空格后是否为空
     *
     * @param condition 待验正的字符串
     * @return 如果待验正的字符串不为空，且去空格后不为空，返回真
     */
    public static boolean isValidStringCondition(String condition) {
        boolean isValid = false;
        if (condition != null) {
            condition = condition.trim();
            if (condition.length() > 0) {
                isValid = true;
            }
        }
        return isValid;
    }

    /**
     * 循环打印字符串数组到系统标准输出流System.out。 字符串数组的各元素间以逗号分隔，如果字符串中已经包含逗号则在字符串的两端加上双引号。
     *
     * @param strings 字符串数组
     * @since 0.2
     */
    public static void printStrings(String[] strings) {
        printStrings(strings, ",", System.out);
    }

    /**
     * 循环打印字符串数组。 字符串数组的各元素间以逗号分隔，如果字符串中已经包含逗号则在字符串的两端加上双引号。
     *
     * @param strings 字符串数组
     * @param out     打印到的输出流
     * @since 0.2
     */
    public static void printStrings(String[] strings, OutputStream out) {
        printStrings(strings, ",", out);
    }

    /**
     * 循环打印字符串数组到标准输出。 字符串数组的各元素间以指定字符分隔，如果字符串中已经包含指定字符则在字符串的两端加上双引号。
     *
     * @param strings 字符串数组
     * @param delim   分隔符
     * @since 0.4
     */
    public static void printStrings(String[] strings, String delim) {
        printStrings(strings, delim, System.out);
    }

    /**
     * 循环打印字符串数组。 字符串数组的各元素间以指定字符分隔，如果字符串中已经包含指定字符则在字符串的两端加上双引号。
     *
     * @param strings 字符串数组
     * @param delim   分隔符
     * @param out     打印到的输出流
     * @since 0.4
     */
    public static void printStrings(String[] strings, String delim, OutputStream out) {
        try {
            if (strings != null) {
                int length = strings.length - 1;
                for (int i = 0; i < length; i++) {
                    if (strings[i] != null) {
                        if (strings[i].indexOf(delim) > -1) {
                            out.write(("\"" + strings[i] + "\"" + delim).getBytes());
                        } else {
                            out.write((strings[i] + delim).getBytes());
                        }
                    } else {
                        out.write("null".getBytes());
                    }
                }
                if (strings[length] != null) {
                    if (strings[length].indexOf(delim) > -1) {
                        out.write(("\"" + strings[length] + "\"").getBytes());
                    } else {
                        out.write(strings[length].getBytes());
                    }
                } else {
                    out.write("null".getBytes());
                }
            } else {
                out.write("null".getBytes());
            }
            // out.write(Constants.LINE_SEPARATOR.getBytes());
        } catch (IOException e) {

        }
    }

    /**
     * 删除字符串前后的“规则字符”
     *
     * @param strTemp  字符串
     * @param charTemp 规则串
     * @return
     */
    public static String trimChar(String strTemp, String charTemp) {
        if (strTemp == null || strTemp.equals("")) {
            return "";
        }
        if (charTemp == null || charTemp.equals("")) {
            charTemp = ",";
        }
        // System.out.println(strTemp);
        if (!"".equals(strTemp) && strTemp.length() > charTemp.length() && strTemp.startsWith(charTemp)) {
            strTemp = strTemp.substring(charTemp.length(), strTemp.length() - 1);
        }
        if (!"".equals(strTemp) && strTemp.length() > charTemp.length() && strTemp.endsWith(charTemp)) {
            strTemp = strTemp.substring(0, strTemp.length() - charTemp.length());
        }
        // System.out.println(strTemp);
        return strTemp;
    }

    /**
     * 去除左边多余的空格。
     *
     * @param value 待去左边空格的字符串
     * @return 去掉左边空格后的字符串
     * @since 0.6
     */
    public static String trimLeft(String value) {
        String result = value;
        if (result == null)
            return result;
        char ch[] = result.toCharArray();
        int index = -1;
        for (int i = 0; i < ch.length; i++) {
            if (Character.isWhitespace(ch[i])) {
                index = i;
            } else {
                break;
            }
        }
        if (index != -1) {
            result = result.substring(index + 1);
        }
        return result;
    }

    /**
     * 去除右边多余的空格。
     *
     * @param value 待去右边空格的字符串
     * @return 去掉右边空格后的字符串
     * @since 0.6
     */
    public static String trimRight(String value) {
        String result = value;
        if (result == null)
            return result;
        char ch[] = result.toCharArray();
        int endIndex = -1;
        for (int i = ch.length - 1; i > -1; i--) {
            if (Character.isWhitespace(ch[i])) {
                endIndex = i;
            } else {
                break;
            }
        }
        if (endIndex != -1) {
            result = result.substring(0, endIndex);
        }
        return result;
    }

    /**
     * 不重复扩展StringBuffer
     *
     * @param buffer    StringBuffer
     * @param target    待扩展String
     * @param seperator 分隔符
     * @return 扩展后的StringBuffer
     */
    public static StringBuffer distinctAppend(StringBuffer buffer, String target, String seperator) {
        if (buffer.length() < 1) {
            return buffer.append(target);
        }

        String newTarget = seperator + target + seperator;
        String newBuffer = seperator + buffer.toString() + seperator;
        if (newBuffer.indexOf(newTarget) >= 0) {
            return buffer;
        } else {
            return buffer.append(seperator + target);
        }
    }

    /**
     * 此方法将给出的字符串source使用delim划分为单词数组。
     *
     * @param source 需要进行划分的原字符串
     * @param delim  单词的分隔字符串
     * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组，
     * 如果delim为null则使用逗号作为分隔字符串。
     * @since 0.1
     */
    public static String[] split(String source, String delim) {
        String[] wordLists;
        if (source == null) {
            wordLists = new String[1];
            wordLists[0] = source;
            return wordLists;
        }
        if (delim == null) {
            delim = ",";
        }
        StringTokenizer st = new StringTokenizer(source, delim);
        int total = st.countTokens();
        wordLists = new String[total];
        for (int i = 0; i < total; i++) {
            wordLists[i] = st.nextToken();
        }
        return wordLists;
    }

    public static String megerTelPhone(String telphone, String cellphone) {

        String megerTel = "";
        if (telphone != null && !telphone.equals("")) {
            if (telphone.charAt(0) == ',') {
                telphone = telphone.substring(1);
            }
            if (telphone.charAt(telphone.length() - 1) == ',') {
                telphone = telphone.substring(0, telphone.length() - 1);
            }
        }
        if (cellphone != null && !cellphone.equals("")) {
            if (cellphone.charAt(0) == ',') {
                cellphone = cellphone.substring(1);
            }
            if (cellphone.charAt(cellphone.length() - 1) == ',') {
                cellphone = cellphone.substring(0, cellphone.length() - 1);
            }
        }
        if (telphone == null && cellphone != null) {
            megerTel = cellphone;
        } else if (telphone != null && cellphone == null) {
            megerTel = telphone;
        } else if (telphone != null && cellphone != null) {
            megerTel = telphone + "," + cellphone;
        }
        return megerTel;
    }

    /**
     * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
     */
    private StringUtils() {
    }

    /**
     * 数字转换中文大写 人民币大写单位银行规定用"元" 无零头金额后跟"整"，有则不跟 角为零时不写角（如：零叁分）
     * 四舍五入到分为减少判读疑惑（一般对大写金额预期较高）和 体现人民币基本单位为元，金额低于壹圆前仍加"零元"
     * 整数转换若干零后若跟非零值，只显示一个零，否则不显示 万(亿)前有零后不加零，因亿、万为一完整单位，（如：拾万贰仟 比 拾万零贰仟 更顺些）
     * 亿为汉语计数最大单位，只要进位到总是显示（如：壹亿亿） 万为次最大单位，亿万之间必须有非零值方显示万（如"壹亿"不可显示为"壹亿万"）
     * 为减少被窜改的可能性，十进位总发壹音，这和下面的习惯读法不一样 （十进位处于第一位不发壹音，如"拾元"非"壹拾元"，
     * 十进位处前有零是否不发壹音不太确定， 如"叁仟零壹拾元"还是"叁仟零拾元"？）
     * 用"拾万"不用"壹拾万"，因为每个整数进位后都有进位单位（拾佰仟万亿） 这样即使金额前没有附防窜改的前缀如"人民币"字样也难窜改些
     * 因为至少要加添两个汉字并且改动后数字必须进位才能窜改成 （如"拾万"可改成"叁拾万"，而"壹拾万"至少要改成"壹佰壹拾万"）
     *
     * @param strNum
     * @return
     */
    @SuppressWarnings("unused")
    public static String getChinessMoney(String strNum) {
        int n, m, k, i, j, q, p, r, s = 0;
        int length, subLength, pstn;
        String change, output, subInput, input = strNum;
        output = "";
        if ("".equals(strNum)) {
            return "";
        } else {
            length = input.length();
            pstn = input.indexOf('.'); // 小数点的位置
            if (pstn == -1) {
                subLength = length;// 获得小数点前的数字
                subInput = input;
            } else {
                subLength = pstn;
                subInput = input.substring(0, subLength);
            }
            char[] array = new char[4];
            char[] array2 = {'仟', '佰', '拾'};
            char[] array3 = {'亿', '万', '元', '角', '分'};
            n = subLength / 4;// 以千为单位
            m = subLength % 4;
            if (m != 0) {
                for (i = 0; i < (4 - m); i++) {
                    subInput = '0' + subInput;// 补充首位的零以便处理
                }
                n = n + 1;
            }
            k = n;
            for (i = 0; i < n; i++) {
                p = 0;
                change = subInput.substring(4 * i, 4 * (i + 1));
                array = change.toCharArray();// 转换成数组处理
                for (j = 0; j < 4; j++) {
                    output += formatC(array[j]);// 转换成中文
                    if (j < 3) {
                        output += array2[j];// 补进单位，当为零是不补（千百十）
                    }
                    p++;
                }
                if (p != 0) {
                    output += array3[3 - k];// 补进进制（亿万元分角）
                }
                // 把多余的零去掉
                String[] str = {"零仟", "零佰", "零拾"};
                for (s = 0; s < 3; s++) {
                    while (true) {
                        q = output.indexOf(str[s]);
                        if (q != -1) {
                            output = output.substring(0, q) + "零" + output.substring(q + str[s].length());
                        } else {
                            break;
                        }
                    }
                }
                while (true) {
                    q = output.indexOf("零零");
                    if (q != -1) {
                        output = output.substring(0, q) + "零" + output.substring(q + 2);
                    } else {
                        break;
                    }
                }
                String[] str1 = {"零亿", "零万", "零元"};
                for (s = 0; s < 3; s++) {
                    while (true) {
                        q = output.indexOf(str1[s]);
                        if (q != -1) {
                            output = output.substring(0, q) + output.substring(q + 1);
                        } else {
                            break;
                        }
                    }
                }
                k--;
            }
            if (pstn != -1)// 小数部分处理
            {
                for (i = 1; i < length - pstn; i++) {
                    if (input.charAt(pstn + i) != '0') {
                        output += formatC(input.charAt(pstn + i));
                        output += array3[2 + i];
                    } else if (i < 2) {
                        output += "零";
                    } else {
                        output += "";
                    }
                }
            }
            if (output.substring(0, 1).equals("零")) {
                output = output.substring(1);
            }
            if (output.substring(output.length() - 1, output.length()).equals("零")) {
                output = output.substring(0, output.length() - 1);
            }
            return output += "整";
        }
    }

    public static String formatC(char x) {
        String a = "";
        switch (x) {
            case '0':
                a = "零";
                break;
            case '1':
                a = "壹";
                break;
            case '2':
                a = "贰";
                break;
            case '3':
                a = "叁";
                break;
            case '4':
                a = "肆";
                break;
            case '5':
                a = "伍";
                break;
            case '6':
                a = "陆";
                break;
            case '7':
                a = "柒";
                break;
            case '8':
                a = "捌";
                break;
            case '9':
                a = "玖";
                break;
        }
        return a;
    }

    public static String rtnFilePathStr(String nodestr, String indexof) {
        String rtnFilePathStr = nodestr;
        int len = rtnFilePathStr.lastIndexOf(indexof);
        if (len > 0) {
            rtnFilePathStr = nodestr.substring(0, len);
        }
        return rtnFilePathStr;
    }

    @SuppressWarnings("unused")
    public static void main(String args[]) {
        String kw = "abcd";

        // System.out.println(makeSphinxString(kw));

    }

    /**
     *
     * @param str
     * @return boolean
     * @throws
     * @since 1.0.0
     */

    public static boolean isEmpty(Object str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检验入参字符串是否为空
     *
     * @param str
     * @return
     * @author tankaichao
     */
    public static boolean isParamEmpty(String... str) {
        for (String emptyparam : str) {
            if (emptyparam == null || emptyparam.equals("")) {
                return true;
            }
        }
        return false;
    }

    private static Pattern p2 = Pattern.compile(".*[A|a][L|l][E|e][R|r][T|t]\\s*\\(.*\\).*" + "|.*[W|w][I|i][N|n][D|d][O|o][W|w]\\.[L|l][O|o][C|c][A|a][T|t][I|i][O|o][N|n]\\s*=.*"
            + "|.*[S|s][T|t][Y|y][L|l][E|e]\\s*=.*[X|x]:[E|e][X|x].*[P|p][R|r][E|e][S|s]{1,2}[I|i][O|o][N|n]\\s*\\(.*\\).*" + "|.*[E|e][V|v][A|a][L|l]\\s*\\(.*\\).*" + "|.*[U|u][N|n][E|e][S|s][C|c][A|a][P|p][E|e]\\s*\\(.*\\).*"
            + "|.*[E|e][X|x][E|e][C|c][S|s][C|c][R|r][I|i][P|p][T|t]\\s*\\(.*\\).*" + "|.*[M|m][S|s][G|g][B|b][O|o][X|x]\\s*\\(.*\\).*" + "|.*[C|c][O|o][N|n][F|f][I|i][R|r][M|m]\\s*\\(.*\\).*" + "|.*[P|p][R|r][O|o][M|m][P|p][T|t]\\s*\\(.*\\).*"
            + "|.*<[S|s][C|c][R|r][I|i][P|p][T|t].*|.*</[S|s][C|c][R|r][I|i][P|p][T|t]>.*" + "|.*<[I|i][F|f][R|r][A|a][M|m][E|e].*|.*</[I|i][F|f][R|r][A|a][M|m][E|e].*" + "|.*<[F|f][R|r][A|a][M|m][E|e].*|.*</[F|f][R|r][A|a][M|m][E|e].*"
            + "|.&[^\"]]*\"[.&[^\"]]*");

    public static boolean illegalChar(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        Matcher m = p2.matcher(str);
        if (m.find())
            return true;
        else
            return false;
    }
}

