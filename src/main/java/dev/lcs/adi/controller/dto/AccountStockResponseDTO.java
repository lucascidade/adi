package dev.lcs.adi.controller.dto;

public record AccountStockResponseDTO(String stockId,
                                      int quantity,
                                      double total) {
}
