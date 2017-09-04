package funjava.block3.solution;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Month;
import java.util.Date;

public class MutableStateBugSolution {
    /**
     * Beispiel für Bugs wegen Veränderlichkeit von java.util.Date Objekten.
     *
     * Szenario: Wir verkaufen Tickets für einen Workshop. Aus Planungsgründen müssen Tickets mindestens 7 Tage
     * vor dem Workshop gekauft werden. Wir bauen eine Funktion die den Workshop-Termin und einen Zeitpunkt als Parameter
     * bekommt, um zu dem angefragten Zeitpunkt noch Tickets für den Workshop verkauft werden können.
     */
    public boolean canSellTicket(Date workshopDate, Date now) {
        //In der Aufgabe wurde hier der Parameter direkt benutzt, statt eine Kopie anzulegen.
        //Dadurch wurde das Datum für die aufrufende Funktion verändert und bei wiederholten Aufrufen immer weiter
        //verschoben.
        Date sevenDaysBeforeWorkshop = new Date(workshopDate.getTime());
        now = new Date(now.getTime());

        //Berechne das Datum 7 Tage vor dem Workshop, um genau 00:00
        sevenDaysBeforeWorkshop.setHours(0);
        sevenDaysBeforeWorkshop.setMinutes(0);
        sevenDaysBeforeWorkshop.setSeconds(0);
        sevenDaysBeforeWorkshop.setTime(sevenDaysBeforeWorkshop.getTime() - 7 * 24 * 60 * 60 * 1000);

        return now.before(sevenDaysBeforeWorkshop);
    }

    @Test
    public void canSellTicketShouldAcceptValidBuyingDates() {
        Date workshop = new Date(117, Month.SEPTEMBER.ordinal() /* Date-Monate sind 0-indiziert! */, 5);
        Date now = new Date(117, Month.APRIL.ordinal(), 1);
        Assert.assertTrue(canSellTicket(workshop, now));
    }

    @Test
    public void canSellTicketsShouldRejectTooCloseBuyingDates() {
        Date workshop = new Date(117, Month.SEPTEMBER.ordinal() /* Date-Monate sind 0-indiziert! */, 5);
        Date now = new Date(117, Month.SEPTEMBER.ordinal(), 1);
        Assert.assertFalse(canSellTicket(workshop, now));
    }

    @Test
    public void canSellTicketsShouldAlwaysReturnTheSameResult() {
        Date workshop = new Date(117, Month.SEPTEMBER.ordinal() /* Date-Monate sind 0-indiziert! */, 5);
        Date now = new Date(117, Month.AUGUST.ordinal(), 25);
        Assert.assertTrue(canSellTicket(workshop, now));
        Assert.assertTrue(canSellTicket(workshop, now));

        //Für noch mehr Spaß: Man stelle sich vor, das Workshop-Datum kommt aus einem Feld einer JPA-Entity.
        //Würde canSellTicket() aufgerufen während die Entity noch an einer Session attached ist, würde die unerwünschte
        //Änderung beim nächsten flush() persistiert. Bei jeder Abfrage ob noch Tickets verkauft werden können, verschiebt
        //sich damit der Workshoptermin in der Datenbank eine Woche zurück...
    }
}
