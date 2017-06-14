package com.digw.it.entity.question;

import java.io.Serializable;
import java.util.List;

/**
 * digw创建于17-6-13.
 */

public class QuestionInfo implements Serializable{
    private double score;
    private Question question;
    private int pos;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public class Question implements Serializable{
        private String content;
        private int id;
        private String title;
        private List<Answer> answer;
        private String uuid;
        private int type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Answer> getAnswer() {
            return answer;
        }

        public void setAnswer(List<Answer> answer) {
            this.answer = answer;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public class Answer implements Serializable{
            private String content;
            private int id;
            private int type;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
