package com.ruoyi.common.utils.i18n;


import org.springframework.context.i18n.LocaleContextHolder;
import java.util.Locale;

/**
 * MongoDB 集合名称工具类
 */
public class MongoCollectionUtils {

    /**
     * 获取聊天消息集合名称，按当前语言动态生成
     * - 中文（默认语言）：返回 "chat_messages"
     * - 其他语言（如 en、fr）：返回 "chat_messages_en"
     *
     * @param baseName 基础集合名，如 "chat_messages"
     * @return 实际集合名
     */
    public static String getChatMessagesCollectionName(String baseName) {
        if (baseName == null || baseName.isEmpty()) {
            baseName = "chat_messages";
        }

        Locale currentLocale = LocaleContextHolder.getLocale();
        String language = currentLocale.getLanguage().toLowerCase();

        // 默认语言（中文）不加后缀
        if ("zh".equals(language)) {
            return baseName;
        }

        // 其他语言：追加 _{language}
        return baseName + "_" + language;
    }

}
