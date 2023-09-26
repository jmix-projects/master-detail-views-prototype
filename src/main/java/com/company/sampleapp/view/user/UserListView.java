package com.company.sampleapp.view.user;

import com.company.sampleapp.entity.User;
import com.company.sampleapp.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersTable")
@DialogMode(width = "50em", height = "37.5em")
public class UserListView extends StandardListView<User> {
    
    private static final Logger log = LoggerFactory.getLogger(UserListView.class);
    
    @Subscribe("testBtn")
    public void onTestBtnClick(final ClickEvent<Button> event) {
        log.info("aaabbbccc");
    }
}