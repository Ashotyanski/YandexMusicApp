package com.yandexmusicapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yandexmusicapp.R;
import com.yandexmusicapp.activities.ArtistActivity;
import com.yandexmusicapp.models.Artist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.yandexmusicapp.Application.ARTIST;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {

    Context mainContext; // для Picasso
    public ArrayList<Artist> allArtists; // так как механизм фильтрации работает по принципу удаления
    // ненужных элементов, то изначальные элементы нужно где-то хранить
    public ArrayList<Artist> filteredArtists; // вывода результата поиска (фильтрованный allArtists)
    public Boolean sortedAlphabetically; //дадим пользователю всего два списка - сортирован либо по алфавиту,
    // либо по количеству песен (выводить чистый ответ с сервера,
    // в котором артисты находятся в хаотичном порядке - плохой UX)

    public ArtistAdapter(Context c){
        this.sortedAlphabetically = true;
        this.allArtists = new ArrayList<>();
        this.filteredArtists = (ArrayList<Artist>) allArtists.clone();
        this.mainContext = c;
    }

    public boolean isEmpty(){
        return filteredArtists.size() == 0;
    }

    public boolean isAbsolutelyEmpty(){
        return allArtists.size() == 0;
    }

    public void setArtists(List<Artist> artists){
        this.allArtists = (ArrayList<Artist>) artists;
        this.filteredArtists = (ArrayList<Artist>) allArtists.clone(); // иначе работа с filteredArtists влияет на allArtists
        sortArtists(false); //сразу же сортируем
        notifyDataSetChanged();
    }

    public void sortArtists(boolean change){
        // этот метод используется для двух задач - 1) сортировать по нажатию кнопки (вверху которая)
        // 2) сортировать вывод любого результата
        // 1 требует изменения флага сортировки (sortedAlphabetically) и иконки на кнопке,
        // а 2 нет, поэтому вводим флаг изменения (change)

        if(change) {
            // смотрим на флаг сортировки и меняем на противоположный, сортируя при этом
            if (!sortedAlphabetically) {
                Collections.sort(this.filteredArtists, new Comparator<Artist>() {
                    @Override
                    public int compare(Artist lhs, Artist rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
                sortedAlphabetically = true;
            } else {
                Collections.sort(this.filteredArtists, new Comparator<Artist>() {
                    @Override
                    public int compare(Artist lhs, Artist rhs) {
                        return rhs.getTracks().compareTo(lhs.getTracks());
                    }
                });
                sortedAlphabetically = false;
            }
        }else{
            // смотрим на флаг сортировки и сортируем по нему
            if (sortedAlphabetically) {
                Collections.sort(this.filteredArtists, new Comparator<Artist>() {
                    @Override
                    public int compare(Artist lhs, Artist rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                });
            } else {
                Collections.sort(this.filteredArtists, new Comparator<Artist>() {
                    @Override
                    public int compare(Artist lhs, Artist rhs) {
                        return rhs.getTracks().compareTo(lhs.getTracks());
                    }
                });
            }
        }
        notifyDataSetChanged();
    }
    public void filterArtists(String query){
        filteredArtists.clear();
        // логика тривиальна - приводим запрос и имя артиста в lowercase и, если есть совпадение, выводим
        for (Artist artist: this.allArtists) {
            if(artist.getName().toLowerCase().contains(query)){
                filteredArtists.add(artist);
            }
        }
        sortArtists(false);
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ArtistHolder(v);
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, int position) {
        holder.setIsRecyclable(false);
        //находим артиста и выводим его
        final Artist artist = filteredArtists.get(position);
        holder.name.setText(artist.getName());
        holder.genres.setText(artist.getImplodedGenres());
        holder.repertoire.setText(artist.getRepertoire());

        //Picasso.with(mainContext).setIndicatorsEnabled(true);
        Picasso.with(mainContext)
                .load(artist.getCover().getSmall()) // вставляем маленькую обложку
                .placeholder(R.drawable.artist)
                .into(holder.cover);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ArtistActivity.class);
                // передаем в следующий активити выбранного артиста
                intent.putExtra(ARTIST, artist);
                ((Activity) v.getContext()).startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredArtists.size();
    }

    class ArtistHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView name;
        TextView genres;
        TextView repertoire;

        public ArtistHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            name = (TextView) itemView.findViewById(R.id.name);
            genres = (TextView) itemView.findViewById(R.id.genres);
            repertoire = (TextView) itemView.findViewById(R.id.repertoire);
        }
    }
}