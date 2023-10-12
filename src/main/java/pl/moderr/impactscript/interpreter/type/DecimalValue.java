package pl.moderr.impactscript.interpreter.type;

import java.math.BigDecimal;

public interface DecimalValue extends Value{
  int toInt();
  long toLong();
  double toDouble();
  BigDecimal toDecimal();
}
