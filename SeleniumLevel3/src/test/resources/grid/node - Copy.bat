set chrome-driver=-Dwebdriver.chrome.driver="../drivers/win/chromedriver.exe"
set firefox-driver=-Dwebdriver.gecko.driver="../drivers/win/geckodriver.exe"
set ie-driver=-Dwebdriver.ie.driver="../drivers/win/IEDriverServer.exe"

set chrome-browser=-browser "browserName=chrome, version=ANY, platform=WINDOWS, maxInstances=5"
set firefox-browser=-browser "browserName=firefox, version=ANY, platform=WINDOWS, maxInstances=5"
set ie-browser=-browser "browserName=internet explorer, version=ANY, platform=WINDOWS, maxInstances=5"

set browsers-config=%chrome-browser% %firefox-browser% %ie-browser%

set driver-path=%chrome-driver% %firefox-driver% %ie-driver%
set hub-url= http://192.168.100.19:4444/grid/register

java %driver-path% -jar selenium-server-standalone-3.11.0.jar -role node -hub %hub-url% -port 5566 %browsers-config%
pause