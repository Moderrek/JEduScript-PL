package pl.moderr.eduscript.interpreter;

import org.jetbrains.annotations.NotNull;
import pl.moderr.eduscript.interpreter.function.ConstFunction;
import pl.moderr.eduscript.interpreter.statements.Expression;
import pl.moderr.eduscript.lib.function.*;

import java.util.Arrays;
import java.util.List;

public final class Interpreter {

  ImpactEnvironment environment;

  public Interpreter() throws Exception {
    environment = new ImpactEnvironment();
    environment.defineFunction(new ConstFunction("pi", Math.PI));
    environment.defineFunction(new ConstFunction("e", Math.E));
    environment.defineFunction(new PotegaFunction());
    environment.defineFunction(new PierwiastekFunction());
    environment.defineFunction(new ZaokrFunction());
    environment.defineFunction(new Log2Function());
    environment.defineFunction(new LogFunction());
    environment.defineFunction(new RadFunction());
    environment.defineFunction(new AbsFunction());
    environment.defineFunction(new SinFunction());
    environment.defineFunction(new CosFunction());
    environment.defineFunction(new TanFunction());
    environment.defineFunction(new FibFunction());
    environment.defineFunction(new FacFunction());
    environment.defineFunction(new LogiFunction());
    environment.defineFunction(new AndFunction());
    environment.defineFunction(new LubFunction());
    environment.defineFunction(new TypFunction());
    environment.defineFunction(new TekstFunction());
    environment.defineFunction(new DlugoscFunction());
    environment.defineFunction(new ZaprzeczFunction());
    environment.defineFunction(new CalkFunction());
    environment.defineFunction(new BinFunction());
    environment.defineFunction(new FloatFunction());
    environment.defineFunction(new WypiszFunction());
    environment.defineFunction(new WpiszFunction());
  }

  public void interpret(@NotNull Expression @NotNull [] expressions) throws Exception {
    try {
      for (Expression expr : expressions) {
        expr.evaluate(environment);
      }
    } catch (Exception e) {
      System.err.println(environment.getFileName());
      System.err.println(Arrays.toString(environment.getHeap().toArray()));
      throw e;
    }
  }

  public void interpret(@NotNull Expression @NotNull [] expressions, String fileName) throws Exception {
    environment.setFileName(fileName);
    try {
      for (Expression expr : expressions) {
        expr.evaluate(environment);
      }
    } catch (Exception e) {
      System.err.println(environment.getFileName());
      System.err.println(Arrays.toString(environment.getHeap().toArray()));
      throw e;
    }
  }

}
