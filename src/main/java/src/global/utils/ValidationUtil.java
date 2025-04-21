package src.global.utils;

public final class ValidationUtil {
	private ValidationUtil() {
		throw new UnsupportedOperationException("이 유틸 클래스는 인스턴스화할 수 없습니다.");
	}
	public static void requireNonNull(Object obj, String fieldName) {
		if (obj == null) {
			throw new IllegalArgumentException(fieldName + "는 null일 수 없습니다.");
		}
	}
}