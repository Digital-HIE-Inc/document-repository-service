package com.repository.document.config;

import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapEnvelope;
import org.springframework.ws.soap.SoapMessage;

/**
 * If a web service has no response, this handler returns: 204 No Content
 */
@Component
public class NoContentInterceptor extends EndpointInterceptorAdapter {

    @Override
    public void afterCompletion(MessageContext messageContext, Object o, Exception e) throws Exception {
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
        return true;
    }

    private SoapBody getSoapBody(MessageContext messageContext) {
        SoapMessage soapMessage = (SoapMessage) messageContext.getResponse();
        SoapEnvelope soapEnvelope = soapMessage.getEnvelope();
        return soapEnvelope.getBody();
    }
}
