package otp1.otpr21fotosdemo;

import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


// TODO: Connect testing methods with values for correct testing
// As of now, test methods generate their own data

// Without TestInstance resetDbChanges() will not work, or rather this whole class
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseTest {
    // Variables
    String dbUserName = System.getenv("APP_DB_USERNAME");
    String dbPassWord = System.getenv("APP_DB_PASSWORD");
    String url = System.getenv("APP_DB_URL");
    private Database database = new Database();
    private ArrayList<Integer> testImageIDs = new ArrayList<>();
    private ArrayList<Integer> testUserIDs = new ArrayList<>();

    @BeforeAll
    public void setDatabaseController() {
        System.out.println("DATABASTEST Env url: " + System.getenv("APP_DB_URL"));
        database.setController(new FotosController());
    }


    @Test
    @AfterAll
    public void resetDbChanges() {
        for (Integer i : testImageIDs){
            database.deleteImage(i);
        }
        for (Integer i : testUserIDs){
            database.deleteUser(i);
        }
        System.out.println("Removing test data from DB");
        Connection conn = null;
        int userid = database.userAndPwExists("test", "1234");

        try {
            // Connection statement
            conn = DriverManager.getConnection(url, dbUserName, dbPassWord);
            System.out.println("\nDatabase Connection Established...");

            // Deleting entry from Image table
            PreparedStatement pstmtImage = conn.prepareStatement("DELETE FROM Fotos.Image WHERE userID = ?;");
            pstmtImage.setInt(1, userid);
            pstmtImage.execute();

           // Deleting entry from Folder table
            PreparedStatement pstmtFolder = conn.prepareStatement("DELETE FROM Fotos.Folder WHERE userID = ?;");
            pstmtFolder.setInt(1, userid);
            pstmtFolder.execute();

            // Deleting entry from User table
            PreparedStatement pstmtUser = conn.prepareStatement("DELETE FROM Fotos.User WHERE userID = ?;");
            pstmtUser.setInt(1, userid);
            pstmtUser.execute();


            System.out.println("Records deleted......");

        } catch (Exception ex) {
            System.err.println("Cannot connect to database server");
            ex.printStackTrace();

        } finally {
            if (conn != null) {
                try {
                    System.out.println("\n***** Let terminate the Connection *****");
                    conn.close();
                    System.out.println("\nDatabase connection terminated...");
                } catch (Exception ex) {
                    System.out.println("Error in connection termination!");
                }
            }
        }
    }

    @Test
    @Order(1)
    public void dbConnectionTest() {
        assertDoesNotThrow(() -> {
            System.out.println("\n\n***** MySQL JDBC Connection Testing *****");
            Connection conn = null;

            try {
                // Connection statement
                conn = DriverManager.getConnection(url, dbUserName, dbPassWord);
                System.out.println("\nDatabase Connection Established...");

            } catch (Exception ex) {
                System.err.println("Cannot connect to database server");
                ex.printStackTrace();
                throw ex;
            } finally {
                if (conn != null) {
                    try {
                        System.out.println("\n***** Let terminate the Connection *****");
                        conn.close();
                        System.out.println("\nDatabase connection terminated...");
                    } catch (Exception ex) {
                        System.out.println("Error in connection termination!");
                    }
                }
            }
        });
    }


    @Test
    @Order(2)
    public void dbUserTest2() {
        assertDoesNotThrow(() -> {
            // Variables
            Connection conn = null;

            try {
                // Connection statement
                conn = DriverManager.getConnection(url, dbUserName, dbPassWord);
                System.out.println("\nDatabase Connection Established...");

                database.saltRegister("test", "1234", "test@test.com", "test@test.com", new Text());
                System.out.println("test user inserted......");


            } catch (Exception ex) {
                System.err.println("Cannot connect to database server");
                ex.printStackTrace();
                throw ex;
            } finally {
                if (conn != null) {
                    try {
                        System.out.println("\n***** Let terminate the Connection *****");
                        conn.close();
                        System.out.println("\nDatabase connection terminated...");
                    } catch (Exception ex) {
                        System.out.println("Error in connection termination!");
                    }
                }
            }
        });
    }

    @Test
    @Order(3)
    public void dbImageTest() {
        assertDoesNotThrow(() -> {
            // Variables
            Connection conn = null;
            int userid = database.userAndPwExists("test", "1234");

            int rootFolder = database.getRootFolderId(userid);
            try {
                // Connection statement
                conn = DriverManager.getConnection(url, dbUserName, dbPassWord);
                System.out.println("\nDatabase Connection Established...");

                // Image statement VALUES(imageID (int11), viewingRights (int11), fileName(varchar64), image(blob), date(date)
                // userID(int11), folderID(int11)
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Fotos.Image(viewingRights, image, userID, folderID) VALUES(?,?,?,?)");

                // viewingRights
                pstmt.setInt(1, 0);
                // blob
                InputStream in = new FileInputStream("src/test/resources/image/noimage.jpg");
                pstmt.setBlob(2, in);
                // userID
                pstmt.setInt(3, userid);
                // folderID
                pstmt.setInt(4, rootFolder);
                //Executing the statement
                pstmt.execute();
                System.out.println("Record inserted......");

            } catch (Exception ex) {
                System.err.println("Cannot connect to database server");
                ex.printStackTrace();
                throw ex;

            } finally {
                if (conn != null) {
                    try {
                        System.out.println("\n***** Let terminate the Connection *****");
                        conn.close();
                        System.out.println("\nDatabase connection terminated...");
                    } catch (Exception ex) {
                        System.out.println("Error in connection termination!");
                    }
                }
            }
        });
    }

    @Test
    @Order(4)
    public void dbFolderTest2() {
        assertDoesNotThrow(() -> {
            // Variables
            int userid = database.userAndPwExists("test", "1234");
            System.out.println("USERID ON FOLDERTEST: " + userid);
            Connection conn = null;

            try {
                // Connection statement
                conn = DriverManager.getConnection(url, dbUserName, dbPassWord);
                System.out.println("\nDatabase Connection Established...");

                database.uploadNewFolder("root", userid, 0);
                System.out.println("Record inserted......");

            } catch (Exception ex) {
                System.err.println("Cannot connect to database server");
                ex.printStackTrace();
                throw ex;
            } finally {
                if (conn != null) {
                    try {
                        System.out.println("\n***** Let terminate the Connection *****");
                        conn.close();
                        System.out.println("\nDatabase connection terminated...");
                    } catch (Exception ex) {
                        System.out.println("Error in connection termination!");
                    }
                }
            }
        });
    }
    @Test
    @Order(5)
    public void uploadNewFolderTest() {
        assertDoesNotThrow(() -> {
            int userid = database.userAndPwExists("test", "1234");
            database.uploadNewFolder("test1", userid, 0);
            database.uploadNewFolder("test2", userid, 0);
            database.uploadNewFolder("test3", userid, 0);
        });
    }

    @DisplayName("Testataan onko k??ytt??j??n kansioita oikea m????r??.")
    @Test
    @Order(6)
    public void folderSizeTest() {
        int userid = database.userAndPwExists("test", "1234");
        HashMap<Integer, String> test = database.getUserFolders(userid, 0);
        assertEquals(3, test.size());
    }


    @DisplayName("Testataan userExists metodi useammalla k??ytt??j??tunnuksella.")
    @ParameterizedTest (name="Testataan loytyyko username {0}")
    @CsvSource({"ppouta, false", "1test, true", "noexist, false", "8u34958u342985u89t3hf89ht298t48h, false","-1,false", "NULL, false" })
    @Order(7)
    public void userExistsTest(String userName, boolean result){
        Database base = new Database();
        assertEquals(result, base.userExists(userName), "UserExiststest failed with username " + userName);
    }

    @Test
    @DisplayName("Testataan kolmen kuvan uploadaus, etsiminen(imageExists) ja poisto.")
    @Order(8)
    public void uploadImagesTest(){
        Database base = new Database();
        File file1 = new File("src/main/resources/otp1/otpr21fotosdemo/image/testImage1.jpg");
        File file2 = new File("src/main/resources/otp1/otpr21fotosdemo/image/testImage2.jpg");
        File file3 = new File("src/main/resources/otp1/otpr21fotosdemo/image/testImage3WithVeryLongFileName1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901.jpg");

        List<File> fileList = new ArrayList<>();
        fileList.add(file1);
        fileList.add(file2);
        fileList.add(file3);

        assertAll(() -> {
            List<Integer> imageIDt = base.uploadImages(1,1,fileList);
            testImageIDs.addAll(imageIDt);
            if(imageIDt.size() > 0){
                //Testataan l??ytyyk?? lis??tty kuva
                assertTrue( base.imageExists(imageIDt.get(0)), "Lis??tty?? kuvaa 1 ei l??ytynyt!");
                //Poistetaan lis??tty kuva
                assertTrue(base.deleteImage(imageIDt.get(0)), "Kuvan 1 poisto ei onnistunut!");
                testImageIDs.remove(imageIDt.get(0));
                //Testataan l??ytyyk?? lis??tty kuva
                assertTrue( base.imageExists(imageIDt.get(1)), "Lis??tty?? kuvaa 2 ei l??ytynyt!");
                //Poistetaan lis??tty kuva
                assertTrue(base.deleteImage(imageIDt.get(1)), "Kuvan 2 poisto ei onnistunut!");
                testImageIDs.remove(imageIDt.get(1));
                //Testataan l??ytyyk?? lis??tty kuva
                assertTrue( base.imageExists(imageIDt.get(2)), "Lis??tty?? kuvaa 3 ei l??ytynyt!");
                //Poistetaan lis??tty kuva
                assertTrue(base.deleteImage(imageIDt.get(2)), "Kuvan 3 poisto ei onnistunut!");
                testImageIDs.remove(imageIDt.get(2));
            }
        });
    }

    @Test
    @DisplayName("Testataan kolmen kuvan lataaminen ja yhden kuvan fullres version lataaminen")
    @Order(9)
    public void downloadImagesTest(){
        Database base = new Database();
        base.setController(new FotosController());
        base.setPrivateUserId(1);
        File file1 = new File("src/main/resources/otp1/otpr21fotosdemo/image/testImage1.jpg");
        File file2 = new File("src/main/resources/otp1/otpr21fotosdemo/image/testImage2.jpg");
        File file3 = new File("src/main/resources/otp1/otpr21fotosdemo/image/testImage3WithVeryLongFileName1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901.jpg");

        List<File> fileList = new ArrayList<>();
        fileList.add(file1);
        fileList.add(file2);
        fileList.add(file3);

        assertAll(() -> {
            Map<Integer, Pair<String, Image>> images = base.downloadImages(1, null, null);
            int imagesBefore = images.size();
            List<Integer> imageIDt = base.uploadImages(1,1,fileList);
            testImageIDs.addAll(imageIDt);
            assertEquals(3, imageIDt.size(), "Virhe uploadattaessa testikuvia");

            images = base.downloadImages(1, null, null);
            assertEquals(3, images.size() - imagesBefore, "V????r?? m????r?? ladattuja kuvia");

            Map<Integer, Pair<String, Image>> imagesWithSearch = base.downloadImages(1, "WithVeryLongFileName12345678901234567", null);
            assertEquals(1, imagesWithSearch.size(), "V????r?? m????r?? tekstihaulla ladattuja kuvia");
            assertDoesNotThrow(()-> {
                Image img = base.downloadFullImage(imageIDt.get(0));
                assertNotNull(img, "Ladattu t??yden resoluution kuva oli null!");
            });

            //Poistetaan lis??tyt kuvat
            assertTrue(base.deleteImage(imageIDt.get(0)), "Kuvan 1 poisto ei onnistunut!");
            testImageIDs.remove(imageIDt.get(0));
            assertTrue(base.deleteImage(imageIDt.get(1)), "Kuvan 2 poisto ei onnistunut!");
            testImageIDs.remove(imageIDt.get(1));
            assertTrue(base.deleteImage(imageIDt.get(2)), "Kuvan 3 poisto ei onnistunut!");
            testImageIDs.remove(imageIDt.get(2));

        });
    }
    @Test
    @DisplayName("Testataan kuvan asettaminen julkiseksi ja yksityiseksi")
    @Order(10)
    public void setImagePublicityTest(){
        Database base = new Database();
        base.setController(new FotosController());
        File file1 = new File("src/main/resources/otp1/otpr21fotosdemo/image/testImage1.jpg");

        List<File> fileList = new ArrayList<>();
        fileList.add(file1);


        assertAll(() -> {
            List<Integer> imageIDt = base.uploadImages(1,1,fileList);
            testImageIDs.addAll(imageIDt);
            assertEquals(1, imageIDt.size(), "Virhe uploadattaessa testikuvia");

            assertTrue(base.setImagePublicity(imageIDt.get(0), true), "Virhe asetettaessa kuvaa julkiseksi.");
            assertTrue(base.imageIsPublic(imageIDt.get(0)), "Juuri julkiseksi asetettu kuva ei ollutkaan julkinen.");
            assertTrue(base.setImagePublicity(imageIDt.get(0), false), "Virhe asetettaessa kuvaa yksityiseksi.");
            assertFalse(base.imageIsPublic(imageIDt.get(0)), "Juuri yksityiseksi asetettu kuva ei ollutkaan yksityinen.");

            //Poistetaan lis??tyt kuvat
            assertTrue(base.deleteImage(imageIDt.get(0)), "Kuvan 1 poisto ei onnistunut!");
            testImageIDs.remove(imageIDt.get(0));

        });
    }
    @Test
    @DisplayName("Testataan julkisten kuvien lataaminen")
    @Order(11)
    public void downloadPublicImagesTest(){
        Database base = new Database();
        base.setController(new FotosController());
        File file1 = new File("src/main/resources/otp1/otpr21fotosdemo/image/testImage1.jpg");

        List<File> fileList = new ArrayList<>();
        fileList.add(file1);

        assertAll(() -> {
            List<Integer> imageIDt = base.uploadImages(1,1,fileList);
            testImageIDs.addAll(imageIDt);
            assertEquals(1, imageIDt.size(), "Virhe uploadattaessa testikuvia");

            assertTrue(base.setImagePublicity(imageIDt.get(0), true), "Virhe asetettaessa kuvaa julkiseksi.");
            assertTrue(base.imageIsPublic(imageIDt.get(0)), "Juuri julkiseksi asetettu kuva ei ollutkaan julkinen.");

            Map<Integer, Pair<String, Image>> images = base.downloadPublicImages(null, null);
            assertTrue(images.size() > 0, "Julkisia kuvia latautui 0!");
            assertTrue(images.containsKey(imageIDt.get(0)), "Julkisista kuvista ei l??ytynyt juuri ladattua ja julkistettua kuvaa!");

            //Poistetaan lis??tyt kuvat
            assertTrue(base.deleteImage(imageIDt.get(0)), "Kuvan 1 poisto ei onnistunut!");
            testImageIDs.remove(imageIDt.get(0));
        });


    }
    @Test
    @DisplayName("Testataan k??ytt??j??n tason vaihtaminen (user -> admin)")
    @Order(12)
    public void changeUserLevelTest(){
        Database base = new Database();
        FotosController controller = new FotosController();
        base.setController(controller);
        long rnd = Math.round(Math.random()*10000000);
        String tstUserName = "TestUserName" + rnd;
        String password = "password";
        Text loginErrorText = new Text("");
        assertAll(() -> {
            base.saltRegister(tstUserName, password, "email", "email", loginErrorText);
            int id = base.userAndPwExists(tstUserName,password);
            testUserIDs.add(id);
            assertTrue(id > 0, "Virhe luotaessa testik??ytt??j????");

            assertEquals(1, controller.getPrivateUserLevel(), "Testik??ytt??j?? \"alkutasossa\"");
            base.changeUserLevel(id,1000);
            base.userAndPwExists(tstUserName,password); //Refreshes userdata to controller
            assertEquals(1000, controller.getPrivateUserLevel(), "Testik??ytt??j?? \"admintasossa\"");
            base.deleteUser(id);
            testUserIDs.remove(Integer.valueOf(id));
        });


    }

    @Test
    @DisplayName("Testataan k??ytt??jien listaaminen ja admincount")
    @Order(13)
    public void listUsersAndCountAdminsTest(){
        Database base = new Database();
        FotosController controller = new FotosController();
        base.setController(controller);
        long rnd = Math.round(Math.random()*10000000);
        String tstUserName = "TestUserName" + rnd;
        String password = "password";
        Text loginErrorText = new Text("");

        assertAll(() -> {
            ArrayList<FotosUser> userList = base.listUsers();
            int admincount = 0;
            for (FotosUser user : userList){
                if (user.getUserLevel() == 1000)
                    admincount++;
                System.out.println(user.getUserName());
                System.out.println(user.getUserID());
                System.out.println(user.getFirstName());
                System.out.println(user.getLastName());
                System.out.println(user.getEmail());
            }
            assertEquals(admincount, base.countAdmins(), "Virhe adminien m????r??ss??.");

        });


    }
    @Test
    @DisplayName("FotosUser testi")
    @Order(14)
    public void fotosUserTest(){

        assertAll(() -> {
            FotosUser user = new FotosUser();
            user.setUserName("name");
            user.setUserID(1);
            user.setUserLevel(2);
            user.setFirstName("first");
            user.setLastName("last");
            user.setEmail("email");
            assertEquals("name", user.getUserName());
            assertEquals(1, user.getUserID());
            assertEquals(2, user.getUserLevel());
            assertEquals("first", user.getFirstName());
            assertEquals("last", user.getLastName());
            assertEquals("email", user.getEmail());
        });


    }
}
