package kr.easw.lesson06.controller;


import kr.easw.lesson06.model.dto.TextDataDto;
import kr.easw.lesson06.service.TextDataService;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/v1/data")


public class DataRestEndpoint {
    private final TextDataService textDataService;


    @GetMapping({"/list"})
    public List<TextDataDto> listText() {
        return this.textDataService.listText();
    }

    public DataRestEndpoint(final TextDataService textDataService) {
        this.textDataService = textDataService;
    }
}
