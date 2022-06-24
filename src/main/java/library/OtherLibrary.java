package library;

import base.LoginPortal;
import base.RMCommonMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author wufeng
 * 稿库用稿
 * @date 2022/6/23 11:28
 */
public class OtherLibrary extends LoginPortal {
    static WebDriver driver;

    //用稿
    public static void use() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='finished-list']/li/div"))) {//校验是否有数据
            driver.findElement(By.xpath("//ul[@class='finished-list']/li[1]/div[@class='item-content']/div/div[1]/div/button[5]")).click();//点击用稿
            Thread.sleep(200);
            driver.findElement(By.className("el-textarea__inner")).sendKeys("autoTest-用稿意见反馈-" + System.currentTimeMillis());//录入用稿反馈
            RMCommonMethod.getButtonDiv(driver).findElement(By.xpath("div/div[@class='el-dialog__footer']/span/button[1]")).click();//点击下一步
            Thread.sleep(1000);
            driver.findElement(By.xpath("//header/button[1]")).click();//点击保存
            System.out.println("~~~ use()，用稿，执行成功 ~~~");
        } else System.out.println("没有稿件数据");
        Thread.sleep(3000);
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!RMCommonMethod.isJudgingElement(driver, By.className("text-p"))) {
                    if (RMCommonMethod.isJudgingElement(driver, By.className("loginBtn"))) driver = login();
                    driver.get(domain + "/reditor/static/index.html#/library");
                    Thread.sleep(2000);
                } else break;
            }
            driver.findElement(By.xpath("//p[@class='float-left']/span[last()]")).click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
