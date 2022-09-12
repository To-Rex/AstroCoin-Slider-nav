package com.sliding.navigator.sample.fragment;

import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sliding.navigator.sample.R;
import com.sliding.navigator.sample.adapter.ApiCleint;
import com.sliding.navigator.sample.adapter.UserListAdapter;
import com.sliding.navigator.sample.constructor.UserRequestdata;
import com.sliding.navigator.sample.data.SqlData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearchUser extends Fragment {

    private SqlData sqlData;
    String token;
    ArrayList<UserRequestdata> dataModalArrayList;
    UserListAdapter adapter;
    ListView listViewsearch;
    ProgressBar searchprogressBar;
    SearchView usersearchView;
    String id = "", name = "", last_name = "", stack = "", photo = "", qwasar = "", status = "", balance = "", wallet = "";
    ImageView imgsearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messages, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        sqlData = new SqlData(getContext());
        dataModalArrayList = new ArrayList<>();
        listViewsearch = view.findViewById(R.id.listViewsearch);
        usersearchView = view.findViewById(R.id.usersearchView);
        listViewsearch.setDivider(null);
        listViewsearch.setDividerHeight(20);
        searchprogressBar = view.findViewById(R.id.searchprogressBar);
        adapter = new UserListAdapter(dataModalArrayList, getContext());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        double width = displayMetrics.widthPixels;
        int h;
        int w;
        h = (int) (height / 100 * 76.1);
        w = (int) (width / 100 * 89.01);

        ViewGroup.LayoutParams params = listViewsearch.getLayoutParams();
        ViewGroup.LayoutParams params1 = usersearchView.getLayoutParams();
        params1.width = w;
        params.height = h;
        params.width = w;
        usersearchView.setLayoutParams(params1);
        listViewsearch.setLayoutParams(params);

        readsql();
        usersearchView.setOnCloseListener(() -> {
            usersearchView.setQuery("", false);
            return false;
        });
        usersearchView.setOnClickListener(v -> usersearchView.setIconified(false));
        usersearchView.setIconified(true);
        usersearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    String text = query.substring(1);
                    adapter.getFilter().filter(text);
                } else {
                    adapter.getFilter().filter("");
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    String text = newText.substring(1);
                    adapter.getFilter().filter(text);
                } else {
                    adapter.getFilter().filter("");
                }
                return true;
            }
        });
    }
    private void readsql() {
        Cursor res = sqlData.oqish();
        StringBuilder stringBuffer1 = new StringBuilder();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer1.append(res.getString(1));
            }
            token = stringBuffer1.toString();
            getData();
        }
    }
    private void getData() {
        searchprogressBar.setVisibility(View.VISIBLE);
        Call<Object> call = ApiCleint.getUserService().userSearchRequest("Bearer " + token);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body());
                    JsonParser parser = new JsonParser();
                    JsonArray array = parser.parse(json).getAsJsonArray();
                    for (JsonElement element : array) {
                        JsonObject object = element.getAsJsonObject();
                        id = object.get("id").getAsString();
                        name = object.get("name").getAsString();
                        last_name = object.get("last_name").getAsString();
                        stack = object.get("stack").getAsString();
                        if (object.get("photo") != null) {
                            photo = object.get("photo").getAsString();
                        } else {
                            photo = "";
                        }
                        qwasar = object.get("qwasar").getAsString();
                        status = object.get("status").getAsString();
                        balance = object.get("balance").getAsString();
                        wallet = object.get("wallet").getAsString();
                        dataModalArrayList.add(new UserRequestdata(id, name, last_name, stack, photo, qwasar, status, balance, wallet));
                    }
                    /*JsonParser parser = new JsonParser();
                    JsonObject rootObj = parser.parse(json).getAsJsonObject();

                    for (String key : rootObj.keySet()) {
                        JsonObject jsonObject = rootObj.getAsJsonObject(key);
                        id = jsonObject.get("id").getAsString();
                        name = jsonObject.get("name").getAsString();
                        last_name = jsonObject.get("last_name").getAsString();
                        stack = jsonObject.get("stack").getAsString();
                        if (jsonObject.get("photo") != null) {
                            photo = jsonObject.get("photo").getAsString();
                        } else {
                            photo = "";
                        }
                        qwasar = jsonObject.get("qwasar").getAsString();
                        status = jsonObject.get("status").getAsString();
                        balance = jsonObject.get("balance").getAsString();
                        wallet = jsonObject.get("wallet").getAsString();
                        if (!id.equals("") || !name.equals("") || !qwasar.equals("")) {
                            dataModalArrayList.add(new UserRequestdata(id, name, last_name, stack, photo, qwasar, status, balance, wallet));
                        }
                    }*/
                    listViewsearch.setAdapter(adapter);
                    searchprogressBar.setVisibility(View.GONE);
                    call.cancel();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Object> call, @NotNull Throwable t) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

}