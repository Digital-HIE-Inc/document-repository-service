package com.repository.document.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadRootSmartSoapEndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/services/*");
	}

	@Bean(name = "description")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema documentRepositorySchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("port");
		wsdl11Definition.setLocationUri("/services");
		wsdl11Definition.setTargetNamespace("urn:ihe:iti:xds-b:2007");
		wsdl11Definition.setSchema(documentRepositorySchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema documentRepositorySchema() {
		return new SimpleXsdSchema(new ClassPathResource("static/services/XDS.b_DocumentRepositoryMTOM.xsd"));
	}
	
	/**
	 * Add our own interceptor for the specified WS endpoint.
	 * @param interceptors
	 */
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
	    interceptors.add(new PayloadRootSmartSoapEndpointInterceptor(
	            new NoContentInterceptor(),
	            "NAMESPACE",
	            "LOCAL_PART"
	    ));
	 }
}
