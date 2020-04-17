package util.validation;

import com.jfoenix.controls.JFXTimePicker;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.time.LocalDate;
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
    public static boolean TextAreaNotEmpty(TextArea textArea){
        //returning text area empty as default value
        boolean returnVal = false;
        if(textArea.getText() != null  && !textArea.getText().isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }
    public static void TextFieldNotEmpty(TextArea textArea, Label label, String validationText){

        if(!TextAreaNotEmpty(textArea)){
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
        if((!integerField.toString().isEmpty()) && (integerField != 0)){
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
    public static boolean TextFieldNotEmpty(Float floatField){
        //returning integer fields empty as default value
        boolean returnVal = false;
        if(floatField.toString() != null  && !floatField.toString().isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }

    public static void TextFieldNotEmpty(PasswordField passwordField, Label label, String validationText){

        if(!TextFieldNotEmpty(passwordField)){
            label.setText(validationText);
        }

    }
    public static boolean PasswordFieldNotEmpty(PasswordField passwordField){
        //returning integer fields empty as default value
        boolean returnVal = false;
        if(passwordField.getText() != null  && !passwordField.getText().isEmpty()){
            returnVal = true;
        }
        return returnVal;
    }
    public static void PasswordFieldNotEmpty(PasswordField passwordField, Label label, String validationText){

        if(!PasswordFieldNotEmpty(passwordField)){
            label.setText(validationText);
        }

    }
    public static boolean PasswordFieldMatch(PasswordField passwordField, PasswordField ConfirmPasswordField){
        //returning integer fields empty as default value
        boolean returnVal = false;
        if(passwordField.getText().equals(ConfirmPasswordField.getText())){
            returnVal = true;
        }
        return returnVal;
    }
    public static void PasswordFieldMatch(PasswordField passwordField, PasswordField ConfirmPasswordField, Label passwordLabel, Label confirmPasswordLabel,String validationText){

        if(!PasswordFieldMatch(passwordField, ConfirmPasswordField)){
            passwordLabel.setText(validationText);
            confirmPasswordLabel.setText(validationText);
        }

    }
    public static boolean ImageFieldNotEmpty(ImageView imageView){
        boolean returnVal = false;
        try{
            if(!(imageView.getImage() == null) || !(imageView.getImage().isError())){
                returnVal = true;
            }
        }catch (NullPointerException ex){

        }

        return returnVal;
    }
    public static void ImageFieldNotEmpty(ImageView imageView, Label label, String validationText){

        if(!ImageFieldNotEmpty(imageView)){
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
    public static  final Pattern VALIDINTEGER = Pattern.compile("\\d+$");
    public static  final Pattern VALIDFLOAT = Pattern.compile("[+-]?([0-9]*[.])?[0-9]+$");
    public static boolean isValidNumberFormat(String number) {
        int numberCheck = 0;
        try{
            Integer.parseInt(number);
            numberCheck = 1;
        }catch(NumberFormatException ex){

        }
        try{
            Float.parseFloat(number);
            numberCheck = 2;
        }catch(NumberFormatException ex){

        }
        if(numberCheck == 1){
            Matcher matcher = VALIDINTEGER.matcher(number);
            if(matcher.matches()){
                return true;
            }else{
                return false;
            }
        }else if(numberCheck == 2){
            Matcher matcher = VALIDFLOAT.matcher(number);
            if(matcher.matches()){
                return true;
            }else{
                return false;
            }
        }
        return false;
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
    public static void isValidMaximumLength(String data, int maxLength, Label label, String validationText){
        if(!isValidMaximumLength(data,maxLength)){
            label.setText(validationText);
        }
    }
    //checking for minimum length
    public static boolean isValidMinimumLength(String data, int minLength){
        boolean returnVal = true;
        if((data.length() < minLength) && (!data.isEmpty())){
            returnVal = false;
        }
        return returnVal;
    }
    public static void isValidMinimumLength(String data, int minLength, Label label, String validationText){

        if(!isValidMinimumLength(data, minLength)){
            label.setText(validationText);
        }
    }

    public static  final Pattern VALIDOLDNIC = Pattern.compile("^[0-9]{9}[vVxX]$");
    public static  final Pattern VALIDNEWNIC = Pattern.compile("^[1-2]{1}[0-9]{11}$");

    public static boolean isValidNIC(TextField textField){
        if(isValidNumberFormat(textField.getText())){
            Matcher matcher = VALIDNEWNIC.matcher(textField.getText());
            if(matcher.matches()){
                return true;
            }else{
                return false;
            }
        }else{
            Matcher matcher = VALIDOLDNIC.matcher(textField.getText());
            if(matcher.matches()){
                return true;
            }else{
                return false;
            }
        }
    }
    public static void isValidNIC(TextField textField, Label label, String validationText){
        if(!(isValidNIC(textField))){
            label.setText(validationText);
        }
    }
    public static boolean DatePickerNotEmpty(DatePicker date){
        if(!(date.getValue() == null || date.getValue().toString().isEmpty())){
            return true;
        }
        return false;
    }
    public static void DatePickerNotEmpty(DatePicker date, Label label, String validationText){
        if(!DatePickerNotEmpty(date)){
            label.setText(validationText);
        }
    }
    public static boolean TimePickerNotEmpty(JFXTimePicker time){
        if(!(time.getValue() == null || time.getValue().toString().isEmpty())){
            return true;
        }
        return false;
    }
    public static void TimePickerNotEmpty(JFXTimePicker time, Label label, String validationText){
        if(!TimePickerNotEmpty(time)){
            label.setText(validationText);
        }
    }
    public static Callback hideOldDates(){
        Callback<DatePicker, DateCell> callB = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                        LocalDate today = LocalDate.now();
                        setDisable(empty || item.compareTo(today) < 0);
                    }

                };
            }

        };
        return  callB;
    }
    public static Callback hideFutureDates(){
        Callback<DatePicker, DateCell> callB = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                        LocalDate today = LocalDate.now();
                        setDisable(empty || item.compareTo(today) > 0);
                    }

                };
            }

        };
        return  callB;
    }
}
