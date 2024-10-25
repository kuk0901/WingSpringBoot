package com.edu.wing.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class Paging implements Serializable {

  // 페이지당 게시물 수, 화면마다 다르게 나올 수 있어야 함
  @Setter
  private int pageScale = 10;

  // 화면당 블럭 수
  public static final int BLOCK_SCALE = 5;

  private int curPage = 0; // 현재 페이지

  private int totPage; // 전체 페이지 수
  private int totBlock; // 전체 페이지 블럭 수

  private int curBlock; // 현재 페이지 블럭

  private int prevBlock;
  private int nextBlock;

  private int pageBegin;
  private int pageEnd;

  private int blockBegin;
  private int blockEnd;

  public Paging(int count, int curPage) {
    this.curBlock = 1;

    setTotPage(count);

    if (curPage > totPage) {
      curPage = totPage;
    }

    this.curPage = curPage;

    setPageRange();
    setTotBlock();
    setBlockRange();
  }

  public Paging(int count, int curPage, int pageScale) {
    this.pageScale = pageScale;
    this.curBlock = 1;

    setTotPage(count);

    if (curPage > totPage) {
      curPage = totPage;
    }

    this.curPage = curPage;

    setPageRange();
    setTotBlock();
    setBlockRange();
  }

  public void setBlockRange() {
    // TODO Auto-generated method stub
    curBlock = (int) Math.ceil((curPage - 1) / BLOCK_SCALE) + 1;

    blockBegin = (curBlock - 1) * BLOCK_SCALE + 1;

    blockEnd = blockBegin + BLOCK_SCALE - 1;

    if (blockEnd > totPage) {
      blockEnd = totPage;
    }

    prevBlock = (curPage == 1) ? 1 : (curBlock - 1) * BLOCK_SCALE;

    nextBlock = curBlock > totBlock ? (curBlock * BLOCK_SCALE) : (curBlock * BLOCK_SCALE) + 1;

    if (prevBlock <= 0) {
      prevBlock = 1;
    }

    if (nextBlock >= totPage) {
      nextBlock = totPage;
    }
  }

  public void setTotBlock() {
    // TODO Auto-generated method stub
    this.totBlock = (int) Math.ceil((double) totPage / (double) BLOCK_SCALE);
  }

  public void setPageRange() {
    // TODO Auto-generated method stub
    pageBegin = (curPage - 1) * pageScale + 1;
    pageEnd = pageBegin + pageScale - 1;

  }

  public void setCurPage(int curPage) {
    this.curPage = curPage;
  }


  public void setTotPage(int totPage) {
    this.totPage = (int) Math.ceil(totPage * 1.0 / pageScale);
  }

  public void setTotBlock(int totBlock) {
    this.totBlock = totBlock;
  }

  public void setCurBlock(int curBlock) {
    this.curBlock = curBlock;
  }

  public void setPrevBlock(int prevBlock) {
    this.prevBlock = prevBlock;
  }

  public void setNextBlock(int nextBlock) {
    this.nextBlock = nextBlock;
  }

  public void setPageBegin(int pageBegin) {
    this.pageBegin = pageBegin;
  }

  public void setPageEnd(int pageEnd) {
    this.pageEnd = pageEnd;
  }

  public void setBlockBegin(int blockBegin) {
    this.blockBegin = blockBegin;
  }

  public void setBlockEnd(int blockEnd) {
    this.blockEnd = blockEnd;
  }

}
