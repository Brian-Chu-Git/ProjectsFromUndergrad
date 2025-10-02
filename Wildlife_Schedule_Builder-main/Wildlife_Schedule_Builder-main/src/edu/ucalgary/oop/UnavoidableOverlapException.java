package edu.ucalgary.oop;

public class UnavoidableOverlapException extends Exception{
    public UnavoidableOverlapException(){
        super("Unavoidable Overlap has occurred, please fix the error");
    }
}
