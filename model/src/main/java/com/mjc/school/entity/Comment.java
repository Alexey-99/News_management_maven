package com.mjc.school.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * The type Comment.
 */
@Entity
@Table(name = "comments",
        schema = "news_management")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "content", nullable = false, length = 255)
    private String content;
    @ManyToOne
    @JoinColumn(name = "news_id")
    private long newsId;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "modified", nullable = false)
    private LocalDateTime modified;

    /**
     * Get id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get news id.
     *
     * @return the news id
     */
    public long getNewsId() {
        return newsId;
    }

    /**
     * Set news id.
     *
     * @param newsId the news id
     */
    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    /**
     * Get created.
     *
     * @return the created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Set created.
     *
     * @param created the created
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * Get modified.
     *
     * @return the modified
     */
    public LocalDateTime getModified() {
        return modified;
    }

    /**
     * Set modified.
     *
     * @param modified the modified
     */
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }


    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + (this.content != null ? this.content.hashCode() : 1);
        result = result * PRIME + Long.hashCode(this.newsId);
        result = result * PRIME + (this.created != null ? this.created.hashCode() : 1);
        result = result * PRIME + (this.modified != null ? this.modified.hashCode() : 1);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        Comment otherComment = (Comment) object;
        if (this.id != otherComment.id) {
            return false;
        }
        if (this.content == null) {
            if (otherComment.content != null) {
                return false;
            }
        } else if (!this.content.equals(otherComment.content)) {
            return false;
        }
        if (this.newsId != otherComment.newsId) {
            return false;
        }
        if (this.created == null) {
            if (otherComment.created != null) {
                return false;
            }
        } else if (!this.created.equals(otherComment.created)) {
            return false;
        }
        if (this.modified == null) {
            if (otherComment.modified != null) {
                return false;
            }
        } else if (!this.modified.equals(otherComment.modified)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("id=").append(id);
        sb.append(", content='").append(content).append('\'');
        sb.append(", newsId=").append(newsId);
        sb.append(", created=").append(created);
        sb.append(", modified=").append(modified);
        sb.append('}');
        return sb.toString();
    }

    /**
     * The type Comment builder.
     */
    public static class CommentBuilder {
        private final Comment comment;

        /**
         * Instantiates a new Comment builder.
         */
        public CommentBuilder() {
            this.comment = new Comment();
        }

        /**
         * Set id.
         *
         * @param id the id
         * @return the id
         */
        public CommentBuilder setId(long id) {
            this.comment.setId(id);
            return this;
        }

        /**
         * Set content.
         *
         * @param content the content
         * @return the content
         */
        public CommentBuilder setContent(String content) {
            this.comment.setContent(content);
            return this;
        }

        /**
         * Set news id.
         *
         * @param newsId the news id
         * @return the news id
         */
        public CommentBuilder setNewsId(long newsId) {
            this.comment.setNewsId(newsId);
            return this;
        }

        /**
         * Set created.
         *
         * @param created the created
         * @return the created
         */
        public CommentBuilder setCreated(LocalDateTime created) {
            this.comment.setCreated(created);
            return this;
        }

        /**
         * Set modified.
         *
         * @param modified the modified
         * @return the modified
         */
        public CommentBuilder setModified(LocalDateTime modified) {
            this.comment.setModified(modified);
            return this;
        }

        /**
         * Build comment.
         *
         * @return the comment
         */
        public Comment build() {
            return this.comment;
        }
    }
}