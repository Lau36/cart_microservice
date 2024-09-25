package com.example.cart_microservice.infrastructure.adapters.input.controller.dto.request;

import com.example.cart_microservice.domain.utils.Filter;
import com.example.cart_microservice.infrastructure.adapters.input.controller.utils.SortDirection;


public class PaginationRequest {
        private int page;
        private int size;
        private String sort;
        private SortDirection sortDirection;
        private Filter filter;
        private String filterName;

        public PaginationRequest(int page, int size, String sort, SortDirection sortDirection, Filter filter, String filterName) {
            this.page = page;
            this.size = size;
            this.sort = sort;
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

        public String getSort() {
            return sort;
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
