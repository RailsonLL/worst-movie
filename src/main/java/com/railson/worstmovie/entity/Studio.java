package com.railson.worstmovie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Studio {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer id;

    private String name;

    @ManyToMany(mappedBy="studios")
    private Set<Movie> movies = new HashSet<>();

    public Studio(Integer id, String name){
        this.id = id;
        this.name = name;
    }

}
