package ssafy.narou.pjt.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.repository.article.ArticleRepository;
import ssafy.narou.pjt.article.repository.feed.FeedRepository;
import ssafy.narou.pjt.member.repository.MemberRepository;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class FeedService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final FeedRepository feedRepository;

//    public void save(NewFeedRequest newFeedRequest){
//
//        Feed feedEntity = newFeedRequest.toEntity();
//        feedRepository.save(feedEntity);
//
//    }



}
