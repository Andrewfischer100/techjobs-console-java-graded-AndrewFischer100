import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLOutput;
import java.util.*;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "src/main/resources/job_data.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        // Bonus mission: sort the results
        Collections.sort(values);

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        // Bonus mission; normal version returns allJobs
        return new ArrayList<>(allJobs);
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     * <p>
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column Column that should be searched.
     * @param value  Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);

            if (aValue.contains(value)) {
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Search all columns for the given term
     *
     * @param value The search term to look for
     * @return List of all jobs with at least one field containing the value
     */
    public static ArrayList<HashMap<String, String>> findByValue(String value) {
        // load data, if not already loaded
        loadData();
        ArrayList<HashMap<String, String>> doesSearchValueMatch = new ArrayList<>();

        //how to get each value available?
        // first we need to take all jobs into key value pairs -- to do that we need a hashmap
        //now that we have the hashmap -- we must then isolate all of our values
        //Now at this point we can kind of match! We want to see if JOB contains any to our keyvalue pairs. We can do that with an If statement
        //keyValues is of type Entry. which we can't do much with. However we can make those entries a string

        //Since KeyValues isn't in scope of the outer loop -- we can add the KeyValues to something else to compare -- perhaps an arrayList of Strings
        //So testArrayList has been populated by single key value pairs. I want to go ahead and make those key value pairs the full job.
        //great now that you are making full job listings - it is time to see if you can write code to only print the listing IF it contains a specific value--
        // that specific value is fed into the above method with the word "value"
        //I'm realizing that perhaps I may have to take the stringKeyValues and write an if else statement to add them to an arrayList. The purpose of the array list is to be used to compare to the value (if arraylist does not contain value, add that value)
        //closer to success! -- notes: using the value front -- we get 10 hits(well, 20) Those 10 hits repeat the same job(must look into loop)
        //note -- had trouble printing ALL job listings into a single array.  -- now get back to work at matching

        ArrayList<String> testArrayList = new ArrayList<>();
        String stringKeyValues = null;
        for (HashMap<String, String> job : allJobs) {
            stringKeyValues = "\n*****\n";
            for (Map.Entry<String, String> keyValues : job.entrySet()) {
                stringKeyValues += (keyValues.getKey() + ": " + keyValues.getValue() + "\n");
            }
            testArrayList.add(stringKeyValues);
        }

        System.out.println(testArrayList);
        // TODO - implement this method
        return doesSearchValueMatch;
    }


    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
