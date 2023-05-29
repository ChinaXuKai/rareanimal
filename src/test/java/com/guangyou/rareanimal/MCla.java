package com.guangyou.rareanimal;


/**
 * @author xukai
 * @create 2023-05-13 10:54
 */
public class MCla {

    public static void main(String[] args) {
        int a= 25,b= 7,c= 3;
        boolean x, y, z;
        x= a> b;
//a整除c的结果小于5,并将结果赋给y
        y = (a / c) < 5;
//将x和y进行与运算,然后再对结果进行非运算，并将结果赋给z
        z = !(x&&y);
        System. out.println( "x= " + x);
        System. out. println( "y= "+ y);
        System. out.println( "z= "+ z);

    }

}