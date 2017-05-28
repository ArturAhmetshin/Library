package ru.javabegin.library.jsfui.controller;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.javabegin.library.dao.GenreDao;
import ru.javabegin.library.domain.Genre;
import ru.javabegin.library.jsfui.model.LazyDataTable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
@Getter
@Setter
public class GenreController extends AbstractController<Genre> {


    // из JSF таблицы обязательно должна быть ссылки на переменные, иначе при использовании постраничности dataTable работает некорректно
    // также - выбранное пользователем значение (кол-во записей на странице) будет сохраняться
    private int rowsCount = 20;
    private int first;

    @Autowired
    private GenreDao genreDao;

    private Genre selectedGenre;

    private LazyDataTable<Genre> lazyModel;

    private Page<Genre> genrePages;


    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable(this);
    }


    @Override
    public Page<Genre> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return genrePages;
    }

    // вызывается для отображения всех жанров слева на странице
    public List<Genre> getAll() {
        return genreDao.getAll(new Sort(Sort.Direction.ASC, "name"));
    }


}
