.PHONY: help build test format lint dev-check

help:
	@echo "Available targets:"
	@echo "  build      - Build the project"
	@echo "  test       - Run tests"
	@echo "  format     - Auto-format code (Spotless)"
	@echo "  lint       - Run linting checks (Spotless check + Checkstyle)"
	@echo "  dev-check  - Full development check (build + test + lint + coverage)"

build:
	./gradlew build

test:
	./gradlew test

format:
	./gradlew format

lint:
	./gradlew lint

dev-check:
	./gradlew check
