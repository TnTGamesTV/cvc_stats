package de.throwstnt.developing.labymod.cvc.api.data.stats.game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import de.throwstnt.developing.labymod.cvc.api.data.stats.WeaponType;

public class CvcGameStats {

    private int kills;
    private int headshots;
    private int deaths;

    private int points;

    private double virtualHeadshotAccuracy;

    private double virtualKillDeathRatio;

    private List<CvcWeaponGameStats> weaponStats;

    public CvcGameStats() {
        this.kills = 0;
        this.deaths = 0;
        this.headshots = 0;

        this.points = 0;

        this.weaponStats = Arrays.asList(WeaponType.values()).stream()
                .map(type -> new CvcWeaponGameStats(type)).collect(Collectors.toList());
    }

    public int getKills() {
        return kills;
    }

    public int getHeadshots() {
        return headshots;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getPoints() {
        return points;
    }

    public double getVirtualHeadshotAccuracy() {
        return virtualHeadshotAccuracy;
    }

    public double getVirtualKillDeathRatio() {
        return virtualKillDeathRatio;
    }

    public List<CvcWeaponGameStats> getWeaponStats() {
        return weaponStats;
    }

    public void setKills(int kills) {
        this.kills = kills;

        this.updateVirtuals();
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;

        this.updateVirtuals();
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;

        this.updateVirtuals();
    }

    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Updates all virtual scores
     */
    public void updateVirtuals() {
        this.virtualHeadshotAccuracy = (double) this.headshots / Math.max((double) this.kills, 1.0);
        this.virtualKillDeathRatio = (double) this.kills / Math.max((double) this.deaths, 1.0);

        this.weaponStats.forEach(CvcWeaponGameStats::updateVirtuals);
    }
}
