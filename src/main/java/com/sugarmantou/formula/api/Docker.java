package com.sugarmantou.formula.api;

import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.sugarmantou.formula.Controllers.Api.Docker.ManagerController;
import com.sugarmantou.formula.Module.Router.Router;

@SpringBootApplication
public class Docker {

    @Bean(name = "DockerRouter")
    public Router router(RequestMappingHandlerMapping handlerMapping) {
        Router DockerRouter = new Router(handlerMapping);

        // Formula本體核心 `/Docker`
        DockerRouter.get("/docker/list", (req, res) -> {
            try {
                ManagerController.List(req, res);
            } catch (Exception e) {
                    res.code(500).jsonBody(Map.of("error", "Internal Server Error", "message", e.getMessage()));
            }
        });

        return DockerRouter;
    }

}
