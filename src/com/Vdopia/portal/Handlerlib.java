package com.Vdopia.portal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Handlerlib {
	Logger log = Logger.getLogger(this.getClass());
	@SuppressWarnings("finally")
	public boolean moveToFrame(WebDriver driver, String frameName)
	{
		boolean flag = false;
		try{
			driver.switchTo().defaultContent();
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
			log.debug("Switching to frame: "+frameName);			
			flag = true;
		}
		catch(Exception e)
		{
			flag = false;
			log.debug("Error occurred while switching frame"+e);
		}
		finally
		{
			return flag;
		}
	}

	public String connection(String query)
	{
		String mysqlquery=query;
		String result=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://54.235.172.134:3306/adplatform","root","QA@1234");
			Statement stmt = con.createStatement();
			System.out.println("Running mysql query"+mysqlquery);
			ResultSet rs = stmt.executeQuery(mysqlquery);
			while(rs.next())
			{
				result=rs.getString(1);

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;

	}
	/**
	public Logger logHandler()
	{
		Logger log = Logger.getLogger("devpinoyLogger");
		return log;
	}
	public static Logger writelogs(String msg)
	{
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("/Users/mukeshkumar/Documents/MarsWorkspace/Portal_Test/UsedFiles/log4j.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties p =new Properties();
		try {
			p.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(p);
		Logger logobj = Logger.getLogger(Handlerlib.class.getName());
		logobj.info(msg);
		return logobj;
	}**/

}
