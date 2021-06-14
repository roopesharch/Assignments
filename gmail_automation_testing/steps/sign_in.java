package steps;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import io.cucumber.java.en.*;



public class sign_in  {
	
//	 Enter properties file path 
	 String Properties_file_path ="/Users/roopesharch/Desktop/config.properties" ;
//	 Enetr Chrome driver path
	 String chromedriver_path ="/Users/roopesharch/Desktop/chromedriver";
//	 Initialise driver
	 WebDriver driver = create_driver_instance(chromedriver_path);
	
	
//	 method to access properties file 
	 private static  List<Object> getproperties(String Properties_file_path) throws IOException {
		 
		 try (InputStream input = new FileInputStream(Properties_file_path)) {
	            Properties prop = new Properties();
	            prop.load(input);
	            String URL = prop.getProperty("URL").toString();
	            String to= prop.getProperty("to").toString();
	            String subject= prop.getProperty("subject").toString();
	            String body= prop.getProperty("body").toString();
	            String username= prop.getProperty("username").toString();
	            String password= prop.getProperty("password").toString();
	            return Arrays.asList(URL,to,subject,body,username,password);

	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } 	return null;  }

//	method to instantiate driver 
	public WebDriver create_driver_instance(String chromedriver_path)  {
		
		WebDriver driver ;
		System.setProperty("webdriver.chrome.driver",chromedriver_path);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize(); 
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        System.out.println(" \t*******\t browser open");
		return driver ;
	}

	 
	
	
	
	@Given("Opened browser and Navigate to google sign in page")
	public void startbrowser() throws InterruptedException, IOException {
		List<Object> properties =getproperties(Properties_file_path);
		String URL=properties.get(0).toString();
		driver.get(URL);		
		
	}
	
	@And("login with google account")
	public void login_google_account() throws InterruptedException, IOException {
		List<Object> properties =getproperties(Properties_file_path);
		String username=properties.get(4).toString();
		String password=properties.get(5).toString();
		 Thread.sleep(4000);
//	        String username = "autohacksmith@gmail.com"; 
	        driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).sendKeys(username);
	    
	        Thread.sleep(3000);
	        driver.findElement(By.xpath("//*[@id=\"identifierNext\"]")).click();
	        
	        Thread.sleep(5000);
//	        String password = "P@SSw0rd"; 
	        driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")).sendKeys(password);
	    	
	    	
	    	Thread.sleep(2000);
	        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]")).click();
		
	}
	
	@When("Navigated to gmail screen and compose button is invoked")
	public void  navigate_gmail() throws InterruptedException {		
		Thread.sleep(10000);
		   driver.get("https://mail.google.com/mail/u/0/#");

		   Thread.sleep(10000);
		   driver.findElement(By.xpath("//*[text()=\"Compose\"]")).click();

	}
	
	
	@And("Enter to_mail address")
	public void enter_to_mail_address() throws InterruptedException, IOException {
		
		List<Object> properties =getproperties(Properties_file_path);
		String to_address=properties.get(1).toString();
		 Thread.sleep(4000);
		 driver.findElement(By.xpath("//*[@name=\"to\"]")).sendKeys(to_address);
		System.out.println("");
	}

	@And("Enter subject for mail")
	public void enter_subject_for_mail() throws InterruptedException, IOException  {
		Thread.sleep(5000);
		List<Object> properties =getproperties(Properties_file_path);
		String subject=properties.get(2).toString();
        driver.findElement(By.xpath("//*[@name=\"subjectbox\"]")).sendKeys(subject);
		System.out.println("");
	}

	@And("Enter Body of the mail")
	public void enter_Body_of_the_mail() throws InterruptedException, IOException  {
		Thread.sleep(3000);
		List<Object> properties =getproperties(Properties_file_path);
		String body=properties.get(3).toString();
		 driver.findElement(By.xpath("//div[@aria-label=\"Message Body\"]")).sendKeys(body);
		System.out.println("");
	}



	@Then("send mail and verify the status")
	public void mail_reached() throws InterruptedException {
		Thread.sleep(6000);
    driver.findElement(By.xpath("//div[@data-tooltip=\"Send ‪(⌘Enter)‬\"]")).click();
    Thread.sleep(8000);
    boolean exists = driver.findElements( By.xpath("//*[@name=\"to\"]") ).size() != 0 ;
		if (exists== true){
			   
			   driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			   driver.findElement(By.xpath("//*[@name=\"to\"]")).click();
		}
		else if (exists== false){
			   System.out.println("Message sent successfully");
		}
		System.out.println("");

	}
	
	@And("Close webdriver")
	public void close_driver()  {
		driver.close();
		driver.quit();
		System.out.println("driver closed successfully");
	}
	

	
	
	
}
