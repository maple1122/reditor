package base;

import org.openqa.selenium.*;

import java.util.List;

import static base.LoginPortal.channel1;
import static base.LoginPortal.siteName;

/**
 * @author wufeng
 * @date 2022/2/7 14:54
 */
public class RMCommonMethod {

    //素材库库获取素材文件，iframe跳转，type=1、2、3、4：图片、音频、视频、竖视频
    public static void getMaterial(WebDriver driver, int type) throws InterruptedException {

        Thread.sleep(1000);
        if (!isAlert(driver) && type >= 1 && type <= 4) {//校验没有异常弹窗并且type在1-4范围
            driver.switchTo().frame("material_iframe");//切换到资源库frame进行操作
            Thread.sleep(1000);
            if (type == 1) getPic(driver);//图片素材库
            if (type == 2) getAudio(driver);//音频素材库
            if (type == 3) getVideo(driver);//视频素材库
            driver.switchTo().parentFrame();//退出当前iframe
            Thread.sleep(1000);
        }
        Thread.sleep(3000);
    }

    //素材库获取图片及视频，在素材页选择图片
    public static void getPic(WebDriver driver) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread.sleep(3000);
            if (isJudgingElement(driver, By.xpath("//ul[@class='mtl_imageList clearfix image-list']/li")))//校验是否返回了素材数据，如果没有循环等待3次，每次3秒
                break;
        }
        //获取素材数据列表
        List<WebElement> pics = driver.findElements(By.xpath("//ul[@class='mtl_imageList clearfix image-list']/li"));//获取图片数据列表

        //判断是否有图片素材，无素材则结束；有素材>则选择第1个图片
        if (pics.size() > 0) {
            if (pics.size() > 3)
                for (int p = 1; p < 4; p++)
                    driver.findElement(By.xpath("//ul[@class='mtl_imageList clearfix image-list']/li[" + p + "]/div")).click();//选择前3张图片
            else
                driver.findElement(By.xpath("//ul[@class='mtl_imageList clearfix image-list']/li[1]/div")).click();//选择第一张图片

            driver.findElement(By.cssSelector("button.mtl_btn.yes")).click();//融媒页确认添加图片返回
        } else {
            System.out.println("没有可用素材！");
            driver.findElement(By.cssSelector("button.mtl_btn.cancel")).click();//融媒页关闭返回
        }
    }

    //素材库获取音频，在素材页选择音频
    public static void getAudio(WebDriver driver) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            Thread.sleep(3000);
            if (isJudgingElement(driver, By.xpath("//ul[@class='mtl_audioList']/li")))//校验是否返回了素材数据，如果没有循环等待3次，每次3秒
                break;
        }
        //获取素材数据列表
        List<WebElement> audioes = driver.findElements(By.xpath("//ul[@class='mtl_audioList']/li"));//获取音频素材数据列表

        //判断是否有音频素材，无素材则结束；有素材>则选择第1个音频
        if (audioes.size() > 0) {
            driver.findElement(By.xpath("//ul[@class='mtl_audioList']/li[1]/input")).click();//选择第一张音频
            driver.findElement(By.cssSelector("button.mtl_btn.yes")).click();//融媒页确认添加音频返回
        } else {
            System.out.println("没有可用素材！");
            driver.findElement(By.cssSelector("button.mtl_btn.cancel")).click();//融媒页关闭返回
        }
    }

    //素材库获取视频，在素材页选择视频
    public static void getVideo(WebDriver driver) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread.sleep(3000);
            if (isJudgingElement(driver, By.xpath("//ul[@class='mtl_videoList clearfix']/li")))//校验是否返回了素材数据，如果没有循环等待3次，每次3秒
                break;
        }
        //获取素材数据列表
        List<WebElement> videoes = driver.findElements(By.xpath("//ul[@class='mtl_videoList clearfix']/li"));//获取视频数据列表

        //判断是否有视频素材，无素材则结束；有素材>则选择第1个视频
        if (videoes.size() > 0) {
            driver.findElement(By.xpath("//ul[@class='mtl_videoList clearfix']/li[1]/div")).click();//选择第一个视频

            driver.findElement(By.cssSelector("button.mtl_btn.yes")).click();//融媒页确认添加图片返回
        } else {
            System.out.println("没有可用素材！");
            driver.findElement(By.cssSelector("button.mtl_btn.cancel")).click();//融媒页关闭返回
        }
    }

    //签发图层
    public static boolean treeIframe(WebDriver driver) throws InterruptedException {
        boolean success = false;
        driver.switchTo().frame("treeIframe");//切换到签发图层
        Thread.sleep(500);
        driver.findElement(By.cssSelector("div.el-input.el-input--suffix")).click();//点击渠道选择框
        Thread.sleep(200);
        if (RMCommonMethod.isJudgingElement(driver, By.xpath("//ul[@class='el-scrollbar__view el-select-dropdown__list']/li"))) {//校验是否有渠道数据
            List<WebElement> li = driver.findElements(By.xpath("//ul[@class='el-scrollbar__view el-select-dropdown__list']/li"));//获取渠道数据列表
            for (int i = 0; i < li.size(); i++) {
                if (li.get(i).findElement(By.xpath("p")).getText().equals(siteName)) {//校验渠道是否是目标测试渠道
                    li.get(i).click();//点击渠道
                    success = true;
                    break;
                }
            }
            Thread.sleep(1000);
            if (success) {
                if (selectChannel(driver))//选择测试频道是否成功
                    driver.findElement(By.cssSelector("button.el-button.lz-tree-ok.el-button--default")).click();//点击确定
                else {
                    success = false;
                    driver.findElement(By.cssSelector("button.el-button.lz-tree-cancel.el-button--default")).click();//点击取消
                }
            } else System.out.println("签发渠道中没有" + siteName);
        } else System.out.println("没有可签发的渠道");
        driver.switchTo().parentFrame();//退出签发iframe，返回到上一级
        Thread.sleep(1000);
        return success;
    }

    //选择测试频道
    public static boolean selectChannel(WebDriver driver) throws InterruptedException {
        boolean selected = false;
        //一级频道
        List<WebElement> li1 = driver.findElements(By.xpath("//div[@class='lz-tree-box']/div[2]/div"));//一级频道列表
        for (int i1 = 0; i1 < li1.size(); i1++) {
            if (!li1.get(i1).findElement(By.xpath("div[1]/span")).getAttribute("class").contains("is-leaf")) {//如果有下一级可展开
                li1.get(i1).findElement(By.xpath("div[1]/span")).click();//点击展开第二级频道
                Thread.sleep(200);
                //二级频道
                List<WebElement> li2 = li1.get(i1).findElements(By.xpath("div[2]/div"));//第二级频道的数据列表
                for (int i2 = 0; i2 < li2.size(); i2++) {
                    if (li2.get(i2).findElement(By.xpath("div/span[2]")).getText().contains(channel1)) {//第二级频道名称是否有测试test
                        li2.get(i2).findElement(By.xpath("div/label/span")).click();//点击勾选
                        selected = true;
                        break;
                    }
                    Thread.sleep(100);
                }
            }
            if (selected) break;
            Thread.sleep(100);
        }
        if (!selected) System.out.println("没有找到测试test频道");
        Thread.sleep(1000);
        return selected;
    }


    //获取有效确定按钮
    public static WebElement getButtonDiv(WebDriver driver) {
        WebElement buttonDiv = null;
        List<WebElement> list = driver.findElements(By.className("el-dialog__wrapper"));
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getAttribute("style").contains("display")){
                buttonDiv=list.get(i);
                break;
            }
        }
        return buttonDiv;
    }

    //校验元素是否能找到
    public static boolean isJudgingElement(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;//有登录按钮，登录界面
        } catch (Exception e) {
            return false;//无登录按钮，非登录界面
        }
    }

    //判断是否有alert
    public static boolean isAlert(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    //校验元素是否存在
//    public static boolean isJudgingElement(WebElement webElement, By by) {
//        try {
//            webElement.findElement(by);
//            return true;//有登录按钮，登录界面
//        } catch (Exception e) {
//            return false;//无登录按钮，非登录界面
//        }
//    }

//    //校验元素有某个属性
//    public static boolean isAttribtuePresent(WebElement element, String attribute) {
//        try {
//            element.getAttribute(attribute);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

//    //获取alert文案、关闭alert
//    public static String closeAlert(WebDriver driver) {
//        Alert alert = driver.switchTo().alert();
//        String text = alert.getText();
//        System.out.println(text);
//        alert.accept();
//        return text;
//    }

}
