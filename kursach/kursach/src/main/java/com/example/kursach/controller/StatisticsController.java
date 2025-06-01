package com.example.kursach.controller;

import com.example.kursach.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/sales-by-cloth-size/{clothId}")
    public ResponseEntity<Map<String, Integer>> getSalesByClothSize(@PathVariable Integer clothId) {
        Map<String, Integer> salesData = statisticsService.getSalesByClothSize(clothId);
        return ResponseEntity.ok(salesData);
    }

    @GetMapping("/top-selling-overall")
    public ResponseEntity<List<Map<String, Object>>> getTopSellingOverall() {
        List<Map<String, Object>> topSelling = statisticsService.getTopSellingOverall();
        return ResponseEntity.ok(topSelling);
    }

    @GetMapping("/least-selling-overall")
    public ResponseEntity<List<Map<String, Object>>> getLeastSellingOverall() {
        List<Map<String, Object>> leastSelling = statisticsService.getLeastSellingOverall();
        return ResponseEntity.ok(leastSelling);
    }
}