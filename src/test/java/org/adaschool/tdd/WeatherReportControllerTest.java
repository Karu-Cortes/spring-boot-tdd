package org.adaschool.tdd;


import com.mongodb.BasicDBObject;
import org.adaschool.tdd.repository.WeatherReportRepository;
import org.adaschool.tdd.repository.document.GeoLocation;
import org.adaschool.tdd.repository.document.WeatherReport;
import org.adaschool.tdd.service.MongoWeatherService;
import org.adaschool.tdd.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class WeatherReportControllerTest {
    WeatherService weatherService;

    @Mock
    WeatherReportRepository repository;

    @Test
    void findNearLocationCallsRepositoryFindAllBy() {
        MongoWeatherService weatherService = new MongoWeatherService(repository);
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation(lat, lng);
        float distanceRangeInMeters = 1000;

        when(repository.findAllBy(any(BasicDBObject.class))).thenReturn(Collections.emptyList());


        List<WeatherReport> result = weatherService.findNearLocation(location, distanceRangeInMeters);


        verify(repository).findAllBy(any(BasicDBObject.class));


        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findWeatherReportsByNameCallsRepositoryFindAllBy() {
        MongoWeatherService weatherService = new MongoWeatherService(repository);
        String reporter = "tester";

        when(repository.findAllBy(any(BasicDBObject.class))).thenReturn(Collections.emptyList());

        List<WeatherReport> result = weatherService.findWeatherReportsByName(reporter);


        verify(repository).findAllBy(any(BasicDBObject.class));


        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
