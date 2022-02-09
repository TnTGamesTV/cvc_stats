package de.throwstnt.developing.labymod.cvc.api.adapters;

import java.util.List;

/**
 * An adapter for the minecraft scoreboard
 */
public abstract class AbstractScoreboardAdapter {

    public class ScoreboardTeam {

        public String name;
        public String prefix;
        public String suffix;

        public ScoreboardTeam(String name, String prefix, String suffix) {
            this.name = name;
            this.prefix = prefix;
            this.suffix = suffix;
        }
    }

    /**
     * The title of the scoreboard
     * 
     * @return the title of the scoreboard
     */
    public abstract String getTitle();

    /**
     * The content of the scoreboard
     * 
     * @return the content of the scoreboard
     */
    public abstract String getContent();

    /**
     * A list of all team names
     * 
     * @return all team names
     */
    public abstract List<String> getTeamNames();

    /**
     * A list of all teams
     * 
     * @return all teams
     */
    public abstract List<ScoreboardTeam> getTeams();
}
