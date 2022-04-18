import com.sun.net.httpserver.HttpServer;

import javax.servlet.http.HttpSession;

/**
 * Sample input file to test svs.sonar.plugins.java.checks.AvoidHttpSessionAttributesCheck
 *
 * @author Serj Sintsov, 2015
 **/
public class TestAvoidHttpSessionAttributesCheck {

  public void aMethod() {
    HttpSession session = null;
    session.getAttribute("attr");
    session.getAttributeNames();
    session.setAttribute("attr2", "value");
  }

}
