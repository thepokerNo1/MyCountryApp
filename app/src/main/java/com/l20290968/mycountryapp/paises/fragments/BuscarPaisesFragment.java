package com.l20290968.mycountryapp.paises.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.l20290968.mycountryapp.R;
import com.l20290968.mycountryapp.databinding.FragmentBuscarPaisesBinding;
import com.l20290968.mycountryapp.paises.adapter.PaisAdapter;
import com.l20290968.mycountryapp.services.restcontries.api.RestContriesApi;
import com.l20290968.mycountryapp.services.restcontries.client.RestCountriesClient;
import com.l20290968.mycountryapp.services.restcontries.models.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BuscarPaisesFragment extends Fragment {

    private FragmentBuscarPaisesBinding viewBinding;
    private RestContriesApi restContriesApi;
    private CompositeDisposable compositeDisposable;
    private PaisAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentBuscarPaisesBinding.inflate(inflater, container, false);

        initComponents();
        return viewBinding.getRoot();

    }

    private void initComponents() {

        restContriesApi = RestCountriesClient.get_instance().create(RestContriesApi.class);

        compositeDisposable = new CompositeDisposable();

        viewBinding.buscarPaisTilBuscar.setEndIconOnClickListener(this::onEndIconClick);
        viewBinding.buscarPaisTilBuscar.getEditText().setOnEditorActionListener(this::onTilBuscarEnter);

        //confugurando el recycler view
        viewBinding.buscarPaisRvPaises.setLayoutManager(new LinearLayoutManager(getActivity()));

        //configurar el fab
        viewBinding.buscarPaisFabClear.setOnClickListener((view) -> {
            viewBinding.buscarPaisTilBuscar.getEditText().setText("");
            setRecyclerViewAdapter(new ArrayList<>());
        });
    }

    private boolean onTilBuscarEnter(TextView textView, int actionID, KeyEvent keyEvent) {
        if(actionID == EditorInfo.IME_ACTION_SEARCH){
            onEndIconClick(textView);
            return true;
        }
        return false;
    }

    private void onEndIconClick(View view) {
        //Cerrar el telclado virtual de android
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        String input = viewBinding.buscarPaisTilBuscar.getEditText().getText().toString();
        fetchCountriesData(input);
        //Toast.makeText(getContext(),"Input: " +  input, Toast.LENGTH_SHORT).show();
    }

    private void fetchCountriesData(String nombre) {
        compositeDisposable.add(
                restContriesApi.searchCountiesByName(nombre)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setRecyclerViewAdapter,this::OnRequestError)
        );
    }

    private void OnRequestError(Throwable e) {
        Toast.makeText(getContext(),"No se encontraron coincidencias ", Toast.LENGTH_SHORT).show();
        setRecyclerViewAdapter(new ArrayList<>());
    }

    private void setRecyclerViewAdapter(List<Country> countries) {
        adapter = new PaisAdapter(getContext(),countries);
        viewBinding.buscarPaisRvPaises.setAdapter(adapter);
    }
}