package com.railson.worstmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerWinIntervalMinMaxDto {
    private List<ProducerWinIntervalDto> min = new ArrayList<>();
    private List<ProducerWinIntervalDto> max = new ArrayList<>();

    private boolean newIntervalMin(Integer interval) {
        return !this.min.isEmpty() ? interval < this.min.get(0).getInterval() : true;
    }

    private boolean newIntervalMax(Integer interval) {
        return !this.max.isEmpty() ? interval > this.max.get(0).getInterval() : true;
    }

    private boolean newProducerMin(String producer) {
        return !this.min.isEmpty() ? !producer.equals(this.min.get(0).getProducer()) : true;
    }

    private boolean newProducerMax(String producer) {
        return !this.max.isEmpty() ? !producer.equals(this.max.get(0).getProducer()) : true;
    }

    public void addIntervalMin(String producer, Integer interval, Integer previousWin, Integer followingWin) {
        if (newIntervalMin(interval)) {
            this.min.clear();
        }
        if (newProducerMin(producer)) {
            this.min.add(new ProducerWinIntervalDto(producer, interval, previousWin, followingWin));
        }
    }

    public void addIntervalMax(String producer, Integer interval, Integer previousWin, Integer followingWin) {
        if (newIntervalMax(interval)) {
            this.max.clear();
        }
        if (newProducerMax(producer)) {
            this.max.add(new ProducerWinIntervalDto(producer, interval, previousWin, followingWin));
        }
    }

}
