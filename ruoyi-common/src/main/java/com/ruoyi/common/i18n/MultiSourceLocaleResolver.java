package com.ruoyi.common.i18n;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import java.util.Locale;

/**
 * 多源语言解析器：按优先级尝试 Header → Cookie → Parameter → Default
 */
public class MultiSourceLocaleResolver implements LocaleResolver {

    private static final String LANG_COOKIE_NAME = "lang";
    private static final String LANG_PARAMETER_NAME = "lang";
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";

    private Locale defaultLocale = Locale.SIMPLIFIED_CHINESE; // 可通过 setter 注入

    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = null;

        // 1. 再次：从 URL 参数 ?lang=en
        String paramLang = request.getParameter(LANG_PARAMETER_NAME);
        if (StringUtils.hasText(paramLang)) {
            locale = parseLocale(paramLang);
            if (locale != null) {
                // 可选：将语言写入 Cookie，便于后续请求保持
                addCookie(request, HttpServletResponse.class.cast(request.getAttribute("org.springframework.web.servlet.DispatcherServlet.RESPONSE")), paramLang);
                return locale;
            }
        }

        // 2. 优先：从 Accept-Language Header（如浏览器自动发送）
        String headerLang = request.getHeader(ACCEPT_LANGUAGE_HEADER);
        if (StringUtils.hasText(headerLang)) {
            locale = parseLocale(headerLang);
            if (locale != null) {
                return locale;
            }
        }

        // 3. 其次：从 Cookie
        Cookie cookie = WebUtils.getCookie(request, LANG_COOKIE_NAME);
        if (cookie != null && StringUtils.hasText(cookie.getValue())) {
            locale = parseLocale(cookie.getValue());
            if (locale != null) {
                return locale;
            }
        }



        // 4. 最后：使用默认语言
        return defaultLocale != null ? defaultLocale : Locale.getDefault();
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        // 当通过 LocaleChangeInterceptor 触发语言切换时，会调用此方法
        // 我们将语言写入 Cookie 和 Session（可选）
        if (locale == null) {
            locale = defaultLocale;
        }
        String localeStr = locale.toLanguageTag();

        // 写入 Cookie（有效期 30 天）
        Cookie cookie = new Cookie(LANG_COOKIE_NAME, localeStr);
        cookie.setMaxAge(30 * 24 * 3600);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 可选：也存入 Session（兼容旧逻辑）
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
    }

    /**
     * 解析字符串为 Locale（支持 en, en_US, zh-CN, zh 等格式）
     */
    private Locale parseLocale(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        str = str.trim();
        try {
            // 支持 "en", "en_US", "zh-CN", "zh"
            if (str.contains("-")) {
                str = str.replace('-', '_'); // zh-CN → zh_CN
            }
            String[] parts = str.split("_");
            if (parts.length == 1) {
                return new Locale(parts[0]);
            } else if (parts.length >= 2) {
                return new Locale(parts[0], parts[1]);
            }
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    /**
     * 辅助方法：添加 Cookie（仅在参数切换时调用）
     */
    private void addCookie(HttpServletRequest request, HttpServletResponse response, String lang) {
        if (response == null) return;
        Cookie cookie = new Cookie(LANG_COOKIE_NAME, lang);
        cookie.setMaxAge(30 * 24 * 3600);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
