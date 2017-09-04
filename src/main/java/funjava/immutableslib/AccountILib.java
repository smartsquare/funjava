package funjava.immutableslib;


import org.immutables.value.Value;

import java.math.BigDecimal;

@Value.Immutable
@MyStyle
public interface AccountILib {

    @Value.Parameter
    String getAccountNumber();

    @Value.Parameter
    BigDecimal getBalance();

    @Value.Parameter
    PersonILib getPerson();

    @Value.Check
    default AccountILib normalize(){
        if (getBalance().scale() != 4){
            return AccountValue.builder()
                    .from(this)
                    .balance(getBalance().setScale(4, BigDecimal.ROUND_HALF_UP))
                    .build();
        }
        return this;
    }

}
