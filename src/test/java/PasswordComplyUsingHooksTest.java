import org.example.PasswordComply;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class PasswordComplyUsingHooksTest {
    PasswordComply password;
    static File myFileReader;
    static Scanner myScannerRead;
    static InputStream passwordFile;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    public static void prepareLoadTestData(){
        try{
            myFileReader = new File("C:/JUnit/PasswordFile.txt");
            myScannerRead = new Scanner(myFileReader);
        }catch (IllegalArgumentException e){
            System.out.println("An error occured.");
            e.printStackTrace();
        }catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @BeforeEach
    void Init(TestInfo testInfo, TestReporter testReporter){
        this.testInfo = testInfo;
        this.testReporter= testReporter;
        testReporter.publishEntry("Running " + testInfo.getDisplayName() + " " +
                "under tags " + testInfo.getTags());
        password = new PasswordComply("");
    }

    @AfterEach
    void cleanUpAfterTest(){
        myScannerRead.close();
        System.out.println("Clean Up of all local resources....");
    }

    @AfterAll
    static void cleanAll(){
        Runtime.getRuntime().gc();
        System.out.println("Clean up of Application Level DB data, Logs, " +
                "resources etc... done.");
    }

    @Nested
    @Tag("Configuration-File-Check")
    @DisplayName("Check ALL config Files")
    class COnfigFilesAvailable{

        @Test
        @DisplayName("Config")
        void checkALLConfigFiles(){
            System.out.println("All Config files created....");
        }


        @DisplayName("Config Entried Created...")
        @RepeatedTest(2)
        void checkALLConfigEntries(){
            System.out.println("All Confiog entries valid...");
        }
    }

    @Test
    @Tag("DB-Password-Checks")
    @DisplayName("Check ALL DB passwword are valid....")
    void testDoesPAsswordComply(){
        assumeTrue((myFileReader != null));
        boolean expedtedResult = true;
        while(myScannerRead.hasNextLine()){
            String passwordRead = myScannerRead.nextLine();
            System.out.println("Password Read from File : " + passwordRead);
            password.setPassword(passwordRead);
            boolean actualResult = password.doesPasswordComply();
            assertAll(
                    () -> assertEquals(expedtedResult, actualResult,
                            "Password conditions failed!")
            );
        }
    }

    @Disabled
    @Test
    void cleanUpFiles(){
        myFileReader.delete();
        System.out.println("Deleting all config, password files created.");
    }

}
