#Handlersocket-Plugin-for-MySQL Installation(RHEL):

1. Download MySQL Binary and MySQL Source
 - 1a. **NOTE**: HandlerSocket-Plugin-for-MySQL does not require a pre-built MySQL source code.
 * MySQL Binary: Please follow installation steps in [link](https://github.com/danielfung/mysql-install).
 * MySQL Source: Please download the same version of mysql source based on version installed with binary.
 - 1b. Find the version of MySQL installed on your system:
 - mysql> SHOW VARIABLES LIKE "%version%";
 - output(example): mysql Ver 14.14 Distrub 5.6.20, for Linux (x86_64)
 - 1c. Download MySQL Source Code:
 - **NOTE**: Please replace **version** with the version of your MySQL installed.
 - Download the MySQL Source Code at this [link](http://mysql.mirror.iweb.ca).
 - Click on the version of MySQL you have on your system(MySQL-version/).
 - Scroll down the list and find the source code for your system(mysql-version.tar.gz).
