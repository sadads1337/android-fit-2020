package ru.nsu.fit.lesson13;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import ru.nsu.fit.lesson13.db.Concert;
import ru.nsu.fit.lesson13.db.ConcertDao;

public class ConcertViewModel extends ViewModel {
    public final LiveData<PagedList<Concert>> concertList;

    public ConcertViewModel() {
        final ConcertDao concertDao = App.getInstance().getDatabase().concertDao();
        concertList = new LivePagedListBuilder<>(concertDao.concerts(), 10).build();
    }

}
