package com.example.selscra.lidl;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private String menuTitle;
    private List<SubMenu> submenu;

    public MainMenu(String menuTitle) {
        this.menuTitle = menuTitle;
        this.submenu = new ArrayList<>();
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public List<SubMenu> getSubmenu() {
        return submenu;
    }

    public void addSubmenu(SubMenu submenu) {
        this.submenu.add(submenu);
    }

    @Override
    public String toString() {
        return "MainMenu{" +
                "name='" + menuTitle + '\'' +
                ", submenu=" + submenu +
                '}';
    }
}
