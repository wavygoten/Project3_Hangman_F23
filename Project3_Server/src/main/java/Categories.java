import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Categories {
    private HashMap<String, ArrayList<String>> categories;

    Categories() {
        categories = new HashMap<>(3);
        ArrayList<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader(System.getProperty("user.dir") + "/src/main/resources/data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<List<String>> tempList = records.subList(1, records.size() - 1);
        for (int i = 0; i < tempList.size(); i += 3) {
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add(tempList.get(i).get(1));
            tmp.add(tempList.get(i + 1).get(1));
            tmp.add(tempList.get(i + 2).get(1));
            categories.put(tempList.get(i).get(0), tmp);
        }
    }

    public HashMap<String, ArrayList<String>> getCategories() {
        return categories;
    }

}
