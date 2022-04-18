import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Sample input file to test svs.sonar.plugins.java.checks.AvoidCookiesCheck
 *
 * @author Serj Sintsov, 2015
 **/
public class TestAvoidCookiesCheck {

  public void aMethod() {
    HttpServletResponse resp = null;
    resp.addCookie((Cookie) null);
  }

}
