package it.degouw.bot.permissions;

import it.degouw.bot.reference.STATIC;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SSSS {

    public static void checkFolders(List<Guild> guilds) {

        guilds.forEach(guild -> {
            File f = new File("SERVER_SETTINGS/" + guild.getId());
            if (!f.exists() || !f.isDirectory()) {
                f.mkdirs();
            }
        });
    }

    public static void listSettings(MessageReceivedEvent event) {

        Guild g = event.getGuild();

        StringBuilder keys = new StringBuilder();
        StringBuilder values = new StringBuilder();

        File[] files = new File("SERVER_SETTINGS/" + g.getId() + "/").listFiles();
        System.out.println(files.length);

        Arrays.stream(files).forEach(file -> {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                keys.append("**" + file.getName() + "**\n");
                values.append("`" + br.readLine() + "`\n");
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        event.getTextChannel().sendMessage(new EmbedBuilder()
                .setTitle("Setings for guild \"" + g.getName() + "\" (" + g.getId() + ")", null)
                .addBlankField(false)
                .addField("Keys", keys.toString(), true)
                .addField("Values", values.toString(), true)
                .build()
        ).queue();
    }

    public static String getPrefix(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/prefix");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATIC.PREFIX;
    }

    public static void setPrefix(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/prefix");
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(f));
            wr.write(entry);
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getServerJoinMessage(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/serverjoinmessage");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }  catch (Exception e) {}
        return "OFF";
    }

    public static void setServerJoinMessage(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/serverjoinmessage");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getServerLeaveMessage(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/serverleavemessage");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return "OFF";
    }

    public static void setServerLeaveMessage(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/serverleavemessage");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getMusicChannel(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/musicchannel");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return "";
    }

    public static void setMusicChannel(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/musicchannel");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String[] getMemberRoles(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/memberRoles");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine().split(", ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATIC.MemberPerms;

    }

    public static void setMemberRoles(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/memberRoles");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getModeratorRoles(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/moderatorRoles");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine().split(", ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATIC.ModeratorPerms;

    }

    public static void setModeratorRoles(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/moderatorRoles");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getAdminRoles(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/adminRoles");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine().split(", ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATIC.AdminPerms;

    }

    public static void setAdminRoles(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/adminRoles");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getOwnerRoles(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/ownerRoles");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine().split(", ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATIC.OwnerPerms;

    }

    public static void setOwnerRoles(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/ownerRoles");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean getLockMusicChannel(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/lockmusicchannel");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine().equals("true");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return false;
    }

    public static void setLockMusicChannel(boolean entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/lockmusicchannel");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry + "");
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getAutoRole(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/autorole");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return "";
    }

    public static void setAutoRole(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/autorole");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getVoiceKickChannel(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/vkickchannel");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return "";
    }

    public static void setVoiceKickChannel(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/vkickchannel");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getR6OPSID(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/r6opsID");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return "OFF";
    }

    public static void setR6OPSID(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/r6opsID");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getWarframeAlerstChannel(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/warframealertschan");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return STATIC.warframeAlertsChannel;
    }

    public static void setWarframeAlerstChannel(String entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/warframealertschan");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            r.write(entry);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getBlacklist(Guild guild) {

        try {
            File f = new File("SERVER_SETTINGS/" + guild.getId() + "/blacklist");
            if (f.exists()) {
                try {
                    return new BufferedReader(new FileReader(f)).lines().map(s -> s.replace("\n", "")).collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {}
        return new ArrayList<>();
    }

    public static void setBlacklist(List<String> entry, Guild guild) {

        File f = new File("SERVER_SETTINGS/" + guild.getId() + "/blacklist");
        try {
            BufferedWriter r = new BufferedWriter(new FileWriter(f));
            entry.forEach(l -> {
                try {
                    r.write(l + "\n");
                } catch (IOException e) {}
            });
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
