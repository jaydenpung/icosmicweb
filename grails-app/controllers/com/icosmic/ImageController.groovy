package com.icosmic

class ImageController {

    def imageService
    def grailsApplication

    def renderImage() {
        try {
            String profilePicturePath = "${grailsApplication.config.storage.shopImage}/${params.id}.png"
            File file = new File(profilePicturePath)
            response.contentType = URLConnection.guessContentTypeFromName(file.getName())
            response.outputStream << file.bytes
            response.outputStream.flush()
        }
        catch(Exception ex) {
            log.error("renderImage() failed: ${ex.message}", ex)
        }
    }
}
