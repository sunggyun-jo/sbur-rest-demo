package com.example.sburrestdemo;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class RestApiDemoController {

    private final CoffeeService coffeeService;
    private final EntityManager entityManager;


    @GetMapping(value = "/coffees/csv", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getCoffeesCsv(Long from, Long to, HttpServletResponse response) throws IOException {
        Stream<Coffee> coffeeStream = coffeeService.find(from, to);

        final CsvMapper mapper = new CsvMapper();
        final CsvSchema schema = mapper.schemaFor(Coffee.class);
        String headers = String.join(",", schema.getColumnNames());
        ObjectWriter csvWriter = mapper.writer(schema.withUseHeader(false));

        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.csv");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");

        BufferedOutputStream bout = new BufferedOutputStream(response.getOutputStream());
        bout.write(headers.getBytes(StandardCharsets.UTF_8));
        bout.write("\n".getBytes(StandardCharsets.UTF_8));

        coffeeStream.forEach(coffee -> {
            try {
                //bout.write(objectMapper.writeValueAsBytes(coffee));
                bout.write(csvWriter.writeValueAsString(coffee).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                entityManager.detach(coffee);
            }
        });

        bout.flush();
        response.flushBuffer();
    }

    @GetMapping(value = "/coffees/all")
    public List<CoffeeDto.Response> getCoffeesAll(Long from, Long to){
        return coffeeService.findList(from, to);
    }

    @GetMapping(value = "/coffees/stream")
    public Stream<CoffeeDto.Response> getCoffeesStream(Long from, Long to){
        return coffeeService.findStream(from, to);
    }

    @GetMapping(value = "/coffees/csv-stream", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getCoffeesCsvStream(Long from, Long to, HttpServletResponse response) throws IOException {

        Stream<CoffeeDto.Response> coffeeStream = coffeeService.findStream(from, to);

        final CsvMapper mapper = new CsvMapper();
        final CsvSchema schema = CsvSchema.builder()
                .addColumn("coffeeId")
                .addColumn("coffeeName")
                .addColumn("count")
                .build();
        String headers = String.join(",", schema.getColumnNames());
        ObjectWriter csvWriter = mapper.writer(schema.withUseHeader(false));

        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test-dto.csv");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");

        BufferedOutputStream bout = new BufferedOutputStream(response.getOutputStream());
        bout.write(headers.getBytes(StandardCharsets.UTF_8));
        bout.write("\n".getBytes(StandardCharsets.UTF_8));

        coffeeStream.forEach(coffee -> {
            try {
                //bout.write(objectMapper.writeValueAsBytes(coffee));
                bout.write(csvWriter.writeValueAsString(coffee).getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        bout.flush();
        response.flushBuffer();
    }
}
