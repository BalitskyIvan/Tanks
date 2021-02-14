package edu.school21.models;

import java.util.Objects;

public class Statistic {
    private final Long statId;
    private final String user;
    private final String enemy;
    private final int shots;
    private final int hits;
    private final int misses;

    public Statistic(Long statId, String user, String enemy, int shots, int hits) {
        this.statId = statId;
        this.user = user;
        this.enemy = enemy;
        this.shots = shots;
        this.hits = hits;
        this.misses = shots - hits;
    }

    public Long getStatId() {
        return statId;
    }

    public String getUser() {
        return user;
    }

    public String getEnemy() {
        return enemy;
    }

    public int getShots() {
        return shots;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return shots == statistic.shots && hits == statistic.hits && misses == statistic.misses && statId.equals(statistic.statId) && user.equals(statistic.user) && enemy.equals(statistic.enemy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statId, user, enemy, shots, hits, misses);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "statId=" + statId +
                ", user=" + user +
                ", enemy=" + enemy +
                ", total shots=" + shots +
                ", hits=" + hits +
                ", misses=" + misses +
                '}';
    }
}
