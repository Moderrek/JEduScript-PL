package pl.moderr.eduscript.interpreter.exception;

import org.jetbrains.annotations.NotNull;

public class TypeErr extends Exception {

  public TypeErr(@NotNull Object message) {
    super("TypeErr: " + String.valueOf(message).toLowerCase());
  }

}
