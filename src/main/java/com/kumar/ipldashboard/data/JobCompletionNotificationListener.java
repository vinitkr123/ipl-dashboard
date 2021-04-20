package com.kumar.ipldashboard.data;

import com.kumar.ipldashboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    //private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate, EntityManager entityManager) {
        //this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            Map<String, Team> teamData = new HashMap<>();

            entityManager.createQuery("select distinct m.team1, count(m.team1) from Match m group by m.team1", Object[].class)
                    .getResultList()
                    .stream()
                   // .map(e -> Team.builder().teamName((String) e[0]).totalMatches((long) e[1]).build())
                    .map(e-> Team.builder().teamName((String) e[0]).totalMatches((long) e[1]).build())
                    .forEach(team -> teamData.put(team.getTeamName(), team));


            entityManager.createQuery("select distinct m.team2, count(m.team2) from Match m group by m.team2", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                      Team team = teamData.get((String)e[0]);
                      if(team != null) team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
                    });
            entityManager.createQuery("select m.matchWinner, count(m.matchWinner) from Match m group by m.matchWinner",Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e->{
                        Team team = teamData.get((String)e[0]);
                      if(team != null)  team.setTotalWins((long)e[1]);
                    });

            teamData.values().forEach(team -> entityManager.persist(team));
            teamData.values().forEach(team-> System.out.println(team));

     /* jdbcTemplate.query("SELECT team1, team2, date FROM match",
        (rs, row) -> Match.builder()
                .team1(rs.getString(1))
                .team2(rs.getString(2))
                .date( LocalDate.parse(rs.getString(3)))
                .build()
      ).forEach(match -> log.info("Found <" + match + "> in the database."));
      */

        }
    }


}