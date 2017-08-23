package it.degouw.bot.reference;

public enum Perm {

    DEFAULT(0, "default"), MEMBER(1, "member"), MODERATOR(2, "moderator"), ADMIN(3, "admin"), OWNER(4, "owner"), SERVER_OWNER(50, "server_owner"), BOT_OWNER(100, "bot_owner");

    private final int numVal;

    private final String name;

    Perm(int numVal, String name) {

        this.numVal = numVal;
        this.name = name;
    }

    public int getNumVal() {
        return numVal;
    }

    public String getName() {return name; }

    public static final Perm[] PERMS = Perm.values();

}
