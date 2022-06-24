package library;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/23 15:03
 */
public class ScriptLibraryTest {

    @Test(priority = 1)//签发
    public void testPush() throws InterruptedException {
        ScriptLibrary.push();
    }

    @Test(priority = 4)//退稿
    public void testReturnScript() throws InterruptedException {
        ScriptLibrary.returnScript();
    }

    @Test(priority = 5)//取消终审
    public void testCancel() throws InterruptedException {
        ScriptLibrary.cancel();
    }

    @Test(priority = 2)//传稿
    public void testFeature() throws InterruptedException {
        ScriptLibrary.feature();
    }

    @Test(priority = 3)//一键撤稿
    public void testBack() throws InterruptedException {
        ScriptLibrary.back();
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