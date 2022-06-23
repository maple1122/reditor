package mymanuscript;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/22 15:25
 */
public class FinishedScriptTest {

    @Test(priority = 1)//稿件签发
    public void testPush() throws InterruptedException {
        FinishedScript.push();
    }

    @Test(priority = 2)//传稿
    public void testFeature() throws InterruptedException {
        FinishedScript.feature();
    }

    @Test(priority = 3)//一键撤稿
    public void testBack() throws InterruptedException {
        FinishedScript.back();
    }

    @BeforeMethod
    public void testStart(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Test case: "
                + method.getName());
    }

    @AfterMethod
    public void testEnd(Method method) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<< Test End!\n");
    }
}