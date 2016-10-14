package com.Vdopia.portal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class WritingTestResults {

	Logger log = Logger.getLogger(this.getClass());
	static HashMap<String, String> testCaseResultsfinal= new HashMap<String, String>();



	public void writingTestStepResults(HashMap<String, String> teststepresults)
	{
		String path = System.getProperty("user.dir")+"//Results//Test_Results.xlsx";
		Xls_Reader xlread = new Xls_Reader(path);
		for(String key: teststepresults.keySet())
		{
			int rowNum = xlread.getCellRowNum("testCaseSteps", "Step_ID", key);
			xlread.setCellData("testCaseSteps", "TestStepResult", rowNum, teststepresults.get(key));
		}
	}
	
	public HashMap<String, String> savingTestCaseResults(HashMap<String, String> testCaseResults)
	{
		testCaseResultsfinal=testCaseResults;
		return testCaseResultsfinal;
	}

	public void writingTestCaseResults(HashMap<String, String> teststepresults)
	{
		String path = System.getProperty("user.dir")+"//Results//Test_Results.xlsx";
		Xls_Reader xlread = new Xls_Reader(path);
		HashMap<String, Boolean> testCaseResultMap = new HashMap<String,Boolean>();
		String result;
		for(String key: teststepresults.keySet())
		{
			String resulttc = key.substring(0,5);
			//System.out.println("Keyresult is"+resulttc);
			String resultaftersplit[]=teststepresults.get(key).split(":");
			String r= resultaftersplit[0];
			boolean flag=false;
			if(key.startsWith(resulttc))
			{
				if(r.equalsIgnoreCase("Pass"))
				{
					flag=true;
					testCaseResultMap.put(resulttc, flag);
				}
				else
				{
					flag=false;
					testCaseResultMap.put(resulttc, flag);
					break;
				}
			}
			
			
			
			
			
			int rowNum = xlread.getCellRowNum("executionControl", "TC_ID", key);
			xlread.setCellData("executionControl", "TestCaseResult", rowNum, testCaseResultsfinal.get(key));
		}
	}
	
	public String createTestResultFile()
	{
		String srcFilepath = System.getProperty("user.dir")+"//TestCases//Test_Cases_Transformer.xlsx";
		String destDirpath = System.getProperty("user.dir")+"//Results//";
		File srcFile = new File(srcFilepath);
		File destDir = new File(destDirpath);
		try {
			FileUtils.copyFileToDirectory(srcFile, destDir);
		} catch (IOException e) {
			log.debug("Not able to copy file in directory "+destDir);

		}
		File oldFile= new File(destDirpath+"//"+srcFile.getName());	
		File newFile= new File(destDirpath+"//Test_Results.xlsx");	
		oldFile.renameTo(newFile);
		String resultFilePath = System.getProperty("user.dir")+"//Results//Test_Results.xlsx";
		log.debug("Test Result Folder and File Created Successfully");
		return resultFilePath;				
	}
	public void addingResultColumn(String ResultFilePath)
	{
		Xls_Reader xlread = new Xls_Reader(ResultFilePath);
		xlread.addColumn("testCaseSteps", "TestStepResult");
		log.debug("Test Step Result Column added in Test Cases sheet successfully");

	}
	public void addingTestCaseResultColumn(String ResultFilePath)
	{
		Xls_Reader xlread = new Xls_Reader(ResultFilePath);
		xlread.addColumn("executionControl", "TestCaseResult");
		log.debug("Test Case Result Column added in Test Cases sheet successfully");

	}
}

