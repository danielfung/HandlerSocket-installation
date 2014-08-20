#Handlersocket-Plugin-for-MySQL Installation(RHEL):

1. Download MySQL Binary and MySQL Source
 - 1a. **NOTE**: HandlerSocket-Plugin-for-MySQL does not require a pre-built MySQL source code.
 - MySQL Binary: Please follow installation steps in [link](https://github.com/danielfung/mysql-install).
 - MySQL Source: Please download the same version of mysql source based on version installed with binary.
 - 1b. Find the version of MySQL installed on your system:
 - mysql> SHOW VARIABLES LIKE "%version%";
 - output(example): mysql Ver 14.14 Distrub 5.6.20, for Linux (x86_64)
 - 1c. Download MySQL Source Code:
 - **NOTE**: Please replace **version** with the version of your MySQL installed. (ex:5.6)
 - Download the MySQL Source Code at this [link](http://mysql.mirror.iweb.ca).
 - Click on the version of MySQL you have on your system(MySQL-version/).
 - **NOTE**: Please replace **full-version** with the full-version of your MySQL installed. (ex:5.6.20)
 - Scroll down the list and find the source code for your system(mysql-full-version.tar.gz).
 - $wget http://mysql.mirror.iweb.ca/version/full-version

2. Download HandlerSocket-Plugin-for-MySQL
 - $git clone https://github.com/DeNA/HandlerSocket-Plugin-for-MySQL

3. Build HandlerSocket-Plugin-for-MySQL
 - $ ./autogen.sh
 - $ ./configure --with-mysql-source=/sourceLocation --with-mysql-bindir=/binLocation --with-mysql-plugindir=/pluginLocation
 - $ sudo make
 - $ sudo make install
 - --with-mysql-source: replace **sourceLocation** with the MySQL source code directory(example: usr/local/src/mysql/mysql-5.6.20).
 - --with-mysql-bindir: replace **binLocation** with the location of binary executables(example: mysql_config, **refer to 3a.**).
 - --with-mysql-plugindir: replace **pluginLocation** with the location of plugin libraries(example: *.so, **refer to 3b.**).
 - 3a.**To find bindir location:** 
 - $ whereis mysql_config
 - output: location of mysql_config(example: /usr/bin/mysql_config)
 - 3b. **To find plugindir:**
 - mysql> SHOW VARIABLES LIKE 'plugin%';
 - output: location of plugin_dir(example: /usr/lib64/mysql/plugin)
