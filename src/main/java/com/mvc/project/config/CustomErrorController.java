package com.mvc.project.config;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

        @GetMapping("/error")
        public String handleError(HttpServletRequest request) {
            String errorPage = "error"; // default
            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

            if (status != null) {
                Integer statusCode = Integer.valueOf(status.toString());

                if (statusCode == HttpStatus.NOT_FOUND.value()) {
                    errorPage = "/errors/notFound";

                } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                    errorPage = "/errors/error403";

                } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    errorPage = "/errors/error500";
                }
            }
            return errorPage;
        }
}