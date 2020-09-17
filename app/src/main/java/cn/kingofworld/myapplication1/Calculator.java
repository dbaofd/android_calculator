package cn.kingofworld.myapplication1;

/**
 * Created by BaoDong on 2016/3/1.
 */

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    private int bao;/*类似c语言的全局变量，用来代替指针*/
    public int MaxCharNum = 300;//最多支持运算的表达式字符数

    public class stack {
        char[] data = new char[MaxCharNum];
        int top;
    }

    public int judge(String a) {//简单的判断表达式是否正确
        int k = 0, t = 0, i;
        for (i = 0; i < a.length(); i++) {
            if (a.charAt(i) == ')')
                k++;
            else if (a.charAt(i) == '(')
                t++;
        }

        //如果左右括号数相等，等号前面的字符不能是除右括号以外的任何运算符
        if (k == t && (a.charAt(a.length() - 2) != '+' && a.charAt(a.length() - 2) != '-' && a.charAt(a.length() - 2) != '/'
                && a.charAt(a.length() - 2) != '*' && a.charAt(a.length() - 2) != '(' && a.charAt(a.length() - 2) != '.'))
            return 1;
        else return 0;
    }

    private char Precede(char a, char b) {
        //判断运算符的优先级，a为操作符栈中的运算符，b为将要进入操作符栈中的运算符
        int i = 0, j = 0;
        char[][] pre = new char[][]{
                /*运算符之间的优先级制作成一张表格*/
                {'>', '>', '>', '>', '>'},
                {'<', '<', '>', '>', '>'},
                {'<', '<', '>', '>', '>'},
                {'<', '<', '<', '<', '>'},
                {'>', '>', '>', '>', '<'},
        };
        switch (a) {
            case '+':
                i = 0;
                break;
            case '-':
                i = 1;
                break;
            case '*':
                i = 2;
                break;
            case '/':
                i = 3;
                break;
            case '(':
                i = 4;
                break;
        }
        switch (b) {
            case '+':
                j = 0;
                break;
            case '-':
                j = 1;
                break;
            case '*':
                j = 2;
                break;
            case '/':
                j = 3;
                break;
            case '(':
                j = 4;
                break;
        }
        return pre[i][j];
    }

    public stack transform(String zz) {//将中缀表达式转换成后缀表达式
        stack ex = new stack();//ex是用来存放后缀值的堆栈，fu是用来存放操作符的堆栈
        stack fu = new stack();
        int i;
        //int flag;
        ex.top = -1;//初始化这些堆栈
        fu.top = -1;
        //flag = 0;
        for (i = 0; i < zz.length(); i++) {
            if (zz.charAt(i) >= '0' && zz.charAt(i) <= '9' || zz.charAt(i) == '.') {//是数字直接入后缀值栈
                ex.data[++ex.top] = zz.charAt(i);
                if ((zz.charAt(i + 1) > '9' || zz.charAt(i + 1) < '0') && zz.charAt(i + 1) != '.')
                    //如果数字后面就是运算符，就在后缀值栈中加一个空格，排除数字后面是小数点的情况
                    ex.data[++ex.top] = ' ';
            } else if (zz.charAt(i) == '=') {//是‘=’号就将操作符栈中的剩余运算符弹到后缀值栈中
                while (fu.top != -1) {//直到将操作符栈中的操作符弹光
                    ex.data[++ex.top] = fu.data[fu.top--];
                    //fu.top--;
                }
                ex.data[++ex.top] = zz.charAt(i);//'='号弹入后缀值栈
                ex.data[++ex.top] = '\0';//后缀值栈录结束要在字符串末尾加结束字符'\0'
            } else if (i - 1 == -1 && zz.charAt(i) == '-') {//i-1=-1时，如果这个else if和下面的合并会造成zz.charAt(i-1)为空报错
                //判断运算符是负号还是减号，减号如果是第一个，在或条件中会有i-1=-1即可通过此条件判断负号，如果不是首个，则看其前一个是否是运算符
                ex.data[++ex.top] = '0';//满足条件就将0和空格加入后缀值栈
                ex.data[++ex.top] = ' ';
                fu.data[++fu.top] = zz.charAt(i);//无需经过与操作符栈中的运算符比较优先级，直接入运算符栈
            } else if (zz.charAt(i) == '-' && (zz.charAt(i - 1) == '-' || zz.charAt(i - 1) == '+' || zz.charAt(i - 1) == '*' || zz.charAt(i - 1) == '/' || zz.charAt(i - 1) == '(')) {
                ex.data[++ex.top] = '0';//满足条件就将0和空格加入后缀值栈
                ex.data[++ex.top] = ' ';
                fu.data[++fu.top] = zz.charAt(i);//无需经过与操作符栈中的运算符比较优先级，直接入运算符栈
            } else if (fu.top == -1)//当操作符栈里面是空栈，就将第一个运算符弹进去
                fu.data[++fu.top] = zz.charAt(i);
            else if (zz.charAt(i) == '(')//遇到左括号就让其直接入操作符栈
                fu.data[++fu.top] = zz.charAt(i);
            else if (zz.charAt(i) == ')') {//遇到右括号就把操作符栈中的截止到左括号的运算符弹到后缀值栈
                while (fu.data[fu.top] != '(') {
                    ex.data[++ex.top] = fu.data[fu.top];
                    fu.top--;
                }
                fu.top--;//删除左括号
            } else if (Precede(fu.data[fu.top], zz.charAt(i)) == '<') {
                //字符串中的运算符优先级小于操作符栈顶的运算符，小于则将操作符栈顶运算符弹入后缀值栈
                ex.data[++ex.top] = fu.data[fu.top];//先将操作符栈顶运算符弹入后缀值栈
                if (fu.top > 0) {//当fu栈中只有一个运算符时，弹走了就不能再进入while，不然--fu.top就会越界
                    while (Precede(fu.data[--fu.top], zz.charAt(i)) == '<') {
                        //直到发现字符串中的那个运算符的优先级大于操作符栈中的，就停止向后缀值栈弹入优先级高的运算符
                        ex.data[++ex.top] = fu.data[fu.top];
                        if (fu.top == 0) {//这个else里面本来有个bug，会使数组下标为-1，导致越界
                            fu.top--;
                            break;
                        }
                    }
                } else {
                    fu.top--;//弹出唯一运算符
                }
                fu.data[++fu.top] = zz.charAt(i);//停止后就将这个字符串中的那个字符弹入操作符栈中
                //flag = 0;
            } else if (Precede(fu.data[fu.top], zz.charAt(i)) == '>')
                //字符串中的那个运算符的优先级大于操作符栈中的，大于则直接将这个字符串中的运算符弹入操作符栈
                fu.data[++fu.top] = zz.charAt(i);
        }
        return ex;//将最后得到的后缀值栈返回
        //ps:之所以将遇到'=','(',')'三种情况放在调用Precede之前，是因为避免这三个运算符和其他运算符进行优先级比较
    }

    private double readnumber(char f[]) {//将后缀值栈中的数由字符型转化成double型
        double x = 0;
        int k = 0;
        while (f[bao] >= '0' && f[bao] <= '9') {
            x = x * 10 + (f[bao] - '0');
            (bao)++;
        }
        if (f[bao] == '.') {
            (bao)++;
            while (f[bao] >= '0' && f[bao] <= '9') {
                x = x * 10 + (f[bao] - '0');
                (bao)++;
                k++;//小数点后位数统计
            }
        }
        while (k != 0) {
            x = x / 10.0;//小数点后有多少位就要除几次10
            k = k - 1;
        }
        return x;
    }

    public BigDecimal evalpost(char f[]) {//求解后缀表达式
        BigDecimal[] obst = new BigDecimal[MaxCharNum];
        //double[] obst=new double[100];//存放double型的数，进行运算
        int top = 0;
        bao = 0;
        //double x1, x2;
        BigDecimal x1, x2;
        while (f[bao] != '=') {
            if (f[bao] >= '0' && f[bao] <= '9') {
                obst[top] = new BigDecimal(readnumber(f));
                top++;
            } else if (f[bao] == ' ')
                bao++;
            else if (f[bao] == '+') {
                x2 = obst[--top];
                x1 = obst[--top];
                obst[top] = x1.add(x2);
                top++;
                bao++;
            } else if (f[bao] == '-') {
                x2 = obst[--top];
                x1 = obst[--top];
                obst[top] = x1.subtract(x2);
                top++;
                bao++;
            } else if (f[bao] == '*') {
                x2 = obst[--top];
                x1 = obst[--top];
                obst[top] = x1.multiply(x2);
                top++;
                bao++;
            } else if (f[bao] == '/') {
                x2 = obst[--top];
                x1 = obst[--top];
                obst[top] = x1.divide(x2, 100, RoundingMode.HALF_UP);
                top++;
                bao++;
            }
        }
        BigDecimal result = obst[0].setScale(4, RoundingMode.HALF_UP);
        //java.text.DecimalFormat fmt = new DecimalFormat("#.#########");//输出格式化
        //System.out.println(fmt.format(result));
        //obst[0].setScale(4, RoundingMode.HALF_UP);
        return result;
    }
}

