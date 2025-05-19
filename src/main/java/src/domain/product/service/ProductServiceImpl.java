package src.domain.product.service;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.PersistenceException;

import src.domain.product.entity.Product;
import src.global.constant.code.ErrorCode;
import src.global.exception.GlobalException;
import src.domain.product.dto.ProductRequest;
import src.domain.product.dto.ProductResponse;
import src.domain.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	@Transactional
	@Override
	public ProductResponse.ProductCreateResponse create(ProductRequest.ProductCreateRequest dto) {
		try {
			Product createProduct = productRepository.save(dto.toCreateEntity());
			return ProductResponse.ProductCreateResponse.from(createProduct);
		} catch (DataIntegrityViolationException e) {
			throw new GlobalException(ErrorCode.BAD_REQUEST_PRODUCT_DATA);
		} catch (PersistenceException e) {
			throw new GlobalException(ErrorCode.INVALID_REQUEST_DATA);
		}
	}
}
