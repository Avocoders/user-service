package io.github.avocoders.userservice.validators;

import io.github.avocoders.userservice.exception.UserValidationException;

public class UserValidator {
    public void validateId(Long id){
        if( id == null || id <= 0 ){
            throw new UserValidationException("User id is not positive number");
        }
    }
    public void validateName(String name){
        if( name == null || name.isBlank()){
            throw new UserValidationException("User name is blank");
        }
        if( name.length() > 100 ){
            throw new UserValidationException("User name have too long characters. Max is 100");
        }
    }
    public void validateEmail(String email){
        if( email == null || email.isBlank()){
            throw new UserValidationException("User email is blank");
        }
        if( email.length() > 255){
            throw new UserValidationException("User email have too long characters. Max is 255");
        }
        if( !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
            throw new UserValidationException("User email has invalid format");
        }
    }
    public void validationAge(Integer age){
        if( age == null || age < 0 || age > 150){
            throw new UserValidationException("User age is not right. It may be between 0 and 150");
        }
    }
}
