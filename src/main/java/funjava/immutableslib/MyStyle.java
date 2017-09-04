package funjava.immutableslib;


import org.immutables.value.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Value.Style(
        typeAbstract = {"*ILib"}, // 'ILib' suffix will be detected and trimmed
        typeImmutable = "*Value", // 'Value' suffix for generated immutable type
        depluralize = true, // enable depluralization feature
        depluralizeDictionary = {"person:people", "address:addresses"}, // specifying dictionary of exceptions
        redactedMask = "####" // mask used for redacted attributes
)
public @interface MyStyle { }


/* example from docu:
@Value.Style(
    get = {"is*", "get*"}, // Detect 'get' and 'is' prefixes in accessor methods
    init = "set*", // Builder initialization methods will have 'set' prefix
    typeAbstract = {"Abstract*"}, // 'Abstract' prefix will be detected and trimmed
    typeImmutable = "*", // No prefix or suffix for generated immutable type
    builder = "new", // construct builder using 'new' instead of factory method
    build = "create", // rename 'build' method on builder to 'create'
    visibility = ImplementationVisibility.PUBLIC, // Generated class will be always public
    defaults = @Value.Immutable(copy = false)) // Disable copy methods by default
 */
