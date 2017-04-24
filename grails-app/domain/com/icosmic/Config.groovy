package com.icosmic

class Config implements Serializable {

    String name
    String value
    boolean enabled = true

    static mapping = {
        table 'CONFIG'
    }

    static constraints = {
        name(size: 1..100)
        value(size: 1..1000)
        enabled()
    }
}
