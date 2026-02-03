package com.koreait.PsnProject.service;

public interface BookmarkService {
    void toggleBookmark(Long hospitalId, Long memberId);
    boolean isBookmarked(Long hospitalId, Long memberId);
}