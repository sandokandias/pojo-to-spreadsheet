package com.github.sandokandias.pojo.to.spreadsheet.writer;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelWriterResult {

    private List<String> filesName = new ArrayList<>();
    private List<OutputStream> streams = new ArrayList<>();

    public ExcelWriterResult() {}

    public ExcelWriterResult(String fileName) {
        this.filesName.add(fileName);
    }

    public ExcelWriterResult(OutputStream stream) {
        this.streams.add(stream);
    }

    public void compose(ExcelWriterResult response) {
        this.filesName.addAll(response.getFilesName());
        this.streams.addAll(response.getStreams());
    }

    public List<String> getFilesName() {
        return filesName;
    }

    public List<OutputStream> getStreams() {
        return streams;
    }

}
