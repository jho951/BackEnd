package src.domain.product.service;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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

	@Transactional
	@Override
	public ProductResponse.ProductUpdateResponse update(ProductRequest.ProductUpdateRequest dto) {
		try {
			Product updateProduct = productRepository.findById(dto.getId())
				.orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_SAMPLE_DATA_ID));
			updateProduct.update(dto);
			return ProductResponse.ProductUpdateResponse.from(updateProduct);
		}catch (DataIntegrityViolationException e) {
			throw new GlobalException(ErrorCode.BAD_REQUEST_PRODUCT_DATA);
		} catch (PersistenceException e) {
			throw new GlobalException(ErrorCode.INVALID_REQUEST_DATA);
		} catch (ObjectOptimisticLockingFailureException e) {
			throw new GlobalException(ErrorCode.CONFLICT_SAMPLE_DATA);
		}
	}
}
