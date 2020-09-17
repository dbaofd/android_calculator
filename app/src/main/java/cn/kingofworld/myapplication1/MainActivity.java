package cn.kingofworld.myapplication1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonClear;
    private Button buttonPlus;
    private Button buttonSub;
    private Button buttonDevide;
    private Button buttonX;
    private Button buttonEqual;
    private Button buttonLeft;
    private Button buttonRight;
    private Button buttonPoint;
    private Button buttonDelete;
    private TextView bellow;
    private TextView over;
    private StringBuffer strbu=new StringBuffer();//存储计算表达式的字符串
    private boolean flagEqual=false;//判断等于操作是否执行
    private int  flagLeft=0;//判断左括号执行次数

    private Calculator calculator = new Calculator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setContentView(R.layout.calculator);
        button0=(Button)findViewById(R.id.btn0);
        button1=(Button)findViewById(R.id.btn1);
        button2=(Button)findViewById(R.id.btn2);
        button3=(Button)findViewById(R.id.btn3);
        button4=(Button)findViewById(R.id.btn4);
        button5=(Button)findViewById(R.id.btn5);
        button6=(Button)findViewById(R.id.btn6);
        button7=(Button)findViewById(R.id.btn7);
        button8=(Button)findViewById(R.id.btn8);
        button9=(Button)findViewById(R.id.btn9);
        buttonPlus=(Button)findViewById(R.id.btnPlus);
        buttonSub=(Button)findViewById(R.id.btnSub);
        buttonX=(Button)findViewById(R.id.btnX);
        buttonDevide=(Button)findViewById(R.id.btnDevide);
        buttonClear=(Button)findViewById(R.id.btnClear);
        buttonEqual=(Button)findViewById(R.id.btnEqual);
        buttonLeft=(Button)findViewById(R.id.btnLeft);
        buttonRight=(Button)findViewById(R.id.btnRight);
        buttonPoint=(Button)findViewById(R.id.btnPoint);
        buttonDelete=(Button)findViewById(R.id.btnDelete);
        bellow = (TextView) this.findViewById(R.id.bellow);
        over=(TextView) this.findViewById(R.id.over);
        strbu.setLength(0);//最开始将字符串清空

        //定义监听事件
        button0.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("0");
                //button0.setBackgroundColor(Color.parseColor("#F5F5DC"));
            }
        });
        button1.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("1");
            }
        });
        button2.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("2");
            }
        });
        button3.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("3");
            }
        });
        button4.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("4");
            }
        });

        button5.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("5");
            }
        });
        button6.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("6");
            }
        });
        button7.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("7");
            }
        });
        button8.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("8");
            }
        });
        button9.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("9");
            }
        });
        buttonPoint.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                int flagPoint=0;//判断小数点使用情况
                if(!flagEqual&&strbu.length()!=0&&(strbu.toString().charAt(strbu.length()-1)>='0')
                        &&(strbu.toString().charAt(strbu.length()-1)<='9')) {
                    for(int i=strbu.length()-1;i>0;i--){
                        if((strbu.toString().charAt(i)>='0'&&strbu.toString().charAt(i)<='9')&&strbu.toString().charAt(i-1)=='.'){
                        //扫描字符串，防止出现“6.6.9”这样的输入错误
                            flagPoint=1;
                            break;
                        }
                        else if(strbu.toString().charAt(i)<'0'||strbu.toString().charAt(i)>'9')
                            break;
                    }
                    if(flagPoint==0)
                    judgeEqual(".");
                }
                //小数点之前只能是数字，小数点不能做表达式第一个，小数点不能在等于执行过后第一个执行
            }
        });
        buttonPlus.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("+");
                //加乘除号前面不能出现其他任何运算符，不能在表达式第一个输,前面不能是左括号
            }
        });
        buttonSub.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                if(strbu.length()==0)
                    judgeEqual("-");
                else if(strbu.length()>=2){
                    boolean flag=strbu.toString().charAt(strbu.length()-1)=='-'&&strbu.toString().charAt(strbu.length()-2)=='-';//判断是否字符串末尾已经输了两个连续的‘-’
                    if(!flag&&strbu.toString().charAt(strbu.length()-1)!='.')//防止连续两次以上输入小数点
                        judgeEqual("-");
                } else if(strbu.toString().charAt(strbu.length()-1)!='.')
                judgeEqual("-");
                //减号前面没有限制，可以跟在任何运算符数字之后
            }
        });
        buttonX.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                judgeEqual("*");
            }
        });
        buttonDevide.setOnClickListener(new MyOnClickListener() {//外部类写法
            @Override
            public void onClick(View v) {
                super.onClick(v);
                judgeEqual("/");
            }
        });
        buttonLeft.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                if(strbu.length()>=0||flagEqual) {
                    if(flagEqual){//执行过等于命令，可以第一个输入
                        judgeEqual("(");
                        flagLeft++;//左括号个数加加
                    } else if(strbu.length()>0){
                        if(strbu.toString().charAt(strbu.length()-1)<'0'
                                ||strbu.toString().charAt(strbu.length()-1)>'9')//左括号前面不能有数字
                            if(strbu.toString().charAt(strbu.length()-1)!='.'
                                    &&strbu.toString().charAt(strbu.length()-1)!=')') {//前面不能是小数点和右括号
                                judgeEqual("(");
                                flagLeft++;//左括号个数加加
                            }
                    } else {
                        judgeEqual("(");
                        flagLeft++;//左括号个数加加
                    }
                }

            }
        });
        buttonRight.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                //不能是第一个输入，只能跟在数字后面使用，不能紧跟在左括号后面,个数与左括号对等
                if(!flagEqual&&strbu.length()!=0&&(((strbu.toString().charAt(strbu.length()-1)>='0')
                        &&(strbu.toString().charAt(strbu.length()-1)<='9'))||(strbu.toString().charAt(strbu.length()-1)==')'))&&
                        (strbu.toString().charAt(strbu.length()-1)!='(')&&flagLeft>0){
                    judgeEqual(")");
                    flagLeft--;
                }

            }
        });
        buttonClear.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                strbu.setLength(0);
                strbu.append("");
                bellow.setText(strbu.toString());//清空上下两个textview
                over.setText(strbu.toString());
                flagLeft=0;//以免输到一半，清空，而这个值没有跟着清0，就会带来左右括号在接下来数量不均衡
                flagEqual=false;//输过等于号清空之后，如果不重置false，会导致刚开始输入就允许运算符输入了
            }
        });
        buttonDelete.setOnClickListener(new MyOnClickListener(){//外部类写法
            @Override
            public void onClick(View v){
                super.onClick(v);
                if(strbu.length()-1>=0) {//防止越界
                    if(strbu.toString().charAt(strbu.length()-1)==')')//判断末尾字符是不是右括号
                        //如果删除了“)”,flagLeft要加加，因为“)”是执行了一次减减操作
                        flagLeft++;
                    else if(strbu.toString().charAt(strbu.length()-1)=='(')//判断末尾字符是不是左括号
                        flagLeft--;
                    strbu.deleteCharAt(strbu.length() - 1);//删除最后一个字符
                }
                bellow.setText(strbu.toString());
            }
        });
        buttonEqual.setOnClickListener(new MyOnClickListener() {//外部类写法
            @Override
            public void onClick(View v) {
                super.onClick(v);
                int tep;//存放判断表达式规范与否方法返回的int值
                if (strbu.length() != 0) {
                    strbu.append("=");
                    tep=calculator.judge(strbu.toString());
                    if ( tep== 1) {//通过表达式格式正确判断才能进行运算
                        Calculator.stack ex = new Calculator().new stack();//new内部类对象
                        ex = calculator.transform(strbu.toString());//ex用于获得后缀表达式
                        try {//利用trycatch检测分母为0错误f
                            String str = "" + calculator.evalpost(ex.data);//将计算结果转换成string类型

                            bellow.setText(str);
                            over.setText(strbu.toString());//将计算表达式在over中显示
                            strbu.setLength(0);//将strbu清空
                            strbu.append(str);//并将答案存入
                            flagEqual = true;//标记进行过等于操作
                        } catch (ArithmeticException e) {//捕捉分母为0错误
                            bellow.setText("Calculation Error");
                            over.setText(strbu.toString());//将计算表达式在re中显示
                            strbu.setLength(0);//将strbu清空
                            flagEqual = true;//标记进行过等于操作
                        }
                    } else {
                        bellow.setText("FORMAT ERROR");
                        strbu.setLength(0);
                        over.setText(strbu.toString());
                        flagEqual = false;//重置，避免符号首次输入
                        flagLeft = 0;//以免输到一半，清空，而这个值没有跟着清0，就会带来左右括号在接下来数量不均衡
                    }

                }
            }
        });
    }

    private void judgeEqual(String str){
        if(strbu.length()<=calculator.MaxCharNum-140) {
            if ((str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) && flagEqual == true) {
                strbu.append(str);
                bellow.setText(strbu.toString());
                flagEqual = false;//如果进行等号运算之后的第一个操作是+-*/的话，就是以结果作为新的表达式中的一部分，则不能清空
            } else if (flagEqual) {//如果第一个操作是其他，则清空strbu并将新的输入
                strbu.setLength(0);
                strbu.append("");//先清空
                strbu.append(str);//再传值
                bellow.setText(strbu.toString());
                flagEqual = false;//进行过等于操作后第一次点击后就要重新置为false
            } else {//正常情况下
                //数字前面不能是有括号
                if (str.equals("0") || str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4") || str.equals("5") ||
                        str.equals("6") || str.equals("7") || str.equals("8") || str.equals("9")) {
                    if (strbu.length() == 0) {//如果是在第一个输入，那就不考虑前面是左括号的情况
                        strbu.append(str);
                        bellow.setText(strbu.toString());
                    } else if (strbu.toString().charAt(strbu.length() - 1) != ')') {//数组不能接在右括号之后
                        strbu.append(str);
                        bellow.setText(strbu.toString());
                    }
                } else if (str.equals("+") || str.equals("*") || str.equals("/")) {
                    //加乘除号前面不能出现其他任何运算符，不能在表达式第一个输,前面不能是左括号
                    if (strbu.length() != 0 && (strbu.toString().charAt(strbu.length() - 1) != '-')
                            && (strbu.toString().charAt(strbu.length() - 1) != '+')
                            && (strbu.toString().charAt(strbu.length() - 1) != '*')
                            && (strbu.toString().charAt(strbu.length() - 1) != '/')
                            && (strbu.toString().charAt(strbu.length() - 1) != '(')
                            && (strbu.toString().charAt(strbu.length() - 1) != '.')) {
                        strbu.append(str);
                        bellow.setText(strbu.toString());
                    }
                } else {
                    strbu.append(str);
                    bellow.setText(strbu.toString());
                }

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
class MyOnClickListener implements View.OnClickListener {//外部类
    @Override
    public void onClick(View v){

    }
}
