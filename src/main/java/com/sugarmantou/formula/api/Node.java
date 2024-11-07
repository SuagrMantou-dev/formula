package com.sugarmantou.formula.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.sugarmantou.formula.Controllers.Api.NodeController;
import com.sugarmantou.formula.Module.Router.Router;

@SpringBootApplication
public class Node {

    @Bean(name = "NodeRouter")
    public Router router(RequestMappingHandlerMapping handlerMapping) {
        Router NodeRouter = new Router(handlerMapping);

        // Formula本體核心 `/node`
        NodeRouter.get("/node/status", NodeController::Status);

        return NodeRouter;
    }

}
