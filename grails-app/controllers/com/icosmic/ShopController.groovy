package com.icosmic

import grails.converters.JSON

import com.icosmic.Shop
import com.icosmic.SearchContext
import com.icosmic.GridStyle

class ShopController {

    def shopService
    def imageService
    def configService
    def grailsApplication

    def list() {
        try {
            def rsp = configService.getConfigValue("SHOP_GRID_STYLE")
            def gridStyle = GridStyle.(rsp.result)

            [ gridStyle: gridStyle ]
        }
        catch (Exception ex) {
            log.error("list() failed: ${ex.message}", ex)
        }
    }

    def search() {
        try {
            def searchContext = new SearchContext()
            [
                [ name: "name", value: params.name, operator: "ilike" ]
            ].each {
                if (it.value != null) {
                    def searchableField = new SearchableField()
                    searchableField.name = it.name
                    searchableField.value = it.value
                    searchableField.operator = it.operator
                    searchContext.fields.add(searchableField)
                }
            }

            def draw = params.draw
            def offset = params.int('start')
            def maxResults = params.length

            def orderDirection = params.find { it.key ==~ /order\[\d+\]\[dir\]/ }
            def orderColumn = params.find { it.key ==~ /order\[\d+\]\[column\]/ }
            def orderColumnName = params.find { it.key ==~ /columns\[${orderColumn.value}]\[name\]/ }

            searchContext.order = orderDirection.value
            searchContext.sort = orderColumnName.value
            searchContext.max = maxResults?.toInteger()
            searchContext.offset = offset?.toInteger()

            def rsp = shopService.search(searchContext)

            def rows = []

            if (rsp) {
                rsp.results.each { shop ->
                    rows << [
                        id: shop.id,
                        name: shop.name,
                        image: "<img src=\"${createLink(action: "renderImage", controller:"image")}/${shop.image.generatedName}\" style='max-width: 150px; max-height: 150px'/>",
                        price: null,
                        quantity: null
                    ]
                }
            }

            def result = [
                draw: params.draw,
                data: rows,
                recordsTotal: rsp.results.totalCount,
                recordsFiltered: rsp.results.totalCount
            ]

            render(result as JSON)
        }
        catch(Exception ex) {
            log.error("search() failed: ${ex.message}", ex)
        }
    }

    def create() {
        try {
        }
        catch (Exception ex) {
            log.error("create() failed: ${ex.message}", ex)
        }
    }

    def save() {
        try {
            def image = request.getFile('image')
            def uploadRsp
            if (image.size > 0) {
                def storePath = grailsApplication.config.storage.shopImage
                uploadRsp = imageService.uploadImage(image, storePath)

                if (uploadRsp.errors) {
                    throw new Exception (uploadRsp.errors)
                }
            }
            else {
                throw new Exception("No Image uploaded")
            }

            //Special handling for url
            def url = params.url
            if (url) {
                if (url.size() > 8) {
                    if (url[0..6] != "http://" && url[0..7] != "https://") {
                        url = "http://" + url
                    }
                }
            }

            def shop = new Shop(
                name: params.name,
                shortDescription: params.shortDescription,
                description: params.description,
                url: url,
                redirect: params.redirect? Boolean.TRUE : Boolean.FALSE,
                coverImage: params.coverImage? Boolean.TRUE : Boolean.FALSE,
                image: uploadRsp?.result
            )

            def rsp = shopService.save(shop)

            if (rsp.errors) {
                throw new Exception (rsp.errors)
            }
        }
        catch (Exception ex) {
            log.error("save() failed: ${ex.message}", ex)
            flash.errors = ex.message
        }

        flash.errors? redirect(action: 'create') : redirect(action: 'list')
    }

    def edit(Long id) {
        try {
            def rsp = shopService.getShopById(id)
            def shop = rsp.result

            [ shop: shop ]
        }
        catch (Exception ex) {
            log.error("edit() failed: ${ex.message}", ex)
        }
    }

    def update() {
        try {
            def image = request.getFile('image')
            def uploadRsp
            if (image.size > 0) {
                def storePath = grailsApplication.config.storage.shopImage
                uploadRsp = imageService.uploadImage(image, storePath)

                if (uploadRsp.errors) {
                    throw new Exception (uploadRsp.errors)
                }
            }

            //Special handling for url
            def url = params.url
            if (url) {
                if (url.size() > 8) {
                    if (url[0..6] != "http://" && url[0..7] != "https://") {
                        url = "http://" + url
                    }
                }
            }

            //TODO: Allow remove picture
            def shop = new Shop(
                name: params.name,
                shortDescription: params.shortDescription,
                description: params.description,
                url: url,
                redirect: params.redirect? Boolean.TRUE : Boolean.FALSE,
                coverImage: params.coverImage? Boolean.TRUE : Boolean.FALSE,
                image: uploadRsp?.result
            )
            shop.id = params.long('id')

            def rsp = shopService.update(shop)

            if (rsp.errors) {
                throw new Exception (rsp.errors)
            }
        }
        catch (Exception ex) {
            log.error("update() failed: ${ex.message}", ex)
            flash.errors = ex.message
        }

        flash.errors? redirect(action: 'edit', params: [ id: params.id ]) : redirect(action: 'list')
    }

    def delete() {
        try {
            def ids = params.list('id')*.toLong()

            def rsp = shopService.delete(ids)

            if (rsp.errors) {
                throw new Exception (rsp.errors)
            }
        }
        catch (Exception ex) {
            log.error("delete() failed: ${ex.message}", ex)
            flash.errors = ex.message
        }
        redirect(action: 'list')
    }

    def updateGridStyle() {
        try {
            def config = new Config(
                name: "SHOP_GRID_STYLE",
                value: params.gridStyle
            )
            def rsp = configService.updateConfig(config)

            if (rsp.errors) {
                throw new Exception (rsp.errors)
            }
        }
        catch (Exception ex) {
            log.error("updateGridStyle() failed: ${ex.message}", ex)
        }
        redirect(action: 'list')
    }
}
