package com.innova.event;

import com.innova.dto.request.EditorApplicationForm;
import com.innova.model.User;
import com.innova.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class EditorApplicationFormEmailListener implements ApplicationListener<OnEditorApplicationSuccessEvent> {

    @Autowired
    EmailSender emailSender;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void onApplicationEvent(OnEditorApplicationSuccessEvent event){
        try {
            this.becomeEditor(event);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

    }

    private void becomeEditor(OnEditorApplicationSuccessEvent event) throws MessagingException, IOException {
        User user = event.getUser();
        EditorApplicationForm applicationForm = event.getApplicationForm();
        String token = jwtProvider.generateJwtTokenForVerification(user);

        String recipient = user.getEmail();
        //TODO accept reject url
        String acceptUrl = "https://book-review-backend.herokuapp.com" + event.getAppUrl() + "/makeEditor?isAccepted=true&email=" + applicationForm.getEmail();
        String rejectUrl = "https://book-review-backend.herokuapp.com" + event.getAppUrl() + "/makeEditor?isAccepted=false&email=" + applicationForm.getEmail();

        Map model = new HashMap();
        model.put("acceptUrl", acceptUrl);
        model.put("rejectUrl", rejectUrl);
        model.put("signature", "https://www.innova.com.tr/tr");
        model.put("name",applicationForm.getName());
        model.put("lastname", applicationForm.getLastname());
        model.put("email", applicationForm.getEmail());
        model.put("phoneNumber", applicationForm.getPhoneNumber());
        model.put("whatDoYouWant", applicationForm.getWhatDoYouWant());
        model.put("education", applicationForm.getEducation());
        model.put("hobbies", applicationForm.getHobbies());
        model.put("favBooks", applicationForm.getFavBook());
        model.put("favMovies", applicationForm.getFavMovie());

        Context context = new Context();
        context.setVariables(model);
        String content = templateEngine.process("db-editor_application_form_email", context);

        emailSender.sendSimpleMessage(content ,recipient, "Editor Application Form");
    }

}