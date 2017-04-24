/*dataSource {
    pooled = true
    dbCreate = ''
    dbCreate = 'update'
    //dialect = imocha.hibernate.dialect.Oracle11gDialect
    //jndiName = 'java:/jdbc/oracle'
    dialect = com.hibernate.dialect.Oracle11gDialect
    url = "jdbc:oracle:thin:@localhost:1521:xe"
    username = "user"
    password = "password"
    driverClassName = "oracle.jdbc.OracleDriver"
}*/
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
}

// environment specific settings
environments {
    development {
        dataSource {
            pooled = true
            jmxExport = true
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "update"
            url = "jdbc:h2:tcp://localhost/~/h2/icosmic/icosmic"
        }
    }
    test {
        dataSource {
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
            url = "jdbc:sqlserver://localhost:1433;databaseName=iCosmic2"
            username = "sa"
            password = "cosmic@dmin1"
        }
    }
}
