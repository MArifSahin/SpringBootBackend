package com.innova.event;

import com.innova.dto.request.EditorApplicationForm;
import com.innova.model.User;
import org.springframework.context.ApplicationEvent;

public class OnEditorApplicationSuccessEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String appUrl;
    private User user;
    private EditorApplicationForm applicationForm;

    public OnEditorApplicationSuccessEvent(User user, EditorApplicationForm applicationForm, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
        this.applicationForm = applicationForm;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EditorApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(EditorApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
    }
}