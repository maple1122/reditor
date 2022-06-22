package base;

import org.openqa.selenium.*;

import java.util.List;

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

    //获取指定测试频道
    public static void getTestTree(WebDriver driver, String id, String channelName) throws InterruptedException {

        List<WebElement> lis1, lis2;
        Boolean isTest = false;
        for (int i = 0; i < 5; i++) {
            Thread.sleep(2000);
            if (isJudgingElement(driver, By.xpath("//ul[@id='" + id + "']/li"))) break;
        }
        lis1 = driver.findElements(By.xpath("//ul[@id='" + id + "']/li"));//一级list
        for (int i = 0; i < lis1.size(); i++) {
            lis2 = lis1.get(i).findElements(By.xpath("ul/li"));//二级list
            for (int j = 0; j < lis2.size(); j++) {
                if (lis2.get(j).findElement(By.xpath("a/span[2]")).getText().contains(channelName)) {//二级中是否有其他频道名称
                    if (!lis2.get(j).findElement(By.xpath("a")).getAttribute("class").contains("curSelectedNode")) {
                        lis2.get(j).findElement(By.xpath("a/span[2]")).click();//点击该频道
                        if (isJudgingElement(lis2.get(j), By.xpath("span[@class='button chk radio_false_full']")))//如果是选择框方式
                            lis2.get(j).findElement(By.xpath("span[@class='button chk radio_false_full']")).click();//点击选择框
                    }
                    isTest = true;
                    break;
                }
            }
            if (isTest) break;
        }
        Thread.sleep(3000);
    }

    //获取默认测试频道“测试test”
    public static void getTestTree(WebDriver driver) throws InterruptedException {
        getTestTree(driver, "columnTree_1_ul", "测试test");
    }

    //获取自动化测试数据，type=1，待编区；type=2，成稿区
    public static WebElement getTestArticle(WebDriver driver, int type) throws InterruptedException {

        if (type == 1 || type == 2) {
            if (type == 1 && !isJudgingElement(driver, By.cssSelector("div.accordion.accordionlist.accordion1.opened"))) {//待编区，且未打开状态
                driver.findElement(By.cssSelector("div.accordion.accordionlist.accordion1")).click();//打开待编区
                Thread.sleep(2000);
            }
            if (isJudgingElement(driver, By.xpath("//ul[@id='ulListArea" + type + "']/li/div"))) {
                String articleName;
                List<WebElement> articles = driver.findElements(By.xpath("//ul[@id='ulListArea" + type + "']/li"));
                for (int i = 0; i < articles.size(); i++) {
                    articleName = articles.get(i).findElement(By.xpath("div/div/div[@class='article-content']/div/section/a")).getText();
                    if (articleName.contains("autoTest") && !articleName.contains("样式卡")) {
                        return articles.get(i);
                    }
                }
            }
        } else System.out.println("type传值错误，1为待编区；2为成稿区；其他值无效");
        return null;
    }

    //获取自动化测试数据
    public static WebElement getTestArticle(WebDriver driver, int type, String exclude) throws InterruptedException {

        if (type == 1 || type == 2) {
            if (type == 1 && !isJudgingElement(driver, By.cssSelector("div.accordion.accordionlist.accordion1.opened"))) {//待编区，且未打开状态
                driver.findElement(By.cssSelector("div.accordion.accordionlist.accordion1")).click();//打开待编区
                Thread.sleep(2000);
            }
            if (isJudgingElement(driver, By.xpath("//ul[@id='ulListArea1']/li/div"))) {
                String articleName;
                List<WebElement> articles = driver.findElements(By.xpath("//ul[@id='ulListArea" + type + "']/li"));
                for (int i = 0; i < articles.size(); i++) {
                    articleName = articles.get(i).findElement(By.xpath("div/div/div[@class='article-content']/div/section/a")).getText();
                    if (articleName.contains("autoTest") && !articleName.contains(exclude)) {
                        return articles.get(i);
                    }
                }
            }
        } else System.out.println("type传值错误，1为待编区；2为成稿区；其他值无效");
        return null;
    }

    //切换到功能页面
    public static void changeMenu(WebDriver driver, String type) throws InterruptedException {
        if (!driver.findElement(By.xpath("//ul[@id='headNavMenu']/li[@class='layui-nav-item layui-this']")).getText().equals(type)) {//校验当前是否在目标tab页
            List<WebElement> menu = driver.findElements(By.xpath("//ul[@id='headNavMenu']/li"));//菜单列表
            for (int i = 0; i < menu.size(); i++) {
                if (menu.get(i).getText().equals(type)) {//菜单标题等于其他tab
                    menu.get(i).click();//点击进入该tab页
                    Thread.sleep(2000);
                    break;
                }
            }
        }
    }

    //获取自动化测试数据
    public static void searchTestArticles(WebDriver driver, int type) throws InterruptedException {
        if (type == 1 && !isJudgingElement(driver, By.cssSelector("div.accordion.accordionlist.accordion1.opened"))) {//待编区，且未打开状态
            driver.findElement(By.cssSelector("div.accordion.accordionlist.accordion1")).click();//打开待编区
            Thread.sleep(2000);
        }
        driver.findElement(By.xpath("//form[@id='formDemo']/ul/li[@class='item search']/input")).sendKeys("autoTest");//录入搜索关键词
        Thread.sleep(200);
        driver.findElement(By.xpath("//form[@id='formDemo']/ul/li[@class='item search']/a")).click();//点击搜索
        Thread.sleep(1500);
    }

    //校验元素是否存在
    public static boolean isJudgingElement(WebElement webElement, By by) {
        try {
            webElement.findElement(by);
            return true;//有登录按钮，登录界面
        } catch (Exception e) {
            return false;//无登录按钮，非登录界面
        }
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

    //校验元素有某个属性
    public static boolean isAttribtuePresent(WebElement element, String attribute) {
        try {
            element.getAttribute(attribute);
            return true;
        } catch (Exception e) {
            return false;
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

    //获取alert文案、关闭alert
    public static String closeAlert(WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        System.out.println(text);
        alert.accept();
        return text;
    }

}
