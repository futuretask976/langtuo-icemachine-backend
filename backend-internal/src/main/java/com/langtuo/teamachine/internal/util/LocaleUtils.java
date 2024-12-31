package com.langtuo.teamachine.internal.util;

import cn.hutool.extra.spring.SpringUtil;
import com.langtuo.teamachine.api.model.ErrorMsgDTO;
import com.langtuo.teamachine.internal.constant.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@Slf4j
public class LocaleUtils {
    public static ErrorMsgDTO getErrorMsgDTO(ErrorCodeEnum errorCodeEnum) {
        MessageSource messageSource = getMessageSource();
        ErrorMsgDTO errorMsgDTO = new ErrorMsgDTO();
        errorMsgDTO.setErrorCode(errorCodeEnum.getErrorCode());
        errorMsgDTO.setErrorMsg(messageSource.getMessage(errorCodeEnum.getErrorCode(), null,
                LocaleContextHolder.getLocale()));
        return errorMsgDTO;
    }

    public static String getLang(String key) {
        MessageSource messageSource = getMessageSource();
        String lang = messageSource.getMessage(key, null,
                LocaleContextHolder.getLocale());
        return lang;
    }

    private static MessageSource getMessageSource() {
        ApplicationContext appContext = SpringUtil.getApplicationContext();
        MessageSource messageSource = appContext.getBean(MessageSource.class);
        return messageSource;
    }
}
