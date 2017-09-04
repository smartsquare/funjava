package funjava.vavr.solution;

import funjava.vavr.BankingService;
import funjava.vavr.PersonVavr;
import funjava.vavr.VavrData;
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

public class BankingServiceNGTest {

    private static VavrData DATA = new VavrData();

    private static Map<String, PersonVavr> PERSON_MAP = DATA.getPeople().toMap(p -> Tuple(p.getName(), p));


    private static PersonVavr ALICE = PERSON_MAP.get("Alice").get();
    private static PersonVavr BOB = PERSON_MAP.get("Bob").get();
    private static PersonVavr CAROL = PERSON_MAP.get("Carol").get();
    private static PersonVavr DAVE = PERSON_MAP.get("Dave").get();
    private static PersonVavr EVE = PERSON_MAP.get("Eve").get();

    private BankingServiceNG bankingService = new BankingServiceNG();

    @Test
    public void alicHasSchufaScoreWithOption(){
        assertEquals(Integer.valueOf(4), bankingService.checkSchufaScoreWithOption(ALICE).get());
    }

    @Test
    public void multipleAddressesWithOption(){
        assertEquals(None(), bankingService.checkSchufaScoreWithOption(BOB));
        assertEquals(None(), bankingService.checkSchufaScoreWithOption(CAROL));
    }

    @Test
    public void unknownNationalityWithOption(){
        assertEquals(None(), bankingService.checkSchufaScoreWithOption(DAVE));
        assertEquals(None(), bankingService.checkSchufaScoreWithOption(EVE));
    }

    @Test
    public void aliceHasSchufaScoreWithEither(){
        assertEquals(Integer.valueOf(4),
                bankingService.checkSchufaScoreWithEither(ALICE).right().get());
    }

    @Test
    public void multipleAddressesWithEither(){
        Either<BankingService.SchufaErrorCode, Integer> schufaResultBob =
                bankingService.checkSchufaScoreWithEither(BOB);
        Either<BankingService.SchufaErrorCode, Integer> schufaResultCarol =
                bankingService.checkSchufaScoreWithEither(CAROL);

        assertTrue(schufaResultBob.isLeft());
        assertTrue(schufaResultCarol.isLeft());
        assertEquals(UNKNOWN_PERSON, schufaResultBob.left().get());
        assertEquals(UNKNOWN_PERSON, schufaResultCarol.left().get());
    }

    @Test
    public void unknownNationalityWithEither(){
        Either<BankingService.SchufaErrorCode, Integer> daveResult =
                bankingService.checkSchufaScoreWithEither(DAVE);

        Either<BankingService.SchufaErrorCode, Integer> eveResult =
                bankingService.checkSchufaScoreWithEither(EVE);

        assertTrue(daveResult.isLeft());
        assertEquals(GENERAL_ERROR, daveResult.left().get());
        assertTrue(eveResult.isLeft());
        assertEquals(GENERAL_ERROR, eveResult.left().get());
    }

    @Test
    public void aliceHasSchufaScoreWithValidation(){
        Validation<BankingService.SchufaErrorCode, Integer> schufaResult =
            bankingService.checkSchufaScoreWithValidation(ALICE);

        assertTrue(schufaResult.isValid());
        assertEquals(Integer.valueOf(4), schufaResult.get());
    }

    @Test
    public void multipleAddressesWithValidation(){
        Validation<BankingService.SchufaErrorCode, Integer> schufaResultBob =
                bankingService.checkSchufaScoreWithValidation(BOB);
        Validation<BankingService.SchufaErrorCode, Integer> schufaResultCarol =
                bankingService.checkSchufaScoreWithValidation(CAROL);

        assertTrue(schufaResultBob.isInvalid());
        assertTrue(schufaResultCarol.isInvalid());
        assertEquals(UNKNOWN_PERSON, schufaResultBob.getError());
        assertEquals(UNKNOWN_PERSON, schufaResultCarol.getError());
    }

    @Test
    public void unknownNationalityWithValidation(){
        Validation<BankingService.SchufaErrorCode, Integer> daveResult =
                bankingService.checkSchufaScoreWithValidation(DAVE);

        Validation<BankingService.SchufaErrorCode, Integer> eveResult =
                bankingService.checkSchufaScoreWithValidation(EVE);

        assertTrue(daveResult.isInvalid());
        assertTrue(eveResult.isInvalid());
        assertEquals(GENERAL_ERROR, daveResult.getError());
        assertEquals(GENERAL_ERROR, eveResult.getError());
    }

    @Test
    public void alicHasSchufaScoreWithTry(){
        Try<Integer> schufaResult = bankingService.checkSchufaScoreWithTry(ALICE);

        assertTrue(schufaResult.isSuccess());
        assertEquals(Integer.valueOf(4), schufaResult.get());
    }

    @Test
    public void multipleAddressesWithTry(){
        Try<Integer> schufaResultBob = bankingService.checkSchufaScoreWithTry(BOB);
        Try<Integer> schufaResultCarol = bankingService.checkSchufaScoreWithTry(CAROL);

        assertTrue(schufaResultBob.isFailure());
        assertTrue(schufaResultCarol.isFailure());
        assertTrue(schufaResultBob.getCause().getMessage().contains("UNKNOWN_PERSON"));
        assertTrue(schufaResultCarol.getCause().getMessage().contains("UNKNOWN_PERSON"));
    }

    @Test
    public void unknownNationalityWithTry(){
        Try<Integer> daveResult = bankingService.checkSchufaScoreWithTry(DAVE);

        Try<Integer> eveResult = bankingService.checkSchufaScoreWithTry(EVE);

        assertTrue(daveResult.isFailure());
        assertTrue(daveResult.getCause().getMessage().contains("GENERAL_ERROR"));
        assertTrue(eveResult.isFailure());
        assertTrue(eveResult.getCause().getMessage().contains("GENERAL_ERROR"));
    }


}
