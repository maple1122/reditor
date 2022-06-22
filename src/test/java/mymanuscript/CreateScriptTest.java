package mymanuscript;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/6/21 9:49
 */
public class CreateScriptTest {

    @Test(priority = 1)//新建文稿
    public void testAddArticle() throws InterruptedException {
        CreateScript.addArticle();
    }

    @Test(priority = 2)//新建图集稿件
    public void testAddImg() throws InterruptedException {
        CreateScript.addImg();
    }

    @Test(priority = 3)//新建视频稿件
    public void testAddVideo() throws InterruptedException {
        CreateScript.addVideo();
    }

    @Test(priority = 4)//新建音频稿件
    public void testAddAudio() throws InterruptedException {
        CreateScript.addAudio();
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