package pl.moderr.impactscript.lib.function;

import pl.moderr.impactscript.interpreter.function.Function;
import pl.moderr.impactscript.interpreter.type.StringType;

import java.util.Scanner;

public class WpiszFunction extends Function<StringType> {
  @Override
  public StringType invoke() throws Exception {
    if(arg(0, StringType.class).isPresent()){
      System.out.print(arg(0, StringType.class).orElseThrow());
      Scanner scanner = new Scanner(System.in);
      String str = scanner.nextLine();
      return StringType.of(str);
    }
    Scanner scanner = new Scanner(System.in);
    String str = scanner.nextLine();
    return StringType.of(str);
  }

  @Override
  public String getName() {
    return "wpisz";
  }

  @Override
  public boolean isNative() {
    return true;
  }
}
