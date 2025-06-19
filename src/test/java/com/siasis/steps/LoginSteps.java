package com.siasis.steps;

import com.siasis.pages.LoginPage;
import org.openqa.selenium.WebDriver;

public class LoginSteps {
    private final LoginPage loginPage;
    private String selectedRole;

    public LoginSteps(WebDriver driver) {
        this.loginPage = new LoginPage(driver);
    }

    public void navigateToLoginPage() {
        loginPage.navigateToLoginPage();
    }

    public void selectRole(String role) {
        this.selectedRole = role;
        loginPage.selectRole(role);
    }

    public void enterCredentials() {
        loginPage.enterCredentials(selectedRole);
    }

    public void clickLoginButton() {
        loginPage.clickLoginButton();
    }

    public void verifySuccessfulLogin() {
        loginPage.verifySuccessfulLogin();
    }
}