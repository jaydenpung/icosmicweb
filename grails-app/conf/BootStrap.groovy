import grails.util.Holders
import grails.util.Environment
import org.springframework.web.multipart.commons.CommonsMultipartFile

import com.icosmic.*

class BootStrap {

    def grailsApplication
    def imageService
    def springSecurityService

    def init = { servletContext ->
        switch (Environment.current) {
            case Environment.PRODUCTION:
                break
            case Environment.DEVELOPMENT:
                break
        }

        if (Role.count() == 0) {
            createDefaultRoles()
        }
        if (SecUser.count() == 0) {
            createDefaultSecUsers()
        }
        if (Config.count() == 0) {
            createDefaultConfigs()
        }
    }
    def destroy = {
        def servletContext = Holders.getServletContext()
        servletContext.getAttribute("executor")?.shutdown()
    }

    def createDefaultRoles() {
        try {
            [
                "ROLE_ADMIN"
            ].each { role ->
                new Role(
                    authority: role
                ).save(flush: true, failOnError: true)

                log.info("Saved role: ${role}")
            }
        }
        catch (Exception ex) {
            log.error("createDefaultRoles() failed: ${ex.message}", ex)
        }
    }

    def createDefaultSecUsers() {
        try {
            [
                [
                    name: "admin",
                    password: "cosmic@dmin1",
                    roles: [
                        "ROLE_ADMIN"
                    ]
                ]
            ].each { rec ->

                def secUser = new SecUser(
                    username: rec.name,
                    password: rec.password,
                ).save(flush: true, failOnError: true)

                def roles = Role.withCriteria {
                    inList('authority', rec.roles)
                }

                roles.each { role ->
                    new SecUserRole(
                        secUser: secUser,
                        role: role
                    ).save(flush: true, failOnError: true)
                }

                log.info("Saved user: ${rec.name} with roles: ${roles.authority}")
            }
        }
        catch (Exception ex) {
            log.error("createDefaultSecUsers() failed: ${ex.message}", ex)
        }
    }

    def createDefaultConfigs() {
        try {
            [
                [
                    name: "SHOP_GRID_STYLE",
                    value: GridStyle.FOUR_PER_ROW
                ]
            ].each { rec ->
                def config = new Config(
                    name: rec.name,
                    value: rec.value
                ).save(flush: true, failOnError: true)

                log.info("Saved config: ${rec.name} with value: ${rec.value}")
            }
        }
        catch (Exception ex) {
            log.error("createDefaultConfigs() failed: ${ex.message}", ex)
        }
    }
}
