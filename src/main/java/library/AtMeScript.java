package library;

import base.LoginPortal;
import base.RMCommonMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author wufeng
 * 稿库@我的稿件进行删除
 * @date 2022/6/23 11:27
 */
public class AtMeScript extends LoginPortal {
    static WebDriver driver;

    //删除稿件
    public static void delete() throws InterruptedException {
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='finished-list']/li/div"))) {//校验是否有搜索结果数据
            driver.findElement(By.xpath("//ul[@class='finished-list']/li[1]/div[@class='item-content']/div/div[1]/div[2]/button[1]")).click();
            Thread.sleep(200);
            RMCommonMethod.getButtonDiv(driver).findElement(By.xpath("div/div[@class='el-dialog__footer']/span/button[2]")).click();
            System.out.println("~~~ delete()，删除@我的稿件，执行成功 ~~~");
        }else System.out.println("@我的稿，没有数据");
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
            driver.findElement(By.xpath("//p[@class='float-left']/span[3]")).click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}