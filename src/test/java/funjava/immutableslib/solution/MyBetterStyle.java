package funjava.immutableslib.solution;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ImplementationVisibility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Value.Style(
        typeImmutable = "Im*Value",
        visibility = ImplementationVisibility.PACKAGE,
        depluralize = true,
        depluralizeDictionary = {"employee:employees", "account:accounts"},
        typeBuilder = "Creator"
)
public @interface MyBetterStyle {
}
