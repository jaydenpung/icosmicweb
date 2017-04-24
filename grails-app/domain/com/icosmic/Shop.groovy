package com.icosmic

import com.icosmic.IEntity
import com.icosmic.EntityStatus

class Shop implements Serializable, IEntity {

    String name
    String shortDescription
    String description
    Image image
    String url
    boolean coverImage = false
    boolean redirect = false
    boolean enabled = true

    // IEntity
    EntityStatus status = EntityStatus.ACTIVE
    Date dateCreated
    Date lastUpdated
    String createdBy = '_SYSTEM_'
    String updatedBy = '_SYSTEM_'

    static mapping = {
        table 'SHOP'
        description type: 'text'
    }

    static constraints = {
        name(size: 1..100)
        shortDescription(nullable: true, size: 1..150)
        description(nullable: true, size: 1..100000)
        url(nullable: true, size: 1..5000)
        coverImage()
        redirect()
        enabled()
        image(nullable: true)

        // IEntity
        status()
        dateCreated()
        lastUpdated()
        createdBy(size: 1..50)
        updatedBy(size: 1..50)
    }
}
