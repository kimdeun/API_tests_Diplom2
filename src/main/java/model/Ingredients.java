package model;

import api.data.DataAboutIngredientsClient;

import java.util.ArrayList;
import java.util.List;

public class Ingredients {
    DataAboutIngredientsClient dataAboutIngredientsClient = new DataAboutIngredientsClient();
    private boolean success;
    private List<Data> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<String> createListOfIngredientsId() {
        List<Data> data = dataAboutIngredientsClient.getResponseWithIngredients().getData();
        int sizeOfIngredients = data.size();

        List<String> listOfIngredientsId = new ArrayList<>();
        List<String> listOfIngredients = new ArrayList<>();

        for (int i = 0; i < sizeOfIngredients; i++) {
            int indexOfRandomIngredient = (int) (Math.random() * sizeOfIngredients);
            if (data.get(indexOfRandomIngredient).getType().equals("bun") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("bun")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            } else if (data.get(indexOfRandomIngredient).getType().equals("sauce") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("sauce")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            } else if (data.get(indexOfRandomIngredient).getType().equals("main") && listOfIngredientsId.size() < 4 && !listOfIngredients.contains("main")) {
                listOfIngredientsId.add(data.get(indexOfRandomIngredient).get_id());
                listOfIngredients.add(data.get(indexOfRandomIngredient).getType());
            }
        }

        return listOfIngredientsId;
    }
}
