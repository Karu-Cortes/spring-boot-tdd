package org.adaschool.tdd;

import com.mongodb.BasicDBObject;
import org.adaschool.tdd.controller.weather.dto.WeatherReportDto;
import org.adaschool.tdd.exception.WeatherReportNotFoundException;
import org.adaschool.tdd.repository.WeatherReportRepository;
import org.adaschool.tdd.repository.document.GeoLocation;
import org.adaschool.tdd.repository.document.WeatherReport;
import org.adaschool.tdd.service.MongoWeatherService;
import org.adaschool.tdd.service.WeatherService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Collections;
import java.util.Date;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance( TestInstance.Lifecycle.PER_CLASS )
class MongoWeatherServiceTest
{
    WeatherService weatherService;

    @Mock
    WeatherReportRepository repository;



    @BeforeAll()
    public void setup()
    {
        weatherService = new MongoWeatherService( repository );
    }

    @Test
    void createWeatherReportCallsSaveOnRepository()
    {
        MongoWeatherService weatherService = new MongoWeatherService(repository);
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation( lat, lng );
        WeatherReportDto weatherReportDto = new WeatherReportDto( location, 35f, 22f, "tester", new Date() );
        weatherService.report( weatherReportDto );
        verify( repository ).save( any( WeatherReport.class ) );
    }

    @Test
    void weatherReportIdFoundTest()
    {
        MongoWeatherService weatherService = new MongoWeatherService(repository);
        String weatherReportId = "awae-asd45-1dsad";
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation( lat, lng );
        WeatherReport weatherReport = new WeatherReport( location, 35f, 22f, "tester", new Date() );
        when( repository.findById( weatherReportId ) ).thenReturn( Optional.of( weatherReport ) );
        WeatherReport foundWeatherReport = weatherService.findById( weatherReportId );
        assertEquals( weatherReport, foundWeatherReport );
    }

    @Test
    void weatherReportIdNotFoundTest()
    {
        MongoWeatherService weatherService = new MongoWeatherService(repository);
        String weatherReportId = "dsawe1fasdasdoooq123";
        when( repository.findById( weatherReportId ) ).thenReturn( Optional.empty() );
        assertThrows( WeatherReportNotFoundException.class, () -> {
            weatherService.findById( weatherReportId );
        } );
    }

    //prueba unitaria de cada método de WeatherService para verificar la correcta implementación en MongoWeatherService

    @Test
    void reportWeatherCallsSaveOnRepository() {
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation(lat, lng);
        WeatherReportDto weatherReportDto = new WeatherReportDto(location, 35f, 22f, "tester", new Date());

        weatherService.report(weatherReportDto);

        verify(repository).save(any(WeatherReport.class));
    }

  @Test

    void findByIdCallsRepositoryFindById() {
      String weatherReportId = "awae-asd45-1dsad";
      double lat = 4.7110;
      double lng = 74.0721;
      GeoLocation location = new GeoLocation(lat, lng);
      WeatherReport weatherReport = new WeatherReport(location, 35f, 22f, "tester", new Date());

      when(repository.findById(weatherReportId)).thenReturn(Optional.of(weatherReport));

      WeatherReport foundWeatherReport = weatherService.findById(weatherReportId);

      verify(repository).findById(weatherReportId);
      assertEquals(weatherReport, foundWeatherReport);
  }

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
