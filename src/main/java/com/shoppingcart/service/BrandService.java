package com.shoppingcart.service;

import java.util.List;

import com.shoppingcart.model.Brand;

public interface BrandService {

    Brand saveBrand(Brand brand);

    Brand updateBrand(Long id,
                      Brand brand);

    void deleteBrand(Long id);

    List<Brand> getAllBrands();

    Brand getBrand(Long id);

}