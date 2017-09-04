package funjava.vavr;

import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;

import static io.vavr.API.*;

public class BankingService {

    public enum SchufaErrorCode {
        UNKNOWN_PERSON, GENERAL_ERROR}

    public static class SchufaException extends RuntimeException{
        public SchufaException(SchufaErrorCode errorCode){
            super("SchufaException: " + errorCode.name());
        }
    }

    public Option<Integer> checkSchufaScoreWithOption(PersonVavr person){
        return checkSchufa(person).toOption();
    }

    public Either<SchufaErrorCode, Integer> checkSchufaScoreWithEither(PersonVavr person){
        return checkSchufa(person);
    }

    public Validation<SchufaErrorCode, Integer> checkSchufaScoreWithValidation(PersonVavr person){
        return Validation.fromEither(checkSchufa(person));
    }

    public Try<Integer> checkSchufaScoreWithTry(PersonVavr person){
        Either<SchufaErrorCode, Integer> schufaCheck = checkSchufa(person);
        return schufaCheck.isRight() ? Success(schufaCheck.get()) : Failure(new SchufaException(schufaCheck.getLeft()));
    }

    private Either<SchufaErrorCode, Integer> checkSchufa(PersonVavr person) {
        return Match(person).of(
                Case($(p -> p.getName().equals("Alice")), Left(SchufaErrorCode.UNKNOWN_PERSON)),
                Case($(this::livesInBahnhofstr), Left(SchufaErrorCode.GENERAL_ERROR)),
                Case($(), p -> Right(p.getName().length()))
        );
    }

    private boolean livesInBahnhofstr(PersonVavr person){
        return person.getAddresses().exists(a -> a.getStreet().equals("Bahnhofstr."));
    }
}
