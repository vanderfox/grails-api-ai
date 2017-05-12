package org.grails.apiai

import ai.api.model.AIResponse
import ai.api.web.AIWebhookServlet

/**
 * Created by rvanderwerf on 5/11/17.
 */
/**
 * Web-hook request model class
 */
class AIWebhookRequest extends AIResponse {
    private static final long serialVersionUID = 1L

    private AIWebhookServlet.OriginalRequest originalRequest

    /**
     * Get original request object
     * @return <code>null</code> if original request undefined in
     * request object
     */
    public AIWebhookServlet.OriginalRequest getOriginalRequest() {
        return originalRequest
    }
}
