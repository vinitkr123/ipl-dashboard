package com.kumar.ipldashboard.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    private Long id;
    private LocalDate date;
    private String city;
    private String playerOfMatch;
    private String venue;
    private String team1;
    private String team2;
    private String tossWinner;
    private String tossDecision;
    private String matchWinner;
    private String result;
    private String resultMargin;
    private String eliminator;
    private String method;
    private String umpire1;
    private String umpire2;
}
