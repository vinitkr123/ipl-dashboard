package com.kumar.ipldashboard.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Data@Builder@ToString
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String teamName;
    private long totalMatches;
    private long totalWins;
}
