package funjava.vavr;

import io.vavr.collection.Map;
import io.vavr.control.Either;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.junit.Test;

import static funjava.vavr.BankingService.SchufaErrorCode.GENERAL_ERROR;
import static funjava.vavr.BankingService.SchufaErrorCode.UNKNOWN_PERSON;
import static io.vavr.API.None;
import static io.vavr.API.Tuple;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankingServiceTest {

    private static VavrData DATA = new VavrData();

    private static Map<String, PersonVavr> PERSON_MAP = DATA.getPeople().toMap(p -> Tuple(p.getName(), p));

    private static PersonVavr ALICE = PERSON_MAP.get("Alice").get();
    private static PersonVavr BOB = PERSON_MAP.get("Bob").get();
    private static PersonVavr CAROL = PERSON_MAP.get("Carol").get();
    private static PersonVavr EVE = PERSON_MAP.get("Eve").get();

    private BankingService bankingService = new BankingService();

    @Test
    public void eveHasSchufaScoreWithOption(){
        assertEquals(Integer.valueOf(3), bankingService.checkSchufaScoreWithOption(EVE).get());
    }

    @Test
    public void aliceIsNotKnownToSchufaWithOption(){
        assertEquals(None(), bankingService.checkSchufaScoreWithOption(ALICE));
    }

    @Test
    public void addressWithBahnhofstrCausesSchufaErrorWithOption(){
        assertEquals(None(), bankingService.checkSchufaScoreWithOption(BOB));
        assertEquals(None(), bankingService.checkSchufaScoreWithOption(CAROL));
    }

    @Test
    public void eveHasSchufaScoreWithEither(){
        assertEquals(Integer.valueOf(3),
                bankingService.checkSchufaScoreWithEither(EVE).right().get());
    }

    @Test
    public void aliceIsNotKnownToSchufaWithEither(){
        Either<BankingService.SchufaErrorCode, Integer> schufaResult =
                bankingService.checkSchufaScoreWithEither(ALICE);

        assertTrue(schufaResult.isLeft());
        assertEquals(UNKNOWN_PERSON, schufaResult.left().get());
    }

    @Test
    public void addressWithBahnhofstrCausesSchufaErrorWithEither(){
        Either<BankingService.SchufaErrorCode, Integer> bobResult =
                bankingService.checkSchufaScoreWithEither(BOB);

        Either<BankingService.SchufaErrorCode, Integer> carolResult =
                bankingService.checkSchufaScoreWithEither(CAROL);

        assertTrue(bobResult.isLeft());
        assertEquals(GENERAL_ERROR, bobResult.left().get());
        assertTrue(carolResult.isLeft());
        assertEquals(GENERAL_ERROR, carolResult.left().get());
    }

    @Test
    public void eveHasSchufaScoreWithValidation(){
        Validation<BankingService.SchufaErrorCode, Integer> schufaResult =
                bankingService.checkSchufaScoreWithValidation(EVE);

        assertTrue(schufaResult.isValid());
        assertEquals(Integer.valueOf(3), schufaResult.get());
    }

    @Test
    public void aliceIsNotKnownToSchufaWithValidation(){
        Validation<BankingService.SchufaErrorCode, Integer> schufaResult =
                bankingService.checkSchufaScoreWithValidation(ALICE);

        assertTrue(schufaResult.isInvalid());
        assertEquals(UNKNOWN_PERSON, schufaResult.getError());
    }

    @Test
    public void addressWithBahnhofstrCausesSchufaErrorWithValidation(){
        Validation<BankingService.SchufaErrorCode, Integer> bobResult =
                bankingService.checkSchufaScoreWithValidation(BOB);

        Validation<BankingService.SchufaErrorCode, Integer> carolResult =
                bankingService.checkSchufaScoreWithValidation(CAROL);

        assertTrue(bobResult.isInvalid());
        assertEquals(GENERAL_ERROR, bobResult.getError());
        assertTrue(carolResult.isInvalid());
        assertEquals(GENERAL_ERROR, carolResult.getError());
    }

    @Test
    public void eveHasSchufaScoreWithTry(){
        Try<Integer> schufaResult = bankingService.checkSchufaScoreWithTry(EVE);

        assertTrue(schufaResult.isSuccess());
        assertEquals(Integer.valueOf(3), schufaResult.get());
    }

    @Test
    public void aliceIsNotKnownToSchufaWithTry(){
        Try<Integer> schufaResult = bankingService.checkSchufaScoreWithTry(ALICE);

        assertTrue(schufaResult.isFailure());
        assertTrue(schufaResult.getCause().getMessage().contains("UNKNOWN_PERSON"));
    }

    @Test
    public void addressWithBahnhofstrCausesSchufaErrorWithTry(){
        Try<Integer> bobResult = bankingService.checkSchufaScoreWithTry(BOB);

        Try<Integer> carolResult = bankingService.checkSchufaScoreWithTry(CAROL);

        assertTrue(bobResult.isFailure());
        assertTrue(bobResult.getCause().getMessage().contains("GENERAL_ERROR"));
        assertTrue(carolResult.isFailure());
        assertTrue(carolResult.getCause().getMessage().contains("GENERAL_ERROR"));
    }





}