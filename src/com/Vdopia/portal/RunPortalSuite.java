package com.Vdopia.portal;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;
import com.Vdopia.portal.ReadingTestCases;



import org.testng.annotations.BeforeClass;



public class RunPortalSuite {
	public Logger log = Logger.getLogger(this.getClass());
	HashMap<String, Boolean> map = new HashMap<String, Boolean>();
	WritingTestResults writingTestResults = new WritingTestResults();

	@BeforeClass
	public void BeforeClass()
	{
		PropertyConfigurator.configure(System.getProperty("user.dir")+"//UsedFiles//log4j.properties");
		log.info("/************************Starting Portal Test***************************/");
		
		log.info("/************************Creating Test Result Folder and File***************************/");
		String ResultFilePath = writingTestResults.createTestResultFile();
		System.out.println("ResultFilePath is"+ResultFilePath);
		
		log.info("/************************Adding Result Column in Test Case Sheet***************************/");
		writingTestResults.addingResultColumn(ResultFilePath);
		
		log.info("/************************Adding Result Column in Test Eecution Control Sheet***************************/");
		writingTestResults.addingTestCaseResultColumn(ResultFilePath);
		

	}

	/** Running Tests **/
	@Test
	public void readTCSheet()
	{	
		ReadingTestCases readtest = new ReadingTestCases();
		/**
		 * Get all runnable test case id which has RUN = Yes from execution control sheet 
		 */
		List <String> tc_id = readtest.readRunnableTestCases("executionControl");

		/**
		 * Get results of runnable test case id into a hashmap
		 */
		HashMap<String, String> teststepresults = readtest.runTestSteps(tc_id, "testCaseSteps");
		
		/**
		 * Writing Test Result per Test Step
		 */
		writingTestResults.writingTestStepResults(teststepresults);
		
		/**
		 * Writing Test Case Result in Execution Control Sheet.
		 */
		writingTestResults.writingTestCaseResults(teststepresults);
		
		


		
		
		
	}
	
	
	
	


}
