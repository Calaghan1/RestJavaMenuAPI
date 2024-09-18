package org.menu.repository;

public enum SqlStatments {
    //Создание баз данных
    CREATE_MENU_TABLE("CREATE TABLE IF NOT EXISTS menu ("
                                + "id SERIAL PRIMARY KEY, "
                                + "name VARCHAR(255) NOT NULL, "
                                + "description TEXT "
                                + ");"),
    CREATE_DISH_TABLE("CREATE TABLE IF NOT EXISTS dishes ("
                                + "id SERIAL PRIMARY KEY, "
                                + "name VARCHAR(255) NOT NULL, "
                                + "description TEXT, "
                                + "menu_id INT,"
                                + "FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE CASCADE"
                                + ");"),
    CREATE_RESTAURANT_TABLE("CREATE TABLE IF NOT EXISTS restaurants ("
                                + "id SERIAL PRIMARY KEY, "
                                + "name VARCHAR(255) NOT NULL, "
                                + "menu_id INT"
                                + ")"),
    CREATE_RESTAURANT_TO_MENU_TABLE(
                    "CREATE TABLE IF NOT EXISTS restaurants_menus ("
                                + "restaurant_id INT, "
                                + "menu_id INT, "
                                + "PRIMARY KEY (restaurant_id, menu_id), "
                                + "FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE, "
                                + "FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE CASCADE"
                                + ")"
    ),
    DROP_TABLE_CASCADE("DROP TABLE IF EXISTS %s CASCADE"),
    DROP_TABLE("DROP TABLE IF EXISTS %s"),
    INSERT("INSERT INTO %s (%s) VALUES (%s);"),
    SELECT("SELECT %s FROM %s"),
    SELECT_WERE("SELECT %s FROM %s WHERE %s = ?;"),
    UPDATE("UPDATE %s SET %s WHERE %s"),
    DELETE("DELETE FROM %s WHERE %s");
    private final String displayName;

    SqlStatments(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }
}

