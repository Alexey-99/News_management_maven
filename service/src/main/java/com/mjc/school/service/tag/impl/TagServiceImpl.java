package com.mjc.school.service.tag.impl;

import com.mjc.school.entity.Pagination;
import com.mjc.school.entity.Tag;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.repository.tag.TagRepository;
import com.mjc.school.service.tag.TagService;
import com.mjc.school.validation.TagValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_PART_OF_TAG_NAME;
import static org.apache.logging.log4j.Level.ERROR;

/**
 * The type Tag service.
 */
@Service
public class TagServiceImpl implements TagService {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TagValidator tagValidator;
    @Autowired
    private PaginationService<Tag> tagPagination;

    /**
     * Create tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws ServiceException            the service exception
     */
    @Override
    public boolean create(Tag tag)
            throws IncorrectParameterException, ServiceException {
        try {
            return tagValidator.validate(tag) &&
                    tagRepository.create(tag);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Add tag to news.
     *
     * @param tagId  the tag id
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean addToNews(long tagId, long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            return (tagValidator.validateId(tagId) &&
                    tagValidator.validateId(newsId)) &&
                    (tagRepository.findById(tagId) != null
                            && newsRepository.findById(newsId) != null) &&
                    tagRepository.addToNews(tagId, newsId);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Remove tag from news.
     *
     * @param tagId  the tag id
     * @param newsId the news id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean removeTagFromNews(long tagId, long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            return (tagValidator.validateId(tagId) &&
                    tagValidator.validateId(newsId)) &&
                    tagRepository.removeTagFromNews(tagId, newsId);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Delete tag by id.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean deleteById(long tagId)
            throws ServiceException, IncorrectParameterException {
        try {
            if (tagValidator.validateId(tagId)) {
                tagRepository.deleteByTagIdFromTableTagsNews(tagId);
                tagRepository.deleteById(tagId);
                return tagRepository.findById(tagId) == null;
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Delete tag by id from table tags news.
     *
     * @param tagId the tag id
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean deleteByTagIdFromTableTagsNews(long tagId)
            throws ServiceException, IncorrectParameterException {
        try {
            return tagValidator.validateId(tagId) &&
                    tagRepository.deleteByTagIdFromTableTagsNews(tagId);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Update tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public boolean update(Tag tag)
            throws ServiceException, IncorrectParameterException {
        try {
            return tagValidator.validateId(tag.getId()) &&
                    tagValidator.validate(tag) &&
                    tagRepository.update(tag);
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Find all tags.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    @Override
    public List<Tag> findAll() throws ServiceException {
        try {
            return tagRepository.findAllTags();
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Find tag by id.
     *
     * @param id the id
     * @return the tag
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public Tag findById(long id)
            throws ServiceException, IncorrectParameterException {
        try {
            return tagValidator.validateId(id) ?
                    tagRepository.findById(id) : null;
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Find tags by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public List<Tag> findByPartOfName(String partOfName)
            throws ServiceException, IncorrectParameterException {
        try {
            if (partOfName != null) {
                String pattern = partOfName.toLowerCase();
                Pattern p = Pattern.compile(pattern);
                return tagRepository.findAllTags()
                        .stream()
                        .filter(tag -> {
                            String tagName = tag.getName().toLowerCase();
                            return (p.matcher(tagName).find())
                                    || (p.matcher(tagName).lookingAt())
                                    || (tagName.matches(pattern));
                        })
                        .toList();
            } else {
                throw new IncorrectParameterException(
                        BAD_PARAMETER_PART_OF_TAG_NAME);
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }

    }

    /**
     * Find tags by news id.
     *
     * @param newsId the news id
     * @return the list
     * @throws ServiceException            the service exception
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    @Override
    public List<Tag> findByNewsId(long newsId)
            throws ServiceException, IncorrectParameterException {
        try {
            return tagValidator.validateId(newsId) ?
                    tagRepository.findByNewsId(newsId) :
                    new ArrayList<>();
        } catch (RepositoryException e) {
            log.log(ERROR, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Get objects from list.
     *
     * @param list                 the list
     * @param numberElementsReturn the number elements return
     * @param numberPage           the number page
     * @return the entity
     */
    @Override
    public Pagination<Tag> getPagination(List<Tag> list, long numberElementsReturn, long numberPage) {
        return tagPagination.getPagination(list, numberElementsReturn, numberPage);
    }
}