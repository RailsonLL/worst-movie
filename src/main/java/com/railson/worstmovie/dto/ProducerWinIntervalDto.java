package com.railson.worstmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerWinIntervalDto {
    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}
