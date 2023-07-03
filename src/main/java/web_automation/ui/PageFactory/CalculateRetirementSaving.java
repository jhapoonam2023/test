package web_automation.ui.PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import WebAutomation.ui.AbstractComponents.AbstractComponents;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CalculateRetirementSaving extends AbstractComponents {

	public WebDriver driver;

	public CalculateRetirementSaving(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.ID, using = "current-age")
	private WebElement currentAge;

	@FindBy(how = How.ID, using = "invalid-current-age-error")
	private WebElement invalidCurrentAgeError;

	@FindBy(how = How.ID, using = "retirement-age")
	private WebElement retirementAge;

	@FindBy(how = How.ID, using = "invalid-retirement-age-error")
	private WebElement invalidRetirementAgeError;

	@FindBy(how = How.ID, using = "current-income")
	private WebElement currentIncome;

	@FindBy(how = How.ID, using = "invalid-current-income-error")
	private WebElement invalidCurrentIncomeError;

	@FindBy(how = How.ID, using = "spouse-income")
	private WebElement spouseIncome;

	@FindBy(how = How.ID, using = "current-total-savings")
	private WebElement currentTotalSavings;

	@FindBy(how = How.ID, using = "invalid-current-total-savings-error")
	private WebElement invalidCurrentTotalSavingError;

	@FindBy(how = How.ID, using = "current-annual-savings")
	private WebElement currenAnnualSavings;

	@FindBy(how = How.ID, using = "invalid-current-annual-savings-error")
	private WebElement invalidCurrentAnnualSavingError;

	@FindBy(how = How.ID, using = "savings-increase-rate")
	private WebElement savingIncreaseRate;

	@FindBy(how = How.ID, using = "invalid-savings-increase-rate-error")
	private WebElement invalidSavingIncreaseRateError;

	@FindBy(how = How.CSS, using = "[type='radio'][value='N']")
	private WebElement sSNBenefitNO;

	@FindBy(how = How.CSS, using = "[type='radio'][value='Y']")
	private WebElement sSNBenefitYes;

	@FindBy(how = How.CSS, using = "[type='radio'][value='S']")
	private WebElement singleStatus;

	@FindBy(how = How.CSS, using = "[type='radio'][value='M']")
	private WebElement marriedStatus;

	@FindBy(how = How.XPATH, using = "//button[@class='dsg-btn-primary btn-block']")
	private WebElement btnCalculate;

	@FindBy(how = How.XPATH, using = "//p[@id='result-message']")
	private WebElement resultMessage;

	@FindBy(how = How.XPATH, using = "//ul[@id='marital-status-ul']/li")
	List<WebElement> aditionalSSNFields;

	@FindBy(how = How.ID, using = "social-security-override")
	private WebElement ssnOverride;

	@FindBy(how = How.LINK_TEXT, using = "Adjust default values")
	private WebElement adjustDefaultValuesLink;

	@FindBy(how = How.XPATH, using = "//div[@class='modal-body']//input[@id='additional-income']")
	private WebElement additionalIncomeDefault;

	@FindBy(how = How.ID, using = "retirement-duration")
	private WebElement retirementDurationDefault;

	@FindBy(how = How.XPATH, using = "//input[@name='inflation-inclusion']")
	private WebElement includeInflation;

	@FindBy(how = How.ID, using = "[type='exclude-inflation")
	private WebElement excludeInflation;

	@FindBy(how = How.ID, using = "expected-inflation-rate")
	private WebElement expectedInflationRate;

	@FindBy(how = How.ID, using = "retirement-annual-income")
	private WebElement retirementAnnualIncome;

	@FindBy(how = How.ID, using = "pre-retirement-roi")
	private WebElement preRetirementROIDefault;

	@FindBy(how = How.ID, using = "post-retirement-roi")
	private WebElement postRetirementROIDefault;

	@FindBy(how = How.XPATH, using = "//form[@id='default-values-form']/div[@class='dsg-row-wrapper']//button[@class='dsg-btn-primary btn-block']")
	private WebElement btnSaveChanges;

	@FindBy(how = How.ID, using = "assumption-label")
	private WebElement message;

	By radioItemsListBy = By.xpath("//ul[@id='marital-status-ul']/li");

	By result = By.xpath("//p[@id='result-message']");

	public void GoTo() {
		driver.get("https://www.securian.com/insights-tools/retirement-calculator.html");
	}

	public void SubmitSavingsRequiredFields(String CurrentAge, String RetirementAge, String CurrentIncome,
			String CurrenAnnualSavings, String CurrentTotalSavingBalance, String rateToIncrease, String SSNBenefit,
			String SSNOverRideAmt) throws InterruptedException {

		// Enter mandatory fields
		EnterMandatoryFields(CurrentAge, RetirementAge, CurrentIncome, CurrenAnnualSavings, CurrentTotalSavingBalance,
				rateToIncrease);

		if (ValidateSSNBenefitOptionsDisplayed(SSNBenefit)) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", marriedStatus);

			ssnOverride.sendKeys(SSNOverRideAmt);
		}

		// Click on Calculate to Submit the data
		btnCalculate.click();

		waitForElement(result);

		// Verify if the submission has happened successfully
		Assert.assertTrue(resultMessage.getText().contains("Congratulations!"), "Submission Failed");
	}

	// Validate if SSN Benefits are displayed
	public boolean ValidateSSNBenefitOptionsDisplayed(String SSNBenefit) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;

		try {
			// Validate SSN Benefit Options are displayed when it is "Yes"
			if (SSNBenefit.equals("Yes")) {
				executor.executeScript("arguments[0].click();", sSNBenefitYes);

				if (GetRadioOptionsList().size() == 2 && singleStatus.isDisplayed() && marriedStatus.isDisplayed()
						&& ssnOverride.isDisplayed())
					return true;
			} else {
				// When the SSN Benefit is set to NO, Marital Status radio buttons and Override
				// field should not be displayed

				if (!ssnOverride.isDisplayed() && !singleStatus.isDisplayed() && !marriedStatus.isDisplayed())
					return true;
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	// Validate Expected Inflation Rate field should be displayed when
	// post-retirement income increase with inflation is set as "Yes"
	public boolean ValidatePostInflationExpectedRateDisplayed(String PostInflationIncomeIncrease) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;

		try {

			if (PostInflationIncomeIncrease.equals("Yes")) {

				// Wait for radio button Include Inflation to be clickable
				waitForElement(includeInflation);

				// Select the radio button "Yes"
				executor.executeScript("arguments[0].click();", includeInflation);

				waitForElement(expectedInflationRate);
				// Check if the Expected Inflation Rate field is displayed
				if (expectedInflationRate.isDisplayed())
					return true;
			} else {

				// If the flag is set "No" the Expected Inflation Rate field should not be
				// displayed
				if (!expectedInflationRate.isDisplayed())

					return true;
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	public void EnterMandatoryFields(String CurrentAge, String RetirementAge, String CurrentIncome,
			String CurrenAnnualSavings, String CurrentTotalSavingBalance, String rateToIncrease)
			throws InterruptedException {

		// Click on Calculate button
		btnCalculate.click();

		// Enter current age
		if (invalidCurrentAgeError.isDisplayed())
			this.currentAge.sendKeys(CurrentAge);

		// Enter Retirement Age
		if (invalidRetirementAgeError.isDisplayed())
			this.retirementAge.sendKeys(RetirementAge);

		// Enter current annual income
		if (invalidCurrentIncomeError.isDisplayed()) {

			waitForElement(currentIncome);
			currentIncome.clear();
			currentIncome.click();
			this.currentIncome.sendKeys(CurrentIncome);
		}

		// Enter current Total Saving Balance
		if (invalidCurrentTotalSavingError.isDisplayed()) {

			waitForElement(currentTotalSavings);
			currentTotalSavings.clear();
			currentTotalSavings.click();
			this.currentTotalSavings.sendKeys(CurrentTotalSavingBalance);
		}

		// Enter currently annual saving
		if (invalidCurrentAnnualSavingError.isDisplayed()) {
			Thread.sleep(1000);
			currenAnnualSavings.clear();
			currenAnnualSavings.click();
			this.currenAnnualSavings.sendKeys(CurrenAnnualSavings);
		}

		if (invalidSavingIncreaseRateError.isDisplayed())
			this.savingIncreaseRate.sendKeys(rateToIncrease);
	}

	public List<WebElement> GetRadioOptionsList() {
		waitForElement(radioItemsListBy);
		return aditionalSSNFields;
	}

	// This method submits complete data (Mandatory and Non-Mandatory)
	public void SubmitCompleteData(String CurrentAge, String RetirementAge, String CurrentIncome, String SpouseIncome,
			String CurrentTotalSavingBalance, String CurrenAnnualSavings, String rateToIncrease, String SSNBenefitYes,
			String SSNOverride) throws InterruptedException {

		// Enter mandatory fields
		EnterMandatoryFields(CurrentAge, RetirementAge, CurrentIncome, CurrenAnnualSavings, CurrentTotalSavingBalance,
				rateToIncrease);

		// Enter Spouse Income
		this.spouseIncome.sendKeys(SpouseIncome);

		// Select the SSN benefit option "Yes" and select Marital status as "Single"
		if (ValidateSSNBenefitOptionsDisplayed(SSNBenefitYes)) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", singleStatus);

			// Enter Override amount
			ssnOverride.sendKeys(SSNOverride);
		} else
			Assert.fail("Marital Status Options should be displayed but are not displaying");

		// Click on Calculate to submit complete data
		btnCalculate.click();

		// Wait for the message to be displayed
		waitForElement(resultMessage);

		// Verify if the data is submitted successfully
		Assert.assertTrue(resultMessage.getText().contains("Congratulations!"), "Submission Failed");
	}

	// Submit Complete data along with default updated values
	public void SubmitWithDefaultDtata(String CurrentAge, String RetirementAge, String CurrentIncome,
			String SpouseIncome, String CurrentTotalSavingBalance, String CurrenAnnualSavings, String SSNBenefitNo,
			String rateToIncrease, String DefaultIncome, String DurationDefault, String PostInflationIncomeIncrease,
			String ExpectedInflationRate, String FinalAnnualIncome, String PreRetirementROIDefault,
			String PostRetirementROIDefault) throws InterruptedException {

		// Enter mandatory fields
		EnterMandatoryFields(CurrentAge, RetirementAge, CurrentIncome, CurrenAnnualSavings, CurrentTotalSavingBalance,
				rateToIncrease);

		// Wait for the spouse income field to be displayed
		waitForElement(spouseIncome);

		// Enter Spouse Income
		spouseIncome.clear();
		spouseIncome.sendKeys(SpouseIncome);

		// Select the SSN benefit option "No"
		if (!ValidateSSNBenefitOptionsDisplayed(SSNBenefitNo))

			Assert.fail("Marital Status Options should not be displayed but are displayed in the form");

		// Click on "Adjust Default Values" link
		adjustDefaultValuesLink.click();

		// Update Default Income
		additionalIncomeDefault.clear();
		additionalIncomeDefault.sendKeys(DefaultIncome);

		// Update Default Duration
		retirementDurationDefault.clear();
		retirementDurationDefault.sendKeys(DurationDefault);

		// Validate if Post Inflation Income is set YES, display Expected Inflation Rate
		if (ValidatePostInflationExpectedRateDisplayed(PostInflationIncomeIncrease)) {

			// Enter Expected Inflation Rate
			expectedInflationRate.sendKeys(ExpectedInflationRate);
		} else
			Assert.fail("Expected Inflation Rate field should be displayed but it is not displaying");

		// Update default retirement annual income
		retirementAnnualIncome.clear();
		retirementAnnualIncome.sendKeys(FinalAnnualIncome);

		// Update default Pre-Retirement ROI
		preRetirementROIDefault.clear();
		preRetirementROIDefault.sendKeys(PreRetirementROIDefault);

		// Update default Post-Retirement ROI
		postRetirementROIDefault.clear();
		postRetirementROIDefault.sendKeys(PostRetirementROIDefault);	

		// Click on Save Changes
		btnSaveChanges.click();
		
		// Wait for the message to be displayed
		waitForElement(message);

		// Check if the Save Changes has happened successfully
		Assert.assertTrue(message.getText().contains("Default calculator values"), "Unable to save Default Values");

		// Click on Calculate to finally submit complete data
		btnCalculate.click();

		// Wait for the message to be displayed
		waitForElement(resultMessage);

		// Verify if the data is submitted successfully
		Assert.assertTrue(resultMessage.getText().contains("In order to retire by"), "Submission Failed");
	}
}
