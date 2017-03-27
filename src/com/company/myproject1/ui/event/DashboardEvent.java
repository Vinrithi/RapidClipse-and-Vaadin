package com.company.myproject1.ui.event;

import com.company.myproject1.ui.domain.User;
import com.company.myproject1.ui.views.dashboard.DashboardViewType;

/*
 * Event bus events used in DashboardMenu are listed here as inner classes.
 */
public abstract class DashboardEvent {

    public static final class UserLoginRequestedEvent {
        private final String phoneNumber, pin;

        public UserLoginRequestedEvent(final String phoneNumber, final String pin) {
            this.phoneNumber = phoneNumber;
            this.pin = pin;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getPin() {
            return pin;
        }
    }

    public static final class UpdateUserEvent{
        private final User user;

        public UpdateUserEvent(final User user){
            this.user = user;
        }

        public User getUserDetails(){
            return user;
        }
    }

    public static class BrowserResizeEvent {

    }

    public static class UserLoggedOutEvent {

    }

    public static final class PostViewChangeEvent {
        private final DashboardViewType view;

        public PostViewChangeEvent(final DashboardViewType view) {
            this.view = view;
        }

        public DashboardViewType getView() {
            return view;
        }
    }

    public static class CloseOpenWindowsEvent {
    }

    public static class ProfileUpdatedEvent {
    }

}
