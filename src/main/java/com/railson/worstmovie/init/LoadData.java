package com.railson.worstmovie.init;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.railson.worstmovie.entity.Movie;
import com.railson.worstmovie.entity.Producer;
import com.railson.worstmovie.entity.Studio;
import com.railson.worstmovie.enums.Winner;
import com.railson.worstmovie.service.MovieService;
import com.railson.worstmovie.service.ProducerService;
import com.railson.worstmovie.service.StudioService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Configuration
public class LoadData {

    @Autowired
    private MovieService serviceMove;

    @Autowired
    private StudioService studioService;

    @Autowired
    private ProducerService producerService;

    @Bean
    public void loadMovies() {

        String path = System.getProperty("user.dir") + "\\resources\\movielist.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            String separator = ";";

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(separator.charAt(0))
                    .withIgnoreQuotations(false)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(new FileReader(path))
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();

            String[] values = csvReader.readNext();

            while ((values = csvReader.readNext()) != null) {
                serviceMove.create(Movie.builder()
                                   .year(Integer.valueOf(values[0]))
                                   .title(values[1])
                                   .studios(getStudios(values[2].trim().split("\\s*,\\s*")))
                                   .producers(getProducers(values[3].replace(" and ", ",").trim().split("\\s*,\\s*")))
                                   //.winner(Strings.isNotBlank(values[4])  ? Winner.YES : Winner.NO)
                                    .winner(Strings.isNotBlank(values[4])  ? true : false)
                                   .build());
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException("Error reading CSV file");
        }
    }

    private Set<Studio> getStudios(String[] studiosNames){
        Set<Studio> studioList = new HashSet<>();
        Arrays.asList(studiosNames).forEach(
            studioName -> {
                Studio studio = studioService.getStudioByName(studioName);
                if (Objects.nonNull(studio)) {
                    studioList.add(studio);
                } else studioList.add(studioService.save(new Studio(null, studioName)));
        });
        return studioList;
    }
    private Set<Producer> getProducers(String[] producersNames){
        Set<Producer> producerList = new HashSet<>();
        Arrays.asList(producersNames).forEach(
                producerName -> {
                    Producer producer = producerService.getProducerByName(producerName);
                    if (Objects.nonNull(producer)) {
                        producerList.add(producer);
                    } else producerList.add(producerService.save(new Producer(null, producerName)));
                });
        return producerList;
    }

}
