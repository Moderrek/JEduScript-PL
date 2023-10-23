package pl.moderr.eduscript.interpreter.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum ValueType {
  UNIT, BOOLEAN, DECIMAL, INTEGER, STRUCT, IDENTIFIER, LIST, STRING;

  @Contract(pure = true)
  public static String TypeToName(@NotNull ValueType type) {
    return switch (type) {
      case UNIT -> "nic";
      case LIST -> "lista";
      case STRING -> "tekst";
      case STRUCT -> "struktura";
      case BOOLEAN -> "logiczna";
      case DECIMAL -> "liczba";
      case INTEGER -> "calk";
      case IDENTIFIER -> "nazwa";
    };
  }

}
