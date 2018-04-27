cd ../../huigou-common
call mvn clean install

cd ../huigou-b2c/huigou-b2c-core
call mvn clean install

cd ../../huigou-pay/huigou-pay-core
call mvn clean install

cd ../../huigou-auth/huigou-auth-core
call mvn clean install

cd ../../huigou-workflow/huigou-workflow-core
call mvn clean install

cd target
xcopy classes ..\src\main\webapp\WEB-INF\classes /s /y /e

echo 执行成功!!
echo 按任意键关闭
pause
exit
