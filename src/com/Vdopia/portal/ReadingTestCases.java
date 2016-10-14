package com.Vdopia.portal;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import com.Vdopia.portal.Xls_Reader;

public class ReadingTestCases {

	public  Xls_Reader xlread = new Xls_Reader(System.getProperty("user.dir")+"//TestCases//Test_Cases_Transformer.xlsx");

	public  Method method=null;
	public  String result="";
	public  String testCaseResult="";

	public Logger log = Logger.getLogger(this.getClass());
	public String result_pass="Pass: ";
	public String result_fail="Fail: ";
	WritingTestResults writingTestResults = new WritingTestResults();


	/**********To Read Test Cases to be run*****************/
	public List<String> readRunnableTestCases(String executionControlsheetname)
	{
		List<String> runtestcase_id = new ArrayList<String>();
		int rowcount = xlread.getRowCount("executionControl");
		String val=null;
		String tcid=null;

		for(int i=2;i<=rowcount;i++)
		{
			val = xlread.getCellData(executionControlsheetname, "Run", i);
			if(val.equalsIgnoreCase("yes"))
			{
				tcid = xlread.getCellData("executionControl", "TC_ID", i);

				runtestcase_id.add(tcid); 
			}
		}
		/**
		Iterator<String> it =al1.iterator();
		while (it.hasNext()) {
			String type = (String) it.next();
			System.out.println(type);
		}
		 **/
		return runtestcase_id;
	}

	/**********To Read test steps to  be run**************/


	/**********To Run test steps ******************/
	public  HashMap<String, String> runTestSteps(List<String> runableteststeps, String testCaseStepssheetname)
	{		
		Iterator<String> it =runableteststeps.iterator();
		HashMap<String, String> testStepResults = new HashMap<String, String>();
		HashMap<String, String> testCaseResults = new HashMap<String, String>();
		boolean resultFlag;


		while (it.hasNext()) {

			String TestCaseId = (String) it.next();

			log.info("/************************Running Test Case - "+TestCaseId+"***************************/");
			//List<String> al1 = new ArrayList<String>();
			int rcount = xlread.getRowCount("testCaseSteps");
			String val2=null;

			for(int i=2;i<=rcount;i++)
			{
				resultFlag = true;

				val2 = xlread.getCellData("testCaseSteps", "TC_ID", i);
				if(val2.equalsIgnoreCase(TestCaseId))
				{	
					try {
						String TCID = xlread.getCellData("testCaseSteps", "TC_ID", i);
						String StepId = xlread.getCellData("testCaseSteps", "Step_ID", i);
						String Description = xlread.getCellData("testCaseSteps", "Description", i);
						String Keyword = xlread.getCellData("testCaseSteps", "Keyword", i);
						String objectName = xlread.getCellData("testCaseSteps", "objectName", i);
						String inputData_QA = xlread.getCellData("testCaseSteps", "inputData_QA", i);
						//System.out.println("StepId is "+ StepId);
						//System.out.println("Description is "+ Description);
						//System.out.println("Keyword is "+ Keyword);
						//System.out.println("objectName is "+ objectName);
						//System.out.println("inputData_QA is "+ inputData_QA);

						Keyword = Keyword.trim().toLowerCase(); 
						log.info("Running Test Step "+StepId);
						if(Keyword!="")
						{
							try {
								method = Keywords.class.getMethod(Keyword, String.class, String.class);
							} catch (NoSuchMethodException e) {
								//log.debug("Please check your keyword entered");
								result = result_fail+"Keyword entered is incorrect";
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
							}
							try {
								log.info(method.getName()+" is being executed for keyword "+Keyword);
								Keywords keywordObj = new Keywords();
								result =  (String) method.invoke(keywordObj, objectName,inputData_QA);


							} catch (Exception e) {
								// TODO Auto-generated catch block
							} 
						}

						else
						{
							result = result_fail+"Keyword not entered in test step";

							//log.debug("Keyword not entered in test step");
						}
						/** writing each test step result in testSteps sheet after completion of each test case.
						 */
						testStepResults.put(StepId, result);
						log.debug(StepId + " has result: " +result);
						/**	
						String teststepresultsplit[] = result.split(":");
						String testcaseresult="Fail";
						if(teststepresultsplit[0].equalsIgnoreCase("Pass"))
						{
							testcaseresult = "Pass";
						}
						else
						{
							testcaseresult = "Fail";
						}
						testCaseResults.put(TCID, testcaseresult);
						**/
					}
					finally{
						method=null;
					}
				}
			}
		}



		//writingTestResults.savingTestCaseResults(testCaseResults);
		return testStepResults;
	}
}
