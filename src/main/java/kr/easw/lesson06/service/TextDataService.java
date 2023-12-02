package kr.easw.lesson06.service;

import java.util.List;
import kr.easw.lesson06.model.dto.TextDataDto;
import kr.easw.lesson06.model.repository.TextDataRepository;
import org.springframework.stereotype.Service;

@Service
public class TextDataService {
    private final TextDataRepository userDataRepository;

    public void addText(TextDataDto dto) {
        System.out.println("Adding text");
        this.userDataRepository.saveAndFlush(dto);
    }

    public List<TextDataDto> listText() {
        return this.userDataRepository.findAll();
    }

    public TextDataService(final TextDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }
}
