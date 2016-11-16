package com.mkr.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("validators.CarNumberValidator")
public class CarNumber implements Validator {

    private static final String NUMBER_PATTERN = "\\w\\w\\s\\d\\d\\d\\d\\s\\w\\w";

    private Pattern pattern;
    private Matcher matcher;

    public CarNumber(){
        pattern = Pattern.compile(NUMBER_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        matcher = pattern.matcher(value.toString());
        if(!matcher.matches()){
            FacesMessage msg = new FacesMessage("Car number validation failed.", "Invalid car number format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
