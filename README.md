#Handlersocket-Plugin-for-MySQL Installation(RHEL):

1. Download MySQL Binary and MySQL Source
 - 1a. **NOTE**: HandlerSocket-Plugin-for-MySQL does not require a pre-built MySQL source code.
 - MySQL Binary: Please follow installation steps in [link](https://github.com/danielfung/mysql-install).
 - MySQL Source: Please download the same version of mysql source based on version installed with binary.
 - 1b. Find the version of MySQL installed on your system:
 ```
    mysql> SHOW VARIABLES LIKE "%version%";
    output(example): mysql Ver 14.14 Distrub 5.6.20, for Linux (x86_64)
 ```
 - 1c. Download MySQL Source Code:
 - **NOTE**: Please replace **version** with the version of your MySQL installed. (ex:5.6)
 - Download the MySQL Source Code at this [link](http://mysql.mirror.iweb.ca).
 - Click on the version of MySQL you have on your system(MySQL-version/).
 - **NOTE**: Please replace **full-version** with the full-version of your MySQL installed. (ex:5.6.20)
 - Scroll down the list and find the source code for your system(mysql-full-version.tar.gz).
 ```
    $wget http://mysql.mirror.iweb.ca/version/full-version
    example: http://mysql.mirror.iweb.ca/MySQL-5.6/mysql-5.6.20.tar.gz
 ```

2. Clone HandlerSocket-Plugin-for-MySQL
 ```
   $git clone https://github.com/DeNA/HandlerSocket-Plugin-for-MySQL
  ```
3. Build HandlerSocket-Plugin-for-MySQL
  ```
   $ ./autogen.sh
   $ ./configure --with-mysql-source=/sourceLocation --with-mysql-bindir=/binLocation --with-mysql-plugindir=/pluginLocation
   $ sudo make
   $ sudo make install
   ```
 - --with-mysql-source: replace **sourceLocation** with the MySQL source code directory(example: usr/local/src/mysql/mysql-5.6.20).
 - --with-mysql-bindir: replace **binLocation** with the location of binary executables(example: mysql_config, **refer to 3a**).
 - --with-mysql-plugindir: replace **pluginLocation** with the location of plugin libraries(example: .so extension files, **refer to 3b**).
 - 3a.**To find bindir location:** 
 ``` 
   $ whereis mysql_config
   output: location of mysql_config(example: /usr/bin/mysql_config)
 ```
 - 3b. **To find plugindir:**
 ```
   mysql> SHOW VARIABLES LIKE 'plugin%';
   output: location of plugin_dir(example: /usr/lib64/mysql/plugin)
 ```

4. Append configuration options for handlersocket to my.cnf
 ```
  [mysqld]
  plugin-load=handlersocket.so
  loose_handlersocket_port = 9998
    # the port number to bind to (for read requests)
  loose_handlersocket_port_wr = 9999
    # the port number to bind to (for write requests)
  loose_handlersocket_threads = 16
    # the number of worker threads (for read requests)
  loose_handlersocket_threads_wr = 1
    # the number of worker threads (for write requests)
  open_files_limit = 65535
    # to allow handlersocket accept many concurrent
    # connections, make open_files_limit as large as
    # possible.
  ```
5. Install HandlerSocket-Plugin-for-MySQL in mysql
 - **NOTE**: Log into mysql as root and execute following query.
 ```
    mysql> install plugin handlersocket soname 'handlersocket.so';
 ```

6. Check if handlersocket.so is installed properly.
 ```
    mysql> show processlist;
 ```

7. Another method to check if handlersocket properly installed, is to check if ports 9998 and 9999 are occupied.
 ```
   $sudo lsof -i -P
   *look for mysqld 
    output: mysqld 12678 mysql 15u 122385031 0t0 TCP *:9998 (LISTEN)
  	    mysqld 12678 mysql 15u 122385031 0t0 TCP *:9999 (LISTEN)
 ```

