package com.digw.it.entity.question;

import java.util.ArrayList;
import java.util.List;

/**
 * digw创建于17-6-4.
 */

public class GroupTitle {
    private int id;
    private String name;
    private List<SubTitle> childData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubTitle> getChildData() {
        return childData;
    }

    public void setChildData(ArrayList<SubTitle> childData) {
        this.childData = childData;
    }

    public class SubTitle {
        private String name;
        private int id;
        private int questionNum;
        private int parentId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuestionNum() {
            return questionNum;
        }

        public void setQuestionNum(int questionNum) {
            this.questionNum = questionNum;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }
    }
}
