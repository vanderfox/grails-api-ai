package org.grails.apiai

import ai.api.GsonFactory
import ai.api.model.Fulfillment
import ai.api.web.AIWebhookServlet
import com.google.gson.Gson
import grails.web.Action
import grails.web.api.WebAttributes
import groovy.util.logging.Slf4j

/**
 * Created by rvanderwerf on 5/11/17.
 */
@Slf4j
trait AiWebhookController implements WebAttributes {

    private static final String RESPONSE_CONTENT_TYPE = "Content-Type: application/json"

    private static final String RESPONSE_CHARACTER_ENCODING = "utf-8"

    private static final long serialVersionUID = 1L

    private final Gson gson = GsonFactory.getDefaultFactory().getGson()


    /**
     * Web-hook processing method.
     * @param input Received request object
     * @param output Response object. Should be filled in the method.
     */
    abstract void doWebhook(AIWebhookServlet.AIWebhookRequest input, Fulfillment output)


    @Action
    def index() {
        Fulfillment output = new Fulfillment()
        String incomingJson = request.JSON.toString()
        log.debug("incoming json=${incomingJson}")
        doWebhook(gson.fromJson(incomingJson, AIWebhookServlet.AIWebhookRequest.class), output)
        response.setCharacterEncoding(RESPONSE_CHARACTER_ENCODING)
        response.setContentType(RESPONSE_CONTENT_TYPE)
        gson.toJson(output, response.getWriter())
        response.flushBuffer()
        null
    }



}
