import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cyb on 2017/10/14.
 */
public class Test {
    public static void main(String[] args) {
        String strInput = "3a7s10@5d2a6s17s56;33";
        String regEx = "[^0-9]";//匹配指定范围内的数字

        //Pattern是一个正则表达式经编译后的表现模式
        Pattern p = Pattern.compile(regEx);

        // 一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
        Matcher m = p.matcher(strInput);

        //将输入的字符串中非数字部分用空格取代并存入一个字符串
        String string = m.replaceAll(" ").trim();

        //以空格为分割符在讲数字存入一个字符串数组中
        String[] strArr = string.split(" ");

        //遍历数组转换数据类型输出
        for(String s:strArr){
            System.out.println(Integer.parseInt(s));
        }
    }
}
