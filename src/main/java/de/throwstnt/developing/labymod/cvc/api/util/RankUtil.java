package de.throwstnt.developing.labymod.cvc.api.util;

import com.google.gson.JsonObject;
import de.throwstnt.developing.labymod.cvc.api.data.stats.RankType;
import net.hypixel.api.reply.PlayerReply.Player;
import net.labymod.support.util.Debug;
import net.labymod.support.util.Debug.EnumDebugMode;
import net.minecraft.util.text.TextFormatting;

public class RankUtil {

    public static RankType rankFromPlayerObject(JsonObject o) {
        if (o != null) {
            if (o.has("rank")) {
                String rank = o.get("rank").getAsString();

                if (!rank.equalsIgnoreCase("normal")) {
                    if (rank.equalsIgnoreCase("admin")) {
                        return RankType.ADMIN;
                    } else if (rank.equalsIgnoreCase("moderator")) {
                        return RankType.MODERATOR;
                    } else if (rank.equalsIgnoreCase("helper")) {
                        return RankType.HELPER;
                    } else if (rank.equalsIgnoreCase("youtuber")) {
                        return RankType.YOUTUBER;
                    }
                }
            }

            if (o.has("monthlyPackageRank")) {
                String monthlyPackageRank = o.get("monthlyPackageRank").getAsString();

                if (monthlyPackageRank.equalsIgnoreCase("superstar")) {
                    return RankType.MVP_PLUS_PLUS;
                }

                Debug.log(EnumDebugMode.ADDON, monthlyPackageRank);
            }

            if (o.has("packageRank") || o.has("newPackageRank")) {
                String packageRank = o.has("packageRank") ? o.get("packageRank").getAsString()
                        : o.has("newPackageRank") ? o.get("newPackageRank").getAsString() : "";

                if (packageRank.equalsIgnoreCase("vip"))
                    return RankType.VIP;
                if (packageRank.equalsIgnoreCase("vip_plus"))
                    return RankType.VIP_PLUS;
                if (packageRank.equalsIgnoreCase("mvp"))
                    return RankType.MVP;
                if (packageRank.equalsIgnoreCase("mvp_plus"))
                    return RankType.MVP_PLUS;
            }
        }

        return RankType.USER;
    }

    public static String prefixFromPlayerObject(Player player) {
        JsonObject o = player.getRaw();

        if (o != null) {
            RankType rankType = rankFromPlayerObject(o);

            if (o.has("prefix")) {
                return o.get("prefix").getAsString();
            }

            TextFormatting rankColor = rankBaseColorFromType(rankType);
            TextFormatting plusColor = null;
            if (rankType == RankType.VIP_PLUS || rankType == RankType.MVP_PLUS
                    || rankType == RankType.MVP_PLUS_PLUS) {
                if (o.has("rankPlusColor")) {
                    plusColor = convertColor(o.get("rankPlusColor").getAsString());
                } else {
                    plusColor = TextFormatting.RED;
                }
            }

            if (rankType == RankType.MVP_PLUS_PLUS) {
                TextFormatting monthlyRankColor = TextFormatting.GOLD;
                if (o.has("monthlyRankColor")) {
                    monthlyRankColor = convertColor(o.get("monthlyRankColor").getAsString());
                }

                return monthlyRankColor + "[MVP" + plusColor + "++" + monthlyRankColor + "]"
                        + monthlyRankColor;
            } else if (rankType == RankType.MVP_PLUS) {
                return rankColor + "[MVP" + plusColor + "+" + rankColor + "]" + rankColor;
            } else if (rankType == RankType.MVP) {
                return rankColor + "[MVP]";
            } else if (rankType == RankType.VIP_PLUS) {
                return rankColor + "[VIP" + plusColor + "+" + rankColor + "]" + rankColor;
            } else if (rankType == RankType.VIP) {
                return rankColor + "[VIP]";
            } else if (rankType == RankType.ADMIN) {
                return rankColor + "[ADMIN]";
            } else if (rankType == RankType.HELPER) {
                return rankColor + "[HELPER]";
            } else if (rankType == RankType.MODERATOR) {
                return rankColor + "[MOD]";
            } else if (rankType == RankType.YOUTUBER) {
                return rankColor + "[" + TextFormatting.WHITE + "YOUTUBE" + rankColor + "]"
                        + rankColor;
            } else if (rankType == RankType.USER) {
                return "" + rankColor;
            }
        }

        return "";
    }

    public static TextFormatting rankBaseColorFromType(RankType type) {
        switch (type) {
            case ADMIN:
                return TextFormatting.RED;
            case YOUTUBER:
                return TextFormatting.RED;
            case MODERATOR:
                return TextFormatting.DARK_GREEN;
            case HELPER:
                return TextFormatting.DARK_BLUE;
            case MVP_PLUS_PLUS:
                return TextFormatting.GOLD;
            case MVP_PLUS:
                return TextFormatting.AQUA;
            case MVP:
                return TextFormatting.AQUA;
            case VIP_PLUS:
                return TextFormatting.GREEN;
            case VIP:
                return TextFormatting.GREEN;
            case USER:
                return TextFormatting.GRAY;

            default:
                return TextFormatting.DARK_GRAY;
        }
    }

    /**
     * Translates a rank color into a instance of TextFormatting
     * 
     * @param color the color as a string
     * @return the represented TextFormatting
     */
    public static TextFormatting convertColor(String color) {
        TextFormatting rankPlusColor;

        if (color.equalsIgnoreCase("aqua")) {
            rankPlusColor = TextFormatting.AQUA;
        } else if (color.equalsIgnoreCase("black")) {
            rankPlusColor = TextFormatting.BLACK;
        } else if (color.equalsIgnoreCase("blue")) {
            rankPlusColor = TextFormatting.BLUE;
        } else if (color.equalsIgnoreCase("dark_aqua")) {
            rankPlusColor = TextFormatting.DARK_AQUA;
        } else if (color.equalsIgnoreCase("dark_blue")) {
            rankPlusColor = TextFormatting.DARK_BLUE;
        } else if (color.equalsIgnoreCase("dark_gray")) {
            rankPlusColor = TextFormatting.DARK_GRAY;
        } else if (color.equalsIgnoreCase("dark_green")) {
            rankPlusColor = TextFormatting.DARK_GREEN;
        } else if (color.equalsIgnoreCase("dark_purple")) {
            rankPlusColor = TextFormatting.DARK_PURPLE;
        } else if (color.equalsIgnoreCase("dark_red")) {
            rankPlusColor = TextFormatting.DARK_RED;
        } else if (color.equalsIgnoreCase("gold")) {
            rankPlusColor = TextFormatting.GOLD;
        } else if (color.equalsIgnoreCase("gray")) {
            rankPlusColor = TextFormatting.GRAY;
        } else if (color.equalsIgnoreCase("green")) {
            rankPlusColor = TextFormatting.GREEN;
        } else if (color.equalsIgnoreCase("light_purple")) {
            rankPlusColor = TextFormatting.LIGHT_PURPLE;
        } else if (color.equalsIgnoreCase("red")) {
            rankPlusColor = TextFormatting.RED;
        } else if (color.equalsIgnoreCase("white")) {
            rankPlusColor = TextFormatting.WHITE;
        } else if (color.equalsIgnoreCase("yellow")) {
            rankPlusColor = TextFormatting.YELLOW;
        } else {
            rankPlusColor = TextFormatting.STRIKETHROUGH;
        }

        return rankPlusColor;
    }

}
