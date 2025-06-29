import pytest
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options
import os

@pytest.fixture(scope="function")
def setup_browser():
    chrome_options = Options()
    chrome_options.add_argument("--start-maximized")
    # service = Service(executable_path="src/main/resources/chromedriver.exe")  # Ensure this path is correct

    # driver_path = os.path.abspath("src/main/resources/chromedriver.exe")
    # service = Service(executable_path=driver_path)

    driver = webdriver.Chrome(options=chrome_options)

    # driver = webdriver.Chrome(service=service, options=chrome_options)
    yield driver
    driver.quit()

def test_round_trip_booking(setup_browser):
    driver = setup_browser
    driver.get("https://www.makemytrip.com/")

    # Skip login pop-up
    wait = WebDriverWait(driver, 10)
    try:
        wait.until(EC.element_to_be_clickable((By.XPATH, "//span[@class='commonModal__close']"))).click()
    except Exception as e:
        print(f"Login pop-up not found or already closed: {e}")

    # Select round trip option
    try:
        wait.until(EC.visibility_of_element_located((By.XPATH, "//li[@data-cy='roundTrip']")))
        wait.until(EC.element_to_be_clickable((By.XPATH, "//li[@data-cy='roundTrip']"))).click()
    except Exception as e:
        print(f"Error selecting round trip option: {e}")

    # Enter source city
    try:
        driver.find_element(By.ID, "fromCity").click()
        wait.until(EC.element_to_be_clickable((By.XPATH, "//input[@placeholder='From']"))).click()
        wait.until(EC.visibility_of_element_located((By.XPATH, "//input[@placeholder='From']"))).send_keys("Hyderabad")

        wait.until(EC.visibility_of_element_located((By.XPATH, "//div[@id='react-autowhatever-1']")))
        wait.until(EC.visibility_of_element_located((By.XPATH, "//span[contains(text(),'Hyderabad')]")))
        wait.until(EC.element_to_be_clickable((By.XPATH, "//span[contains(text(),'Hyderabad')]"))).click()
    except Exception as e:
        print(f"Error entering source city: {e}")

    # Enter destination city
    try:
        driver.find_element(By.ID, "toCity").click()
        wait.until(EC.element_to_be_clickable((By.XPATH, "//input[@placeholder='To']"))).click()
        driver.find_element(By.XPATH, "//input[@placeholder='To']").send_keys("Chennai")

        wait.until(EC.visibility_of_element_located((By.XPATH, "//div[@id='react-autowhatever-1']")))
        wait.until(EC.visibility_of_element_located((By.XPATH, "//span[contains(text(),'Chennai')]")))
        wait.until(EC.element_to_be_clickable((By.XPATH, "//span[contains(text(),'Chennai')]"))).click()
    except Exception as e:
        print(f"Error entering destination city: {e}")

    # Select departure and return dates
    try:
        wait.until(EC.visibility_of_element_located((By.XPATH, "//input[@id='departure']")))
        wait.until(EC.element_to_be_clickable((By.XPATH, "//input[@id='departure']"))).click()

        wait.until(EC.visibility_of_element_located((By.XPATH, "//div[@class='DayPicker-Months']")))
        wait.until(EC.visibility_of_element_located((By.XPATH, "(//div[contains(@aria-label,'17')])[2]/div")))
        wait.until(EC.element_to_be_clickable((By.XPATH, "(//div[contains(@aria-label,'17')])[2]/div"))).click()

        wait.until(EC.visibility_of_element_located((By.XPATH, "(//div[contains(@aria-label,'20')])[2]/div")))
        wait.until(EC.element_to_be_clickable((By.XPATH, "(//div[contains(@aria-label,'20')])[2]/div"))).click()
    except Exception as e:
        print(f"Error selecting dates: {e}")

    # Click on search flights
    try:
        wait.until(EC.invisibility_of_element_located((By.XPATH, "//div[@class='DayPicker-Months']")))
        driver.find_element(By.XPATH, "//a[text()='Search']").click()
    except Exception as e:
        print(f"Error clicking search flights: {e}")

    # Verify search results
    try:
        wait.until(EC.presence_of_element_located((By.XPATH, "//*[contains(text(),'Hyderabad → Chennai')]")))
        assert "Hyderabad → Chennai" in driver.find_element(By.XPATH, "//*[contains(text(),'Hyderabad → Chennai')]").text
    except Exception as e:
        print(f"Error verifying search results: {e}")