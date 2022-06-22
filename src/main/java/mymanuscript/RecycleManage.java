package mymanuscript;

import base.LoginPortal;
import base.RMCommonMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * @author wufeng
 * 回收站删除、恢复稿件
 * @date 2022/6/22 11:29
 */
public class RecycleManage extends LoginPortal {

    static WebDriver driver;

    //回收站恢复稿件
    public static void resumeScript() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='list-cont']/li"))) {//校验回收站是否有数据
            driver.findElement(By.xpath("//ul[@class='list-cont']/li[1]/div[@class='item-cont']/div/span[2]")).click();//点击第一条数据的恢复
            Thread.sleep(200);
            System.out.println("~~~ resumeScript()，回收站恢复稿件，执行成功 ~~~");
        } else System.out.println("回收站没有数据");
    }

    //回收站删除稿件
    public static void delScript() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='list-cont']/li"))) {//校验回收站是否有数据
            driver.findElement(By.xpath("//ul[@class='list-cont']/li[1]/div[@class='item-cont']/div/span[1]")).click();//点击第一条数据的删除
            Thread.sleep(200);
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();//确定删除
            System.out.println("~~~ delScript()，回收站删除稿件，执行成功 ~~~");
        } else System.out.println("回收站没有数据");
    }

    //回收站删除所有稿件
    public static void delScripts() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='list-cont']/li"))) {//校验回收站是否有数据
            driver.findElement(By.xpath("//div[@class='float-left btn-group']/div/label/span/span")).click();//点击全选
            Thread.sleep(200);
            driver.findElement(By.xpath("//div[@class='float-left btn-group']/button[1]")).click();//点击批量操作的彻底删除
            Thread.sleep(200);
            driver.findElement(By.cssSelector("button.el-button.el-button--default.el-button--small.el-button--primary")).click();//点击确定
            System.out.println("~~~ delScripts()，回收站批量删除稿件，执行成功 ~~~");
        } else System.out.println("回收站没有数据");
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!RMCommonMethod.isJudgingElement(driver, By.className("text-p"))) {
                    if (RMCommonMethod.isJudgingElement(driver, By.className("loginBtn"))) driver = login();
                    driver.get(domain + "/reditor/static/index.html#/mymanuscript/recovery");
                    Thread.sleep(2000);
                } else break;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
