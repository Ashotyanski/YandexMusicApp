package com.yandexmusicapp;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.yandexmusicapp.adapters.ArtistAdapter;
import com.yandexmusicapp.models.Artist;
import com.yandexmusicapp.utils.CacheUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AdapterTest extends AndroidTestCase {

    List<Artist> artists;
    ArtistAdapter artistAdapter;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // 3 dummy исполнителя, заносим в кэш и тестируем на них
        String test = "[{\"id\":1080505,\"name\":\"Tove Lo\",\"genres\":[\"pop\",\"dance\",\"electronics\"],\"tracks\":381,\"albums\":22,\"link\":\"http://www.tove-lo.com/\",\"description\":\"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}}," +
                "{\"id\":2915,\"name\":\"Ne-Yo\",\"genres\":[\"rnb\",\"pop\",\"rap\"],\"tracks\":256,\"albums\":152,\"link\":\"http://www.neyothegentleman.com/\",\"description\":\"обладатель трёх премии Грэмми, американский певец, автор песен, продюсер, актёр, филантроп. В 2009 году журнал Billboard поставил Ни-Йо на 57 место в рейтинге «Артисты десятилетия».\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/1000x1000\"}}," +
                "{\"id\":91546,\"name\":\"Usher\",\"genres\":[\"rnb\",\"pop\",\"rap\"],\"tracks\":450,\"albums\":183,\"link\":\"http://usherworld.com/\",\"description\":\"американский певец и актёр. Один из самых коммерчески успешных R&B-музыкантов афроамериканского происхождения. В настоящее время продано более 65 миллионов копий его альбомов по всему миру. Выиграл семь премий «Грэмми», четыре премии World Music Awards, четыре премии American Music Award и девятнадцать премий Billboard Music Awards. Владелец собственной звукозаписывающей компании US Records. Он занимает 21 место в списке самых успешных музыкантов по версии Billboard, а также второе место, уступив Эминему в списке самых успешных музыкантов 2000-х годов. В 2010 году журнал Glamour включил его в список 50 самых сексуальных мужчин.\",\"cover\":{\"small\":\"http://avatars.yandex.net/get-music-content/b0e14f75.p.91546/300x300\",\"big\":\"http://avatars.yandex.net/get-music-content/b0e14f75.p.91546/1000x1000\"}}]";
        CacheUtils.setCache(test);
    }

    @Test
    public void checkArtists_simple() {
        artistAdapter = new ArtistAdapter(getContext());
        artists = CacheUtils.getCachedArtists();
        artistAdapter.setArtists(artists);
        assertEquals(3, artistAdapter.getItemCount());
    }

    @Test
    public void checkArtists_sort() {
        artistAdapter = new ArtistAdapter(getContext());
        artists = CacheUtils.getCachedArtists();
        artistAdapter.setArtists(artists);
        assertTrue(artistAdapter.sortedAlphabetically);
        artistAdapter.sortArtists(true);
        assertFalse(artistAdapter.sortedAlphabetically);
        assertEquals(Integer.valueOf(450), artistAdapter.filteredArtists.get(0).getTracks());
    }

    @Test
    public void checkArtists_filter() {
        artistAdapter = new ArtistAdapter(getContext());
        artists = CacheUtils.getCachedArtists();
        artistAdapter.setArtists(artists);
        artistAdapter.filterArtists("o");
        assertEquals(2, artistAdapter.filteredArtists.size());
    }

    @Test
    public void checkArtists_filterAndSort() {
        artistAdapter = new ArtistAdapter(getContext());
        artists = CacheUtils.getCachedArtists();
        artistAdapter.setArtists(artists);
        artistAdapter.sortArtists(true);
        assertFalse(artistAdapter.sortedAlphabetically);
        artistAdapter.filterArtists("o");
        assertEquals(Integer.valueOf(256), artistAdapter.filteredArtists.get(0).getTracks());
        assertEquals(2, artistAdapter.filteredArtists.size());
    }
}