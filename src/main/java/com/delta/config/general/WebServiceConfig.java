package com.delta.config.general;

import com.delta.constant.WebServiceConstant;
import com.delta.model.ws.ObjectFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurationSupport;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.validation.XmlValidatorFactory;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.XsdSchemaCollection;

@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurationSupport {

    private static final Logger logger = LogManager.getLogger(WebServiceConfig.class);

    @Bean
    public Resource notificationResource() {
        return new ClassPathResource("notification.xsd");
    }

    @Bean
    public XsdSchema notificationSchema(Resource notificationResource) {
        return new SimpleXsdSchema(notificationResource);
    }

    @Bean
    public XsdSchemaCollection xsdSchemaCollection(Resource[] resources, XsdSchema[] schemas) {
        return new XsdSchemaCollection() {
            @Override
            public XsdSchema[] getXsdSchemas() {
                return schemas;
            }

            @Override
            public XmlValidator createValidator() {
                try {
                    return XmlValidatorFactory.createValidator(resources,
                        "http://www.w3.org/2001/XMLSchema");
                } catch (IOException e) {
                    logger.error(e);
                }
                return null;
            }
        };
    }

    @Bean
    public WsConfigurer wsConfigurer(PayloadLoggingInterceptor payloadLoggingInterceptor,
        PayloadValidatingInterceptor payloadValidatingInterceptor,
        XwsSecurityInterceptor xwsSecurityInterceptor) {
        return new WsConfigurer() {
            @Override
            public void addInterceptors(List<EndpointInterceptor> interceptors) {
                interceptors.add(payloadLoggingInterceptor);
                interceptors.add(payloadValidatingInterceptor);
                interceptors.add(xwsSecurityInterceptor);
            }

            @Override
            public void addArgumentResolvers(List<MethodArgumentResolver> list) {
            }

            @Override
            public void addReturnValueHandlers(List<MethodReturnValueHandler> list) {
            }
        };
    }

    @Bean(name = "notification")
    public Wsdl11Definition notificationWsdl11Definition(XsdSchema notificationSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("NotificationPort");
        wsdl11Definition.setLocationUri("/webservice");
        wsdl11Definition.setTargetNamespace(WebServiceConstant.NOTIFICATION_NAMESPACE);
        wsdl11Definition.setServiceName("NotificationService");
        wsdl11Definition.setResponseSuffix("Response");
        wsdl11Definition.setRequestSuffix("Request");
        wsdl11Definition.setFaultSuffix("Fault");
        wsdl11Definition.setSchema(notificationSchema);
        return wsdl11Definition;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Primary
    public ServletRegistrationBean<HttpServlet> messageDispatcherServlet(
        ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/webservice/*");
    }

    @Bean(name = "objectFactory")
    public ObjectFactory objectFactory() {
        return new ObjectFactory();
    }
}
