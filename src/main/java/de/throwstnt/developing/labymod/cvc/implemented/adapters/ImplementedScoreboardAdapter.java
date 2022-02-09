package de.throwstnt.developing.labymod.cvc.implemented.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import de.throwstnt.developing.labymod.cvc.api.adapters.AbstractScoreboardAdapter;
import de.throwstnt.developing.labymod.cvc.api.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

public class ImplementedScoreboardAdapter extends AbstractScoreboardAdapter {

    @SuppressWarnings("resource")
    private Scoreboard _getScoreboard() {
        if (Minecraft.getInstance() != null) {
            if (Minecraft.getInstance().world != null) {
                return Minecraft.getInstance().world.getScoreboard();
            }
        }
        return null;
    }

    @Override
    public String getTitle() {
        if (this._getScoreboard() != null) {
            ScoreObjective sidebarObjective = this._getScoreboard().getObjectiveInDisplaySlot(1);

            if (sidebarObjective != null) {
                return ChatUtil.cleanColorCoding(sidebarObjective.getDisplayName().getString());
            }
        }
        return "";
    }

    @Override
    public String getContent() {
        if (this._getScoreboard() != null) {
            List<ScorePlayerTeam> teams = new ArrayList<>(this._getScoreboard().getTeams().stream()
                    .filter(team -> team.getName().contains("team_")).collect(Collectors.toList()));

            List<String> teamPrefixAndSuffix = teams.stream()
                    .map(team -> team.getPrefix().getString() + team.getSuffix().getString())
                    .collect(Collectors.toList());

            Optional<String> optionalTotalScoreboard =
                    teamPrefixAndSuffix.stream().reduce((result, element) -> {
                        if (result == null)
                            return element;
                        return result + "\n" + element;
                    });

            if (optionalTotalScoreboard.isPresent()) {
                return optionalTotalScoreboard.get();
            }
        }
        return "";
    }

    @Override
    public List<String> getTeamNames() {
        if (this._getScoreboard() != null) {
            return new ArrayList<>(this._getScoreboard().getTeamNames());
        }
        return new ArrayList<>();
    }

    @Override
    public List<ScoreboardTeam> getTeams() {
        if (this._getScoreboard() != null) {
            return this._getScoreboard().getTeams().stream()
                    .map(team -> new ScoreboardTeam(team.getName(), team.getPrefix().getString(),
                            team.getSuffix().getString()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
