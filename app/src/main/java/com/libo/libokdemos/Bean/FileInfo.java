package com.libo.libokdemos.Bean;

import java.io.Serializable;

/**
 * Created by libok on 2018-02-02.
 */

public class FileInfo implements Serializable {
    private int id;
    private String url;
    private String fileName;
    private long length;
    private long finishedLength;

    public FileInfo() {
    }

    public FileInfo(int id, String url, String fileName, long length, long finishedLength) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finishedLength = finishedLength;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getFinishedLength() {
        return finishedLength;
    }

    public void setFinishedLength(long finishedLength) {
        this.finishedLength = finishedLength;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finishedLength=" + finishedLength +
                '}';
    }
}
