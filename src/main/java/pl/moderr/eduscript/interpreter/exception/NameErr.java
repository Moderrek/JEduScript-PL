package pl.moderr.eduscript.interpreter.exception;

import org.jetbrains.annotations.NotNull;

public class NameErr extends Exception {

  public NameErr(@NotNull Object message) {
    super("NameErr: " + String.valueOf(message).toLowerCase());
  }

}
