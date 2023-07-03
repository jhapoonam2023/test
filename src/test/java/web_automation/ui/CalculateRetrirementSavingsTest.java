package web_automation.ui;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import web_automation.ui.PageFactory.CalculateRetirementSaving;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import java.time.Duration;

public class CalculateRetrirementSavingsTest extends BaseTest {
	WebDriver driver;
	Properties prop;
	String currentAge = null;
	String ageToRetire = null;
	String currentAnnualIncome = null;
	String currentRetirementSavingBalance = null;
	String rateToIncrease = null;
	String SSNBenefit = null;
	String currenAnnualSavings = null;
	String SSNBenefitYes = null;
	String SSNBenefitNo = null;
	String spouseIncome = null;
	String SSNOverride = null;
	String DefaultIncome = null;
	String DurationDefault = null;
	String preRetirementROIDefault = null;
	String postRetirementROIDefault = null;
	String postInflationIncomeIncrease = null;
	String expectedInflationRate = null;
	String finalAnnualIncome = null;

	@BeforeClass
	public void LoadData() throws FileNotFoundException, InterruptedException {

		Properties propRetSave = new Properties();
		FileInputStream fisRetSaveData = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/testdata/RetirementSavingData.properties");
		try {
			propRetSave.load(fisRetSaveData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentAge = propRetSave.getProperty("current_age");
		ageToRetire = propRetSave.getProperty("retirement_age");
		currentAnnualIncome = propRetSave.getProperty("current_annual_income");
		spouseIncome = propRetSave.getProperty("spouse_annual_income");
		currentRetirementSavingBalance = propRetSave.getProperty("current_retirement_savings");
		currenAnnualSavings = propRetSave.getProperty("current_retirement_contribution");

		rateToIncrease = propRetSave.getProperty("annualcontribution_increase");
		SSNBenefit = propRetSave.getProperty("social_security_income");

		SSNBenefitNo = propRetSave.getProperty("ssnBenefitNo");
		SSNOverride = propRetSave.getProperty("social_security_override");

		DefaultIncome = propRetSave.getProperty("additional_income");
		DurationDefault = propRetSave.getProperty("retirementyears_needtolast");
		preRetirementROIDefault = propRetSave.getProperty("pre_retirement_investmentreturn");
		postRetirementROIDefault = propRetSave.getProperty("post_retirement_investmentreturn");

		postInflationIncomeIncrease = propRetSave.getProperty("postretirement_inflation");

		expectedInflationRate = propRetSave.getProperty("expected_inflationrate");

		finalAnnualIncome = propRetSave.getProperty("final_annual_income_desired");
	}

	@Test
	public void SubmitSavingData_WithRequiredFields() throws Exception {
		Reporter.log("Submit the page with Required fields");

		// Submit form with only required fields
		objCalculate.SubmitSavingsRequiredFields(currentAge, ageToRetire, currentAnnualIncome,
				currentRetirementSavingBalance, currenAnnualSavings, rateToIncrease, SSNBenefit, SSNOverride);
	}

	@Test
	public void SubmitData_WithCompleteFields() throws Exception {
		Reporter.log("Submit the page with complete data with SSN Benefit 'Yes'");

		// Submit form with complete data with SSN Benefits "Yes"
		objCalculate.SubmitCompleteData(currentAge, ageToRetire, currentAnnualIncome, spouseIncome,
				currentRetirementSavingBalance, currenAnnualSavings, rateToIncrease, SSNBenefit, SSNOverride);

	}

	@Test
	public void UpdateDefaultSavingDataAndSubmit() throws Exception {
		Reporter.log("Submit the page with complete set of fields including Deafult values");

		// Submit complete form along with Default data (SSN Benefit is set to 'No')
		objCalculate.SubmitWithDefaultDtata(currentAge, ageToRetire, currentAnnualIncome, spouseIncome,
				currentRetirementSavingBalance, currenAnnualSavings, SSNBenefitNo, rateToIncrease, DefaultIncome,
				DurationDefault, postInflationIncomeIncrease, expectedInflationRate, finalAnnualIncome,
				preRetirementROIDefault, postRetirementROIDefault);
	}

}
