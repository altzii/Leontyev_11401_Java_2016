package ru.kpfu.itis.leontjev.desktop_fx.config;

import javafx.fxml.FXMLLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by alt on 27/05/2016.
 */

@Configuration
public class ControllerConfig {
    @Bean(name = "loginLoader")
    public FXMLLoader getLoginLoader() {
        return loadView("login.fxml");
    }

    @Bean(name = "clientsLoader")
    public FXMLLoader getUsersLoader() {
        return loadView("clients.fxml");
    }

    @Bean(name = "addClientLoader")
    public FXMLLoader addClientLoader() {
        return loadView("add_client.fxml");
    }

    @Bean(name = "serviceCentersLoader")
    public FXMLLoader ServiceCentersLoader() {
        return loadView("service_centers.fxml");
    }

    @Bean(name = "addServiceCenterLoader")
    public FXMLLoader addServiceCenterLoader() {
        return loadView("add_service_center.fxml");
    }

    @Bean(name = "deviceTypesLoader")
    public FXMLLoader deviceTypesLoader() {
        return loadView("device_types.fxml");
    }

    @Bean(name = "addDeviceTypeLoader")
    public FXMLLoader addDeviceTypeLoader() {
        return loadView("add_device_type.fxml");
    }

    @Bean(name = "brandsLoader")
    public FXMLLoader brandsLoader() {
        return loadView("brands.fxml");
    }

    @Bean(name = "addBrandLoader")
    public FXMLLoader addBrandLoader() {
        return loadView("add_brand.fxml");
    }

    @Bean(name = "menuLoader")
    public FXMLLoader getMenuLoader() {
        return loadView("menu.fxml");
    }

    protected FXMLLoader loadView(String url) {
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fxmlStream != null) {
                try {
                    fxmlStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
