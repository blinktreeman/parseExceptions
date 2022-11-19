package ru.bcomms.ExceptionsClasses;

public class WrongAmountOfDataException extends RuntimeException{
    public WrongAmountOfDataException(String message) {
        super(message);
    }
}
