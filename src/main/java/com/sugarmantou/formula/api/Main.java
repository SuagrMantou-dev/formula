package com.sugarmantou.formula.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.sugarmantou.formula.Controllers.Api.MainController;
import com.sugarmantou.formula.Module.Router.Router;

@SpringBootApplication
public class Main {

    @Bean(name = "MainRouter")
    public Router router(RequestMappingHandlerMapping handlerMapping) {
        Router MainRouter = new Router(handlerMapping);

        // API系統本體核心 `/`
        MainRouter.get("/", MainController::Main);

        return MainRouter;
    }

}
