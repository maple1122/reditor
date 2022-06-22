package mymanuscript;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/22 13:57
 */
public class RecycleManageTest {

    @Test(priority = 1)//恢复稿件
    public void testResumeScript() throws InterruptedException {
        RecycleManage.resumeScript();
    }

    @Test(priority = 2)//删除单条稿件
    public void testDelScript() throws InterruptedException {
        RecycleManage.delScript();
    }

    @Test(priority = 3)//批量删除稿件
    public void testDelScripts() throws InterruptedException {
        RecycleManage.delScripts();
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