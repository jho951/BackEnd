package src.domain.product.service;

import src.domain.product.dto.ProductRequest;
import src.domain.product.dto.ProductResponse;

public interface ProductService {
	ProductResponse.ProductCreateResponse create(ProductRequest.ProductCreateRequest dto);
}
