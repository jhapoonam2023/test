package web_automation.ui.PageFactory;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

public class WebPageFactory {
	private static boolean initializedPages = false;
	public static CalculateRetirementSaving calculator;

	public static void initializePageObjects(WebDriver driver) {

		if (initializedPages == false) {
			initializedPages = true;
			calculator = PageFactory.initElements(driver, CalculateRetirementSaving.class);
		}
	}

}
