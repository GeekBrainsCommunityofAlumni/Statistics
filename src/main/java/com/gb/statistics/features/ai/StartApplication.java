package com.gb.statistics.features.ai;

import com.gb.statistics.features.ai.controllers.RootFrameController;
import com.gb.statistics.features.ai.model.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class StartApplication extends Application {

    public static void main(String[] args) {
        //launch();
        Person person = new Person();
        person.setName("Putin");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        Person person1 = restTemplate.postForObject("http://127.0.0.1:8080/person{name}", person, Person.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/rootTabPane.fxml"));
        Parent main = loader.load();
        RootFrameController rootFrameController = loader.getController();
        rootFrameController.setMainStage(primaryStage);

        primaryStage.setTitle("Панель администрирования");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(main, 300, 300));
        primaryStage.show();
    }
}
