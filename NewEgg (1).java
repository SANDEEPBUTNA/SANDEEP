package com.facebook.testScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;

public class NewEgg {

	public static void main(String[] args) throws InterruptedException {

		String chromePath = "C:\\Users\\Sandeep Butna\\Desktop\\Sandeep\\Softwares\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromePath);
		
		String item1 ="SanDisk Ultra 256GB";
		String item2 ="UNCHARTED: The Lost Legacy";
		String item3 ="INSTEON Thermostat (2441TH)";
		
		try{
			//open the chrome browser
			WebDriver driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			//Navigate to the application home page
			driver.get("http://newegg.com/");
			driver.manage().window().maximize();
			
			//click on login link
			driver.findElement(By.xpath(".//*[@id='usaSite']/a")).click();
			
			//enter newegg id and password and click on submit button
			driver.findElement(By.id("UserName")).sendKeys("ctschallenge20@gmail.com");
			driver.findElement(By.id("UserPwd")).sendKeys("Challenge20");
			driver.findElement(By.id("submit")).click();
			WebDriverWait wait = new WebDriverWait(driver, 10);
			
			//search "Micro SD Card" item and add to cart
			driver.findElement(By.id("haQuickSearchBox")).sendKeys("Micro SD Card");
			driver.findElement(By.xpath(".//*[@id='haFormQuickSearch']/div/div[3]/button")).click();
			//filtering items
			driver.findElement(By.id("SrchInDesc_top")).sendKeys(item1);
			driver.findElement(By.id("btn_InnerSearch")).click();
			driver.findElement(By.xpath(".//div[contains(@class,'items-view')]/div[3]/div/div[2]/div/div[1]/button")).click();
			
			//search "PS4 games" item and add to cart
			driver.findElement(By.id("haQuickSearchBox")).sendKeys("PS4 games");
			driver.findElement(By.xpath(".//*[@id='haFormQuickSearch']/div/div[3]/button")).click();
			//filtering items
			driver.findElement(By.id("SrchInDesc_top")).sendKeys(item2);
			driver.findElement(By.id("btn_InnerSearch")).click();
			driver.findElement(By.xpath(".//div[contains(@class,'items-view')]/div/div/div[2]/div/div[1]/button")).click();
			driver.findElement(By.xpath(".//*[@id='landingpage-cart']/div/div[2]/button")).click();
			
			//search "Smart Thermostats" item and add to cart
			driver.findElement(By.id("haQuickSearchBox")).sendKeys("Smart Thermostats");
			driver.findElement(By.xpath(".//*[@id='haFormQuickSearch']/div/div[3]/button")).click();
			//filtering items
			driver.findElement(By.id("SrchInDesc_top")).sendKeys(item3);
			driver.findElement(By.id("btn_InnerSearch")).click();
			driver.findElement(By.xpath(".//div[contains(@class,'items-view')]/div/div/div[2]/div/div[1]/button")).click();
			
			//validate the items in the cart
			String itemInCart2 = driver.findElement(By.xpath("//form[@name='form1']/table[2]/tbody/tr/td/div/div/a")).getAttribute("title");
			String itemInCart1 = driver.findElement(By.xpath("//form[@name='form1']/table[3]/tbody/tr/td/div/div/a")).getAttribute("title");
			String itemInCart3 = driver.findElement(By.xpath("//form[@name='form1']/table[4]/tbody/tr/td/div/div/a")).getAttribute("title");
			if(itemInCart1.contains(item1)&&itemInCart2.contains(item2)&&itemInCart3.contains(item3))
				System.out.println("All the items added to the cart are validated");
			
			//remove one item and check the total
			String totalTextBefore = driver.findElement(By.xpath("//form[@name='form1']/table/thead/tr/th/h1")).getText();
			String initialTotal = totalTextBefore.substring(33);
			Assert.assertEquals("$280.93", initialTotal);
			System.out.println("Total of all items: "+initialTotal);
			driver.findElement(By.id("79-261-688.1.0.0")).click();
			driver.findElement(By.id("removeFromCart")).click();
			String totalTextAfter = driver.findElement(By.xpath("//form[@name='form1']/table/thead/tr/th/h1")).getText();
			String totalAfter = totalTextAfter.substring(33);
			Assert.assertEquals("$240.94", totalAfter);
			System.out.println("Total after removing one item: "+totalAfter);
			
			//Modify the quanity of the item and validate the totals
			String itemTotalBefore = driver.findElement(By.xpath("//form[@name='form1']/table[2]/tbody/tr/td[3]/ul/li")).getText();
			Assert.assertEquals("$140.95", itemTotalBefore);
			System.out.println("Total at item level before modifying the quantities: "+itemTotalBefore);
			driver.findElement(By.xpath("//form[@name='form1']/table[2]/tbody/tr/td[2]/input")).clear();
			driver.findElement(By.xpath("//form[@name='form1']/table[2]/tbody/tr/td[2]/input")).sendKeys("4");
			driver.findElement(By.xpath("//form[@name='form1']/table/tbody/tr[2]/td[2]/a")).click();
			String itemTotal = driver.findElement(By.xpath("//form[@name='form1']/table[2]/tbody/tr/td[3]/ul/li")).getText();
			Assert.assertEquals("$563.80", itemTotal);
			System.out.println("Total at item level after modifying the quantities: "+itemTotal);
			String totalAfterQuantity = driver.findElement(By.xpath("//form[@name='form1']/table/thead/tr/th/h1")).getText();
			String totalAfterQuantityChanges = totalAfterQuantity.substring(33);
			Assert.assertEquals("$663.79", totalAfterQuantityChanges);
			System.out.println("Total at cart level after modifying the quantities: "+totalAfterQuantityChanges);
			
			//Validating the required fields in checkout page
			driver.findElement(By.xpath(".//div[@class='call-to-action-shopping-cart']/a[2]")).click();
			driver.findElement(By.xpath(".//div[contains(@class,'call-to-action-checkout')]/a")).click();
			String errorMessage = driver.findElement(By.xpath(".//div[@id='checkoutShippingAddPanel']/form/ul/li/label[2]")).getText();
			Assert.assertEquals("This field is required.",errorMessage);
			System.out.println("The error message for required fields is displayed as \""+errorMessage+"\"");
			
			//logout of the account
			driver.navigate().to("http://newegg.com/");
			driver.findElement(By.xpath(".//div[@class='top-nav-menu']/i")).click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//div[@class='top-nav-menu']/div/ul/li[4]/a")))).click();
			
			//reset password
			driver.findElement(By.xpath(".//*[@id='usaSite']/a")).click();
			driver.findElement(By.id("UserName")).sendKeys("ctschallenge20@gmail.com");
			driver.findElement(By.partialLinkText("Forgot your password?")).click();
			driver.findElement(By.name("loginname")).sendKeys("ctschallenge20@gmail.com");
			Thread.sleep(10000);
			driver.findElement(By.xpath(".//*[@id='QuickLinks']/dd/form/table/tbody/tr[6]/td/input")).click();
			
			//open gmail and complete password reset
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("sectionHead"))));
			driver.get("https://accounts.google.com");
			driver.findElement(By.id("identifierId")).sendKeys("ctschallenge20@gmail.com");
			driver.findElement(By.xpath(".//*[@id='identifierNext']/content/span")).click();
			driver.findElement(By.xpath(".//*[@id='password']/div[1]/div/div[1]/input")).sendKeys("Challenge2018");
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(".//*[@id='passwordNext']/content/span")))).click();
			driver.findElement(By.className("WaidBe")).click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("info@newegg.com")))).click();
			String href = driver.findElement(By.partialLinkText("Reset Password Link")).getAttribute("href");
			System.out.println(href);
			driver.navigate().to(href);
			try{
				driver.switchTo().alert().dismiss();
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}finally{
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("newpassword")))).sendKeys("Challenge20");
				driver.findElement(By.id("newpassword1")).sendKeys("Challenge20");
				driver.findElement(By.xpath(".//*[@id='form1']/div/a")).click();
				String passwordResetMsg = driver.findElement(By.xpath(".//*[@id='infoContent']/span/p[2]")).getText();
				Assert.assertEquals("Your password has been successfully updated!", passwordResetMsg);
				System.out.println("The password has been reset successfully!");
				driver.close();
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
