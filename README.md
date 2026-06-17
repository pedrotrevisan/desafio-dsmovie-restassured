# Desafio DSMovie RestAssured — Testes de API

Projeto desenvolvido como desafio do curso **Java Spring Expert** da [DevSuperior](https://devsuperior.com.br), com foco na implementação de testes de API utilizando RestAssured.

## Sobre o projeto

Testes de API do sistema DSMovie (filmes e avaliações) implementados com RestAssured. Os testes verificam os contratos HTTP dos endpoints de filmes e avaliações, incluindo cenários de autorização, validação e não encontrado.

## Tecnologias utilizadas

- Java
- Spring Boot 3
- RestAssured 5
- JUnit 5
- Maven

## Testes implementados

### MovieControllerRA (7 testes)
- `findAllShouldReturnOkWhenMovieNoArgumentsGiven` → 200
- `findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty` → 200 filtrado
- `findByIdShouldReturnMovieWhenIdExists` → 200
- `findByIdShouldReturnNotFoundWhenIdDoesNotExist` → 404
- `insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle` → 422
- `insertShouldReturnForbiddenWhenClientLogged` → 403
- `insertShouldReturnUnauthorizedWhenInvalidToken` → 401

### ScoreControllerRA (3 testes)
- `saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist` → 404
- `saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId` → 422
- `saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero` → 422

## Como executar

> ⚠️ Este projeto é um **runner de testes** — exige que o projeto DSMovie base esteja rodando na porta 8080.

```bash
# 1. Sobe o projeto base DSMovie na porta 8080
# 2. Clone e execute os testes:
git clone https://github.com/pedrotrevisan/desafio-dsmovie-restassured.git
cd desafio-dsmovie-restassured
./mvnw test
```

## Autor

**Pedro Henrique Trevisan**

[![GitHub](https://img.shields.io/badge/GitHub-pedrotrevisan-black)](https://github.com/pedrotrevisan)
