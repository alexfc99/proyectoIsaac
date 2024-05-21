package jpaswing;

import jpaswing.ui.ItemUI;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class SwingApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context  = new SpringApplicationBuilder(SwingApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);
        EventQueue.invokeLater(()->{
            ItemUI interfaz = context.getBean(ItemUI.class);
            interfaz.setVisible(true);
        });
    }
}
