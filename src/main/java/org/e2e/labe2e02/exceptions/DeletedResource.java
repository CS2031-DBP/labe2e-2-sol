package org.e2e.labe2e02.exceptions;

public class DeletedResource extends RuntimeException{
    public DeletedResource(String message) {
        super(message);
    }
}
