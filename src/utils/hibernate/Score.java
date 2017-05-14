package utils.hibernate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="scores")
public class Score {

    @EmbeddedId
    private ScoreId id;
    @Column(name = "steps",nullable = false)
    private int steps;
    @Column(name = "time_passed",nullable = false)
    private long time;

    public Score(ScoreId id, int steps, long time) {
        this.id = id;
        this.steps = steps;
        this.time = time;
    }

    public Score() {
    }

    public ScoreId getId() {
        return id;
    }

    public void setId(ScoreId id) {
        this.id = id;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
