package com.example.poekedexapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poekedexapp.models.PojoPokemon;
import com.example.poekedexapp.models.Pokemon;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * The type Menu activity.
 */
public class MenuActivity extends AppCompatActivity {

    private VideoView videoView;
    private ArrayList<PojoPokemon> pokemonsViewed = new ArrayList<>();
    private ArrayList<PojoPokemon> pokemonsFav = new ArrayList<>();
    private LinearLayout pokemonsViewedLayout, pokemonsFavLayout;
    private TextView welcomeText, recentText, favText;
    private String username;
    private FrameLayout frameLayout1, frameLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        pokemonsViewedLayout = (LinearLayout) findViewById(R.id.list_pokemons_viewed);
        pokemonsFavLayout = (LinearLayout) findViewById(R.id.list_pokemons_fav);
        welcomeText = (TextView) findViewById(R.id.welcome_text);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        welcomeText.setText(new StringBuilder().append("¡Bienvenido ").append(username).append(" a Pokedash!."));

        recentText = (TextView) findViewById(R.id.recientes_text);
        favText = (TextView) findViewById(R.id.fav_text);
        frameLayout1 = (FrameLayout) findViewById(R.id.frame_layout1);
        frameLayout2 = (FrameLayout) findViewById(R.id.frame_layout2);
        frameLayout1.setVisibility(View.GONE);
        frameLayout2.setVisibility(View.GONE);

        videoView = (VideoView)findViewById(R.id.vw);
        String ruta = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(ruta);
        videoView.setVideoURI(uri);
        MediaController media = new MediaController(this);
        videoView.setMediaController(media);
    }

    @Override
    public void onResume() {
        super.onResume();
        pokemonsViewed.clear();
        pokemonsViewedLayout.removeAllViews();
        pokemonsFav.clear();
        pokemonsFavLayout.removeAllViews();
        frameLayout1.setVisibility(View.GONE);
        frameLayout2.setVisibility(View.GONE);
        try {
            checkPokemonsInDB();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * On click.
     *
     * @param view the view
     */
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.pokedex_btn:
                intent = new Intent(MenuActivity.this, PokedexActivity.class);
                intent.putExtra("username", username);
                break;
            case R.id.location_btn:
                intent = new Intent(MenuActivity.this, LocationActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void addPokemonsToFavList() throws ExecutionException, InterruptedException {
        if(pokemonsFav.size() > 0) {
            frameLayout2.setVisibility(View.VISIBLE);
        }
        for (int i = pokemonsFav.size()-1; i > -1; i--) {
            PojoPokemon pokemon = pokemonsFav.get(i);
            MaterialCardView cardView = new MaterialCardView(this);
            cardView.setRadius(20);
            cardView.setCardElevation(10);
            if(pokemon.getColor() != null) {
                cardView.setCardBackgroundColor(Color.parseColor(pokemon.getColor()));
            }
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setPadding(70,70,70,70);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeByteArray(pokemon.getSprite(), 0, pokemon.getSprite().length);
            imageView.setImageBitmap(bitmap);
            int width = 200;
            int height = 200;
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            imageView.setLayoutParams(parms);
            TextView textView = new TextView(this);
            textView.setText(pokemon.getName());
            textView.setTextColor(Color.WHITE);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            linearLayout.addView(imageView, 0);
            linearLayout.addView(textView, 1);
            cardView.addView(linearLayout);
            pokemonsFavLayout.addView(cardView);
        }
    }

    private void addPokemonsToViewedList() throws ExecutionException, InterruptedException {
        if(pokemonsViewed.size() > 0) {
            frameLayout1.setVisibility(View.VISIBLE);
        }
        for (int i = pokemonsViewed.size()-1; i > -1; i--) {
            PojoPokemon pokemon = pokemonsViewed.get(i);
            MaterialCardView cardView = new MaterialCardView(this);
            cardView.setRadius(20);
            cardView.setCardElevation(10);
            if(pokemon.getColor() != null) {
                cardView.setCardBackgroundColor(Color.parseColor(pokemon.getColor()));
            }
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setPadding(70,70,70,70);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            ImageView imageView = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeByteArray(pokemon.getSprite(), 0, pokemon.getSprite().length);
            imageView.setImageBitmap(bitmap);
            int width = 200;
            int height = 200;
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
            imageView.setLayoutParams(parms);
            TextView textView = new TextView(this);
            textView.setText(pokemon.getName());
            textView.setTextColor(Color.WHITE);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            linearLayout.addView(imageView, 0);
            linearLayout.addView(textView, 1);
            cardView.addView(linearLayout);
            pokemonsViewedLayout.addView(cardView);
        }
    }


    private void checkPokemonsInDB() throws ExecutionException, InterruptedException {
        AdminsSQLiteOpenHelper adminsSQLiteOpenHelper = new AdminsSQLiteOpenHelper(this, "pokedex", null, 2);
        SQLiteDatabase database = adminsSQLiteOpenHelper.getWritableDatabase();
        Cursor rows = database.rawQuery("SELECT id, name, url, sprite, color, user from pokemons where user ='"+username+"'", null);
        if (pokemonsViewed.size() > 0) {
            pokemonsViewed.clear();
        }
        while (rows.moveToNext()) {
            PojoPokemon pokemon = new PojoPokemon();
            pokemon.setId(rows.getInt(0));
            pokemon.setName(rows.getString(1).substring(0, 1).toUpperCase() + rows.getString(1).substring(1));
            pokemon.setUrl(rows.getString(2));
            pokemon.setSprite(rows.getBlob(3));
            pokemon.setColor(rows.getString(4));
            pokemonsViewed.add(pokemon);
        }

        rows = database.rawQuery("SELECT id, name, url, sprite, color, user from pokemons_favs where user = '"+username+"'", null);
        if (pokemonsFav.size() > 0) {
            pokemonsFav.clear();
        }
        while (rows.moveToNext()) {
            PojoPokemon pokemon = new PojoPokemon();
            pokemon.setId(rows.getInt(0));
            pokemon.setName(rows.getString(1).substring(0, 1).toUpperCase() + rows.getString(1).substring(1));
            pokemon.setUrl(rows.getString(2));
            pokemon.setSprite(rows.getBlob(3));
            pokemon.setColor(rows.getString(4));
            pokemonsFav.add(pokemon);
        }
        rows.close();
        database.close();
        addPokemonsToViewedList();
        addPokemonsToFavList();
    }


}