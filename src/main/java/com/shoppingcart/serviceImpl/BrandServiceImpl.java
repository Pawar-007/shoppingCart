package com.shoppingcart.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.model.Brand;
import com.shoppingcart.repository.BrandRepository;
import com.shoppingcart.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Long id, Brand brand) {

        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Brand not found with id: " + id));

        if (brand.getBrandName() != null) {
            existingBrand.setBrandName(brand.getBrandName());
        }

        if (brand.getDescription() != null) {
            existingBrand.setDescription(brand.getDescription());
        }

        if (brand.getLogoUrl() != null) {
            existingBrand.setLogoUrl(brand.getLogoUrl());
        }

        return brandRepository.save(existingBrand);
    }

    @Override
    public void deleteBrand(Long id) {

        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));

        brandRepository.delete(existingBrand);
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrand(Long id) {

        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
    }
}
