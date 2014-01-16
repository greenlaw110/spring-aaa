package org.osgl.aaa.spring.web;

import org.osgl.aaa.NoAccessException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by luog on 4/12/13.
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@EnableWebMvc
public class A3ExceptionHandler implements MessageSourceAware {

    private MessageSource messageSource;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = NoAccessException.class)
    public ModelAndView defaultErrorHandler(NoAccessException e, HttpServletResponse response) throws Exception {
        int statusCode = HttpStatus.FORBIDDEN.value();
        String reason = e.getMessage();
        if (this.messageSource != null) {
            reason = this.messageSource.getMessage(reason, null, reason, LocaleContextHolder.getLocale());
        }
        if (!StringUtils.hasLength(reason)) {
            response.sendError(statusCode);
        } else {
            response.sendError(statusCode, reason);
        }
        return new ModelAndView();
    }
}
