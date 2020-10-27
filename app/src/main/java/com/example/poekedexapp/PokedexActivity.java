package com.example.poekedexapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poekedexapp.models.Pokemon;
import com.example.poekedexapp.models.TypeColor;
import com.example.poekedexapp.models.TypeDamages;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * The type Pokedex activity.
 */
public class PokedexActivity extends AppCompatActivity {

    // Interfaz
    private Spinner pokemonSpinner;
    private TextView expPokemon, pesoPokemon, alturaPokemon, nombrePokemon;
    private ImageView pokemonView;
    private ChipGroup typeGroup, debilGroup, fuerteGroup;
    private ConstraintLayout constraintLayout;
    private ArrayList<Pokemon> pokemons;
    private SwitchMaterial favSwitch;
    private ProgressBar progressBar;
    private CardView infoCard, typeCard;

    private String username;
    private Pokemon currentPokemon;
    private ArrayList<TypeColor> typePokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);
        loadAllPokemons();
        infoCard = (CardView) findViewById(R.id.card_info);
        typeCard = (CardView) findViewById(R.id.card_type);
        infoCard.setVisibility(View.GONE);
        typeCard.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.loading_pokedex);
        progressBar.setVisibility(View.VISIBLE);
        pokemonSpinner = (Spinner) findViewById(R.id.spinner);
        typeGroup = (ChipGroup) findViewById(R.id.type_chips);
        debilGroup = (ChipGroup) findViewById(R.id.debil_chips);
        fuerteGroup = (ChipGroup) findViewById(R.id.fuerte_chips);
        favSwitch = (SwitchMaterial) findViewById(R.id.switch_fav);
        favSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked && currentPokemon.isFav()) {
                    deleteFavPokemon(currentPokemon);
                } else {
                    addFavPokemon(currentPokemon);
                }
                currentPokemon.setFav(isChecked);
            }
        });
        pokemonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Al seleccionar un pokémon
                loadPokemon(pokemons.get(position).getUrl());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // al seleccionar nada
            }
        });
        loadPokemonTypes();
        constraintLayout = (ConstraintLayout) findViewById(R.id.background_pokedex);
        pokemonView = (ImageView) findViewById(R.id.pokemon_view);
        nombrePokemon = (TextView) findViewById(R.id.nombre_pokemon);
        alturaPokemon = (TextView) findViewById(R.id.altura_pokemon);
        pesoPokemon = (TextView) findViewById(R.id.peso_pokemon);
        expPokemon = (TextView) findViewById(R.id.exp_pokemon);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    /**
     * Load pokemon.
     *
     * @param url the url
     */
    public void loadPokemon(String url) {
        infoCard.setVisibility(View.GONE);
        typeCard.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response);
                            Gson gson = new Gson();
                            Type type = new TypeToken<Pokemon>() {
                            }.getType();
                            currentPokemon = gson.fromJson(obj.toString(), type);
                            nombrePokemon.setText(new StringBuilder()
                                    .append(currentPokemon.getName().substring(0, 1).toUpperCase())
                                    .append(currentPokemon.getName().substring(1))
                                    .append(" N°").append(currentPokemon.getId()));

                            double weight = currentPokemon.getWeight() / 10;
                            String weightString = Double.toString(weight);
                            pesoPokemon.setText(new StringBuilder()
                                    .append("Peso\n")
                                    .append(weightString.replace(".", ","))
                                    .append("kg"));

                            double height = currentPokemon.getHeight() / 10;
                            String heightString = Double.toString(height);
                            alturaPokemon.setText(new StringBuilder()
                                    .append("Altura\n")
                                    .append(heightString.replace(".", ","))
                                    .append("mt(s)"));

                            double exp = currentPokemon.getBase_experience();
                            String expString = Double.toString(exp);
                            expPokemon.setText(new StringBuilder()
                                    .append("Experiencia Base\n")
                                    .append(expString.replace(".", ",")));

                            String spriteUrl = (String) currentPokemon.getSprites().get("front_default");
                            Bitmap bitmap = new ImageLoadTask(spriteUrl, pokemonView)
                                    .execute().get();
                            currentPokemon.setSpriteBitMap(bitmap);
                            boolean fav = checkIsFav(currentPokemon);
                            currentPokemon.setFav(fav);
                            currentPokemon.setColorHex(null);
                            favSwitch.setChecked(fav);
                            getPokemonTypes();
                        } catch (JSONException | ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Tag", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void addPokemonToDB(Pokemon pokemon) {
        AdminsSQLiteOpenHelper adminsSQLiteOpenHelper = new AdminsSQLiteOpenHelper(this, "pokedex", null, 2);
        SQLiteDatabase database = adminsSQLiteOpenHelper.getWritableDatabase();
        Bitmap bitmap = pokemon.getSpriteBitMap();
        Cursor cursor = database.rawQuery("SELECT name from pokemons where name='" + pokemon.getName() + "' and user='"+ username +"'", null);
        if (!cursor.moveToFirst() && pokemon.getColorHex() != null) {
            ContentValues contentValues = new ContentValues();
            cursor = database.rawQuery("SELECT MAX(id)+1 from pokemons", null);
            int id = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
            Log.i("Poke ID", "Id generada " + id);
            contentValues.put("id", id);
            contentValues.put("name", pokemon.getName());
            contentValues.put("url", pokemon.getUrl());
            contentValues.put("color", pokemon.getColorHex());
            contentValues.put("user", username);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] img = bos.toByteArray();
            contentValues.put("sprite", img);
            database.insert("pokemons", null, contentValues);
        }
        database.close();
        cursor.close();
    }

    private boolean checkIsFav(Pokemon pokemon) {
        AdminsSQLiteOpenHelper adminsSQLiteOpenHelper = new AdminsSQLiteOpenHelper(this, "pokedex", null, 2);
        SQLiteDatabase database = adminsSQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT name from pokemons_favs where user='" + username + "'", null);
        boolean result = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(pokemon.getName())) {
                result = true;
            }
        }
        database.close();
        cursor.close();
        return result;
    }

    private void addFavPokemon(Pokemon pokemon) {
        AdminsSQLiteOpenHelper adminsSQLiteOpenHelper = new AdminsSQLiteOpenHelper(this, "pokedex", null, 2);
        SQLiteDatabase database = adminsSQLiteOpenHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT name from pokemons_favs where name='" + pokemon.getName() + "' and user='"+ username +"'", null);
        Bitmap bitmap = pokemon.getSpriteBitMap();
        if (!cursor.moveToFirst() && pokemon.getColorHex() != null) {
            ContentValues contentValues = new ContentValues();
            cursor = database.rawQuery("SELECT MAX(id)+1 from pokemons_favs", null);
            int id = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
            Log.i("Poke ID", "Id generada " + id);
            contentValues.put("id", id);
            contentValues.put("name", pokemon.getName());
            contentValues.put("url", pokemon.getUrl());
            contentValues.put("color", pokemon.getColorHex());
            contentValues.put("user", username);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] img = bos.toByteArray();
            contentValues.put("sprite", img);
            database.insert("pokemons_favs", null, contentValues);

        }
        database.close();
        cursor.close();
    }

    private void deleteFavPokemon(Pokemon pokemon) {
        AdminsSQLiteOpenHelper adminsSQLiteOpenHelper = new AdminsSQLiteOpenHelper(this, "pokedex", null, 2);
        SQLiteDatabase database = adminsSQLiteOpenHelper.getWritableDatabase();
        database.execSQL("DELETE FROM pokemons_favs WHERE name='" + pokemon.getName()+"' and user='"+username+"'");
        Log.i("Pokedex delete", "Eliminado el pokemon id " + pokemon.getId());
        database.close();
    }

    private void getPokemonTypes() {
        List<com.example.poekedexapp.models.Type> pokemonTypes = currentPokemon.getTypes();
        typeGroup.removeAllViews();
        debilGroup.removeAllViews();
        fuerteGroup.removeAllViews();
        for (com.example.poekedexapp.models.Type pokemonType : pokemonTypes) {
            for (TypeColor typeColor : typePokemons) {
                if (typeColor.getName().toLowerCase().equals(pokemonType.getType().get("name"))) {
                    if (currentPokemon.getColorHex() == null) {
                        constraintLayout.setBackgroundColor(Color.parseColor(typeColor.getColor()));
                        currentPokemon.setColorHex(typeColor.getColor());
                    }
                    Chip chip = new Chip(typeGroup.getContext());
                    chip.setText(translatePokemonType(typeColor.getName()));
                    chip.setTextColor(Color.WHITE);
                    chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor(typeColor.getColor())));
                    typeGroup.addView(chip);
                    if (pokemonType.getType().get("url") != null) {
                        getPokemonTypeDamages(Objects.requireNonNull(pokemonType.getType().get("url")).toString());
                    }
                }
            }
        }
        addPokemonToDB(currentPokemon);
        infoCard.setVisibility(View.VISIBLE);
        typeCard.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void getPokemonTypeDamages(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response);
                            Gson gson = new Gson();
                            Type type = new TypeToken<TypeDamages>() {
                            }.getType();
                            TypeDamages typeDamages = gson.fromJson(obj.getString("damage_relations"), type);

                            // Debil contra
                            for (TypeColor typeColor : typePokemons) {
                                for (Map<String, String> value : typeDamages.getDouble_damage_from()) {
                                    if (typeColor.getName().toLowerCase().equals(value.get("name"))) {
                                        Chip chip = new Chip(debilGroup.getContext());
                                        chip.setText(translatePokemonType(typeColor.getName()));
                                        chip.setTextColor(Color.WHITE);
                                        chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor(typeColor.getColor())));
                                        debilGroup.addView(chip);
                                    }
                                }
                            }

                            // Fuerte contra
                            for (TypeColor typeColor : typePokemons) {
                                for (Map<String, String> value : typeDamages.getDouble_damage_to()) {
                                    if (typeColor.getName().toLowerCase().equals(value.get("name"))) {
                                        Chip chip = new Chip(fuerteGroup.getContext());
                                        chip.setText(translatePokemonType(typeColor.getName()));
                                        chip.setTextColor(Color.WHITE);
                                        chip.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor(typeColor.getColor())));
                                        fuerteGroup.addView(chip);
                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Tag", error.toString());
            }
        });
        queue.add(stringRequest);
    }

    private void loadAllPokemons() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://pokeapi.co/api/v2/pokemon?limit=1049";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;
                        try {
                            obj = new JSONObject(response);
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Pokemon>>() {
                            }.getType();
                            pokemons = gson.fromJson(obj.getString("results"), type);
                            ArrayAdapter<Pokemon> adapts = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, pokemons);
                            pokemonSpinner.setAdapter(adapts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Tag", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /**
     * Load pokemon types.
     */
    public void loadPokemonTypes() {
        typePokemons.add(new TypeColor("Normal", "#A8A77A"));
        typePokemons.add(new TypeColor("Fire", "#EE8130"));
        typePokemons.add(new TypeColor("Water", "#6390F0"));
        typePokemons.add(new TypeColor("Electric", "#F7D02C"));
        typePokemons.add(new TypeColor("Grass", "#7AC74C"));
        typePokemons.add(new TypeColor("Ice", "#96D9D6"));
        typePokemons.add(new TypeColor("Fighting", "#C22E28"));
        typePokemons.add(new TypeColor("Poison", "#A33EA1"));
        typePokemons.add(new TypeColor("Ground", "#E2BF65"));
        typePokemons.add(new TypeColor("Flying", "#A98FF3"));
        typePokemons.add(new TypeColor("Psychic", "#F95587"));
        typePokemons.add(new TypeColor("Bug", "#A6B91A"));
        typePokemons.add(new TypeColor("Rock", "#B6A136"));
        typePokemons.add(new TypeColor("Ghost", "#735797"));
        typePokemons.add(new TypeColor("Dragon", "#6F35FC"));
        typePokemons.add(new TypeColor("Dark", "#705746"));
        typePokemons.add(new TypeColor("Steel", "#B7B7CE"));
        typePokemons.add(new TypeColor("Fairy", "#D685AD"));
    }

    /**
     * Translate pokemon type string.
     *
     * @param pokemonType the pokemon type
     * @return the string
     */
    public String translatePokemonType(String pokemonType) {
        String result;
        switch (pokemonType) {
            case "Normal":
                result = "Normal";
                break;
            case "Fire":
                result = "Fuego";
                break;
            case "Water":
                result = "Agua";
                break;
            case "Electric":
                result = "Electrico";
                break;
            case "Grass":
                result = "Planta";
                break;
            case "Ice":
                result = "Hielo";
                break;
            case "Fighting":
                result = "Pelea";
                break;
            case "Poison":
                result = "Venenoso";
                break;
            case "Ground":
                result = "Tierra";
                break;
            case "Flying":
                result = "Volador";
                break;
            case "Bug":
                result = "Insecto";
                break;
            case "Rock":
                result = "Roca";
                break;
            case "Ghost":
                result = "Fantasma";
                break;
            case "Dragon":
                result = "Dragón";
                break;
            case "Dark":
                result = "Siniestro";
                break;
            case "Steel":
                result = "Acero";
                break;
            case "Fairy":
                result = "Hada";
                break;
            case "Psychic":
                result = "Psíquico";
                break;
            default:
                result = "Desconocido";
                break;
        }
        return result;
    }
}