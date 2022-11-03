package de.produktsuche.backend.commons;

@FunctionalInterface
public interface RequestOperationHandler<R> {
    void execute(R r);
}
