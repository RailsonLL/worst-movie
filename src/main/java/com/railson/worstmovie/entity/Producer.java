package com.railson.worstmovie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Integer id;

    private String name;

    @ManyToMany(mappedBy="producers")
    private Set<Movie> movies = new HashSet<>();

    public Producer(Integer id, String name){
        this.id = id;
        this.name = name;
    }

}
