package com.digw.it.entity.question;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * digw创建于17-6-13.
 */

public class SectionTitle extends SectionEntity<GroupTitle.SubTitle> {

    public SectionTitle(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionTitle(GroupTitle.SubTitle subTitle) {
        super(subTitle);
    }
}
