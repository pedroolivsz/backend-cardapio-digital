package com.io.github.pedroolivsz.cardapio.DTO;

import java.util.List;

public record CategoryMenuDTO(Long categoryId,
                              String categoryName,
                              List<FoodMenuDTO> foods) {
}
