package com.ouilift.ui.search;

import com.ouilift.model.SearchViewModel;

public interface SearchFragmentListener {
    void setViewModel(SearchViewModel viewModel);

    void setData(String rDate, int from, int to);
}
