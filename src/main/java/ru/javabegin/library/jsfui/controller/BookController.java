package ru.javabegin.library.jsfui.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.javabegin.library.dao.BookDao;
import ru.javabegin.library.dao.GenreDao;
import ru.javabegin.library.domain.Book;
import ru.javabegin.library.jsfui.enums.SearchType;
import ru.javabegin.library.jsfui.model.LazyDataTable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean
@SessionScoped
@Component
@Getter @Setter
@Log
public class BookController extends ru.javabegin.library.jsfui.controller.AbstractController<Book> {

    public static final int DEFAULT_PAGE_SIZE = 20;// по-умолчанию сколько книг отображать на странице

    // из JSF таблицы обязательно должна быть ссылки на переменные, иначе при использовании постраничности dataGrid работает некорректно (не отрабатывает bean)
    // также - выбранное пользователем значение (кол-во записей на странице) будет сохраняться
    private int rowsCount = DEFAULT_PAGE_SIZE;
    public static final int TOP_BOOKS_LIMIT = 5;// сколько показывать популярных книг

    private SearchType searchType; // запоминает последний выбранный вариант поиска


    @Autowired
    private BookDao bookDao;// будет автоматически подставлен BookService, т.к. Spring контейнер по-умолчанию ищет бин-реализацию по типу

    @Autowired
    private GenreDao genreDao;

    private LazyDataTable<Book> lazyModel; // класс-утилита, которая помогает выводить данные постранично (работает в паре с компонентами на странице JSF)

    private Page<Book> bookPages; //хранит список найденных книг
    private List<Book> topBooks;// хранит полученные ТОП книги (может использоваться наприемр для получения изображений книги)

    private String searchText; // текст поиска

    private long selectedGenreId; // хранит выбранный жанр (при поиске книг по жанру)


    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable(this);
    }




    // метод автоматически вызывается из LazyDataTable
    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {


        if (sortField == null) {
            sortField = "name";
        }

        if (searchType == null){
            bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
        }else {

            switch (searchType) {
                case SEARCH_GENRE:
                    bookPages = bookDao.findByGenre(pageNumber, pageSize, sortField, sortDirection, selectedGenreId);
                    break;
                case SEARCH_TEXT:
                    break;
                case ALL:
                    bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
                    break;

            }
        }


        return bookPages;
    }


    public List<Book> getTopBooks() {
        topBooks = bookDao.findTopBooks(TOP_BOOKS_LIMIT);
        return topBooks;
    }


    // поиск по определенному жанру
    public void showBooksByGenre(long selectedGenreId){
        searchType = SearchType.SEARCH_GENRE; //
        this.selectedGenreId = selectedGenreId;
    }

    public void showAll(){
        searchType = SearchType.ALL;
    }


    public String getSearchMessage(){

        ResourceBundle bundle = ResourceBundle.getBundle("library", FacesContext.getCurrentInstance().getViewRoot().getLocale());


        String message=null;

        if (searchType==null){
            return null;
        }
        switch (searchType) {
            case SEARCH_GENRE:
                message = bundle.getString("genre")+ ": '"+genreDao.get(selectedGenreId)+"'";
                break;
            case SEARCH_TEXT:

                if (searchText==null || searchText.trim().length()==0){
                    return null;
                }

                message = bundle.getString("search")+ ": '"+searchText+"'";
                break;
        }

        return message;
    }

}
