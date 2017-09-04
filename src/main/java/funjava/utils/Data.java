package funjava.utils;

public interface Data<A, P, Ac, B> {

    Iterable<P> getPeople();

    Iterable<A> getAddresses();

    Iterable<Ac> getAccounts();

    Iterable<B> getBankingTransactions();
}
