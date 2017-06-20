package com.gb.statistics.features.ai;

import com.gb.statistics.features.ai.controllers.RootFrameController;
import com.gb.statistics.features.ai.model.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartApplication extends Application {

    public static void main(String[] args) {
        launch();


/*
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Person person = new Person();
        person.setName("Putin");
        //Person response = template.postForObject("http://127.0.0.1:8080/person", person, Person.class);
        //System.out.println(response.getId());
        //System.out.println(response.getName());
        //Person page = template.getForObject("http://127.0.0.1:8080/person", Person.class);

        ResponseEntity<List<Person>> rateResponse = template.exchange("http://127.0.0.1:8080/person", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
                        });
        List<Person> rates = rateResponse.getBody();
        for (Person person3: rates) {
            System.out.println(person3.getName());
        }*/

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
