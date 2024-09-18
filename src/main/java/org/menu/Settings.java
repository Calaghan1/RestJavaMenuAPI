package org.menu;
public enum Settings {
    ENV_PATH("/usr/local/tomcat/conf");

    private final String displayName;

    Settings(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }

}
