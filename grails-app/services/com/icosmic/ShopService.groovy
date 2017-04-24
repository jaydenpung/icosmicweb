package com.icosmic

import com.icosmic.Shop
import com.icosmic.SearchContext
import com.icosmic.SearchableField

class ShopService {

    def imageService

    final static fcn = [
        'like': 'like',
        'ilike': 'ilike',
        '=': 'eq',
        '!=': 'ne',
        '<=': 'le',
        '>=': 'ge',
        '<': 'lt',
        '>': 'gt',
        'isNull': 'isNull'
    ]

    static decorator = [
        'like': { '%' + it + '%' },
        'ilike': { '%' + it + '%' },
        '=': { it },
        '!=': { it },
        '<=': { it },
        '>=': { it },
        '<': { it },
        '>': { it },
        'isNull': { it },
        'isNotNull': { it }
    ]

    def search(SearchContext searchContext) {
        def rsp = [:]
        try {
            def criteria = Shop.createCriteria()

            def resultList = criteria.list(max: searchContext.max ?: null, offset: searchContext.offset) {

                searchContext.fields.each {
                    def value = it.value
                    def name = it.name
                    def operator = it.operator

                    if (it.name == 'coverImage' || it.name == 'enabled') {
                        value = Boolean.(it.value.toUpperCase())
                        "${fcn[operator]}"(name, decorator[operator](value))
                    }
                    else {
                        "${fcn[operator]}"(name, decorator[operator](value))
                    }
                }
                if (searchContext.sort != null && !searchContext.sort.isEmpty()) {
                    order(searchContext.sort, searchContext.order)
                }
                ne("status", EntityStatus.DELETED)
            }

            rsp.results = resultList
        }
        catch (Exception ex) {
            log.error("search() failed: ${ex.message}", ex)
            rsp.errors = ex.message
        }
        return rsp
    }

    def getShopById(Long id) {
        def rsp = [:]
        try {
            def shop = Shop.withCriteria {
                eq('id', id)
                ne('status', EntityStatus.DELETED)
            }[0]

            if (!shop) {
                throw new Exception ("Error getting shop with id: ${id}")
            }

            rsp.result = shop
        }
        catch (Exception ex) {
            log.error("getShopById() failed: ${ex.message}", ex)
            rsp.errors = ex.message
        }
        return rsp
    }

    def save(Shop shop) {
        def rsp = [:]
        try {
            shop.save(flush: true, failOnError: true)
        }
        catch (Exception ex) {
            log.error("save() failed: ${ex.message}", ex)
            rsp.errors = ex.message
        }
        return rsp
    }

    def update(Shop updatedShop) {
        def rsp = [:]
        try {
            def shop = Shop.withCriteria {
                eq('id', updatedShop.id)
                ne('status', EntityStatus.DELETED)
            }[0]

            if (!shop) {
                throw new Exception ("Error getting shop with id: ${updatedShop.id}")
            }

            // replace image
            if (shop.image && updatedShop.image) {
                def imageRsp = imageService.deleteImage(shop.image)
                if (imageRsp.errors) {
                    throw new Exception (imageRsp.errors)
                }
                shop.image = updatedShop.image
            }
            else if (updatedShop.image) {
                shop.image = updatedShop.image
            }

            shop.name = updatedShop.name
            shop.url = updatedShop.url
            shop.redirect = updatedShop.redirect
            shop.coverImage = updatedShop.coverImage
            shop.shortDescription = updatedShop.shortDescription
            shop.description = updatedShop.description

            shop.save(flush: true, failOnError: true)

            rsp.result = shop
        }
        catch (Exception ex) {
            log.error("update() failed: ${ex.message}", ex)
            rsp.errors = ex.message
        }
        return rsp
    }
    def delete(List<Long> ids) {
        def rsp = [:]
        try {
            def shops = Shop.withCriteria {
                inList('id', ids)
                ne('status', EntityStatus.DELETED)
            }

            def invalidIds = ids - shops?.id
            if (invalidIds) {
                throw new Exception ("Unable to get shops with id: ${invalidIds}")
            }

            shops.each { shop ->
                if (shop.image) {
                    def imageRsp = imageService.deleteImage(shop.image)
                    if (imageRsp.errors) {
                        throw new Exception (imageRsp.errors)
                    }
                }
                shop.status = EntityStatus.DELETED
                shop.save(flush: true, failOnError: true)
            }

            rsp.results = shops
        }
        catch (Exception ex) {
            log.error("delete() failed: ${ex.message}", ex)
            rsp.errors = ex.message
        }
        return rsp
    }

    def enable(List<Long> ids) {
        def rsp = [:]
        try {
            def shops = Shop.withCriteria {
                inList('id', ids)
                ne('status', EntityStatus.DELETED)
                ne('enabled', true)
            }

            def invalidIds = ids - shops?.id
            if (invalidIds) {
                throw new Exception ("Unable to get shops with id: ${invalidIds}")
            }

            shops.each { shop ->
                shop.enabled = true
                shop.save(flush: true, failOnError: true)
            }

            rsp.results = shops
        }
        catch (Exception ex) {
            log.error("delete() failed: ${ex.message}", ex)
            rsp.errors = ex.message
        }
        return rsp
    }

    def disable(List<Long> ids) {
        def rsp = [:]
        try {
            def shops = Shop.withCriteria {
                inList('id', ids)
                ne('status', EntityStatus.DELETED)
                ne('enabled', false)
            }

            def invalidIds = ids - shops?.id
            if (invalidIds) {
                throw new Exception ("Unable to get shops with id: ${invalidIds}")
            }

            shops.each { shop ->
                shop.enabled = false
                shop.save(flush: true, failOnError: true)
            }

            rsp.results = shops
        }
        catch (Exception ex) {
            log.error("delete() failed: ${ex.message}", ex)
            rsp.errors = ex.message
        }
        return rsp
    }
}
