package com.Vdopia.portal;

import java.util.concurrent.TimeUnit;

import org.apache.james.mime4j.storage.StorageProvider;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Keywords {
	public  String objectName2="";
	public  String data2="";		
	public static WebDriver driver;
	GenericKeywords genericKeywordsObject = new GenericKeywords();
	Handlerlib handlerLib = new Handlerlib();
	//public static WebDriverWait wait = new WebDriverWait(driver, 60);
	Logger log = Logger.getLogger(this.getClass());
	public String result_pass="Pass: ";
	public String result_fail="Fail: ";
	
	public String  launchbrowser(String objectName, String data)
	{
		String result;
		objectName2 = objectName;
		data2 = data;
		log.info("In Launch Browser Method");
		log.debug("Received browser is "+data);
		if(data.equalsIgnoreCase("chrome"))
		{
			log.debug("Starting launching Browser: "+data);

			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//UsedFiles//chromedriver");
			driver=new ChromeDriver();
			log.info("Browser Launched is "+data);
			result = result_pass + "Browser Launched successfully";
			//log.debug(result_pass+" : "+result);
			
		}
		else
		{
			log.debug("No such browser exists");
			result=result_fail+"Browser not found";
			//log.debug(result_fail+" : "+result);

		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return result;
	}


	public String navigateurl(String objectName, String data)
	{

		String result;
		objectName2 = objectName;
		data2 = data;

		if(driver!=null)
		{
			driver.get(data);	
			result = result_pass+"Navigated url successfully";
			//log.debug(result_pass+" : "+result);

		}
		else
		{
			result=result_fail+"Couldn't navigate to url.";
			//log.debug(result_fail+" : "+result);

		}
		return result;

	}
	public String selectradiobutton(String objectname, String data)
	{
		String result=null;
		WebElement element;
		if(objectname!="")
		{
			element = genericKeywordsObject.readObject(driver, objectname);
			element.click();
			result = result_pass+"Radio button selected successfully";
			//log.debug(result_pass+" : "+result);

		}
		else
		{
			result=result_fail+"Object name doesn't exists. Please check Object Repository";
			//log.debug(result_fail+" : "+result);

		}

		return result;
	}
	public String typevalue(String objectname, String data) throws InterruptedException
	{
		String result=null;
		WebElement element;

		if(objectname!="")
		{
			element = genericKeywordsObject.readObject(driver, objectname);			
			element.clear();
			element.sendKeys(data);
			log.debug("Data typed is "+data);
			result=result_pass+"Data entered sucessfully";
			//log.debug(result_pass+" : "+result);

		}
		else
		{
			result=result_fail+"Object name doesn't exists. Please check Object Repository";			
			//log.debug(result_fail+" : "+result);
		}

		return result;

	}
	public String clickbutton(String objectname, String data)
	{
		String result=null;
		WebElement element;
		if(objectname!="")
		{
			element = genericKeywordsObject.readObject(driver, objectname);
			element.click();
			result = result_pass+": Button Clicked successfully";
			//log.debug(result_pass+" : "+result);

		}
		else
		{
			result=result_fail+"Object name doesn't exists. Please check Object Repository";			
			//log.debug(result_fail+" : "+result);
		}

		return result;

	}
	public String movetopage(String objectname, String data)
	{
		String result=null;
		WebElement element;
		boolean methodresult;
		if(data.equalsIgnoreCase("View Channels"))
		{
			methodresult = handlerLib.moveToFrame(driver, "ifrm");
			if(methodresult==true)
			{
				result = result_pass+"Moved to page: "+data+" sucessfully";
				//log.debug(result_pass+" : "+result);
				
			}
			else
			{
				result = result_fail+"Unable to move to page: "+data;
				//log.debug(result_fail+" : "+result);

			}
		}
		else
		{
			result=result_fail+"Object name doesn't exists. Please check Object Repository";			
			//log.debug(result_fail+" : "+result);
		}
		return result;

	}
	public String selectcheckbox(String objectname, String data)
	{
		String result=null;
		WebElement element;
		if(objectname!="")
		{
			element = genericKeywordsObject.readObject(driver, objectname);
			element.click();
			result = result_pass+"CheckBox selected successfully";
			//log.debug(result_pass+" : "+result);

		}
		else
		{
			result=result_fail+"Object name doesn't exists. Please check Object Repository";			
			//log.debug(result_fail+" : "+result);
		}

		return result;

	}
	public String verifytext(String objectname, String data)
	{
		String result=null;
		String msgtext=null;
		WebElement element;
		if(objectname!="")
		{
			element = genericKeywordsObject.readObject(driver, objectname);
			msgtext=element.getText().trim();
			if(msgtext.equalsIgnoreCase(data))
			{
				result = result_pass+"Message text is as expected";
				//log.debug(result_pass+" : "+result);

			}
			else {
				result = result_fail+"Message text not as expected";
				//log.debug(result_fail+" : "+result);

			}
		}
		else
		{
			result=result_fail+"Object name doesn't exists. Please check Object Repository";			
			//log.debug(result_fail+" : "+result);
		}

		return result;

	}

	@SuppressWarnings("unused")
	public String verifydbdetails(String objectname, String data)
	{
		String result=null;
		String msgtext=null;
		String query = objectname;
		String queryresult=null;
		WebElement element;
		if(objectname.isEmpty())
		{
			result = result_fail+"No query was supplied";
			//log.debug(result_fail+" : "+result);

		}

		else
		{
			log.debug("Query is "+query);
			queryresult = handlerLib.connection(query);
			
			log.debug("Output of query is"+queryresult);
			if(queryresult!=null)
			{
				/**
				if(data.contains("#"))
				{
					
				}**/
				
				
				if(queryresult.equalsIgnoreCase(data))
				{
					
					result = result_pass+"Data is as Expected";
					//log.debug(result_pass+" : "+result);


				}
				else
				{
					result=result_fail+"Data is not as Expected";
					//log.debug(result_fail+" : "+result);

				}
			}
			else 
				result = result_fail+"Received null from database";
				//log.debug(result_fail+" : "+result);

		}

		return result;

	}
	public String closebrowser(String objectname, String data)
	{
		String result=null;
		log.info("Preparing to close browser");
		driver.quit();
		result=result_pass+"Browser Closed";
		//log.debug(result_pass+"Browser Closed");
		return result;
		
	}

}
