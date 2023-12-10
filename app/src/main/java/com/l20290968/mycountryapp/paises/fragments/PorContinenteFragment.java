package com.l20290968.mycountryapp.paises.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.l20290968.mycountryapp.R;
import com.l20290968.mycountryapp.databinding.FragmentPorContinenteBinding;
import com.l20290968.mycountryapp.paises.adapter.PaisAdapter;
import com.l20290968.mycountryapp.services.restcontries.api.RestContriesApi;
import com.l20290968.mycountryapp.services.restcontries.client.RestCountriesClient;
import com.l20290968.mycountryapp.services.restcontries.models.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class PorContinenteFragment extends Fragment {


    private FragmentPorContinenteBinding viewBinding;

    private RestContriesApi restContriesApi;
    private CompositeDisposable compositeDisposable;
    private PaisAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentPorContinenteBinding.inflate(inflater,container,false);
        initComponets();

        return viewBinding.getRoot();
    }

    private void initComponets() {
        //construir nuestro objeto de conexion a la api
        restContriesApi = RestCountriesClient.get_instance().create(RestContriesApi.class);
        //preparar bote de basura
        compositeDisposable = new CompositeDisposable();



        //Llenar de info el spinner
            //crear un adaptador
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.continentes,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewBinding.porContinentesSpiContinentes.setAdapter(adapter);
        //configurar para obtener el valor seleccionado del spinner
        viewBinding.porContinentesSpiContinentes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PorContinenteFragment.this.onItemSelected(parent,position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(),"Seleccione un elemento",Toast.LENGTH_SHORT).show();
            }
        });

        //configurar el reclyvew
        viewBinding.porContinentesRVPaises.setLayoutManager(new LinearLayoutManager(getActivity()));

        //CONFIGURAR COMPORTAMINETO DEL FABCLEAR

        viewBinding.porContinentesFabClear.setOnClickListener((view) -> {
            viewBinding.porContinentesSpiContinentes.setSelection(0);
        });

    }

    private void onItemSelected(AdapterView<?> parent,int position){
        if(position == 0){
            setRecycleViewAdapter(new ArrayList<>());
            return;
        }
        String continente = (String) parent.getAdapter().getItem(position);
        fetchCountriesByRegion(continente);

        //Toast.makeText(getContext(), "Elemento seleccionado " + continente, Toast.LENGTH_SHORT).show();
    }

    private void fetchCountriesByRegion(String continente) {
        compositeDisposable.add(
                restContriesApi.getCountriesByRegion(continente,RestCountriesClient.FIELS_VALUES)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setRecycleViewAdapter,this::OnRequestError)
        );
    }

    private void setRecycleViewAdapter(List<Country> countries) {
        adapter = new PaisAdapter(getContext(), countries);

        viewBinding.porContinentesRVPaises.setAdapter(adapter);
    }

    private void OnRequestError(Throwable throwable) {
        setRecycleViewAdapter(new ArrayList<>());
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}