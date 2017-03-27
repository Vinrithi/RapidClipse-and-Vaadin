package com.company.myproject1.ui.event;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.util.Collection;

/*
 * Created by elon on 3/7/2017.
 */
public class Notifications{
    public static void showNotification(String caption, String description, Notification.Type notificationType, int delay, String style, Position pos){
        Notification noty = new Notification(caption,description, notificationType, true);
        noty.setPosition(pos);
        noty.setDelayMsec(delay);
        if(!style.equals(""))
            noty.setStyleName(style);
        noty.show(Page.getCurrent());
    }
}
