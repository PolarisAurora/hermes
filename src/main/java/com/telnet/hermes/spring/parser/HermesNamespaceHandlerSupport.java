package com.telnet.hermes.spring.parser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author Ternura
 * @since 2020/8/29 17:04
 */
public class HermesNamespaceHandlerSupport extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
    }
}
