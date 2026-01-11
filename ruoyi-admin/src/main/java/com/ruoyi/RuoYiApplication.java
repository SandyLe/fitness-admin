package com.ruoyi;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
    }


    @Bean
    public ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            String port = environment.getProperty("server.port", "8080");
            String contextPath = environment.getProperty("server.servlet.context-path", "");

            System.out.println("(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                    " .-------.       ____     __        \n" +
                    " |  _ _   \\      \\   \\   /  /    \n" +
                    " | ( ' )  |       \\  _. /  '       \n" +
                    " |(_ o _) /        _( )_ .'         \n" +
                    " | (_,_).' __  ___(_ o _)'          \n" +
                    " |  |\\ \\  |  ||   |(_,_)'         \n" +
                    " |  | \\ `'   /|   `-'  /           \n" +
                    " |  |  \\    /  \\      /           \n" +
                    " ''-'   `'-'    `-..-'              ");

            System.out.println("\n" + "=".repeat(70));
            System.out.println("🎯 若依管理系统启动成功！");
            System.out.println("🏠 本地地址: http://localhost:" + port + contextPath);
            System.out.println("🌐 网络地址: http://" + getSimpleLocalIP() + ":" + port + contextPath);
            System.out.println("📚 OpenAPI 文档: http://localhost:" + port + contextPath + "/swagger-ui.html");
            System.out.println("🔍 API 定义: http://localhost:" + port + contextPath + "/v3/api-docs");
            System.out.println("🔧 服务端口: " + port);
            System.out.println("=".repeat(70));
        };
    }

    private static String getSimpleLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("⚠️  无法获取本机 IP 地址: " + e.getMessage());
            return "127.0.0.1";
        }
    }
}
