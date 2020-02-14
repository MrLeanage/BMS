package util.validation;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {
    //Checking not empty for Text Fields with same name
    public static boolean TextFieldNotEmpty(TextField textField){
        //returning text fields empty as default value
        boolean returnVal = false;
        if(textField.getText() != null  && !textField.getText().isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }
    public static void TextFieldNotEmpty(TextField textField, Label label, String validationText){

        if(!TextFieldNotEmpty(textField)){
            label.setText(validationText);
        }

    }

    //Checking TextAreas for not empty with same name
    public static boolean TextFieldNotEmpty(TextArea textArea){
        //returning text area empty as default value
        boolean returnVal = false;
        if(textArea.getText() != null  && !textArea.getText().isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }
    public static void TextFieldNotEmpty(TextArea textArea, Label label, String validationText){

        if(!TextFieldNotEmpty(textArea)){
            label.setText(validationText);
        }


    }

    //Checking Strings for not empty with same name

    public static boolean TextFieldNotEmpty(String stringField){
        //returning string fields empty as default value
        boolean returnVal = false;
        if(stringField != null  && !stringField.isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }
    public static void TextFieldNotEmpty(String stringField, Label label, String validationText){

        if(!TextFieldNotEmpty(stringField)){
            label.setText(validationText);
        }

    }

    //Checking Integers for not empty with same name

    public static boolean TextFieldNotEmpty(Integer integerField){
        //returning integer fields empty as default value
        boolean returnVal = false;
        if(integerField.toString() != null  && !integerField.toString().isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }
    public static void TextFieldNotEmpty(Integer integerField, Label label, String validationText){

        if(!TextFieldNotEmpty(integerField)){
            label.setText(validationText);
        }


    }
    //Checking Long for not empty with same name

    public static boolean TextFieldNotEmpty(Long longField){
        //returning integer fields empty as default value
        boolean returnVal = false;
        if(longField.toString() != null  && !longField.toString().isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }
    public static void TextFieldNotEmpty(Long longField, Label label, String validationText){

        if(!TextFieldNotEmpty(longField)){
            label.setText(validationText);
        }


    }
    //email validation
    public static final Pattern VALIDEMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean isValidEmail(String emailStr) {
        boolean returnVal = false;
        Matcher matcher = VALIDEMAIL.matcher(emailStr);

        if(matcher.find()){
            returnVal = true;
        }
        return returnVal;
    }

    public static void isValidEmail(String emailStr, Label label, String validationText) {

        if((!isValidEmail(emailStr))&& (!emailStr.isEmpty())){
            label.setText(validationText);
        }
    }
    //phone number validation for 10 digits, start with zero, next value from 1-9 and rest 8 digits from 0-9
    public static  final Pattern VALIDPHONENO = Pattern.compile("^[0][1-9][0-9]{8}$");

    public static boolean isValidPhoneNo(String phone){

        Matcher matcher = VALIDPHONENO.matcher(phone);
        if(matcher.matches()){
            return true;
        }else{
            return false;
        }
    }

    public static void isValidPhoneNo(String phone, Label label, String validationText){

        if((!isValidPhoneNo(phone))&& (!phone.isEmpty())){
            label.setText(validationText);
        }
    }
    //checking for integer number
    public static  final Pattern VALIDINTEGER    = Pattern.compile("\\d+$");
    public static boolean isValidNumberFormat(String number) {
        Matcher matcher = VALIDINTEGER.matcher(number);
        if(matcher.matches()){
            return true;
        }else{
            return false;
        }
    }
    public static void isValidNumberFormat(String number, Label label, String validationText) {
        if((!isValidNumberFormat(number)) && (!number.isEmpty())){
            label.setText(validationText);
        }
    }
    //checking for maximum length
    public static boolean isValidMaximumLength(String data, int maxLength){
        boolean returnVal = true;
        if(data.length() > maxLength){
            returnVal = false;
        }
        return returnVal;
    }
    public static void isValidMaximumLength(String data, int maxLength, Label label, String validationtext){
        if(!isValidMaximumLength(data,maxLength)){
            label.setText(validationtext);
        }
    }
}
