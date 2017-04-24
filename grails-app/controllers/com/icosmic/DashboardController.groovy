package com.icosmic

import com.icosmic.Shop
import com.icosmic.GridStyle

class DashboardController {

    def shopService
    def configService

    def index() {
        try {
            def shops = searchShops()
            def rsp = configService.getConfigValue("SHOP_GRID_STYLE")
            def gridStyle = GridStyle.(rsp.result)

            def coverShops = searchCoverShops()

            [ shops: shops, gridStyle: gridStyle, coverShops: coverShops ]
        }
        catch (Exception ex) {
            log.error("index() failed: ${ex.message}", ex)
        }
    }

    def searchShops() {
        try {
            def searchContext = new SearchContext()
            [
                [ name: "name", value: params.name, operator: "ilike" ],
                [ name: "coverImage", value: "false", operator: "=" ],
                [ name: "enabled", value: "true", operator: "=" ]
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

            /*def orderDirection = params.find { it.key ==~ /order\[\d+\]\[dir\]/ }
            def orderColumn = params.find { it.key ==~ /order\[\d+\]\[column\]/ }
            def orderColumnName = params.find { it.key ==~ /columns\[${orderColumn.value}]\[name\]/ }

            searchContext.order = orderDirection.value
            searchContext.sort = orderColumnName.value
            searchContext.max = maxResults?.toInteger()
            searchContext.offset = offset?.toInteger()*/

            def rsp = shopService.search(searchContext)

            def shops = rsp.results

            return shops
        }
        catch (Exception ex) {
            log.error("searchShops() failed: ${ex.message}", ex)
        }
    }

    def searchCoverShops() {
        try {
            def searchContext = new SearchContext()
            [
                [ name: "coverImage", value: "true", operator: "=" ],
                [ name: "enabled", value: "true", operator: "=" ]
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

            def rsp = shopService.search(searchContext)

            def shops = rsp.results

            return shops
        }
        catch (Exception ex) {
            log.error("searchCoverShops() failed: ${ex.message}", ex)
        }
    }

    def viewShop(Long id) {
        try {
            def rsp = shopService.getShopById(id)
            def shop = rsp.result

            [ shop: shop ]
        }
        catch (Exception ex) {
            log.error("view() failed: ${ex.message}", ex)
        }
    }
}
