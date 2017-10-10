package cc.blog.test;

/**
 * Created by Elvis on 2017/9/29.
 */
public class Main {

    public static void main(String[] args) {
        String s = "aaaaa";
        String ss = "bbbbb";
        String sss = "ccccc";
        String result = testStr(s,ss,sss);
        System.out.println(result);

        String a = "a";
        String b = a + "b";
        String c = b + "c";

        String resultTest = a + b + c;
        System.out.println(resultTest);
    }

    private static String testStr(String s, String ss, String sss) {

        return " insert into " + s +
                " select * from " + s +
                " where " + ss +
                " in ( select " + ss +
                " from " + sss;
    }
}
