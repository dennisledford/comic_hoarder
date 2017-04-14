package com.dledford.comichoarder.rest.services;

import com.dledford.comichoarder.rest.model.ComicVineCharacterModel;
import com.dledford.comichoarder.rest.model.ComicVineIssueModel;
import com.dledford.comichoarder.rest.model.ComicVineModel;
import com.dledford.comichoarder.rest.model.ComicVineResult;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

import static android.R.attr.type;
import static java.util.Arrays.asList;

/**
 * Created by phesto on 11/18/2016.
 */

public class ComicVineService {
    private static IComicVineService REST_CLIENT;
    private static final String API_KEY = "a9d286db3de34f421aad9d6e04443a2d5b1fb619";
    private static final String FORMAT = "json";
    private static final String CHARACTER_TYPE="4005";
    private static final String ISSUE_TYPE="4000";
    private static final String NAME_FILTER = "name:";
    private static final String NUMBER_FILTER = "issue_number:";
    private static final String FILTER_DELIMITER = ",";


    public Call<ComicVineResult<ArrayList<ComicVineCharacterModel>>> findCharacterByName(String name) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        return comicVineAPI.getCharacterByName(API_KEY, FORMAT, NAME_FILTER + name,getFieldList(ComicVineCharacterModel.class));
    }

    public Call<ComicVineResult<ComicVineCharacterModel>> findCharacterById(Long id) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        return comicVineAPI.getCharacterById(CHARACTER_TYPE+"-"+id,API_KEY, FORMAT, getFieldList(ComicVineCharacterModel.class));
    }
    public ComicVineCharacterModel findCharacterByIdSync(Long id) throws IOException {
        return findCharacterById(id).execute().body().getResults();
    }
    public List findCharacterByNameSync(String name) throws IOException {
        return findCharacterByName(name).execute().body().getResults();
    }

    public Call<ComicVineResult<ArrayList<ComicVineCharacterModel>>> findIssueByNameAndNumber(String name, Integer number) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        String filter = NAME_FILTER+name;//+FILTER_DELIMITER+NUMBER_FILTER+number;
        return comicVineAPI.getIssueByNameAndNumber(API_KEY, FORMAT, filter);
    }

    public List findIssueByNameAndNumberSync(String name, Integer number) throws IOException {
        return findIssueByNameAndNumber(name,number).execute().body().getResults();
    }

    public Call<ComicVineResult<ComicVineIssueModel>> findIssueById(Long id) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        return comicVineAPI.getIssueById(ISSUE_TYPE+"-"+id,API_KEY, FORMAT);
    }

    public ComicVineIssueModel findIssueByIdSync(Long id) throws IOException {
        return findIssueById(id).execute().body().getResults();
    }


    public Call<ComicVineResult<ArrayList<ComicVineCharacterModel>>> findSeriesByName(String name) {
        IComicVineService comicVineAPI = IComicVineService.retrofit.create(IComicVineService.class);
        return comicVineAPI.getSeriesByName(API_KEY, FORMAT, NAME_FILTER + name);
    }

    public List findSeriesByNameSync(String name) throws IOException {
        return findSeriesByName(name).execute().body().getResults();
    }


    private <T extends ComicVineModel> String getFieldList(Class<T> type){
        List<Field> fields = new ArrayList<>();
        fields = getAllFields(fields, type);
        StringBuilder fieldList = new StringBuilder();
        for(Field f : fields){
            fieldList.append(f.getName()).append(FILTER_DELIMITER);
        }
        fieldList = fieldList.deleteCharAt(fieldList.length() - 1);
        return fieldList.toString();
    }

    private static  List<Field>  getAllFields( List<Field> fields,Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }
}
