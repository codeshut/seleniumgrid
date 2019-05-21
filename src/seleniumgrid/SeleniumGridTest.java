package seleniumgrid;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SeleniumGridTest {

	//This annotation accepts the "name" value (browser) from testng.xml
	// @Parameters("browser")
	//TestNG annotation
	@Test(dataProvider = "getData")
	public void testGrid(String username, String password , String browse) throws MalformedURLException {
		
		
		ChromeOptions co = null;
		FirefoxOptions fo = null;
		RemoteWebDriver driver = null;
		
		System.out.println("Executing on "+browse);
		
		if(browse.equals("chrome")) {		
		
		//ChromeOptions is used to manage options specific to ChromeDriver
		//Google for additional ChromeDriver specific options 
		co = new ChromeOptions();
		co.addArguments("start-maximized");
		
		// RemoteWebDriver class is used to execute the tests on remote machines or nodes
		// RemoteWebDriver has multiple constructors
		
		//initiate the remote web driver with ChromeOptions reference
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), co);
		
		}else {
			
			//FirefoxOptions is same as ChromeOptions, but for firefox browser
			fo = new FirefoxOptions();
			//initiate the remote web driver with FirefoxOptions reference
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), fo);
			
		}
				
		
		//Navigate to the application
		driver.get("https://the-internet.herokuapp.com/login");
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		//Store the message displayed on the webpage after successful login
		WebElement successMsg = driver.findElement(By.xpath("//div[@id='flash']"));

		//Verify if above text is displayed
		Assert.assertTrue(successMsg.isDisplayed());

		driver.quit();
	}
	
	@DataProvider(parallel=true)
	public Object[][] getData() {

		Object data[][] = new Object[2][3];

		data[0][0] = "tomsmith";
		data[0][1] = "SuperSecretPassword!";
		data[0][2] = "chrome";

		data[1][0] = "tomsmith";
		data[1][1] = "SuperSecretPassword!";
		data[1][2] = "firefox";
		
		return data;

	}
}
