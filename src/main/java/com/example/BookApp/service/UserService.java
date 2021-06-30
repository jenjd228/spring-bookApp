package com.example.BookApp.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    public void sendCode(String contact) {
        if (checkPhone(contact)){
            System.out.println(contact);
        }else if (checkEmail(contact)){
            System.out.println(contact);
        }
    }

    private boolean checkPhone(String contact) {
        String phoneRule = "^[+][7][(][0-9]{3}[)][0-9]{3}[-][0-9]{2}[-][0-9]{2}$";
        Pattern pattern = Pattern.compile(phoneRule);
        Matcher matcher = pattern.matcher(contact.replaceAll(" ", ""));
        return matcher.find();
    }

    private boolean checkEmail(String contact) {
        return EmailValidator.getInstance().isValid(contact);
    }
}
