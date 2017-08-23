package it.degouw.bot.reference;

public enum Perm {

    DEFAULT(0), MEMBER(1), MODERATOR(2), ADMIN(3), OWNERS(4), SERVER_OWNER(50), BOT_OWNER(100);

    private int numVal;

    Perm(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

    public static final Perm[] PERMS = Perm.values();

}
