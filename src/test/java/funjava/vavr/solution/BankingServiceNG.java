package funjava.vavr.solution;

import funjava.vavr.BankingService.SchufaErrorCode;
import funjava.vavr.BankingService.SchufaException;
import funjava.vavr.PersonVavr;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;

import static io.vavr.API.*;

public class BankingServiceNG {

    public Option<Integer> checkSchufaScoreWithOption(PersonVavr person) {
        return checkSchufa(person).toOption();
    }

    public Either<SchufaErrorCode, Integer> checkSchufaScoreWithEither(PersonVavr person) {
        return checkSchufa(person).toEither();
    }

    public Validation<SchufaErrorCode, Integer> checkSchufaScoreWithValidation(PersonVavr person) {
        return checkSchufa(person);
    }

    public Try<Integer> checkSchufaScoreWithTry(PersonVavr person) {
        Validation<SchufaErrorCode, Integer> schufaCheck = checkSchufa(person);
        return schufaCheck.isValid() ? Success(schufaCheck.get()) : Failure(new SchufaException(schufaCheck.getError()));
    }

    private Validation<SchufaErrorCode, Integer> checkSchufa(PersonVavr person) {
        return Match(person).of(
                Case($(p -> p.getNationality().isEmpty()), Invalid(SchufaErrorCode.GENERAL_ERROR)),
                Case($(p -> p.getAddresses().size() > 1), Invalid(SchufaErrorCode.UNKNOWN_PERSON)),
                Case($(), p -> Valid(score(p.getName())))
        );

    }

    private int score(String name){
        return CharSeq(name)
                .filter(Character::isLowerCase)
                .size();
    }

}
