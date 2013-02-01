/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.techest.ug;

import java.util.Date;
import net.techest.ug.WebSystem;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Web application lifecycle listener.
 * @author princehaku
 */
public class StartUp implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
            WebSystem.getInstance().setContext(ctx);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("选题系统启动成功 时间:"+new Date().toLocaleString());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
