package com.icosmic

import com.icosmic.Config

class ConfigService {

    def updateConfig(Config updatedConfig) {
        def rsp = [:]
        try {
            def config = Config.withCriteria {
                eq('name', updatedConfig.name)
                ne('enabled', false)
            }[0]

            config.value = updatedConfig.value
            config.save(flush: true, failOnError: true)
        }
        catch (Exception ex) {
            log.error("updateConfig() failed: ${ex.message}", ex)
            rsp.errors = ex.message
        }
        return rsp
    }

    def getConfigValue(String configName) {
        def rsp = [:]
        try {
            def config = Config.withCriteria {
                eq('name', configName)
                ne('enabled', false)
            }[0]

            if (!config) {
                throw new Exception("Unable to find config with name: ${configName}")
            }

            rsp.result = config.value
        }
        catch (Exception ex) {
            log.error("getConfigValue() failed: ${ex.message}", ex)
            rsp.errors = ex.message
        }
        return rsp
    }
}
