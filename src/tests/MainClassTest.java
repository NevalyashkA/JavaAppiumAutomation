package tests;

import org.junit.Test;

import main.MainClass;

public class MainClassTest {

    @Test
    public void myFirstTest()
    {
        if(MainClass.getLocalNumber()==14){
            System.out.println("Это число 14");
        }else {
            System.out.println("Это число не 14 =)");
        }
    }
}
