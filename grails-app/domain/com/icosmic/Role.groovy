package com.icosmic

class Role {

    String authority

    static mapping = {
    	table 'ROLE'
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }
}
