package co.com.template.services;

import co.com.template.Repositories.*;

import co.com.template.Repositories.dto.CreateCommentFeedbackDTO;
import co.com.template.Repositories.dto.FeedbackCommentDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.*;
import co.com.template.utils.Constants;
import co.com.template.utils.StatusEnum;
import co.com.template.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import co.com.template.utils.EmailServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
@Log4j2
public class CommentFeedbackService {

    private final EmailServiceImpl emailService;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final StatusRepository statusRepository;

    private final CommentFeedbackRepository commentFeedbackRepository;

    private final CommentUserRepository commentUserRepository;

    private final ConfigurationSystemService configurationService;



    public ResponseDTO setCommentFeedback(CreateCommentFeedbackDTO createCommentFeedbackDTO)   {
        try{

            Comment comment =  commentRepository.findByCommentId(createCommentFeedbackDTO.getCommentId());
            User user = userRepository.findByUserId(createCommentFeedbackDTO.getUserId());
            Status status = statusRepository.findByStatusId(StatusEnum.ACTIVE_COMMENT.getId());


            CommentFeedback commentFeedback = new CommentFeedback();

            commentFeedback.setComment(comment);
            commentFeedback.setUserFrom(user);
            commentFeedback.setCommentFeedbackDescribe(createCommentFeedbackDTO.getCommentFeedbackDescribe());
            commentFeedback.setStatus(status);
            commentFeedback.setCommentFeedbackType(comment.getCommentType());

            commentFeedbackRepository.save(commentFeedback);


            Map<String, Object> data = new HashMap<>();
            data.put(Constants.EMAIL_NAME, commentFeedback.getUserFrom().getUserName());
            data.put(Constants.EMAIL_LASTNAME, commentFeedback.getUserFrom().getUserLastName());
            data.put(Constants.EMAIL_URL, configurationService.getConfigValue(Constants.URL_SYSTEM)+Constants.EMAIL_FEEDBACK_URL);
            data.put(Constants.EMAIL_DATE, Util.convertToDateTimeHourFormatted(commentFeedback.getCommentFeedbackDate(),Constants.DATETIME_FORMAT));
            data.put(Constants.EMAIL_DESCRIBE, commentFeedback.getCommentFeedbackDescribe());
            data.put(Constants.EMAIL_COMMENT_TYPE, comment.getCommentCommentType().getCommentTypeDescribe());
            data.put(Constants.EMAIL_IMAGE_URL, configurationService.getConfigValue(Constants.IMAGE_URL_SYSTEM));
            emailService.sendMail(data, comment.getUserFrom().getUserEmail(), Constants.SUBJECT_MESSAGE + comment.getUserFrom().getUserName(), Constants.FEEDBACK_TEMPLATE, null);

            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, createCommentFeedbackDTO);
        }catch(Exception err){
            log.error(err.getMessage(), err);
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);

        }
    }

    public ResponseDTO getCommentFeedback(Long CommentCommentId) {

        Comment comment = commentRepository.findByCommentId(CommentCommentId);
        List<CommentFeedback> com = commentFeedbackRepository.findByComment(comment);

        List<FeedbackCommentDTO> feedbackCommentDTO = new ArrayList<>();



        for (CommentFeedback c : com) {
            FeedbackCommentDTO feedback = new FeedbackCommentDTO();


            feedback.setCommentFeedbackId(c.getCommentFeedbackId());
            feedback.setUserId(c.getUserFrom().getUserId());
            feedback.setUserName(c.getUserFrom().getUserName());
            feedback.setUserLastName(c.getUserFrom().getUserLastName());
            String date = Util.convertToDateTimeHourFormatted(c.getCommentFeedbackDate(), Constants.DATETIME_FORMAT);
            feedback.setCommentFeedbackType(comment.getCommentCommentType().getCommentTypeId());
            feedback.setCommentFeedbackDate(date);
            feedback.setCommentFeedbackDescribe(c.getCommentFeedbackDescribe());


           feedbackCommentDTO.add(feedback);

        }

        return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, feedbackCommentDTO);

    }


}
