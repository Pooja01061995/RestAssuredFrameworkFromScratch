package Utilities;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;

/**
 * TestNG data providers that feed Excel data into tests.
 * Sheet layout: row 0 = headers, rows 1..n = data.
 * Columns: 0 UserID, 1 Username, 2 First Name, 3 Last Name, 4 Email, 5 Password, 6 Phone
 */
public class DataProviders {

    // Excel file location, relative to the project folder (where pom.xml is)
    private static final String PATH =
            System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                    + File.separator + "resources" + File.separator + "Testdata1"
                    + File.separator + "User_Test_Data.xlsx";

    /** All data: every row and every column. A test using it needs 7 parameters. */
    @DataProvider(name = "userData")
    public String[][] getAllData() throws IOException {
        XLUtility xl = new XLUtility(PATH);

        int rownum = xl.getRowCount("Users");        // number of data rows (header not counted)
        int colcount = xl.getCellCount("Users", 0);  // number of columns

        String[][] apidata = new String[rownum][colcount];

        for (int i = 1; i <= rownum; i++) {          // start at 1 to skip the header row
            for (int j = 0; j < colcount; j++) {
                apidata[i - 1][j] = xl.getCellData("Users", i, j);
            }
        }
        return apidata;
    }

    /** Only the Username column. A test using it needs 1 String parameter. */
    @DataProvider(name = "userNames")
    public String[] getUserNames() throws IOException {
        XLUtility xl = new XLUtility(PATH);

        int rownum = xl.getRowCount("Users");

        String[] apidata = new String[rownum];

        for (int i = 1; i <= rownum; i++) {
            apidata[i - 1] = xl.getCellData("Users", i, 1);   // column 1 = Username
        }
        return apidata;
    }
}