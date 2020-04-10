package au.edu.unsw.infs3634.cryptobag;

import java.util.List;

import au.edu.unsw.infs3634.cryptobag.Entities.Coin;
import au.edu.unsw.infs3634.cryptobag.Entities.CoinLoreResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CoinService {
    @GET("tickers")
    Call<CoinLoreResponse> getAllCoins();
}
