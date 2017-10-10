package com.sinosoft.common.web.handlerMapping;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Elvis on 2017/9/30.
 */
@Component
public class DemoHandlerMapping implements HandlerMapping {
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {

        System.out.println("我进来了....");
        return null;
    }
}
