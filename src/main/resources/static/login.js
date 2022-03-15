var someDatabaseFun = function() {
    var Properties = Java.type("java.util.Properties");
    var Driver = Java.type("org.h2.Driver"); //JDBC interface for H2

    var driver = new Driver();
    var properties = new Properties();

    properties.setProperty("user", "sa");     // database username
    properties.setProperty("password", ""); // database password

    try {
        var conn = driver.connect(
            "jdbc:h2:~/db", properties);  // connect to database

        // Database code here
    }
    finally {
        try { 
            if (conn) conn.close();
        } catch (e) {}
    }
}

someDatabaseFun();