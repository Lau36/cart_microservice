package com.example.cart_microservice.domain.utils.paginationitems;

import com.example.cart_microservice.domain.utils.Filter;
import com.example.cart_microservice.domain.utils.SortDirection;

public class ItemsInCartPaginationRequest {
        private int page;
        private int size;
        private SortDirection sortDirection;
        private Filter filter;
        private String filterName;

        public ItemsInCartPaginationRequest(int page, int size, SortDirection
        sortDirection, Filter filter, String filterName) {
        this.page = page;
        this.size = size;
        this.sortDirection = sortDirection;
        this.filter = filter;
        this.filterName = filterName;
    }

        public int getPage() {
        return page;
    }

        public int getSize() {
        return size;
    }

        public SortDirection getSortDirection() {
        return sortDirection;
    }

        public Filter getFilter() {
        return filter;
    }

        public String getFilterName() {
        return filterName;
    }
}
