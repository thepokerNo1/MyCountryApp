package com.l20290968.mycountryapp.services.restcontries.api;

import com.l20290968.mycountryapp.services.restcontries.models.Country;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestContriesApi {
    @GET("name/{name}?fields=name,cca3,flag,flags,capital,population")
    Observable<List<Country>> searchCountiesByName(@Path("name") String nombre);

    @GET("alpha/{code}?fields=name,cca3,flag,flags,capital,population")
    Observable<Country> getCountryByCca3 (@Path("code") String cca3);

    @GET("region/{region}")
    Observable<List<Country>> getCountriesByRegion (
            @Path("region") String continente,
            @Query("fiels") String campos);

}
