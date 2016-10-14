package com.Vdopia.portal;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericKeywords {

	Logger log = Logger.getLogger(this.getClass());

	public void readObjectRepositorySheet()
	{
		



	}
	public WebElement readObject(WebDriver driver, String objectname)
	{
		String oName;
		int objectRowNumber=0;
		Xls_Reader xlread = new Xls_Reader(System.getProperty("user.dir")+"//ObjectRepository//transformerPortal_ObjectRepository.xlsx");
		int rcount = xlread.getRowCount("objectRepository");
		for(int i=0;i<rcount;i++)
		{
			oName = xlread.getCellData("objectRepository", "objectName", i);
			if(oName.equalsIgnoreCase(objectname))
			{
				objectRowNumber=i;
				break;
			}
		}
		String identifier;
		String identifierValue;

		log.debug("For Object "+objectname+" present in objectRowNumber "+objectRowNumber);
		identifier=xlread.getCellData("objectRepository", "identifierName", objectRowNumber);
		identifierValue=xlread.getCellData("objectRepository", "identifierValue", objectRowNumber);
		log.debug("Identifier Value is: "+identifierValue);
		WebElement element=getElement(driver, identifier, identifierValue);
		return element;
	}


	public WebElement getElement(WebDriver driver, String identifier, String identifierValue)
	{
		WebElement element=null;
		if(identifier.equalsIgnoreCase("xpath"))
		{
			/**try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}**/
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(identifierValue)));
			element = driver.findElement(By.xpath(identifierValue));
		}
		else if(identifier.equalsIgnoreCase("id"))
		{
			/**try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}**/
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(identifierValue)));
			element = driver.findElement(By.id(identifierValue));
		}
		return element;
	}

	
}
