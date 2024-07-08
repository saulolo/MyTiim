package co.com.template.Repositories;

import co.com.template.Repositories.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByCommentId(Long commentId);

    List<Comment> findTop20ByObjectiveObjectiveIdOrderByCommentDateDesc(Long objectiveId);


    List<Comment> findByCommentCommentTypeCommentTypeIdOrderByCommentDateDesc(Long typeId);






}

