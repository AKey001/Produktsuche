package de.produktsuche.backend.commons;

public interface RequestOperationHandler<R> {

    void execute(R r);

}
