package ssafy.narou.pjt.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.entity.HashTag;
import ssafy.narou.pjt.article.repository.HashTagRepository;

import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public Optional<HashTag> findByTagName(String tagName){
        return hashTagRepository.findByTagName(tagName);
    }

    public HashTag save(String tagName){
        return hashTagRepository.save(
                HashTag.builder()
                        .tagName(tagName)
                        .build());
    }

}
