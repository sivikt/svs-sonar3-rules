import java.io.*;
import java.io.File;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystem;
import java.nio.file.Path;

/**
 * Sample input file to test svs.sonar.plugins.java.checks.AvoidLocalDiskIOCheck
 *
 * @author Serj Sintsov, 2015
 **/
public class TestAvoidLocalDiskIOCheck {

  public void aMethod() throws FileNotFoundException {
    // instantiate forbidden type
    File f = new File("filename");

    // instantiate type with arguments of forbidden type
    FileInputStream fin = new FileInputStream(f);

    // invoke method on forbidden type
    String path = f.getAbsolutePath();

    // invoke method with arguments of forbidden type
    bMethos(fin);

    // invoke method that return forbidden type
    try {
      FileChannel in = new FileInputStream(f).getChannel();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void bMethos(FileInputStream in) {

  }

}
