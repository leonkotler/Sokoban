package utils.hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class ScoreId implements Serializable{
    @Column(name="player_name", nullable=false)
    private String playerName;
    @Column(name="level_name", nullable=false)
    private String levelName;

    public ScoreId(String playerName, String levelName) {
        this.playerName = playerName;
        this.levelName = levelName;
    }

    public ScoreId() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((playerName == null) ? 0 : playerName.hashCode());
        result = prime * result + levelName.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ScoreId other = (ScoreId) obj;
        if (playerName == null) {
            if (other.playerName != null)
                return false;
        } else if (!playerName.equals(other.playerName))
            return false;
        if (!levelName.equals(other.levelName))
            return false;
        return true;
    }

}
