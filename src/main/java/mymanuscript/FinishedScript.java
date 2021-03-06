package mymanuscript;

import base.LoginPortal;
import base.RMCommonMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author wufeng
 * 成品稿，签发、传稿、一键撤稿
 * @date 2022/6/22 15:14
 */
public class FinishedScript extends LoginPortal {

    static WebDriver driver;

    //签发
    public static void push() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='finished-list']/li/div"))) {//校验是否有成品稿数据
            driver.findElement(By.xpath("//ul[@class='finished-list']/li[1]/div[@class='item-content']/div/div[1]/div[1]/button[1]")).click();//点击签发
            Thread.sleep(200);
            boolean push = RMCommonMethod.treeIframe(driver);//签发频道图层操作
            if (push) System.out.println("~~~ push()，稿件签发，执行成功 ~~~");
            else System.out.println("稿件签发失败");
        } else System.out.println("没有可签发的成品稿");
        Thread.sleep(3000);
    }

    //传稿
    public static void feature() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='finished-list']/li/div"))) {//校验是否有成品稿数据
            driver.findElement(By.xpath("//ul[@class='finished-list']/li[1]/div[@class='item-content']/div/div[1]/div[1]/button[4]")).click();//点击传稿
            Thread.sleep(200);
            driver.findElement(By.xpath("//div[@class='el-select']/div/input")).click();
            Thread.sleep(200);
            driver.findElement(By.xpath("//div[@class='el-select']/div/input")).sendKeys(usernick);
            Thread.sleep(200);
            if (RMCommonMethod.isJudgingElement(driver,By.xpath("//ul[@class='el-select-group']/li/span"))){
                driver.findElement(By.xpath("//ul[@class='el-select-group']/li")).click();
                Thread.sleep(100);
                driver.findElement(By.xpath("//textarea[@class='el-textarea__inner']")).sendKeys("这里是传稿说明。这里是传稿说明。这里是传稿说明。这里是传稿说明。这里是传稿说明。这里是传稿说明。");
                driver.findElement(By.xpath("//div[@class='content-list']/div[1]/div/div[@class='el-dialog__footer']/div/button[1]")).click();
                System.out.println("~~~ feature()，传稿，执行成功 ~~~");
            }else System.out.println("没有找到传稿目标-"+usernick);
        } else System.out.println("没有可传稿的成品稿");
        Thread.sleep(3000);
    }

    //一键撤稿
    public static void back() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='finished-list']/li/div"))) {//校验是否有成品稿数据
            driver.findElement(By.xpath("//ul[@class='finished-list']/li[1]/div[@class='item-content']/div/div[1]/div[1]/button[last()]")).click();//点击一键撤稿
            System.out.println("~~~ back()，一键撤稿，执行成功 ~~~");
        }else System.out.println("没有可传稿的成品稿");
        Thread.sleep(3000);
    }


    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {//校验是已登录成功，是否已正常打开页面
                if (!RMCommonMethod.isJudgingElement(driver, By.className("text-p"))) {
                    if (RMCommonMethod.isJudgingElement(driver, By.className("loginBtn"))) driver = login();
                    driver.get(domain + "/reditor/static/index.html#/mymanuscript");
                    Thread.sleep(2000);
                } else break;
            }
            driver.findElement(By.xpath("//ul[@class='tab-header clearfix']/li[2]")).click();//切换到成品稿
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
