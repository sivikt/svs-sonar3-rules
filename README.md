Some of my java rules for Sonar, 2015
=====================================

## How to deploy this plugin
1. Build the project running `mvn clean install`.
2. Go to `target` directory. Copy `svs-sonar-java-rules-<version>.jar` into `$SONARQUBE_HONE/extensions/plugins`.
3. Restart SonarQube server.
4. Goto [http://localhost:9000/profiles](http://localhost:9000/profiles) and make sure profile 
'SVS Code Convention Rules' is exists and active.

## How to develop this plugin
Useful links to start from:
*[SonarQube platform documentation](http://docs.sonarqube.org/display/HOME/SonarQube+Platform)
*[Developer documentation](http://docs.sonarqube.org/display/DEV/Extension+Guide)
*[Plugin example sources](https://github.com/SonarSource/sonar-examples)
*[Sonar Java plugin sources](https://github.com/SonarSource/sonar-java)
*[SonarQube sources](https://github.com/SonarSource/sonarqube)
*[SonarQube google group](https://groups.google.com/forum/#!forum/sonarqube)

!(Sonar platform)[SonarPlatform.png]

##How to use this plugin
Suppose that:  

1. Your SonarQube server is running on `SERVER_ADDRESS=http://localhost:9000`.
2. SonarQube uses MySQL database accessible via jdbc `SERVER_JDBC_URL=jdbc:mysql://localhost:3306/sonar?useUnicode=true&amp;characterEncoding=utf8`.


1. Download [SonarQube Runner](http://www.sonarqube.org/downloads)  
2. Edit SonarQube Runner config at `$SONARQUBE_RUNNER_HOME/conf/sonar-runner.properties`  
  2.1. Setup `sonar.host.url=$SERVER_ADDRESS`  
  2.2. Setup `sonar.jdbc.url=$SERVER_JDBC_URL`  
  2.3. Setup `sonar.jdbc.username` and `sonar.jdbc.password`  
  2.4. Setup `sonar.login` and `sonar.password`. Default is admin/admin 
3. Add `sonar-project.properties` into the root of a project to be analyzed. As described [here](http://docs.sonarqube.org/display/SONAR/Analyzing+with+SonarQube+Runner) in section 'Simple Project'. 
Additional SonarQube Runner parameters can be found [here](http://docs.sonarqube.org/display/SONAR/Analysis+Parameters)
4. Run `$SONARQUBE_RUNNER_HOME/bin/sonar-runner` from the project root.
