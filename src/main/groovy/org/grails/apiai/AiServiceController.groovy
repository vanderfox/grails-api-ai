package org.grails.apiai

import ai.api.AIConfiguration
import ai.api.AIDataService
import ai.api.AIServiceContext
import ai.api.AIServiceContextBuilder
import ai.api.AIServiceException
import ai.api.GsonFactory
import ai.api.model.AIRequest
import ai.api.model.AIResponse
import ai.api.model.Fulfillment
import ai.api.web.AIWebhookServlet
import com.google.gson.Gson
import grails.web.Action
import grails.web.api.WebAttributes
import groovy.util.logging.Slf4j

import javax.servlet.http.HttpSession

/**
 * Created by rvanderwerf on 5/11/17.
 */
@Slf4j
trait AiServiceController implements WebAttributes {

    /**
     * Api.ai access token parameter name
     */
    public static final String PARAM_API_AI_KEY = "apiAiKey"

    private AIDataService aiDataService

    void init() {
        AIConfiguration aiConfig = new AIConfiguration((String)grailsApplication.config.getProperty(PARAM_API_AI_KEY))
        aiDataService = new AIDataService(aiConfig)
    }

    /**
     * Perform request to AI data service
     * @param aiRequest Request object. Cannot be <code>null</code>.
     * @param serviceContext Service context. If <code>null</code> then default context will be used.
     * @return Response object
     * @throws ai.api.AIServiceException Thrown on server access error
     */
    AIResponse request(AIRequest aiRequest, AIServiceContext serviceContext)
            throws AIServiceException {
        init()
        return aiDataService.request(aiRequest, serviceContext);
    }

    /**
     * Perform request to AI data service
     * @param query Request plain text string. Cannot be <code>null</code>.
     * @param serviceContext Service context. If <code>null</code> then default context will be used.
     * @return Response object
     * @throws AIServiceException Thrown on server access error
     */
    AIResponse request(String query, AIServiceContext serviceContext)
            throws AIServiceException {
        init()
        return request(new AIRequest(query), serviceContext);
    }

    /**
     * Perform request to AI data service
     * @param aiRequest Request object. Cannot be <code>null</code>.
     * @param session Session object. If <code>null</code> then default context will be used.
     * @return Response object
     * @throws AIServiceException Thrown on server access error
     */
    AIResponse request(AIRequest aiRequest, HttpSession session)
            throws AIServiceException {
        init()
        return request(aiRequest,
                (session != null) ? AIServiceContextBuilder.buildFromSessionId(session.getId()) : null);
    }

    /**
     * Perform request to AI data service
     * @param query Request plain text string. Cannot be <code>null</code>.
     * @param session Session object. If <code>null</code> then default context will be used.
     * @return Response object
     * @throws AIServiceException Thrown on server access error
     */
    AIResponse request(String query, HttpSession session) throws AIServiceException {
        init()
        return request(new AIRequest(query),
                (session != null) ? AIServiceContextBuilder.buildFromSessionId(session.getId()) : null);
    }

    /**
     * Perform request to AI data service
     * @param aiRequest Request object. Cannot be <code>null</code>.
     * @param sessionId Session string id. If <code>null</code> then default context will be used.
     * @return Response object
     * @throws AIServiceException Thrown on server access error
     */
    AIResponse request(AIRequest aiRequest, String sessionId)
            throws AIServiceException {
        init()
        return request(aiRequest,
                (sessionId != null) ? AIServiceContextBuilder.buildFromSessionId(sessionId) : null);
    }

    /**
     * Perform request to AI data service
     * @param query Request plain text string. Cannot be <code>null</code>.
     * @param sessionId Session string id. If <code>null</code> then default context will be used.
     * @return Response object
     * @throws AIServiceException Thrown on server access error
     */
    AIResponse request(String query, String sessionId) throws AIServiceException {
        init()
        return request(new AIRequest(query),
                (sessionId != null) ? AIServiceContextBuilder.buildFromSessionId(sessionId) : null);
    }

    @Action
    def defaultIndex() {
        try {
            AIResponse aiResponse = request(request.getParameter("query"), request.getSession())
            response.setContentType("text/plain")
            response.getWriter().append(aiResponse.getResult().getFulfillment().getSpeech())
        } catch (AIServiceException e) {
            e.printStackTrace()
        }
    }





}
