package mymanuscript;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/21 15:16
 */
public class ScriptManageTest {

    @Test(priority = 1)//稿件编辑
    public void testEditScript() throws InterruptedException {
        ScriptManage.editScript();
    }

    @Test(priority = 2)//稿件删除
    public void testDelScript() throws InterruptedException {
        ScriptManage.delScript();
    }

    @Test(priority = 3)//稿件提交
    public void testSubmitScript() throws InterruptedException {
        ScriptManage.submitScript();
    }

    @Test(priority = 4)//稿件撤回
    public void testRecallScript() throws InterruptedException {
        ScriptManage.recallScript();
    }

    @Test(priority = 5)//稿件提交并审核
    public void testSubmitAndVerify() throws InterruptedException {
        ScriptManage.SubmitAndVerify();
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