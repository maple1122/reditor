package manuscript;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/23 9:53
 */
public class AuditTest {

    @Test(priority = 1)//稿件终审
    public void testFinalAudit() throws InterruptedException {
        Audit.finalAudit();
    }

    @Test(priority = 2)//稿件转审
    public void testTransAudit() throws InterruptedException {
        Audit.transAudit();
    }

    @Test(priority = 3)//稿件退回
    public void testReturnScript() throws InterruptedException {
        Audit.returnScript();
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