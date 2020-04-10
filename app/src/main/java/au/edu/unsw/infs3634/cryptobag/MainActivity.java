package au.edu.unsw.infs3634.cryptobag;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;
import au.edu.unsw.infs3634.cryptobag.Entities.CoinLoreResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
        }

        RecyclerView mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        final RecyclerView.Adapter mAdapter = new CoinAdapter(this, new ArrayList<Coin>(), mTwoPane);
        mRecyclerView.setAdapter(mAdapter);

        //Create retrofit in the Main Activity
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinlore.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Get the api here with the following
        CoinService service = retrofit.create(CoinService.class);
        Call<CoinLoreResponse> coinCall = service.getAllCoins();

        coinCall.enqueue(new Callback<CoinLoreResponse>() {
            @Override
            public void onResponse(Call<CoinLoreResponse> call, Response<CoinLoreResponse> response) {
                if (response.isSuccessful()){
                    List<Coin> coins = response.body().getData();
                    //call the setData method on the Adapter
                    //object, parsing the List of Coins returned from the API call
                    ((CoinAdapter) mAdapter).setData(coins);
                    Log.d("TAG", "onResponse works");
                } else {
                    Log.d("TAG", "onReponse: ERROR IS: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                Log.d("TAG", "onResponse Failure is " + t.getLocalizedMessage());
            }
        });

    }
}
