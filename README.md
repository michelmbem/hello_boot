# Hello Boot
A simple Spring-Boot RESTful API with an Oracle DB backend.

## Installing Oracle Database free edition on Docker

1. Create a __volume__ directory with a __data__ subdirectory where the __install-oracle-on-docker.bat__ script is located.
2. Run the __install-oracle-on-docker.bat__ script.
3. Once the container is launched run the __./setPassword.sh \<dba-pwd\>__ command from the container's terminal to define a password for the **sys**, **system** and **pdbadmin** DBA accounts.
4. Launch SQL Plus and run the following commands generate a __testuser__ schema:

#### Launching SQL Plus

````CMD
sqlplus sys/<dba-pwd>@//localhost:1521/free as sysdba
````

#### Create the testuser schema

````SQL
create user testuser idendified by testpwd;
grant all privileges to testuser;
connect testuser/testpwd@//localhost:1521/free
````

## Documentation

* [How to install Oracle Database on Docker](https://datmt.com/backend/how-to-install-oracle-database-on-docker/#:~:text=How%20To%20Install%20Oracle%20Database%20on%20Docker%201,Oracle%20database%20using%20sqldeveloper%20...%207%20Conclusion)
* [Oracle container registry](https://container-registry.oracle.com/ords/f?p=113:4:106420417697997:::4:P4_REPOSITORY,AI_REPOSITORY,AI_REPOSITORY_NAME,P4_REPOSITORY_NAME,P4_EULA_ID,P4_BUSINESS_AREA_ID:1863,1863,Oracle%20Database%20Free,Oracle%20Database%20Free,10,0&cs=3V1sCZHxfZ5NPbVowwRcIX58bslga7rn6G9f_Yy9X7jii4q178rM6V9FXr0QiGeVklR26rP8ZYgbp_xLipHkf_w)
