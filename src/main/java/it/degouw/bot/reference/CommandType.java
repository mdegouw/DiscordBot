package it.degouw.bot.reference;

public enum CommandType {

    ETC("Etc"), ADMINISTRATION("Administration"), SETTINGS("Settings"), GUILDADMIN("Guild Administration"), ESSENTIALS("Essentials"), CHATUTILS("Chat Utilities"), MUSIC("Music");

    private String Name;

    CommandType(String name) { this.Name = name; }

    public String getName() {return this.Name; }

    public static final CommandType[] type = CommandType.values();

}
