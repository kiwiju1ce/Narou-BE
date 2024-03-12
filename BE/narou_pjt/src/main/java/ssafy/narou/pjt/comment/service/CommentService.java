package ssafy.narou.pjt.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.article.entity.Article;
import ssafy.narou.pjt.article.repository.article.ArticleRepository;
import ssafy.narou.pjt.comment.dto.request.CommentRequestDto;
import ssafy.narou.pjt.comment.dto.response.CommentResponseDto;
import ssafy.narou.pjt.comment.entity.Comment;
import ssafy.narou.pjt.comment.exception.CustomException;
import ssafy.narou.pjt.comment.repository.CommentRepository;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public ResponseEntity<?> createComment(Long articleId, Long userId, CommentRequestDto commentRequestDto){

        Optional<Member> targetMember = memberRepository.findById(userId);

        Optional<Article> targetArticle = articleRepository.findById(articleId);

        Comment comment = Comment.builder()
                .member(targetMember.get())
                .article(targetArticle.get())
                .content(commentRequestDto.getContent())
                .deleted(0)
                .build();

        commentRepository.save(comment);

//        return ResponseEntity.ok(CommentResponseDto.builder()
//                .comment(comment)
//                .build());
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    public ResponseEntity<?> updateComment(Long commentId, Long userId, CommentRequestDto commentRequestDto) {

        String commentContent = commentRequestDto.getContent();

        Optional<Comment> findComment = commentRepository.findById(commentId);

        Optional<Member> member = memberRepository.findById(userId);

        // 수정하려는 사람이랑 쓴 사람이 다르면
        if(!findComment.get().getMember().equals(member.get())){
            throw new CustomException();
        }

        findComment.get().update(commentContent);

//        return ResponseEntity.ok().body(
//                CommentResponseDto.builder()
//                        .comment(findComment.get())
//                        .build()
//        );
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    public List<CommentResponseDto> readComment(Long articleId){

        List<CommentResponseDto> commentList = new ArrayList<>();

//        commentList=commentRepository.findAllByArticleIdOrderByCreatedTimeDesc(articleId)

        commentList=commentRepository.findCommentsWithMemberInfoByArticleId(articleId)
                .stream()
//                .filter(result->{
//                    Comment comment = (Comment) result[0];
//                    return comment.getDeleted() == 0;
//                })
//                .map(CommentResponseDto::new)
                .map(result -> {
                    Comment comment = (Comment) result[0];
                    String nickname = (String) result[1];
                    String profileImage = (String) result[2];
                    return CommentResponseDto.builder()
                            .comment(comment)
                            .nickname(nickname)
                            .profileImage(profileImage)
                            .build();
                })
                .collect(Collectors.toList());

        return commentList;
    }

    public ResponseEntity<String> deleteComment(Long commentId, Long userId) {

        Optional<Comment> findComment = commentRepository.findById(commentId);

        Optional<Member> member = memberRepository.findById(userId);

        // 삭제하려는 사람이랑 쓴 사람이 다르면
        if(findComment.get().getMember()!=member.get()){
            throw new CustomException();
        }

        findComment.get().delete();

        // commentRepository.delete(findComment.get());

        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

}

